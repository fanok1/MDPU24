package com.fanok.mdpu24;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ResetPaswordActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private EditText mPasswordConfirm;
    private TextInputLayout layoutPassword;
    private TextInputLayout layoutPasswordConfirm;
    private TextInputLayout layoutEmail;

    public static final String KEY_LOGIN = "login";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL = "email";

    public String getEmail() {
        return mEmail.getText().toString();
    }
    public String getPassword() {
        return mPassword.getText().toString();
    }
    public String getPasswordConfirm() {
        return mPasswordConfirm.getText().toString();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        mPassword = findViewById(R.id.password);
        mPasswordConfirm = findViewById(R.id.passwordConfirm);
        mEmail = findViewById(R.id.email);
        layoutPassword = findViewById(R.id.layoutPassword);
        layoutPasswordConfirm = findViewById(R.id.layoutPasswordConfirm);
        layoutEmail = findViewById(R.id.layoutEmail);
        Button button = findViewById(R.id.reset_passwoed);

        mPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                RegistrationActivity.editTextEmpty(b, getPassword(), layoutPassword, getResources().getString(R.string.error_field_required));
            }
        });

        mPasswordConfirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                RegistrationActivity.equalsPassword(b, getPasswordConfirm(),getPassword(), layoutPasswordConfirm, getResources().getString(R.string.error_incorrect_password_confirm));
            }
        });

        mEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
            String patterm = "^([a-zA-Z0-9.-_]+@([a-z]+\\.+)+[a-z]+)|((\\+?38)?0(39|67|68|96|97|98|50|66|95|99|63|93|91|92|94)\\d{7})|([a-zA-Z]\\w*)$";
            RegistrationActivity.checkPatern(b, getEmail(), patterm, layoutEmail, getResources().getString(R.string.error_incorrect_data));
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                empty(getPassword(), layoutPassword, getResources().getString(R.string.error_field_required));
                empty(getPasswordConfirm(), layoutPasswordConfirm, getResources().getString(R.string.error_field_required));
                empty(getEmail(), layoutEmail, getResources().getString(R.string.error_field_required));
                if (layoutPassword.isErrorEnabled()||layoutPasswordConfirm.isErrorEnabled()||layoutPasswordConfirm.isErrorEnabled()) return;

                String phonePattern = "^(\\+?38)?0(39|67|68|96|97|98|50|66|95|99|63|93|91|92|94)\\d{7}$";
                String emailPattern = "^[a-zA-Z0-9.-_]+@([a-z]+\\.+)+[a-z]+$";
                String name = getEmail().trim();
                if (name.matches(emailPattern)) insertDataResetPassword(name, getPassword().trim(), KEY_EMAIL);
                else if (name.matches(phonePattern)){
                    name = RegistrationActivity.convertPhoneFormat(name);
                    insertDataResetPassword(name, getPassword().trim(), KEY_PHONE);
                } else insertDataResetPassword(name, getPassword().trim(), KEY_LOGIN);


            }
        });


    }

    private void insertDataResetPassword(String name, String password, String key) {
        //отправить данные на сервер
    }

    public static void empty(String text, TextInputLayout layout, String error) {
        if (text.isEmpty()){
            layout.setErrorEnabled(true);
            layout.setError(error);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
