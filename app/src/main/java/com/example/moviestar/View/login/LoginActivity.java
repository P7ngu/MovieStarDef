package com.example.moviestar.View.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.bumptech.glide.Glide;
import com.example.moviestar.Controllers.CurrentUser;
import com.example.moviestar.Controllers.LoginController;
import com.example.moviestar.Controllers.PopupController;
import com.example.moviestar.Controllers.VerificaController;
import com.example.moviestar.DAO.UtenteDAO;
import com.example.moviestar.R;
import com.example.moviestar.View.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static com.example.moviestar.Controllers.LoginController.loadCurrentUserDetails;

public class LoginActivity extends AppCompatActivity  {
    Context mContext = this;
    EditText emailEditText, passwordEditText;
    TextView forgotPassword;
    Button accediButton,  registratiButton;
    ImageView logo;
    private FirebaseAuth mAuth;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    GoogleSignInClient mGoogleSignInClient;

    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("Users");

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, RegistrazioneActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            handleSignInResult(task);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                        }

                        // ...
                    }
                });
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Intent intent = getIntent();

        firebaseAuth=FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestIdToken("343239065340-2koibciaa32plkfpfu21ge23kci919jg.apps.googleusercontent.com")
               .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        ImageView img = findViewById(R.id.imageView);
        Glide.with(mContext).load("https://i.ibb.co/zVhNTdy/logo.png").into(img);

        emailEditText = findViewById(R.id.editText_email_login);
        passwordEditText = findViewById(R.id.editText_password_login);
        forgotPassword = findViewById(R.id.textView_forgotpassword_login);

        accediButton = findViewById(R.id.button_login_login);
        registratiButton = findViewById(R.id.button_registrati_login);

        accediButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Test", "bottone");

                //LoginController.login(emailEditText.getText().toString().trim(), passwordEditText.getText().toString().trim(), mContext);
                //loginEmailPasswordUser(emailEditText.getText().toString().trim(), passwordEditText.getText().toString().trim());
                LoginController.Firebase_loginEmailPasswordUser(emailEditText.getText().toString().trim(), passwordEditText.getText().toString().trim(), mContext);
                //LoginController.Firebase_loginEmailPasswordUser("email@email.it", "password", mContext);
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

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }


    private void handleSignInResult(Task<AuthResult> completedTask) {
        try {
            //GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            MainActivity.setUserLogged(true);
            MainActivity.setIsUserVerified(true);
            VerificaController.setUserVerifiedByGoogle(true);
            Intent intent = new Intent(mContext, MainActivity.class);

            mContext.startActivity(intent);
            MainActivity.setUserLogged(true);
            MainActivity.setIsUserVerified(true);

            GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
            if(signInAccount != null){
                CurrentUser.getInstance().setUsername(signInAccount.getDisplayName());
                CurrentUser.getInstance().setUserId(signInAccount.getId());
                loadCurrentUserDetails();
                UtenteDAO.insertGoogleUser_Firebase(signInAccount.getDisplayName(), signInAccount.getId());
            }


        } catch (Exception e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Debug Google", "signInResult:failed code=" + e);
            //MainActivity.setUserLogged(true);
            //MainActivity.setIsUserVerified(true);
            //VerificaController.setUserVerifiedByGoogle(true);
            //mContext.startActivity(new Intent(mContext, MainActivity.class));

        }
    }



}
