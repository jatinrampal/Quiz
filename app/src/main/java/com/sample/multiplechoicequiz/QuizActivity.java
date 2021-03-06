package com.sample.multiplechoicequiz;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.Scanner;


public class QuizActivity extends AppCompatActivity {

    private QuestionBank mQuestionLibrary = new QuestionBank();
    private TextView mScoreView;   // view for current total score
    private TextView mQuestionView;  //current question to answer
    private Button mButtonChoice1; // multiple choice 1 for mQuestionView
    private Button mButtonChoice2; // multiple choice 2 for mQuestionView
    private Button mButtonChoice3; // multiple choice 3 for mQuestionView
    private Button mButtonChoice4; // multiple choice 4 for mQuestionView

    private String mAnswer;  // correct answer for question in mQuestionView
    private int mScore = 0;  // current total score
    private int mQuestionNumber = 0; // current question number
    private int counter = 0;
    private int mHighScore=0;
    DataBaseHelper db;

    SharedPreferences myPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        myPrefs = getPreferences(MODE_PRIVATE);
        db = new DataBaseHelper(getApplicationContext());
        // setup screen for the first question with four alternative to answer
        mScoreView = (TextView)findViewById(R.id.score);
        mQuestionView = (TextView)findViewById(R.id.question);
        mButtonChoice1 = (Button)findViewById(R.id.choice1);
        mButtonChoice2 = (Button)findViewById(R.id.choice2);
        mButtonChoice3 = (Button)findViewById(R.id.choice3);
        mButtonChoice4 = (Button)findViewById(R.id.choice4);
        //updateQuestion();
        // show current total score for the user

        db.addQuestion(1, "1. When did Google acquired Android?");
        db.addQuestion(2, "2. What is the name of build toolkit for Android Studio?");
        db.addQuestion(3, "3. What widget can replace any use of radio buttons?");
        db.addQuestion(4, "4. What is the most recent Android OS ?");
        db.addQuestion(5, "5. Which was the first commercially available phone running Android?");
        db.addQuestion(6, "6. Which one of the among would be the next version of Android?");

        db.addOption(1,"2003",1);
        db.addOption(2,"2004",1);
        db.addOption(3,"2005",1);
        db.addOption(4,"2006",1);

        db.addOption(1,"JVM",2);
        db.addOption(2,"Gradle",2);
        db.addOption(3,"Dalvik",2);
        db.addOption(4,"HAXM",2);

        db.addOption(1,"Toggle",3);
        db.addOption(2,"Spinner",3);
        db.addOption(3,"Switch",3);
        db.addOption(4,"CheckBox",3);

        db.addOption(1,"Oreo",4);
        db.addOption(2,"Nougat",4);
        db.addOption(3,"Marshmallow",4);
        db.addOption(4,"Octopus",4);

        db.addOption(1,"Galaxy",5);
        db.addOption(2,"Nexus",5);
        db.addOption(3,"Dream",5);
        db.addOption(4,"LG",5);

        db.addOption(1,"Pineapple",6);
        db.addOption(2,"Popcorn",6);
        db.addOption(3,"P",6);
        db.addOption(4,"Plus",6);

        db.addAnswer(1, "2005");
        db.addAnswer(2, "Gradle");
        db.addAnswer(3, "Spinner");
        db.addAnswer(4, "Oreo");
        db.addAnswer(5, "Dream");
        db.addAnswer(6, "P");

