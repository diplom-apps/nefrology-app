package com.example.nefrology_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.nefrology_app.helpers.AuthHelper;
// TODO:: доделать меню, вынести дублирование
public class MainActivity extends AppCompatActivity implements OnClickListener {

    private AuthHelper authHelper = new AuthHelper(this);
    private AppBarConfiguration mAppBarConfiguration;
    private Button accountBtn, calculatorBtn, mapBtn, articlesBtn, logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!authHelper.isAuth()){
            Intent authActivity = new Intent(MainActivity.this, AuthorizationActivity.class);
            startActivity(authActivity);
        }
        initButtons();
        setListeners();

    }

    private void setListeners() {
        accountBtn.setOnClickListener(this);
        calculatorBtn.setOnClickListener(this);
        mapBtn.setOnClickListener(this);
        articlesBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
    }

    private void initButtons() {
        accountBtn = (Button) findViewById(R.id.account);
        calculatorBtn = (Button) findViewById(R.id.calculator);
        mapBtn = (Button) findViewById(R.id.map);
        articlesBtn = (Button) findViewById(R.id.articles);
        logoutBtn = (Button) findViewById(R.id.logout);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.account:
                Intent accountActivity = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(accountActivity);
                break;
            case R.id.calculator:
                Intent calculatorActivity = new Intent(MainActivity.this, CalculatorActivity.class);
                startActivity(calculatorActivity);
                break;
            case R.id.map:
                Intent mapActivity = new Intent(MainActivity.this, MapActivity.class);
                startActivity(mapActivity);
                break;
            case R.id.articles:
                Intent articlesActivity = new Intent(MainActivity.this, ArticlesActivity.class);
                startActivity(articlesActivity);
                break;
            case R.id.logout:
                authHelper.logout();
                Intent regActivity = new Intent(MainActivity.this, AuthorizationActivity.class);
                startActivity(regActivity);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}