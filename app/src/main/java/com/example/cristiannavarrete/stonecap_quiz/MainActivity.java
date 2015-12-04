package com.example.cristiannavarrete.stonecap_quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Button quizzes;
    Button leaderboard;
    Button instructions;
    Button aboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quizzes = (Button) findViewById(R.id.quizzes);
        leaderboard = (Button) findViewById(R.id.leaderboard);
        instructions = (Button) findViewById(R.id.instructionsButton);
        aboutUs = (Button) findViewById(R.id.aboutButton);

        quizzes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(MainActivity.this, ChooseQuiz.class);
                startActivity(k);
            }
        });

        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(MainActivity.this, Leaderboard.class);
                startActivity(k);
            }
        });

        instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(MainActivity.this, Instructions.class);
                startActivity(k);
            }
        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(MainActivity.this, AboutUs.class);
                startActivity(k);
            }
        });


    }

    @Override
    public void onBackPressed() {}


}
