package com.example.cristiannavarrete.stonecap_quiz;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Cristian Navarrete on 10/28/2015.
 */
public class Instructions extends AppCompatActivity{

    Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructions);

        returnButton = (Button) findViewById(R.id.instructionsBackButton);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(Instructions.this, MainActivity.class);
                startActivity(k);
            }
        });

    }

    @Override
    public void onBackPressed() {
        return;
    }
}
