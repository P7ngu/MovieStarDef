package com.example.moviestar.View.profilo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviestar.R;

import org.w3c.dom.Text;

public class EditProfiloFotoActivity extends AppCompatActivity {
    Button selectPicButton, updatePicButton;
    TextView filenameTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofilofoto);

        selectPicButton=findViewById(R.id.button_changename_ep);
        updatePicButton=findViewById(R.id.button_changepassword_ep);
        filenameTV=findViewById(R.id.textView_nomefile_ep);

        selectPicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        updatePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });
    }
}
