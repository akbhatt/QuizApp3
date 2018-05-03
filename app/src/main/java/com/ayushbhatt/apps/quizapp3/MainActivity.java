package com.ayushbhatt.apps.quizapp3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //to get the name from the name edit view
    public String getName() {
        EditText nameEditText = findViewById(R.id.nameEditText);
        String name = nameEditText.getText().toString();
        return name;
    }

    //scoring the quiz
    public int quizScoring() {

        //initialization of the score integer
        int score = 0;

        //get necessary views
        RadioButton answerOne = findViewById(R.id.questionOneRadioButtonTwo);
        RadioButton answerThree = findViewById(R.id.questionThreeRadioButtonTwo);
        CheckBox answerTwoOptionOne = findViewById(R.id.questionTwoCheckBox1);
        CheckBox answerTwoOptionTwo = findViewById(R.id.questionTwoCheckBox2);
        CheckBox answerTwoOptionThree = findViewById(R.id.questionTwoCheckBox3);
        CheckBox answerTwoOptionFour = findViewById(R.id.questionTwoCheckBox4);
        EditText answerFour = findViewById(R.id.questionFourAnswerEditText);
        String answerFourString = answerFour.getText().toString().toLowerCase();


        //check for correct answer
        if (answerOne.isChecked()) {
            score++;
        }
        if (answerThree.isChecked()) {
            score++;
        }
        if (answerTwoOptionOne.isChecked() && answerTwoOptionTwo.isChecked()) {
            score++;
            if (answerTwoOptionThree.isChecked() || answerTwoOptionFour.isChecked()) {
                score--;
            }
        }
        if (answerFourString.equalsIgnoreCase("canada")) {
            score++;
        }

        return score;
    }

    //getting the score toast by clicking the score button
    public void getScore(View v) {
        int score = quizScoring();
        String name = getName();
        if (name.equals("")) {
            Toast errorToast = Toast.makeText(this, "Please enter a name to get score!", LENGTH_LONG);
            errorToast.show();
        } else {
            quizScoring();
            String scoreString = String.valueOf(score);
            Toast scoreToast = Toast.makeText(this, name + ", your score is: " + scoreString + "/4", LENGTH_LONG);
            scoreToast.show();
        }
    }

    //sharing the score to any app that uses EXTRA_SUBJECT or EXTRA_TEXT by clicking the share button
    public void shareScore() {
        //get values from edit text field and adding the string subject and message for sharing
        int score = quizScoring();
        String scoreString = String.valueOf(score);
        EditText extraCreditAnswer = findViewById(R.id.questionExtraCreditAnswerEditText);
        String extraCreditAnswerString = extraCreditAnswer.getText().toString();
        String name = getName();
        String subject = name + ", your score is ready!";
        String message = name + ", your score is: " + scoreString;
        message += "\n Your extra credit answer is: " + extraCreditAnswerString;

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("*/*");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, message);

        if (sharingIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(sharingIntent);
        }

    }
}
