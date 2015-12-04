package com.example.cristiannavarrete.stonecap_quiz;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Cristian Navarrete on 11/24/2015.
 */
public class OutlineLeaderboard extends Fragment{

    MyDBHandler db;
    int rows;
    ArrayList<Player> players = new ArrayList<>();

    LinearLayout l;
    //LinearLayout l2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancenickname) {
        super.onCreateView(inflater, container, savedInstancenickname);
        View view = inflater.inflate(R.layout.outline_leaderboard_frag, container, false);
        l = (LinearLayout) view.findViewById(R.id.outlineLinearScroll);
        //l2 = (LinearLayout) view.findViewById(R.id.outlineHorizontalScroll);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        loadLeaderboard();
    }

    public void loadLeaderboard() {


        db = new MyDBHandler(getActivity(), null, null, 4);
        rows = db.numRowsOutline();

        for (int i = 0; i <= rows - 1; i++) {
            Player playerToAdd;
            playerToAdd = db.getRowProductDataOutline(i);
            players.add(playerToAdd);
        }

        for (int i = 0; i < players.size(); i++) {

            LinearLayout l2 = new LinearLayout(getActivity());
            TextView nameTV = new TextView(getActivity());
            TextView scoreTV = new TextView(getActivity());

            nameTV.setText(players.get(i).getName());
            //nameTV.setPadding(0, 0, 35, 0);
            nameTV.setWidth(435);
            nameTV.setTextSize(20);
            scoreTV.setText(Integer.toString(players.get(i).getScore()));
            scoreTV.setTextSize(20);
            //scoreTV.setPadding(0, 35, 0, 0);
            //double roundOff = Math.round(products.get(i).getSellPrice() * 100.0) / 100.0;

            l2.addView(nameTV);
            l2.addView(scoreTV);
            l.addView(l2);

        }
    }
}
