package com.fanok.mdpu24;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class LoginActivity extends AppCompatActivity {

    private EditText mLogin;
    private EditText mPassword;
    private TextInputLayout layoutLogin;
    private TextInputLayout layoutPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button mButtonSignUp = findViewById(R.id.sign_in_button);
        Button mButtonRegistration = findViewById(R.id.registration_button);
        TextView mResetPassword = findViewById(R.id.rememberPassword);
        mLogin = findViewById(R.id.login);
        mPassword = findViewById(R.id.password);
        layoutLogin = findViewById(R.id.layoutLogin);
        layoutPassword = findViewById(R.id.layoutPassword);

        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });

        mButtonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RegistrationActivity.class);
                intent.putExtra("login", getLogin());
                startActivity(intent);
            }
        });

        mLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                RegistrationActivity.editTextEmpty(b, getLogin(), layoutLogin, getResources().getString(R.string.error_field_required));
            }
        });
        mPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                RegistrationActivity.editTextEmpty(b, getPassword(), layoutPassword, getResources().getString(R.string.error_field_required));
            }
        });
       mResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ResetPaswordActivity.class));
            }
        });

    }


    private String getPassword() {
        return mPassword.getText().toString();
    }

    private String getLogin() {
        return mLogin.getText().toString();
    }
}

