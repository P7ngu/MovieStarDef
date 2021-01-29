package com.example.moviestar.Controllers;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.example.moviestar.View.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerificaController {
    static Context mContext;
    static boolean userVerifiedByGoogle;

    public static boolean isUserVerifiedByGoogle() {
        return userVerifiedByGoogle;
    }

    public static void setUserVerifiedByGoogle(boolean userVerifiedByGoogle) {
        VerificaController.userVerifiedByGoogle = userVerifiedByGoogle;
    }

    public static void sendEmailConLinkDiVerifica(String idUtente, Context context) {
        mContext = context;
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            PopupController.mostraPopup("Link inviato", "Nuovo link inviato con successo, controlla le tue email!", mContext);
                        }
                    }
                });
    }



    public static boolean IsEmailVerified() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user!=null && user.isEmailVerified()) {
            return true;
        } else {
            return userVerifiedByGoogle;
        }

    }
}



