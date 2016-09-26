package com.nougust3.a9goals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class GoalActivity extends AppCompatActivity {

    public final static String GOAL = "GOAL";
    public final static String TYPE = "TYPE";
    private Intent answerIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        Button saveBtn = (Button) findViewById(R.id.button);
        RadioButton radioMain = (RadioButton) findViewById(R.id.radio_main);
        RadioButton radioSecondary = (RadioButton) findViewById(R.id.radio_secondry);
        RadioButton radioOver = (RadioButton) findViewById(R.id.radio_over);
        final EditText goalEdit = (EditText) findViewById(R.id.editGoal);

        answerIntent = new Intent();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerIntent.putExtra(GOAL, goalEdit.getText().toString());
                setResult(RESULT_OK, answerIntent);
                finish();
            }
        });

        radioMain.setChecked(true);
        answerIntent.putExtra(TYPE, 1);

        if(getIntent().getIntExtra("MAIN_COUNT", 0) >= 1) {
            radioMain.setEnabled(false);
            radioSecondary.setChecked(true);
            answerIntent.putExtra(TYPE, 2);
        }

        if(getIntent().getIntExtra("SECONDARY_COUNT", 0) >= 3) {
            radioSecondary.setEnabled(false);
            radioOver.setChecked(true);
            answerIntent.putExtra(TYPE, 3);
        }

        radioMain.setOnClickListener(listener);
        radioSecondary.setOnClickListener(listener);
        radioOver.setOnClickListener(listener);

        goalEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                answerIntent.putExtra(GOAL, goalEdit.getText().toString());
                setResult(RESULT_OK, answerIntent);
                finish();
                return false;
            }
        });
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RadioButton rb = (RadioButton) view;
            switch (rb.getId()) {
                case R.id.radio_main: answerIntent.putExtra(TYPE, 1);
                    break;
                case R.id.radio_secondry: answerIntent.putExtra(TYPE, 2);
                    break;
                case R.id.radio_over: answerIntent.putExtra(TYPE, 3);
                    break;
                default: break;
            }
        }
    };
}
