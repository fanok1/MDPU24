package com.fanok.mdpu24;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    private EditText mLogin;
    private EditText mPassword;
    private TextInputLayout layoutLogin;
    private TextInputLayout layoutPassword;
    private ProgressBar progressBar;

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
        progressBar = findViewById(R.id.login_progress);
        final String URL = getResources().getString(R.string.login_api);

        mButtonSignUp.setOnClickListener((View view) -> {
            RegistrationActivity.editTextEmpty(false, getLogin(), layoutLogin, getResources().getString(R.string.error_incorrect_data));
            RegistrationActivity.editTextEmpty(false, getPassword(), layoutPassword, getResources().getString(R.string.error_incorrect_data));
            if (layoutLogin.isErrorEnabled() || layoutPassword.isErrorEnabled()) return;
            JSONObject request = new JSONObject();
            try {
                request.put("user", getLogin());
                request.put("password", getPassword());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressBar.setVisibility(ProgressBar.VISIBLE);
            JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                    (Request.Method.POST, URL, request, response -> {
                        try {
                            if (response.getInt("status") == 1) {
                                startActivity(new Intent(this, MainActivity.class));
                            } else {
                                progressBar.setVisibility(ProgressBar.INVISIBLE);
                                Toast.makeText(getApplicationContext(), response.getString("massage"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error -> {
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                    });
            MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
        });

        mButtonRegistration.setOnClickListener((View view) -> {
            Intent intent = new Intent(view.getContext(), RegistrationActivity.class);
            intent.putExtra("login", getLogin());
            startActivity(intent);
        });

        mLogin.setOnFocusChangeListener((View view, boolean b) -> RegistrationActivity.editTextEmpty(b, getLogin(), layoutLogin, getResources().getString(R.string.error_incorrect_data)));

        mPassword.setOnFocusChangeListener((View view, boolean b) -> RegistrationActivity.editTextEmpty(b, getPassword(), layoutPassword, getResources().getString(R.string.error_incorrect_data)));

        mResetPassword.setOnClickListener(view -> {
            startActivity(new Intent(view.getContext(), ResetPaswordActivity.class));
        });
    }


    private String getPassword() {
        return mPassword.getText().toString();
    }

    private String getLogin() {
        return mLogin.getText().toString();
    }

}

