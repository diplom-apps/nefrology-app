package com.example.nefrology_app.helpers;

import android.content.Context;
import android.content.SharedPreferences;


public class AuthHelper {

    private Context context;

    public AuthHelper(Context context) {
        this.context = context;
    }

    public boolean isAuth(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userData", Context.MODE_PRIVATE);
        return sharedPreferences.contains("email") && sharedPreferences.contains("password");
    }

    public String getEmail(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userData", Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", "error");
    }

    public void logout(){
        clearCache();
    }

    public void saveInCache(String email, String password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }

    private void clearCache(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("email");
        editor.remove("password");
        editor.apply();
    }
}
