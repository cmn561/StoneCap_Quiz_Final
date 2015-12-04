package com.example.cristiannavarrete.stonecap_quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Cristian Navarrete on 11/23/2015.
 */
public class Leaderboard extends AppCompatActivity {

    String myQuiz = "";
    Button returnToMenu, outline, flag, nickname;
    TextView leaderboardDescription;
    MyDBHandler db = new MyDBHandler(this, null, null, 4);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

        returnToMenu = (Button) findViewById(R.id.bReturnToMenu);
        outline = (Button) findViewById(R.id.bOutlineLeaderboard);
        flag = (Button) findViewById(R.id.bFlagLeaderboard);
        nickname = (Button) findViewById(R.id.bNickNameLeaderboard);
        leaderboardDescription = (TextView) findViewById(R.id.leaderboardDescription);


        Bundle extras = getIntent().getExtras();
        if (extras != null)
            myQuiz = extras.getString("quiz");

        switch (myQuiz) {
            case "Outline Quiz":
                outline.setEnabled(false);
                leaderboardDescription.setText("Outline Quiz Leaderboard");
                Fragment outlineFrag = new OutlineLeaderboard();
                FragmentManager fragmentManager = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.leaderboardFrag, outlineFrag);
                fragmentTransaction.commit();
                break;
            case "Flag Quiz":
                flag.setEnabled(false);
                leaderboardDescription.setText("Flag Quiz Leaderboard");
                Fragment flagFrag = new FlagLeaderboard();
                FragmentManager fragmentManager2 = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                fragmentTransaction2.add(R.id.leaderboardFrag, flagFrag);
                fragmentTransaction2.commit();
                break;
            case "Nickname Quiz":
                nickname.setEnabled(false);
                leaderboardDescription.setText("Nickname Quiz Leaderboard");
                Fragment nickNameFrag = new NicknameLeaderboard();
                FragmentManager fragmentManager3 = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                fragmentTransaction3.add(R.id.leaderboardFrag, nickNameFrag);
                fragmentTransaction3.commit();
                break;
            default:
                outline.setEnabled(false);
                leaderboardDescription.setText("Outline Quiz Leaderboard");
                Fragment outlineFrag4 = new OutlineLeaderboard();
                FragmentManager fragmentManager4 = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction4 = fragmentManager4.beginTransaction();
                fragmentTransaction4.add(R.id.leaderboardFrag, outlineFrag4);
                fragmentTransaction4.commit();
                break;

        }

        returnToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(Leaderboard.this, MainActivity.class);
                startActivity(k);
            }
        });

        outline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outline.setEnabled(false);
                flag.setEnabled(true);
                nickname.setEnabled(true);
                leaderboardDescription.setText("Outline Quiz Leaderboard");

                OutlineLeaderboard newFrag = new OutlineLeaderboard();
                FragmentManager fragmentManager = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.leaderboardFrag, newFrag);
                fragmentTransaction.commit();
            }
        });

        flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outline.setEnabled(true);
                flag.setEnabled(false);
                nickname.setEnabled(true);
                leaderboardDescription.setText("Flag Quiz Leaderboard");

                FlagLeaderboard newFrag = new FlagLeaderboard();
                FragmentManager fragmentManager = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.leaderboardFrag, newFrag);
                fragmentTransaction.commit();
            }
        });

        nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outline.setEnabled(true);
                flag.setEnabled(true);
                nickname.setEnabled(false);
                leaderboardDescription.setText("Nickname Quiz Leaderboard");

                NicknameLeaderboard newFrag = new NicknameLeaderboard();
                FragmentManager fragmentManager = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.leaderboardFrag, newFrag);
                fragmentTransaction.commit();
            }
        });


    }

    @Override
    public void onBackPressed() {}
}
