package edu.sjsu.starruc.mathquiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class QuizActivity extends AppCompatActivity {
    int score = 0;
    int questionId = 0, quizType = 0;
    int opnd1 = 0, opnd2 = 0;
    int expectedAns = 0, ans = 0;
    Random random;
    CountDownTimer countDownTimer;

    TextView txtQid, txtOpnd1, txtOpnd2, txtOptr;
    EditText ansEditTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                countDownTimer.
//
//                // go to previous screen when app icon in action bar is clicked
//                Intent intent = new Intent(this, HomeActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void init() {
        // Get widget objs
        txtQid = (TextView) findViewById(R.id.question_id_txt);
        txtOpnd1 = (TextView) findViewById(R.id.opnd1_txt);
        txtOpnd2 = (TextView) findViewById(R.id.opnd2_txt);
        txtOptr = (TextView) findViewById(R.id.optr_txt);
        ansEditTxt = (EditText) findViewById(R.id.ans_txt);

        // Get Quiz Type
        quizType = getIntent().getIntExtra("quizType", 1);
//        Bundle bdl = getIntent().getExtras();
//        quizType = bdl.getInt("quizType");

        random = new Random();

        // Initialize the widget content in the activity.
        setQuestionView();

        // Define Listener for Number Pad
        View.OnClickListener numberKeyListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNumberPadInput(v);
            }
        };

        // Define Listener for Enter Key
        View.OnClickListener enterKeyListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                validateAnswer();
            }
        };

        findViewById(R.id.pad_key_0).setOnClickListener(numberKeyListener);
        findViewById(R.id.pad_key_1).setOnClickListener(numberKeyListener);
        findViewById(R.id.pad_key_2).setOnClickListener(numberKeyListener);
        findViewById(R.id.pad_key_3).setOnClickListener(numberKeyListener);
        findViewById(R.id.pad_key_4).setOnClickListener(numberKeyListener);
        findViewById(R.id.pad_key_5).setOnClickListener(numberKeyListener);
        findViewById(R.id.pad_key_6).setOnClickListener(numberKeyListener);
        findViewById(R.id.pad_key_7).setOnClickListener(numberKeyListener);
        findViewById(R.id.pad_key_8).setOnClickListener(numberKeyListener);
        findViewById(R.id.pad_key_9).setOnClickListener(numberKeyListener);
        findViewById(R.id.pad_key_enter).setOnClickListener(enterKeyListener);

    }

    private void getNumberPadInput(View v) {
        int digit = 0;

        switch (v.getId()) {
            case R.id.pad_key_0:
                digit = 0;
                break;
            case R.id.pad_key_1:
                digit = 1;
                break;
            case R.id.pad_key_2:
                digit = 2;
                break;
            case R.id.pad_key_3:
                digit = 3;
                break;
            case R.id.pad_key_4:
                digit = 4;
                break;
            case R.id.pad_key_5:
                digit = 5;
                break;
            case R.id.pad_key_6:
                digit = 6;
                break;
            case R.id.pad_key_7:
                digit = 7;
                break;
            case R.id.pad_key_8:
                digit = 8;
                break;
            case R.id.pad_key_9:
                digit = 9;
                break;
            default:
                digit = 0;
        }

        ans = ans * 10 + digit;
        ansEditTxt.setText(String.valueOf(ans));
        if (ans == expectedAns) {
            countDownTimer.cancel();
            validateAnswer();
        }
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
            setQuestionView();
        }
        else {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            builder2.setMessage("Total Score: " + score + "/10")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(QuizActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

            final AlertDialog quizResultAlert= builder2.create();
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

    private void setQuestionView()
    {
        questionId++;
        txtQid.setText("Question " + questionId + "/10");

        opnd1 = random.nextInt(10);
        if (quizType == 2) {
            opnd2 = random.nextInt(opnd1 + 1);
        }
        else {
            opnd2 = random.nextInt(10);
        }
        txtOpnd1.setText(String.valueOf(opnd1));
        txtOpnd2.setText(String.valueOf(opnd2));
        switch (quizType) {
            case 1:
                txtOptr.setText("+");
                expectedAns = opnd1 + opnd2;
                break;
            case 2:
                txtOptr.setText("-");
                expectedAns = opnd1 - opnd2;
                break;
            case 3:
                txtOptr.setText("*");
                expectedAns = opnd1 * opnd2;
                break;
            default:
                break;
        }

        ansEditTxt.setText("");

        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onFinish() {
                validateAnswer();
            }

            @Override
            public void onTick(long millisUntilFinished) {

            }
        }.start();
    }
}

