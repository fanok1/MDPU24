package com.fanok.mdpu24;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


public class SartActivity extends AppCompatActivity {
    private static final String PREF_NAME = "mdpu";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences mPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String login = mPref.getString("LOGIN", "");

        if (login.isEmpty()) startActivity(new Intent(this, LoginActivity.class));
        else {
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }
}
