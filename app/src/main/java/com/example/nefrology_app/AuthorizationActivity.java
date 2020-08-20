package com.example.nefrology_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nefrology_app.entity.User;
import com.example.nefrology_app.helpers.AuthHelper;
import com.example.nefrology_app.helpers.Validator;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AuthorizationActivity extends AppCompatActivity
        implements OnClickListener {

    private Button authBtn;
    private Button regBtn;

    private EditText email;
    private EditText password;

    private AuthHelper authHelper = new AuthHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        initInputs();
        initButtons();
        setListeners();
    }

//    private void setTextWatchers() {
//        email.addTextChangedListener(this);
//        password.addTextChangedListener(this);
//    }

    private void initInputs() {
        email = (EditText) findViewById(R.id.auth_email);
        password = (EditText) findViewById(R.id.auth_password);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.auth_authorization:
                authAction();
                break;
            case R.id.auth_registration:
                Intent regActivity = new Intent(AuthorizationActivity.this, RegistrationActivity.class);
                startActivity(regActivity);
                break;
        }
    }

    private void authAction() {
        if (!isValid()) return;
        final String userEmail = email.getText().toString();
        final String userPassword = password.getText().toString();

        final OkHttpClient client = new OkHttpClient();

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("email", userEmail)
                .addFormDataPart("password", userPassword)
                .build();

        final Request request = new Request.Builder()
                .url(getResources().getString(R.string.connection_url) + "/authorization")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                makeToast("Попробуйте еще раз.");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                String result = response.body().string();

                Log.i("TEST", result);
                switch (result) {
                    case ("email_error"):
                        AuthorizationActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                makeToast("Ошибка ввода email.");
                            }
                        });
                        break;
                    case ("password_error"):
                        AuthorizationActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                makeToast("Ошибка ввода пароля.");
                            }
                        });
                        break;
                    case ("auth_success"):
                        AuthorizationActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                authHelper.saveInCache(userEmail, userPassword);
                                Intent authActivity = new Intent(AuthorizationActivity.this, MainActivity.class);
                                startActivity(authActivity);
                            }
                        });
                        break;
                    default:
                        AuthorizationActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                makeToast("Попробуйте еще раз.");
                            }
                        });
                        break;
                }
            }
        });

        // заглушка
        /*if(userEmail.equalsIgnoreCase("qwe@gmail.com") && userPassword.equals("123")){
            authHelper.saveInCache(userEmail, userPassword);
            Intent authActivity = new Intent(AuthorizationActivity.this, MainActivity.class);
            startActivity(authActivity);
        } else {
            makeToast("Попробуйте еще раз.");
        }*/
    }

    public void initButtons() {
        authBtn = (Button)findViewById(R.id.auth_authorization);
        regBtn = (Button)findViewById(R.id.auth_registration);
    }

    public void setListeners(){
        authBtn.setOnClickListener(this);
        regBtn.setOnClickListener(this);
    }

    private void makeToast(String message){
        Toast.makeText(AuthorizationActivity.this, message, Toast.LENGTH_LONG).show();
    }


    private boolean isValid(){

        if (!Validator.pattern("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])",
                email.getText().toString())) {
            email.setError("Проверьте правильность ввода Email");
            return false;
        } else {
            email.setError(null);
        }

        if (!Validator.between(3, 20, password.getText().length())) {
            password.setError("От 3 до 20 символов");
            return false;
        } else {
            password.setError(null);
        }

        return true;
    }
}