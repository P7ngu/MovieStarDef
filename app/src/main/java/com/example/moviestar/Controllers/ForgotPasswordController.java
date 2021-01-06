package com.example.moviestar.Controllers;

import android.content.Context;
import android.content.Intent;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.ForgotPasswordHandler;
import com.example.moviestar.View.ForgotPasswordActivity;
import com.example.moviestar.View.LoginActivity;

public class ForgotPasswordController {
    static Context mContext;

    static final ForgotPasswordContinuation[] resultContinuation = new ForgotPasswordContinuation[1];

    static final ForgotPasswordHandler callback=new ForgotPasswordHandler() {
        @Override
        public void onSuccess() {

        }

        @Override
        public void getResetCode(ForgotPasswordContinuation continuation) {
            CognitoUserCodeDeliveryDetails codeSentHere=continuation.getParameters();
            resultContinuation[0] =continuation;
        }

        @Override
        public void onFailure(Exception exception) {

        }
    };


    public static void sendNewCode(Context context, String idUtente){
        mContext=context;
        CognitoSettings cognitoSettings=new CognitoSettings(context);
        CognitoUser thisUser = cognitoSettings.getUserPool().getUser(idUtente);

        thisUser.forgotPasswordInBackground(callback);
    }

    public static void resetPassword(Context context, String newPass, String code){
        mContext=context;
        resultContinuation[0].setPassword(newPass);
        resultContinuation[0].setVerificationCode(code);
        resultContinuation[0].continueTask();

        Intent intent=new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }


}
