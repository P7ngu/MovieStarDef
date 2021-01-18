package com.example.moviestar.View.profilo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.moviestar.Controllers.CurrentUser;
import com.example.moviestar.Controllers.EditProfiloController;
import com.example.moviestar.Controllers.PopupController;
import com.example.moviestar.DAO.UtenteDAO;
import com.example.moviestar.Model.Utente;
import com.example.moviestar.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static com.example.moviestar.Controllers.EditProfiloController.clickOnUpdatePicButton_EditProfiloFotoActivity;

public class EditProfiloFotoActivity extends AppCompatActivity {
    Button selectPicButton, updatePicButton;
    String currentUserID, currentUsername;
    ImageView  propicImg;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private Uri imageUri;
    static Context mContext;
    static SharedPreferences prefs;

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
                CurrentUser.getInstance();
                CurrentUser.setImageUri(data.getData());
                imageUri = data.getData();
                propicImg.setImageURI(CurrentUser.getImageUri());

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
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();


        mContext=this;
        currentUserID = CurrentUser.getInstance().getUserId();
        currentUsername = CurrentUser.getInstance().getUsername();


        selectPicButton = findViewById(R.id.button_changename_ep);
        updatePicButton = findViewById(R.id.button_changepassword_ep);
        filenameTV = findViewById(R.id.textView_nomefile_ep);
        propicImg = findViewById(R.id.propic_ep_imageview);


       UtenteDAO.getImageFromDatabase(currentUserID, mContext);

        prefs = mContext.getSharedPreferences("myPrefsKeys", Context.MODE_PRIVATE);
        final String uri_string = prefs.getString("uri", "");

        PopupController.mostraPopup(uri_string, uri_string, mContext);

       imageUri=CurrentUser.getInstance().getImageUri();
        propicImg.setImageURI(Uri.parse(uri_string));


        //Glide.with(mContext).load(imageUri).into(propicImg);


        selectPicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_CODE);
            }
        });

        updatePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfiloController.clickOnUpdatePicButton_EditProfiloFotoActivity(imageUri,currentUserID, currentUsername, mContext);
            }
        });


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {

                } else {

                }
            }

        };

    }





}



