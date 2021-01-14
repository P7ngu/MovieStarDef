package com.example.moviestar.View.profilo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviestar.Controllers.CurrentUser;
import com.example.moviestar.Controllers.PopupController;
import com.example.moviestar.DAO.UtenteDAO;
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

public class EditProfiloFotoActivity extends AppCompatActivity {
    Button selectPicButton, updatePicButton;
    String currentUserID, currentUsername;
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
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        currentUserID = CurrentUser.getInstance().getUserId();
        currentUsername = CurrentUser.getInstance().getUsername();


        selectPicButton = findViewById(R.id.button_changename_ep);
        updatePicButton = findViewById(R.id.button_changepassword_ep);
        filenameTV = findViewById(R.id.textView_nomefile_ep);
        propicImg = findViewById(R.id.propic_ep_imageview);

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
                clickOnUpdatePicButton();
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

    public void clickOnUpdatePicButton() {
        if(imageUri!=null&&currentUserID!=null){
            final StorageReference filepath=storageReference //.../profile_pictures/image_jpeg
                    .child("profile_pictures")
                    .child(currentUserID);
            filepath.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl=uri.toString();
                                    UtenteDAO utenteDAO=new UtenteDAO();
                                    utenteDAO.setCurrentID(currentUserID);
                                    utenteDAO.setCurrentUsername(currentUsername);
                                    utenteDAO.setImageURI(imageUrl);

                                    collectionReference.add(utenteDAO).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            //Cambia activity
                                            PopupController.mostraPopup("Foto aggiornata", "foto aggiornata", EditProfiloFotoActivity.this);
                                            // finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }else{
        PopupController.mostraPopup("Titoolo", currentUserID+imageUri, EditProfiloFotoActivity.this);
        }
    }



}



