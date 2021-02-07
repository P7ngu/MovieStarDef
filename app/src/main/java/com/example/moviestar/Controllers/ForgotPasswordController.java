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
import com.example.moviestar.View.profilo.ChangePasswordActivity;
import com.example.moviestar.View.profilo.EditProfiloActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPasswordController {
    static Context mContext;


    public static void sendNewCode(Context context, String emailUtente){
        mContext = context;
       if(checkEmailIsValid(emailUtente)) {
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
                   }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   PopupController.mostraPopup("Errore", e.getMessage(), mContext);
               }
           });
       }
       else PopupController.mostraPopup("Errore!", "Inserire indirizzo email valido", context);
    }

    private static boolean checkEmailIsValid(String emailUtente) {
        if(emailUtente.length()>6) return true;
        return false;
    }

    public static void resetPassword(Context context, String newPass, String code){
        if( checkPasswordIsValid(newPass )) {
            mContext = context;
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
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        } else PopupController.mostraPopup("Errore", "Inserire password valida, minimo 6 caratteri", context);

    }


    public static void changePassword(EditText editTextNewPass, Context context){
        mContext=context;
        if( checkPasswordIsValid(editTextNewPass.getText().toString().trim() )) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newPassword = editTextNewPass.getText().toString();

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            PopupController.mostraPopup("Password aggiornata", "Password aggiornata con successo!", mContext);
                            ChangePasswordActivity.emptyFields();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                PopupController.mostraPopup("Errore!", e.getMessage(), mContext);
            }
        });
    } else PopupController.mostraPopup("Errore", "Inserire password valida, minimo 6 caratteri", context);

}

    private static boolean checkPasswordIsValid(String passwordDaCheckare) {
        return (passwordDaCheckare.length()>5);
    }

    public static boolean checkCampiPasswordPerCambiaPassword(String password1, String password2, Context mContext){
        if(password1.length()>0 && password2.length()>0){
            if(password1.length()>5 && password2.length()>5)
                if(password1.equals(password2))
                    return true;
        } if(password1.length()==0 || password2.length()==0) PopupController.mostraPopup("Errore", "Compilare entrambi i campi!", mContext);
        else if(password1.length()<6 || password2.length()<6) PopupController.mostraPopup("Errore", "Numero minimo di caratteri: 6.", mContext);
        else if(!password1.equals(password2)) PopupController.mostraPopup("Errore", "Le password devono corrispondere!", mContext);
        return false;
    }
}

