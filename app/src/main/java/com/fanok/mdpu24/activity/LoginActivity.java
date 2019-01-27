package com.fanok.mdpu24.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fanok.mdpu24.MySingleton;
import com.fanok.mdpu24.R;
import com.fanok.mdpu24.StartActivity;

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


        mButtonSignUp.setOnClickListener(this::onClick);


        mButtonRegistration.setOnClickListener((View view) -> {
            Intent intent = new Intent(view.getContext(), RegistrationActivity.class);
            intent.putExtra("login", getLogin());
            intent.putExtra("type", "0");
            startActivity(intent);
        });

        mLogin.setOnFocusChangeListener((View view, boolean b) -> RegistrationActivity.editTextEmpty(b, getLogin(), layoutLogin, getResources().getString(R.string.error_incorrect_data)));

        mPassword.setOnFocusChangeListener((View view, boolean b) -> RegistrationActivity.editTextEmpty(b, getPassword(), layoutPassword, getResources().getString(R.string.error_incorrect_data)));

        mResetPassword.setOnClickListener(view -> startActivity(new Intent(view.getContext(), ResetPaswordActivity.class)));

        mPassword.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_ENDCALL) {
                onClick(textView);
            }
            return false;
        });

    }


    private String getPassword() {
        return mPassword.getText().toString();
    }

    private String getLogin() {
        return mLogin.getText().toString();
    }

    private void onClick(View view) {

        final String url = getResources().getString(R.string.server_api) + "signup.php";

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
                (Request.Method.POST, url, request, response -> {
                    try {
                        if (response.getInt("status") == 1) {
                            SharedPreferences preferences = getSharedPreferences(StartActivity.PREF_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("login", response.getString("login"));
                            editor.putString("name", response.getString("name"));
                            editor.putString("photo", response.getString("photo"));
                            editor.putInt("level", response.getInt("level"));
                            editor.apply();
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
    }

}

