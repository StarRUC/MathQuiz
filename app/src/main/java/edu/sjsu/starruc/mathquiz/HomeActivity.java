package edu.sjsu.starruc.mathquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button addQuizBtn = (Button) findViewById(R.id.add_quiz_btn);
        Button subtractQuizBtn = (Button) findViewById(R.id.subtract_quiz_btn);
        Button multiplyQuizBtn = (Button) findViewById(R.id.multiply_quiz_btn);
        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, QuizActivity.class);
                int quizType = 0;
//                Bundle bdl = new Bundle();
                switch (v.getId()) {
                    case R.id.add_quiz_btn:
                        quizType = 1;
//                        bdl.putInt("quizType", 1); //Set Quiz Type: 1 for add
                        break;
                    case R.id.subtract_quiz_btn:
                        quizType = 2;
//                        bdl.putInt("quizType", 2); //Set Quiz Type: 2 for subtract
                        break;
                    case R.id.multiply_quiz_btn:
                        quizType = 3;
//                        bdl.putInt("quizType", 3); //Set Quiz Type: 3 for multiply
                        break;
                    default:
                        quizType = 0;
//                        bdl.putInt("quizType", 0); //Set Quiz Type: 0 for default
                }

                intent.putExtra("quizType", quizType);
//                intent.putExtras(bdl); //Put your Quiz Type in Intent
                startActivity(intent);
                finish();
            }
        };

        addQuizBtn.setOnClickListener(listener);
        subtractQuizBtn.setOnClickListener(listener);
        multiplyQuizBtn.setOnClickListener(listener);

        Button quit_button = (Button) findViewById(R.id.quit_btn);
        quit_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }
}

