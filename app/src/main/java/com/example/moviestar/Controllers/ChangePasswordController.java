package com.example.moviestar.Controllers;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;

import static android.content.ContentValues.TAG;

public class ChangePasswordController {
   static Context mContext;

    public static void changePassword(EditText editTextNewPass, EditText editTextUsername, EditText editTextOldPass, Context context){
        mContext=context;
        CognitoSettings cognitoSettings=new CognitoSettings(context);

        cognitoSettings.getUserPool().getUser( String.valueOf (editTextUsername.getText () ) )
                .changePasswordInBackground(String.valueOf(editTextOldPass.getText())
                        , String.valueOf(editTextNewPass.getText()), handler);
    }

    final static GenericHandler handler=new GenericHandler() {
        @Override
        public void onSuccess() {
            Log.i(TAG, "password changed!");
        }

        @Override
        public void onFailure(Exception exception) {
            Log.i(TAG, "change pass failed "+ exception.getLocalizedMessage());
        }
    };

}
