package com.example.cristiannavarrete.stonecap_quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Created by Cristian Navarrete on 11/23/2015.
 */
public class ScoreSubmission extends AppCompatActivity {

    MyDBHandler db;
    TextView quizName;
    TextView finalScore;
    EditText nameET;
    Button menu;
    Button submit;
    String myQuiz = "";
    int myScore = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_submission);

        db = new MyDBHandler(this, null, null, 4);

        quizName = (TextView) findViewById(R.id.setQuizNameTV);
        finalScore = (TextView) findViewById(R.id.setFinalScoreTV);
        nameET = (EditText) findViewById(R.id.nameEditText);
        menu = (Button) findViewById(R.id.button);
        submit = (Button) findViewById(R.id.button2);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            myQuiz = extras.getString("quiz");
            myScore = extras.getInt("score");
        }






        quizName.setText(myQuiz);
        finalScore.setText(Integer.toString(myScore));


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(ScoreSubmission.this, MainActivity.class);
                startActivity(k);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameET.getText().toString().length() > 15) {
                    Toast.makeText(getApplicationContext(), "Username cannot exceed 15 characters.", Toast.LENGTH_SHORT).show();
                }
                else if (nameET.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in name field.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Player p = new Player(nameET.getText().toString(), myScore);
                    switch (myQuiz) {
                        case "Outline Quiz":
                            db.addPlayerToOutline(p);
                            break;
                        case "Flag Quiz":
                            db.addPlayerToFlag(p);
                            break;
                        case "Nickname Quiz":
                            db.addPlayerToNickname(p);
                            break;
                    }
                    Intent k = new Intent(ScoreSubmission.this, Leaderboard.class);
                    k.putExtra("quiz", myQuiz);
                    startActivity(k);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {}



}
