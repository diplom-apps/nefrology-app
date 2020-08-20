package com.example.nefrology_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
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

import static javax.xml.transform.OutputKeys.MEDIA_TYPE;

public class RegistrationActivity extends AppCompatActivity
        implements OnClickListener {


    private Button authBtn;
    private Button regBtn;

    private EditText email;
    private EditText password;

    private AuthHelper authHelper = new AuthHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initInputs();
        initButtons();
        setListeners();
    }

    private void initInputs() {
        email = (EditText) findViewById(R.id.reg_email);
        password = (EditText) findViewById(R.id.reg_password);
    }

    private void setListeners() {
        authBtn.setOnClickListener(this);
        regBtn.setOnClickListener(this);
    }

    private void initButtons() {
        authBtn = (Button)findViewById(R.id.reg_authorization);
        regBtn = (Button)findViewById(R.id.reg_registration);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reg_authorization:
                Intent authActivity = new Intent(RegistrationActivity.this, AuthorizationActivity.class);
                startActivity(authActivity);
                break;
            case R.id.reg_registration:
                registrationAction();
                break;
        }
    }

    private void registrationAction() {
        if (!isValid()) return;
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        final OkHttpClient client = new OkHttpClient();

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("email", userEmail)
                .addFormDataPart("password", userPassword)
                .build();

        final Request request = new Request.Builder()
                .url(getResources().getString(R.string.connection_url) + "/registration")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i("TEST", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                String result = response.body().string();

                Log.i("TEST", result);

                switch (result) {
                    case ("registration_error"):
                        RegistrationActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                makeToast("Ваш email уже зарегистрирован.");
                            }
                        });
                        break;
                    case ("registration_success"):
                        RegistrationActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                makeToast("Вы успешно зарегистрированы.");
                                Intent regActivity = new Intent(RegistrationActivity.this, AuthorizationActivity.class);
                                startActivity(regActivity);
                            }
                        });
                        break;
                    default:
                        RegistrationActivity.this.runOnUiThread(new Runnable() {
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
        /*makeToast("Вы успешно зарегистрированы.");
        Intent regActivity = new Intent(RegistrationActivity.this, AuthorizationActivity.class);
        startActivity(regActivity);*/

    }

    private void makeToast(String message){
        Toast.makeText(RegistrationActivity.this, message, Toast.LENGTH_LONG).show();
    }

    // TODO:: подключить библиатеку - валидатор
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