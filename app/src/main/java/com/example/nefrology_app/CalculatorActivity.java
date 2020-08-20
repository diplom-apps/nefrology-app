package com.example.nefrology_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.nefrology_app.utils.Calculator;

import java.util.Locale;
//D:\nefrology\app\build\outputs\apk\debug\app-debug.apk
public class CalculatorActivity extends AppCompatActivity {

    private Button resultBtn;
    private RadioGroup genderRadioGroup;
    private RadioButton genderRadioButton;
    private RadioGroup raceRadioGroup;
    private RadioButton raceRadioButton;
    private EditText age;
    private EditText kreatininLevel;
    private TextView calcResult;

    private Calculator calculator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        initButtons();
        initRadio();
        initInputs();
        initTextViews();
        setListeners();
    }

    private void initTextViews() {
        calcResult = (TextView)findViewById(R.id.calc_result);
    }

    private void initInputs() {
        age = (EditText) findViewById(R.id.calc_age);
        kreatininLevel = (EditText) findViewById(R.id.calc_creatinin);
    }

    private void initRadio() {
        genderRadioGroup = (RadioGroup) findViewById(R.id.calc_gender_group);
        raceRadioGroup = (RadioGroup) findViewById(R.id.calc_race_group);
    }

    private void setListeners() {
        resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int genderId = genderRadioGroup.getCheckedRadioButtonId();
                genderRadioButton = (RadioButton) findViewById(genderId);

                int raceId = raceRadioGroup.getCheckedRadioButtonId();
                raceRadioButton = (RadioButton) findViewById(raceId);

                Calculator.Gender gender =
                        genderRadioButton.getText().toString().equals(getString(R.string.male)) ?
                        Calculator.Gender.MALE : Calculator.Gender.FEMALE;
                Calculator.Race race = raceRadioButton.getText().toString().equals(getString(R.string.white_race)) ?
                        Calculator.Race.WHITE : Calculator.Race.NEGROID;

                calculator = new Calculator(
                        gender, race,
                        Integer.parseInt(age.getText().toString()),
                        Integer.parseInt(kreatininLevel.getText().toString())
                );

                double result = calculator.getResult();

                calcResult.setText(String.format(Locale.ROOT,"%.2f", result));
            }
        });
    }

    private void initButtons() {
        resultBtn = (Button) findViewById(R.id.calc_calculate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}