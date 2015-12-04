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
 * Created by Cristian Navarrete on 10/28/2015.
 */
public class OutlineFrag extends Fragment {

    private static final String TAG = "OutlineFrag Activity";

    //--------------------QUIZ VARIABLES---------------------------//

    private int finalScore=0;
    private int questionScore = 100;
    private boolean rightAnswer = false;
    public static final int NUMBER_OF_QUESTIONS = 10;
    private List<String> fileNameList;
    private List<String> stateOutlineList;
    private Set<String> flagSet;
    private String correctAnswer;
    private int totalGuesses;
    private int questionNumber;
    private int guessRows;
    private SecureRandom random;
    private Handler handler;

    //---------------------------GUI VIEWS--------------------//

    private TextView questionNumberTV;
    private TextView scoreTextView;
    private ImageView outlineView;
    private LinearLayout[] guessLinearLayouts;
    private TextView answerTextView;

    //-----------------------HANDLERS-------------------------//

    final Handler scoreHandler = new Handler();

    final Runnable scoreRunnable = new Runnable() {
        @Override
        public void run() {
            if (questionScore == 1 && questionNumber < 9) {
                scoreTextView.setText("0");
                scoreHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        answerTextView.setText("Time Up!");
                        answerTextView.setTextColor(getResources().getColor(R.color.incorrect));
                        questionScore = 101;
                        questionNumber++;
                        scoreHandler.removeCallbacks(scoreRunnable);
                        disableButtons();
                        handler.postDelayed(guessRunnable, 2000);
                    }
                });
            }
            else if (questionScore == 1 && questionNumber >= 9) {
//                scoreHandler.removeCallbacks(scoreRunnable);
//                handler.removeCallbacks(guessRunnable);
                scoreHandler.removeCallbacksAndMessages(null);
                handler.removeCallbacksAndMessages(null);
                disableButtons();
//                Toast.makeText(getContext(), "Quiz is over!", Toast.LENGTH_LONG).show();
//                Toast.makeText(getContext(), "Final Score = " + Integer.toString(finalScore), Toast.LENGTH_LONG).show();
                Intent k = new Intent(getActivity(), ScoreSubmission.class);
                k.putExtra("quiz", "Outline Quiz");
                k.putExtra("score", finalScore);
                startActivity(k);
            }
            else {
                questionScore--;
                scoreTextView.setText(Integer.toString(questionScore));
                scoreHandler.postDelayed(this, 65);
            }
        }
    };

    final Runnable guessRunnable = new Runnable() {
        @Override
        public void run() {
            //scoreHandler.removeCallbacks(r);
            loadNextFlag();
            rightAnswer = false;
            questionScore = 101;
            scoreTextView.setText(Integer.toString(questionScore));
            scoreHandler.post(scoreRunnable);

        }
    };





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.outline_quiz_frag, container, false);



        fileNameList = new ArrayList<String>();
        stateOutlineList = new ArrayList<String>();
        random = new SecureRandom();
        handler = new Handler();

        //shakeAnimation.setRepeatCount(3);

        questionNumberTV = (TextView) view.findViewById(R.id.outlineQuestionNumberTV);
        outlineView = (ImageView) view.findViewById(R.id.outlineImageView);
        guessLinearLayouts = new LinearLayout[2];
        guessLinearLayouts[0] = (LinearLayout) view.findViewById(R.id.outlineRow1);
        guessLinearLayouts[1] = (LinearLayout) view.findViewById(R.id.outlineRow2);
        answerTextView = (TextView) view.findViewById(R.id.outlineAnswer);
        scoreTextView = (TextView) view.findViewById(R.id.outlineScoreTextView);


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
            String[] paths = assets.list("Outline");

            for (String path : paths) {
                fileNameList.add(path.replace(".png", ""));
            }
        }
        catch (IOException e) {
            Log.e(TAG, "Error", e);
        }

        questionNumber = 0;
        totalGuesses = 0;
        stateOutlineList.clear();

        int outlineCounter = 1;
        int numberOfOutlines = fileNameList.size();

        while (outlineCounter <= NUMBER_OF_QUESTIONS) {
            int randomIndex = random.nextInt(numberOfOutlines);

            String fileName = fileNameList.get(randomIndex);

            if (!stateOutlineList.contains(fileName)) {
                stateOutlineList.add(fileName);
                ++outlineCounter;
            }
        }
//        scoreTimer.scheduleAtFixedRate(scoreTask, 0, 800);

        loadNextFlag();

    }

    public void loadNextFlag() {
        String nextImage = stateOutlineList.remove(0);
        correctAnswer = nextImage;
        answerTextView.setText("");

        questionNumberTV.setText(getResources().getString(R.string.questionNumberTV, (questionNumber + 1), NUMBER_OF_QUESTIONS));

        AssetManager assets = getActivity().getAssets();

        try {
            InputStream stream = assets.open("Outline" + "/" + nextImage + ".png");
            Drawable outline = Drawable.createFromStream(stream, nextImage);
            outlineView.setImageDrawable(outline);
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
                    k.putExtra("quiz", "Outline Quiz");
                    k.putExtra("score", finalScore);
                    startActivity(k);
                }
                else {
                    scoreHandler.removeCallbacks(scoreRunnable);
                    handler.postDelayed(guessRunnable, 2000);
                }
            }
            else {
                //outlineView.startAnimation(shakeAnimation);
                answerTextView.setText(answer);
                answerTextView.setTextColor(getResources().getColor(R.color.incorrect));
                guessButton.setEnabled(false);
                disableButtons();

                if (questionNumber == NUMBER_OF_QUESTIONS) {
//                    scoreHandler.removeCallbacks(scoreRunnable);
//                    handler.removeCallbacks(guessRunnable);
                    scoreHandler.removeCallbacksAndMessages(null);
                    handler.removeCallbacksAndMessages(null);
//                    Toast.makeText(getContext(), "Quiz is over!", Toast.LENGTH_LONG).show();
//                    Toast.makeText(getContext(), "Final Score = " + Integer.toString(finalScore), Toast.LENGTH_LONG).show();
                    Intent k = new Intent(getActivity(), ScoreSubmission.class);
                    k.putExtra("quiz", "Outline Quiz");
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