        readPreferences();
        updateQuestion();
        updateScore(mScore);

    }

    private void updateQuestion(){
        /*
        // check if we are not outside array bounds for questions
        if(mQuestionNumber<mQuestionLibrary.getLength() ){
            // set the text for new question, and new 4 alternative to answer on four buttons
            mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
            mButtonChoice1.setText(mQuestionLibrary.getChoice(mQuestionNumber, 1));
            mButtonChoice2.setText(mQuestionLibrary.getChoice(mQuestionNumber, 2));
            mButtonChoice3.setText(mQuestionLibrary.getChoice(mQuestionNumber, 3));
            mButtonChoice4.setText(mQuestionLibrary.getChoice(mQuestionNumber,4));
            mAnswer = mQuestionLibrary.getCorrectAnswer(mQuestionNumber);
            mQuestionNumber++;

       }
        else
            Toast.makeText(QuizActivity.this, "It was the last question!", Toast.LENGTH_SHORT).show();
            */

        /*
        InputStream inputQuestions = getResources().openRawResource(R.raw.questions);
        Scanner scanQuestions = new Scanner(inputQuestions);

        String[] arrayQues = new String[100];
        int i = 0;

        while(scanQuestions.hasNextLine()) {
            arrayQues[i] = scanQuestions.nextLine();
            i++;
        }
        if(mQuestionNumber<arrayQues.length)
            mQuestionView.setText(arrayQues[mQuestionNumber]); */

        /*
        InputStream inputOptions = getResources().openRawResource(R.raw.options);
        Scanner scanOptions = new Scanner(inputOptions);

        String[][] arrayOptions = new String[100][100];
        int a = 0;
        int q=0;

        while(scanOptions.hasNextLine()) {
            String line = scanOptions.next();
            for( a=0; a <4 ; a++)
            {
                String[] pieces = line.split(",");
                arrayOptions[q][a] = pieces[a];
            }
            q++;
        }




        mButtonChoice1.setText(arrayOptions[mQuestionNumber][0]);
        mButtonChoice2.setText(arrayOptions[mQuestionNumber][1]);
        mButtonChoice3.setText(arrayOptions[mQuestionNumber][2]);
        mButtonChoice4.setText(arrayOptions[mQuestionNumber][3]);
        mQuestionNumber++;
        */

        if(mQuestionNumber<db.totalQuestions())
        {
            mQuestionView.setText(db.updateQuestion(mQuestionNumber+1));
            mButtonChoice1.setText(db.updateOptions(mQuestionNumber+1,1));
            mButtonChoice2.setText(db.updateOptions(mQuestionNumber+1,2));
            mButtonChoice3.setText(db.updateOptions(mQuestionNumber+1,3));
            mButtonChoice4.setText(db.updateOptions(mQuestionNumber+1,4));
        }

        else
        {
            Toast.makeText(QuizActivity.this, "It was the last question!", Toast.LENGTH_SHORT).show();
        }






        mQuestionNumber++;
    }

    // show current total score for the user
    private void updateScore(int point) {
        if(mScore>db.totalQuestions())
            Toast.makeText(QuizActivity.this, "Invalid!", Toast.LENGTH_SHORT).show();
        else
            mScoreView.setText("" + mScore+"/"+db.totalQuestions() + "\n" + "" + mHighScore+"/"+db.totalQuestions() );
    }



    public void onClick(View view) {


        //all logic for all answers buttons in one method
        Button answer = (Button) view;
        // if the answer is correct, increase the score

        /*
        String[] arrayAnswers = new String[100];
        int i=0;

        InputStream inputAnswers = getResources().openRawResource(R.raw.answers);
        Scanner scanAnswers = new Scanner(inputAnswers);

        while(scanAnswers.hasNextLine())
        {
            arrayAnswers[i] = scanAnswers.nextLine();
            i++;
        }
        */



            if (answer.getText().equals(db.checkAnswer(mQuestionNumber))) {
                mScore = mScore + 1;
                Toast.makeText(QuizActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(QuizActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();

            }



        if(mScore>mHighScore)
            mHighScore=mScore;
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putInt("keyscore", mHighScore);
        editor.commit();

        // once user answer the question, we move on to the next one
        if(counter<=db.totalQuestions())
        {
            updateQuestion();
            updateScore(mScore);
        }
        counter++;

    }

    public void onReset(View view) {
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.clear();
        editor.commit();
        readPreferences();
    }

    private void readPreferences() {
        mHighScore = myPrefs.getInt("keyscore", 0);
    }

}