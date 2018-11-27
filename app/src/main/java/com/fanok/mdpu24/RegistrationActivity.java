package com.fanok.mdpu24;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;


public class RegistrationActivity extends AppCompatActivity {
    private Button mButtonRegistration;
    private EditText mLogin;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mPassword;
    private EditText mPasswordConfirm;
    private EditText mPhone;
    private EditText mEmail;
    private TextInputLayout layoutLogin;
    private TextInputLayout layoutFirstName;
    private TextInputLayout layoutLastName;
    private TextInputLayout layoutPassword;
    private TextInputLayout layoutPasswordConfirm;
    private TextInputLayout layoutPhone;
    private TextInputLayout layoutEmail;
    private EditText mGroup;

    public static String groupName;

    public String getLogin() {
        return mLogin.getText().toString();
    }

    public String getFirstName() {
        return mFirstName.getText().toString();
    }

    public String getLastName() {
        return mLastName.getText().toString();
    }

    public String getPassword() {
        return mPassword.getText().toString();
    }

    public String getPasswordConfirm() {
        return mPasswordConfirm.getText().toString();
    }

    public String getPhone() {
        return mPhone.getText().toString();
    }

    public String getEmail() {
        return mEmail.getText().toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGroup.setText(groupName);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        mButtonRegistration = findViewById(R.id.registration_button);
        mLogin = findViewById(R.id.login);
        mFirstName = findViewById(R.id.firstName);
        mLastName = findViewById(R.id.lastName);
        mPassword = findViewById(R.id.password);
        mPasswordConfirm = findViewById(R.id.passwordConfirm);
        mPhone = findViewById(R.id.phone);
        mEmail = findViewById(R.id.email);
        layoutLogin = findViewById(R.id.layoutLogin);
        layoutFirstName = findViewById(R.id.layoutFirstName);
        layoutLastName = findViewById(R.id.layoutLastName);
        layoutPassword = findViewById(R.id.layoutPassword);
        layoutPasswordConfirm = findViewById(R.id.layoutPasswordConfirm);
        layoutPhone = findViewById(R.id.layoutPhone);
        layoutEmail = findViewById(R.id.layoutEmail);
        mGroup = findViewById(R.id.group);

        init();


    }

    private void init() {

        groupName = "";

        Bundle arguments = getIntent().getExtras();
        if (arguments != null)
            mLogin.setText(Objects.requireNonNull(arguments.get("login")).toString());

        mGroup.setKeyListener(null);

        mButtonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });

        mLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (mLogin.getText().toString().length() == 0) {
                        layoutLogin.setErrorEnabled(true);
                        layoutLogin.setError(getResources().getString(R.string.error_field_required));
                    } else layoutLogin.setErrorEnabled(false);
                } else layoutLogin.setErrorEnabled(false);
            }
        });

        mFirstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (mFirstName.getText().toString().length() == 0) {
                        layoutFirstName.setErrorEnabled(true);
                        layoutFirstName.setError(getResources().getString(R.string.error_field_required));
                    } else layoutFirstName.setErrorEnabled(false);
                } else layoutFirstName.setErrorEnabled(false);
            }
        });

        mLastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (mLastName.getText().toString().length() == 0) {
                        layoutLastName.setErrorEnabled(true);
                        layoutLastName.setError(getResources().getString(R.string.error_field_required));
                    } else layoutLastName.setErrorEnabled(false);
                } else layoutLastName.setErrorEnabled(false);
            }
        });

        mPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (mPassword.getText().toString().length() == 0) {
                        layoutPassword.setErrorEnabled(true);
                        layoutPassword.setError(getResources().getString(R.string.error_field_required));
                    } else layoutPassword.setErrorEnabled(false);
                } else layoutPassword.setErrorEnabled(false);
            }
        });

        mPasswordConfirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (!mPasswordConfirm.getText().toString().equals(mPassword.getText().toString())) {
                        layoutPasswordConfirm.setErrorEnabled(true);
                        layoutPasswordConfirm.setError(getResources().getString(R.string.error_incorrect_password_confirm));
                    } else layoutPasswordConfirm.setErrorEnabled(false);
                } else layoutPasswordConfirm.setErrorEnabled(false);
            }
        });

        mPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String phone = mPhone.getText().toString();
                String patterm = "^(\\+?38)?0(39|67|68|96|97|98|50|66|95|99|63|93|91|92|94)\\d{7}$";
                if (!b) {
                    if (phone.length() == 0) {
                        layoutPhone.setErrorEnabled(true);
                        layoutPhone.setError(getResources().getString(R.string.error_field_required));
                    } else if (!phone.trim().matches(patterm)) {
                        layoutPhone.setErrorEnabled(true);
                        layoutPhone.setError(getResources().getString(R.string.error_incorrect_phone));
                    } else layoutPhone.setErrorEnabled(false);
                } else layoutPhone.setErrorEnabled(false);
            }
        });

        mEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String email = mEmail.getText().toString();
                String patterm = "^[a-zA-Z0-9.-_]+@([a-z]+\\.+)+[a-z]+$";
                if (!b) {
                    if (email.length() == 0) {
                        layoutEmail.setErrorEnabled(true);
                        layoutEmail.setError(getResources().getString(R.string.error_field_required));
                    } else if (!email.trim().matches(patterm)) {
                        layoutEmail.setErrorEnabled(true);
                        layoutEmail.setError(getResources().getString(R.string.error_incorrect_email));
                    } else layoutEmail.setErrorEnabled(false);
                } else layoutEmail.setErrorEnabled(false);
            }
        });

        mGroup.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showPopup(view);
                }
            }
        });

        mGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });
    }

    private void showPopup(View view) {
        Intent intent = new Intent(view.getContext(), PopupGroupSearchActivity.class);
        startActivity(intent);
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
