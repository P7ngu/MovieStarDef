package com.example.moviestar.Controllers;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.moviestar.DAO.UtenteDAO;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EditProfiloController {
    //todo

    public static void clickOnUpdatePicButton_EditProfiloFotoActivity(Uri imageUri, String currentUserID, String currentUsername, Context mContext) {
      FirebaseFirestore db=FirebaseFirestore.getInstance();
        StorageReference storageReference;
        storageReference = FirebaseStorage.getInstance().getReference();

        CollectionReference collectionReference=db.collection("Journal");

        if(imageUri!=null&&currentUserID!=null){
            final StorageReference filepath=storageReference //.../profile_pictures/image_jpeg
                    .child("profile_pictures")
                    .child(currentUserID+".jpeg");
            filepath.putFile(Uri.parse(imageUri+"jpeg"))
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
                                            PopupController.mostraPopup("Foto aggiornata", "foto aggiornata", mContext);
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
            PopupController.mostraPopup("Titoolo", currentUserID+imageUri, mContext);
        }
    }
}
