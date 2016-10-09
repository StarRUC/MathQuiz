package edu.sjsu.starruc.mathquiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Random;

//public class QuizActivity extends AppCompatActivity
public class QuizActivity extends AppCompatActivity
        implements KeyboardFragment.KeyboardListener{
    int score = 0;
    int questionId = 0, quizType = 0;
    int opnd1 = 0, opnd2 = 0;
    int expectedAns = 0, ans = 0;
    Random random;
    CountDownTimer countDownTimer;
    long millisRemaining = 0;

    QuestionFragment questionFragment;
    KeyboardFragment keyboardFragment;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("score", score);
        outState.putInt("questionId", questionId);
        outState.putInt("quizType", quizType);
        outState.putInt("opnd1", opnd1);
        outState.putInt("opnd2", opnd2);
        outState.putInt("expectedAns", expectedAns);
        outState.putLong("millisRemaining", millisRemaining);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        random = new Random();
        questionFragment = (QuestionFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_question);
        keyboardFragment = (KeyboardFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_keyboard);
//        getSupportFragmentManager().beginTransaction().add(R.layout.activity_quiz, questionFragment);

        Toolbar quizToolbar =
                (Toolbar) findViewById(R.id.quiz_toolbar);
        setSupportActionBar(quizToolbar);
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null) {
            score = savedInstanceState.getInt("score");
            questionId = savedInstanceState.getInt("questionId");
            quizType = savedInstanceState.getInt("quizType");
            opnd1 = savedInstanceState.getInt("opnd1");
            opnd2 = savedInstanceState.getInt("opnd2");
            expectedAns = savedInstanceState.getInt("expectedAns");
            millisRemaining = savedInstanceState.getLong("millisRemaining");
            setQuestionViewAndTiming(millisRemaining);
        }
        else {
            quizType = getIntent().getIntExtra("quizType", 1);
            generateQuestion();
        }

    }


//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//        // Checks the orientation of the screen
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
//            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            countDownTimer.cancel();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure to go back and quit the quiz?")
                    .setCancelable(true)
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int id) {
                            countDownTimer = new CountDownTimer(millisRemaining, 1000) {
                                @Override
                                public void onFinish() {
                                    validateAnswer();
                                }

                                @Override
                                public void onTick(long millisUntilFinished) {
                                    millisRemaining = millisUntilFinished;
                                }
                            }.start();
                        }
                    })
                    .setPositiveButton("Quit", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            Intent intent = new Intent(QuizActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    });

            final AlertDialog goUpAlert= builder.create();
            goUpAlert.show();

        }
        return true;
    }

    private void generateQuestion() {
        questionId++;

        opnd1 = random.nextInt(10);
        if (quizType == 2) {
            opnd2 = random.nextInt(opnd1 + 1);
        }
        else {
            opnd2 = random.nextInt(10);
        }

        ans = 0;
        switch (quizType) {
            case 1:
                expectedAns = opnd1 + opnd2;
                break;
            case 2:
                expectedAns = opnd1 - opnd2;
                break;
            case 3:
                expectedAns = opnd1 * opnd2;
                break;
            default:
                break;
        }

        setQuestionViewAndTiming(5000);

    }

    private void setQuestionViewAndTiming(long leftMillis) {

        questionFragment.setQuestionView(questionId, opnd1, opnd2, quizType);

        countDownTimer = new CountDownTimer(leftMillis, 500) {
            @Override
            public void onFinish() {
                validateAnswer();
            }

            @Override
            public void onTick(long millisUntilFinished) {
                millisRemaining = millisUntilFinished;
            }
        }.start();

    }
    private void validateAnswer() {
        String ansCorrect = "Correct";
        String ansWrong = "Wrong";

        final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

        if (ans == expectedAns) {
            score++;
            builder1.setMessage(ansCorrect);
        }
        else {
            builder1.setMessage(ansWrong);
        }
        ans = 0;
        builder1.setCancelable(true);

        final AlertDialog alert= builder1.create();
        alert.show();

        new CountDownTimer(500, 500) {
            @Override
            public void onFinish() {
                alert.dismiss();
            }

            @Override
            public void onTick(long millisUntilFinished) {

            }
        }.start();


        if (questionId < 3) {
            generateQuestion();
        }
        else {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            builder2.setMessage("Total Score: " + score + "/10")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            Intent intent = new Intent(QuizActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    });

            final AlertDialog quizResultAlert= builder2.create();
//            AlertDialog quizResultAlert= builder2.create();
            new CountDownTimer(1000, 1000) {
                @Override
                public void onFinish() {
                    quizResultAlert.show();
                }

                @Override
                public void onTick(long millisUntilFinished) {

                }
            }.start();
        }
    }



    public void pressDigit(int digit) {
        ans = ans * 10 + digit;
        questionFragment.setAnswerText(String.valueOf(ans));

        if (ans == expectedAns) {
            countDownTimer.cancel();
            validateAnswer();
        }

    }


    public void pressEnter() {
        countDownTimer.cancel();
        validateAnswer();
    }
}

