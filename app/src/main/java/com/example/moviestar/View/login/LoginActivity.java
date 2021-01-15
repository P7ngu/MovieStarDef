package com.example.moviestar.View.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviestar.Controllers.CurrentUser;
import com.example.moviestar.Controllers.LoginController;
import com.example.moviestar.Controllers.PopupController;
import com.example.moviestar.R;
import com.example.moviestar.View.MainActivity;
import com.example.moviestar.View.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity  {
    Context mContext = this;
    EditText emailEditText, passwordEditText;
    TextView forgotPassword;
    Button accediButton, accedi2Button, registratiButton;
    ImageView logo;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("Users");



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Intent intent = getIntent();

        firebaseAuth=FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.editText_email_login);
        passwordEditText = findViewById(R.id.editText_password_login);
        forgotPassword = findViewById(R.id.textView_forgotpassword_login);

        accediButton = findViewById(R.id.button_login_login);
        accedi2Button = findViewById(R.id.button3);
        registratiButton = findViewById(R.id.button_registrati_login);

        accediButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Test", "bottone");

                //LoginController.login(emailEditText.getText().toString().trim(), passwordEditText.getText().toString().trim(), mContext);
                loginEmailPasswordUser(emailEditText.getText().toString().trim(), passwordEditText.getText().toString().trim());
            }
        });

        registratiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RegistrazioneActivity.class);
                startActivity(intent);
            }
        });

        TextView forgotPasswordTV=findViewById(R.id.textView_forgotpassword_login);
        forgotPasswordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loginEmailPasswordUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    assert user !=null;
                    final String currentuserid= user.getUid();

                    collectionReference
                            .whereEqualTo("userID", currentuserid)
                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if(error!=null){
                                        PopupController.mostraPopup("Errore durante il login", error.toString() , mContext);
                                    }
                                    assert value !=null;
                                    if(!value.isEmpty()){
                                        for(QueryDocumentSnapshot snapshot : value) {
                                            CurrentUser currentUser = CurrentUser.getInstance();
                                            currentUser.setUsername(snapshot.getString("username"));
                                            currentUser.setUserId(currentuserid);
                                            PopupController.mostraPopup("Dentro query", currentuserid+snapshot.getString("username") , mContext);

                                            Intent intent=new Intent(mContext, MainActivity.class);
                                            mContext.startActivity(intent);

                                        }
                                    }
                                }
                            });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        PopupController.mostraPopup("Errore durante il login", e.toString() , mContext);

                    }
                });

    }


}
