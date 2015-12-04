package com.example.cristiannavarrete.stonecap_quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Cristian Navarrete on 10/28/2015.
 */
public class ChooseQuiz extends Activity {

    Button outline;
    Button flag;
    Button nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_quiz);

        outline = (Button) findViewById(R.id.stateOutline);
        flag = (Button) findViewById(R.id.stateFlag);
        nickname = (Button) findViewById(R.id.stateNickname);

        outline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(ChooseQuiz.this, OutlineQuiz.class);
                startActivity(k);
            }
        });

        flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(ChooseQuiz.this, FlagQuiz.class);
                startActivity(k);
            }
        });

        nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(ChooseQuiz.this, NicknameQuiz.class);
                startActivity(k);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent k = new Intent(this, MainActivity.class);
        startActivity(k);
    }
}
