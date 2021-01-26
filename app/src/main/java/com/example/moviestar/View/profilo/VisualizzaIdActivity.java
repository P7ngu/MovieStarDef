package com.example.moviestar.View.profilo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import com.example.moviestar.Controllers.CurrentUser;
import com.example.moviestar.R;

public class VisualizzaIdActivity extends AppCompatActivity {

    TextView usernameTV, idTV;
    Button shareButton;
    ImageView propicImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        String currentUserID = CurrentUser.getUserId();
        String currentUsername = CurrentUser.getUsername();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visuallizzaid);
        usernameTV = findViewById(R.id.nomeutente_ep);
        usernameTV.setText(currentUsername);
        idTV = findViewById(R.id.id_textview_ep);
        idTV.setText(currentUserID);
        shareButton = findViewById(R.id.button_shareid_ep);
        propicImg = findViewById(R.id.propic_ep_imageview);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String idtext = idTV.getText().toString();
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, idtext);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                //Log.d("shareID", "idtext = " + idtext);

            }
        });
    }
}
