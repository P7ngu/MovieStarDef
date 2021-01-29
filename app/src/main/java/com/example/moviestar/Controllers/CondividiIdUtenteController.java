package com.example.moviestar.Controllers;

import android.content.Context;
import android.content.Intent;

public class CondividiIdUtenteController {

    public static void clickOnShareID(Context mContext, String idtext){
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");

        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, idtext);
        mContext.startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }
}
