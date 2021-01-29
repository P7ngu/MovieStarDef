package com.example.moviestar.Controllers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.ForgotPasswordHandler;
import com.example.moviestar.View.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPasswordController {
    static Context mContext;


    public static void sendNewCode(Context context, String emailUtente){
        mContext=context;
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = emailUtente;

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Firebase Debug", "Email sent.");
                            PopupController.mostraPopup("Link inviato", "Nuovo link inviato con successo, controlla la tua casella di posta!", mContext);
                        }
                    }
                });
    }

    public static void resetPassword(Context context, String newPass, String code){
        mContext=context;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newPassword = "SOME-SECURE-PASSWORD";

        user.updatePassword(newPass)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Firebase Debug", "User password updated.");
                        }
                    }
                });
        Intent intent=new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }


    public static void changePassword(EditText editTextNewPass, Context context){
        mContext=context;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newPassword = editTextNewPass.getText().toString();

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            PopupController.mostraPopup("Password aggiornata", "Password aggiornata con successo!", mContext);
                        }
                    }
                });
    }

}
