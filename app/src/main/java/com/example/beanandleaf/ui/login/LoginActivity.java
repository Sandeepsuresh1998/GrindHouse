package com.example.beanandleaf.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Gravity;
import android.view.View;


import com.example.beanandleaf.BottomNavigation;
import com.example.beanandleaf.MerchantBottomNav;
import com.example.beanandleaf.R;
import com.example.beanandleaf.Register;

import database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button registerButton = findViewById(R.id.link_signup);
        final RadioGroup userTypeRadioButton = findViewById(R.id.userType);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);


        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    int selectedUserTypeId = userTypeRadioButton.getCheckedRadioButtonId();
                    final RadioButton radioButton = findViewById(selectedUserTypeId);
                    updateUiWithUser(loginResult.getSuccess(), radioButton.getText().toString());
                    setSharedPreferences(usernameEditText.getText().toString(), radioButton.getText().toString());
                    setResult(Activity.RESULT_OK);
                    finish();
                }

            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        final DatabaseHelper db = new DatabaseHelper(this);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    int selectedUserTypeId = userTypeRadioButton.getCheckedRadioButtonId();
                    final RadioButton radioButton = findViewById(selectedUserTypeId);
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString(),
                            radioButton.getText().toString(),
                            db);

                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedUserTypeId = userTypeRadioButton.getCheckedRadioButtonId();
                final RadioButton radioButton = findViewById(selectedUserTypeId);
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        radioButton.getText().toString(),
                        db);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerActivity = new Intent(LoginActivity.this, Register.class);
                startActivity(registerActivity);
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model, String userType) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        Intent homeScreen = null;
        if (userType.contentEquals("Customer")) {
            homeScreen = new Intent(LoginActivity.this, BottomNavigation.class);
        }
        else if (userType.contentEquals("Merchant")) {
            homeScreen = new Intent(LoginActivity.this, MerchantBottomNav.class);
        }
        if (homeScreen != null) {
            startActivity(homeScreen);
        }

    }

    private void showLoginFailed(String errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void setSharedPreferences(String email, String userType) {
        DatabaseHelper db = new DatabaseHelper(this);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("loggedIn", true);
        editor.putString("username", db.getUserName(email, userType));
        editor.putString("password", db.getUserPassword(email, userType));
        editor.putString("email", email);
        editor.putString("userType", userType);
        editor.putString("gender", db.getUserGender(email, userType));
        editor.commit();
    }
}
