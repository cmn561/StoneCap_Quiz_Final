package com.example.cristiannavarrete.stonecap_quiz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

/**
 * Created by Cristian Navarrete on 11/23/2015.
 */
public class FlagQuiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flag_quiz);

//        if (findViewById(R.id.outlineFrag) != null) {
//
//            if (savedInstanceState != null) {
//                return;
//            }
//
////            OutlineFrag frag = new OutlineFrag();
////            frag.setArguments(getIntent().getExtras());
////            getSupportFragmentManager().beginTransaction()
////                    .add(R.id.outlineFrag, frag).commit();
//
//        }

    }

    @Override
    protected void onStart() {

        super.onStart();

//        OutlineFrag frag = (OutlineFrag) getFragmentManager().findFragmentById(R.id.outlineFrag);

        Fragment frag = new FlagFrag();
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.flagFrag, frag);

        fragmentTransaction.commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.outline_quiz, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        getSupportFragmentManager().findFragmentById(R.id.flagFrag).onDestroy();
    }

    @Override
    public FragmentManager getSupportFragmentManager() {
        return super.getSupportFragmentManager();
    }

}
