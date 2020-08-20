package com.example.nefrology_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nefrology_app.helpers.AuthHelper;

public class AccountActivity extends AppCompatActivity {

    private AuthHelper authHelper = new AuthHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // FIXME:: нет информации по внешнему виду личного кабинета и тому,
        //  как отображать данные
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.user_info);
        LinearLayout layout = getLinearLayout("Email: ", authHelper.getEmail());
        linearLayout.addView(layout);
    }


    private LinearLayout getLinearLayout(String name, String data){
        final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);

        TextView nameView = new TextView(this);
        nameView.setGravity(Gravity.CENTER);
        nameView.setTextSize(22);
        nameView.setText(name);

        TextView dataView = new TextView(this);
        dataView.setGravity(Gravity.CENTER);
        dataView.setTextColor(getResources().getColor(R.color.colorBlue));
        dataView.setTextSize(22);
        dataView.setText(data);

        layout.addView(nameView);
        layout.addView(dataView);

        return layout;
    }
}