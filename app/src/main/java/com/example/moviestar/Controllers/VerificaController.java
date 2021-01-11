package com.example.moviestar.Controllers;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.example.moviestar.View.login.LoginActivity;

public class VerificaController {
    static Context mContext;


    public static void verificaCodice(String codice, String idUtente, Context context) {
        mContext=context;

        new ConfirmTask().execute(codice,
                idUtente);


    }

    private static class ConfirmTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            final String[] result = new String[1];

            //callback handler
            final GenericHandler confirmationCallback = new GenericHandler() {
                @Override
                public void onSuccess() {
                    result[0] = "Successo!";
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                }

                @Override
                public void onFailure(Exception exception) {
                    result[0] = "Fallimento!" + exception.getMessage();
                }
            };

            CognitoSettings cognitoSettings = new CognitoSettings(mContext);

            CognitoUser thisUser = cognitoSettings.getUserPool().getUser(strings[1]);
            thisUser.confirmSignUp(strings[0], false, confirmationCallback);
            return result[0];
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("Cognito", "result " + result);
        }
    }
}

