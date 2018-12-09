package com.fanok.mdpu24;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class RegistrationActivity extends ResetPaswordActivity {
    private Button mButtonRegistration;
    private EditText mLogin;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mPassword;
    private EditText mPasswordConfirm;
    private EditText mPhone;
    private EditText mEmail;
    private EditText mGroup;
    private TextInputLayout layoutPasswordConfirm;
    private TextInputLayout layoutPhone;
    private TextInputLayout layoutEmail;

    private List<EditText> editTextList;
    private List<TextInputLayout> layoutList;


    private String error;

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


        initVar();

        init();


    }

    private void initVar() {


        mButtonRegistration = findViewById(R.id.registration_button);

        mLogin = findViewById(R.id.login);
        mFirstName = findViewById(R.id.firstName);
        mLastName = findViewById(R.id.lastName);
        mPassword = findViewById(R.id.password);
        mPasswordConfirm = findViewById(R.id.passwordConfirm);
        mPhone = findViewById(R.id.phone);
        mEmail = findViewById(R.id.email);
        mGroup = findViewById(R.id.group);
        TextInputLayout layoutLogin = findViewById(R.id.layoutLogin);
        TextInputLayout layoutFirstName = findViewById(R.id.layoutFirstName);
        TextInputLayout layoutLastName = findViewById(R.id.layoutLastName);
        TextInputLayout layoutPassword = findViewById(R.id.layoutPassword);
        layoutPasswordConfirm = findViewById(R.id.layoutPasswordConfirm);
        layoutPhone = findViewById(R.id.layoutPhone);
        layoutEmail = findViewById(R.id.layoutEmail);
        TextInputLayout layoutGroup = findViewById(R.id.layoutGroup);

        error = getResources().getString(R.string.error_field_required);

        editTextList = new ArrayList<>();
        layoutList = new ArrayList<>();


        editTextList.add(mLogin);
        editTextList.add(mFirstName);
        editTextList.add(mLastName);
        editTextList.add(mPassword);
        editTextList.add(mPasswordConfirm);
        editTextList.add(mPhone);
        editTextList.add(mEmail);
        editTextList.add(mGroup);

        layoutList.add(layoutLogin);
        layoutList.add(layoutFirstName);
        layoutList.add(layoutLastName);
        layoutList.add(layoutPassword);
        layoutList.add(layoutPasswordConfirm);
        layoutList.add(layoutPhone);
        layoutList.add(layoutEmail);
        layoutList.add(layoutGroup);

        groupName = "";

    }

    private void init() {


        Bundle arguments = getIntent().getExtras();
        if (arguments != null)
            mLogin.setText(Objects.requireNonNull(arguments.get("login")).toString());

        mGroup.setKeyListener(null);

        mButtonRegistration.setOnClickListener((View view) -> {
            for (int i = 0; i < editTextList.size(); i++) {
                ResetPaswordActivity.empty(editTextList.get(i).getText().toString(), layoutList.get(i), getResources().getString(R.string.error_field_required));
            }
            for (int i = 0; i < layoutList.size(); i++) {
                if (layoutList.get(i).isErrorEnabled()) return;
            }
        });

        for (int i = 0; i < editTextList.size(); i++) {
            EditText editText = editTextList.get(i);
            TextInputLayout layout = layoutList.get(i);
            editText.setOnFocusChangeListener((View view, boolean b) -> editTextEmpty(b, editText.getText().toString(), layout, error));
        }

        mGroup.setOnFocusChangeListener((View view, boolean b) -> {
            if (b) {
                showPopup(view);
            }
        });

        mPasswordConfirm.setOnFocusChangeListener((View view, boolean b) -> equalsPassword(b, getPasswordConfirm(), getPassword(), layoutPasswordConfirm, getResources().getString(R.string.error_incorrect_password_confirm)));

        mPhone.setOnFocusChangeListener((View view, boolean b) -> {
            String patterm = "^(\\+?38)?0(39|67|68|96|97|98|50|66|95|99|63|93|91|92|94)\\d{7}$";
            checkPatern(b, getPhone(), patterm, layoutPhone, getResources().getString(R.string.error_incorrect_phone));
            if (!b || !layoutPhone.isErrorEnabled()) mPhone.setText(convertPhoneFormat(getPhone()));
        });


        mEmail.setOnFocusChangeListener((View view, boolean b) -> {
            String patterm = "^[a-zA-Z0-9.-_]+@([a-z]+\\.+)+[a-z]+$";
            checkPatern(b, getEmail(), patterm, layoutEmail, getResources().getString(R.string.error_incorrect_email));
        });


        mGroup.setOnClickListener(this::showPopup);
    }

    public static void equalsPassword(boolean b, String passwordConfirm, String password, TextInputLayout layout, String error) {
        if (!b && !passwordConfirm.equals(password)) {
            layout.setErrorEnabled(true);
            layout.setError(error);
        } else layout.setErrorEnabled(false);
    }

    public static void checkPatern(boolean b, String text, String patterm, TextInputLayout layout, String error) {
        if (!b && !text.trim().matches(patterm)) {
            layout.setErrorEnabled(true);
            layout.setError(error);
        } else layout.setErrorEnabled(false);
    }

    public static String convertPhoneFormat(String phone) {
        if (phone.length() == 12) return "+" + phone;
        else if (phone.length() == 10) return "+38" + phone;
        else return phone;
    }

    public static void editTextEmpty(boolean b, String text, TextInputLayout layout, String error) {
        if (!b && text.length() == 0) {
            layout.setErrorEnabled(true);
            layout.setError(error);
        } else layout.setErrorEnabled(false);
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
