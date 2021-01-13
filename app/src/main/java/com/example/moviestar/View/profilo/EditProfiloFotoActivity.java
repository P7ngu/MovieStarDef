package com.example.moviestar.View.profilo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviestar.Controllers.CurrentUser;
import com.example.moviestar.R;
import com.example.moviestar.View.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

public class EditProfiloFotoActivity extends AppCompatActivity {
    Button selectPicButton, updatePicButton;
    ImageView  propicImg;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private Uri imageUri;

    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private StorageReference storageReference;

    private CollectionReference collectionReference=db.collection("Journal");
    TextView filenameTV;
    private int GALLERY_CODE=1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_CODE && resultCode == RESULT_OK){
            if(data!=null){
                imageUri = data.getData();
                propicImg.setImageURI(imageUri);

            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        user=firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(firebaseAuth!=null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofilofoto);
        firebaseAuth = FirebaseAuth.getInstance();
        String currentUserID = CurrentUser.getUserId();
        String currentUsername = CurrentUser.getUsername();

        selectPicButton = findViewById(R.id.button_changename_ep);
        updatePicButton = findViewById(R.id.button_changepassword_ep);
        filenameTV = findViewById(R.id.textView_nomefile_ep);
        propicImg=findViewById(R.id.propic_ep_imageview);

        selectPicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_CODE);
            }
        });

        updatePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            user=firebaseAuth.getCurrentUser();
            if(user!=null){

            }else{

            }
            }

        };


    }
}
