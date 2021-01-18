package com.example.moviestar.DAO;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.moviestar.Controllers.CurrentUser;
import com.example.moviestar.Controllers.PopupController;
import com.example.moviestar.View.profilo.EditProfiloFotoActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UtenteDAO {
    String imageURI;
    public static SharedPreferences prefs;
    static Uri ImageUri;
    static String currentID;

    public static void getImageFromDatabase(String currentID, Context mContext){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        StorageReference storageRef;
        storageRef = FirebaseStorage.getInstance().getReference();

        storageRef.child("profile_pictures/"+currentID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                prefs = mContext.getSharedPreferences("myPrefsKeys", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = prefs.edit();
                editor1.putString("uri", uri.toString());
                editor1.apply();

                CurrentUser.getInstance().setImageUri(uri);

                // Got the download URL for 'users/me/profile.png'
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    public String getCurrentID() {
        return currentID;
    }

    public void setCurrentID(String currentID) {
        this.currentID = currentID;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    String currentUsername;

    public UtenteDAO(String imageURI, String currentID, String currentUsername) {
        this.imageURI = imageURI;
        this.currentID = currentID;
        this.currentUsername = currentUsername;
    }

    public UtenteDAO(){}

    public static boolean checkEmailNonPresente(String email) {
        return false;
    }

    public static boolean checkIdUtenteNonPresente(String idUtente) {
        return false;
    }
}
