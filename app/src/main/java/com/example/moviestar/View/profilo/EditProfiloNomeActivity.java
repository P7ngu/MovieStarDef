package com.example.moviestar.View.profilo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviestar.Controllers.CurrentUser;
import com.example.moviestar.R;

public class EditProfiloNomeActivity extends AppCompatActivity {
    EditText newNameET;
    TextView oldNameTV;
    Button updateNameButton;
    ImageView propicImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        String currentUserID= CurrentUser.getUserId();
        String currentUsername=CurrentUser.getUsername();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofilonome);

        newNameET=findViewById(R.id.editText_newname_ep);
        oldNameTV=findViewById(R.id.nomeutente_ep);
        oldNameTV.setText(currentUsername);

        updateNameButton=findViewById(R.id.button_changename_ep);
        propicImg=findViewById(R.id.propic_ep_imageview);

        updateNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO

            }
        });
    }
}
