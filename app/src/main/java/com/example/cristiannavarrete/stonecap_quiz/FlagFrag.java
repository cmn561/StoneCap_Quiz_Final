package com.example.cristiannavarrete.stonecap_quiz;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Cristian Navarrete on 11/23/2015.
 */
public class FlagFrag extends Fragment {

    private static final String TAG = "FlagFrag Activity";

    //--------------------QUIZ VARIABLES---------------------------//

    private int finalScore = 0;
    private int questionScore = 150;
    private boolean rightAnswer = false;
    public static final int NUMBER_OF_QUESTIONS = 7;
    private List<String> fileNameList;
    private List<String> flagflagList;
    private Set<String> flagSet;
    private String correctAnswer;
    private int totalGuesses;
    private int questionNumber;
    private int guessRows;
    private SecureRandom random;


    //---------------------------GUI VIEWS--------------------//

    private TextView questionNumberTV;
    private TextView scoreTextView;
    private ImageView flagView;
    private LinearLayout[] guessLinearLayouts;
    private TextView answerTextView;

    //-----------------------HANDLERS-------------------------//

    final Handler scoreHandler = new Handler();
    final Handler handler = new Handler();

    final Runnable scoreRunnable = new Runnable() {
        @Override
        public void run() {
            if (questionScore == 1 && questionNumber < 14) {
                scoreTextView.setText("0");
                scoreHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        answerTextView.setText("Time Up!");
                        answerTextView.setTextColor(getResources().getColor(R.color.incorrect));
                        questionScore = 151;
                        questionNumber++;
                        scoreHandler.removeCallbacks(scoreRunnable);
                        disableButtons();
                        handler.postDelayed(guessRunnable, 2000);
                    }
                });
            }
            else if (questionScore == 1 && questionNumber >= 14) {
//                scoreHandler.removeCallbacks(scoreRunnable);
//                handler.removeCallbacks(guessRunnable);
                scoreHandler.removeCallbacksAndMessages(null);
                handler.removeCallbacksAndMessages(null);
                disableButtons();
//                Toast.makeText(getContext(), "Quiz is over!", Toast.LENGTH_LONG).show();
//                Toast.makeText(getContext(), "Final Score = " + Integer.toString(finalScore), Toast.LENGTH_LONG).show();
                Intent k = new Intent(getActivity(), ScoreSubmission.class);
                k.putExtra("quiz", "Flag Quiz");
                k.putExtra("score", finalScore);
                startActivity(k);
            }
            else {
                questionScore--;
                scoreTextView.setText(Integer.toString(questionScore));
                scoreHandler.postDelayed(this, 30);
            }
        }
    };

    final Runnable guessRunnable = new Runnable() {
        @Override
        public void run() {
            //scoreHandler.removeCallbacks(r);
            loadNextFlag();
            rightAnswer = false;
            questionScore = 151;
            scoreTextView.setText(Integer.toString(questionScore));
            scoreHandler.post(scoreRunnable);

        }
    };
    
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.flag_quiz_frag, container, false);



        fileNameList = new ArrayList<String>();
        flagflagList = new ArrayList<String>();
        random = new SecureRandom();


        //shakeAnimation.setRepeatCount(3);

        questionNumberTV = (TextView) view.findViewById(R.id.flagQuestionNumberTV);
        flagView = (ImageView) view.findViewById(R.id.flagImageView);
        guessLinearLayouts = new LinearLayout[2];
        guessLinearLayouts[0] = (LinearLayout) view.findViewById(R.id.flagRow1);
        guessLinearLayouts[1] = (LinearLayout) view.findViewById(R.id.flagRow2);
        answerTextView = (TextView) view.findViewById(R.id.flagAnswer);
        scoreTextView = (TextView) view.findViewById(R.id.flagScoreTextView);


        for (LinearLayout row : guessLinearLayouts) {
            for (int column = 0; column < row.getChildCount(); column++) {
                Button button = (Button) row.getChildAt(column);
                button.setOnClickListener(guessButtonListener);
            }
        }

        questionNumberTV.setText(
                getResources().getString(R.string.questionNumberTV, 1, NUMBER_OF_QUESTIONS));
        scoreTextView.setText(Integer.toString(questionScore));

        scoreHandler.postDelayed(scoreRunnable, 1000);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();

        resetQuiz();
    }

    public void resetQuiz() {
        AssetManager assets =  getActivity().getAssets();
        fileNameList.clear();

        try {
            String[] paths = assets.list("Flag");

            for (String path : paths) {
                fileNameList.add(path.replace(".png", ""));
            }
        }
        catch (IOException e) {
            Log.e(TAG, "Error", e);
        }

        questionNumber = 0;
        totalGuesses = 0;
        flagflagList.clear();

        int flagCounter = 1;
        int numberOfflags = fileNameList.size();

        while (flagCounter <= NUMBER_OF_QUESTIONS) {
            int randomIndex = random.nextInt(numberOfflags);

            String fileName = fileNameList.get(randomIndex);

            if (!flagflagList.contains(fileName)) {
                flagflagList.add(fileName);
                ++flagCounter;
            }
        }
//        scoreTimer.scheduleAtFixedRate(scoreTask, 0, 800);

        loadNextFlag();

    }

    public void loadNextFlag() {
        String nextImage = flagflagList.remove(0);
        correctAnswer = nextImage;
        answerTextView.setText("");

        questionNumberTV.setText(getResources().getString(R.string.questionNumberTV, (questionNumber + 1), NUMBER_OF_QUESTIONS));

        AssetManager assets = getActivity().getAssets();

        try {
            InputStream stream = assets.open("Flag" + "/" + nextImage + ".png");
            Drawable flag = Drawable.createFromStream(stream, nextImage);
            flagView.setImageDrawable(flag);
        } catch (IOException exception) {
            Log.e(TAG, "Error Loading " + nextImage, exception);
        }

        Collections.shuffle(fileNameList);

        int correct = fileNameList.indexOf(correctAnswer);
        fileNameList.add(fileNameList.remove(correct));

        for (int row = 0; row < 2; row++) {

            for (int column = 0; column < guessLinearLayouts[row].getChildCount(); column++) {
                Button newGuessButton = (Button) guessLinearLayouts[row].getChildAt(column);
                newGuessButton.setEnabled(true);

                String fileName = fileNameList.get((row * 2) + column);
                newGuessButton.setText(getStateName(fileName));
            }
        }

        int row = random.nextInt(2);
        int column = random.nextInt(2);
        LinearLayout randomRow = guessLinearLayouts[row];
        String stateName = getStateName(correctAnswer);
        ((Button) randomRow.getChildAt(column)).setText(stateName);

    }

    public String getStateName(String name) {
        return name.replace('_', ' ');
    }

    public void disableButtons() {
        for (int row = 0; row < 2; row++) {
            LinearLayout guessRow = guessLinearLayouts[row];
            for (int i = 0; i < guessRow.getChildCount(); i++) {
                guessRow.getChildAt(i).setEnabled(false);
            }
        }
    }

    private View.OnClickListener guessButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button guessButton = ((Button) v);
            String guess = guessButton.getText().toString();
            String answer = getStateName(correctAnswer);
            ++totalGuesses;
            ++questionNumber;


            if (guess.equals(answer)) {
                //rightAnswer = true;
                finalScore += questionScore;
                answerTextView.setText(answer + "!");
                answerTextView.setTextColor(getResources().getColor(R.color.correct));
                disableButtons();
                if (questionNumber == NUMBER_OF_QUESTIONS) {
                    finalScore  += questionScore;
//                    scoreHandler.removeCallbacks(scoreRunnable);
//                    handler.removeCallbacks(guessRunnable);
                    scoreHandler.removeCallbacksAndMessages(null);
                    handler.removeCallbacksAndMessages(null);
//                    Toast.makeText(getContext(), "Quiz is over!", Toast.LENGTH_LONG).show();
//                    Toast.makeText(getContext(), "Final Score = " + Integer.toString(finalScore), Toast.LENGTH_LONG).show();
                    Intent k = new Intent(getActivity(), ScoreSubmission.class);
                    k.putExtra("quiz", "Flag Quiz");
                    k.putExtra("score", finalScore);
                    startActivity(k);
                }
                else {
                    scoreHandler.removeCallbacks(scoreRunnable);
                    handler.postDelayed(guessRunnable, 2000);
                }
            }
            else {
                //flagView.startAnimation(shakeAnimation);
                answerTextView.setText(answer);
                answerTextView.setTextColor(getResources().getColor(R.color.incorrect));
                guessButton.setEnabled(false);
                disableButtons();

                if (questionNumber == NUMBER_OF_QUESTIONS) {
                    finalScore  += questionScore;
//                    scoreHandler.removeCallbacks(scoreRunnable);
//                    handler.removeCallbacks(guessRunnable);
                    scoreHandler.removeCallbacksAndMessages(null);
                    handler.removeCallbacksAndMessages(null);
//                    Toast.makeText(getContext(), "Quiz is over!", Toast.LENGTH_LONG).show();
//                    Toast.makeText(getContext(), "Final Score = " + Integer.toString(finalScore), Toast.LENGTH_LONG).show();
                    Intent k = new Intent(getActivity(), ScoreSubmission.class);
                    k.putExtra("quiz", "Flag Quiz");
                    k.putExtra("score", finalScore);
                    startActivity(k);
                }
                else {
                    scoreHandler.removeCallbacks(scoreRunnable);
                    handler.postDelayed(guessRunnable, 2000);
                }

            }

        }
    };


    @Override
    public void onDestroy() {

        scoreHandler.removeCallbacksAndMessages(null);
        handler.removeCallbacksAndMessages(null);

        Intent k = new Intent(getActivity(), ChooseQuiz.class);
        startActivity(k);
        super.onDestroy();
    }



}
