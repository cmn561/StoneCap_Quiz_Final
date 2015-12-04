package com.example.cristiannavarrete.stonecap_quiz;

/**
 * Created by Cristian Navarrete on 11/23/2015.
 */
public class Player {

    int _id;
    String name;
    int score;

    Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    Player() {}

    public void setName(String name) {this.name = name;}
    public void setScore(int score) {this.score = score;}

    public String getName() {return this.name;}
    public int getScore() {return this.score;}
}
