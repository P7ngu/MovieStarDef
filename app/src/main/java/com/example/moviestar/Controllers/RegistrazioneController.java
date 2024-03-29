package com.example.moviestar.Controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.example.moviestar.DAO.UtenteDAO;
import com.example.moviestar.Model.Utente;
import com.example.moviestar.R;
import com.example.moviestar.View.MainActivity;
import com.example.moviestar.View.login.VerificationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrazioneController extends AppCompatActivity {
    static Utente myUtente;

    Button registratiButton=findViewById(R.id.button_registrati);
    private static FirebaseAuth firebaseAuth;
    private static FirebaseAuth.AuthStateListener authStateListener;
    private static FirebaseUser currentUser;

    //Firestore connection
    private static FirebaseFirestore db=FirebaseFirestore.getInstance();
    private static CollectionReference collectionReference=db.collection("Users");


    public static void registerOnCreate(){
       authStateListener=new FirebaseAuth.AuthStateListener() {
           @Override
           public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               currentUser = firebaseAuth.getCurrentUser();
               if(currentUser!=null){
                   //user is logged in
               }else{
                   //not logged

               }
           }
       };
    }

    public static void  registerOnStart() {
        firebaseAuth=FirebaseAuth.getInstance();

        currentUser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);

    }


    public static boolean checkCampiPerRegistraUtente(String email, String password1, String password2, String idUtente, final Context myContext) {
        if(checkCampiNonVuoti(idUtente, password1, password2, email)) {
            if (checkPasswordMatching( password1, password2, myContext)) {
                if(idUtente.length()>3) {
                    myUtente = new Utente(idUtente, password1, email);
                    return true;
                }
            }
        }
        if(!checkCampiNonVuoti(idUtente, password1, password2, email)){
            String errorMessage="Errore, compilare tutti i campi!";
            PopupController.mostraPopup("Errore", errorMessage, myContext);
            return false;

        }
        if(idUtente.length()<4)
          PopupController.mostraPopup("Errore", "Numero minimo di caratteri per nome utente: 4.", myContext);
        return false;

    }


    private static boolean checkCampiNonVuoti(String idUtente, String password1, String password2, String email) {
        if(idUtente.length() > 0 && password1.length() > 0 && password2.length() > 0 && email.length() > 0) return true;
        else
        return false;
    }

//    static boolean checkCampiValidi(String idUtente, String password, String password2, String email) {
//        //if( checkId(idUtente) && checkPassword(password, password2) && checkEmail(email) )
//        return true;
//        //else return false;
//    }


    public static boolean checkPasswordMatching(String password1, String password2, final Context myContext) {
        if(password1.equals(password2)) return true;
        else
            PopupController.mostraPopup("Errore", "Password not matching", myContext);
            return false;
    }



    public static void createUserEmailAccount(String email, String password1, String password2, String username, Context mContext) {
        if(checkCampiPerRegistraUtente(email, password1, password2, username, mContext))
    firebaseAuth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){
            currentUser=firebaseAuth.getCurrentUser();
            assert currentUser!=null;
            String currentUserID=currentUser.getUid();

            Map<String, String> userObj=new HashMap<>();
            userObj.put("userID", currentUserID);
            userObj.put("username", username);
            collectionReference.add(userObj)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    documentReference.get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.getResult().exists()){
                                String name=task.getResult()
                                        .getString("username");
                                String userid=task.getResult().getString("uid");
                                VerificaController.sendEmailConLinkDiVerifica(currentUserID, mContext);


                                Intent intent = new Intent(mContext, MainActivity.class);
                                intent.putExtra("username", name);
                                intent.putExtra("userID", currentUserID);
                                mContext.startActivity(intent);

                                PopupController.mostraPopup("Congratulazioni", "Utente registrato con successo, verifica ora il tuo account!", mContext);

                            }else{

                            }
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String errore = e.getMessage();
                    PopupController.mostraPopup("Errore durante la registrazione: ", e.getMessage(), mContext);
                }
            });
        }else{

        }
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            String errore = e.getMessage();
            PopupController.mostraPopup("Errore durante la registrazione: ", e.getMessage(), mContext);
        }
    });

    }
}



