package com.example.moviestar.Controllers;

import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class CognitoSettings {
    private String userPoolID = "us-east-2_j7PB7rJzo";
    private String clientID="787448bvbssnbt91usue70l37j";
    private String clientSecret="1dp9u4t3drgjia9td5vcuqss0t6lsrqak0uk5gceuq1kdi00b5s";
    private Regions cognitoRegion=Regions.US_EAST_2;

    private Context context;

    public CognitoSettings(Context context){
        this.context=context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getUserPoolID() {
        return userPoolID;
    }


    public String getClientID() {
        return clientID;
    }


    public String getClientSecret() {
        return clientSecret;
    }


    public Regions getCognitoRegion() {
        return cognitoRegion;
    }

    public void setCognitoRegion(Regions cognitoRegion) {
        this.cognitoRegion = cognitoRegion;
    }

    public CognitoUserPool getUserPool(){
        return new CognitoUserPool(context, userPoolID, clientID, clientSecret, cognitoRegion);
    }
}


