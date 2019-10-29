package com.example.beanandleaf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import database.DatabaseHelper;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final Button registerButton = findViewById(R.id.register);
        final EditText usernameEditText = findViewById(R.id.name);
        final EditText emailEditText = findViewById(R.id.email);
        final EditText passwordEditText = findViewById(R.id.password);
        final RadioGroup userTypeRadioButton = findViewById(R.id.userType);

        final DatabaseHelper db = new DatabaseHelper(this);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedUserTypeId = userTypeRadioButton.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(selectedUserTypeId);

                boolean isUsernameValid = isUsernameValid(usernameEditText.getText().toString());
                boolean isPasswordValid = isPasswordValid(passwordEditText.getText().toString());
                boolean isEmailValid = isEmailValid(emailEditText.getText().toString());
                if (!isUsernameValid) {
                    Toast.makeText(getApplicationContext(), "Please enter your first and last name separated by a space", Toast.LENGTH_SHORT).show();
                }
                else if (!isEmailValid) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                }
                else if (!isPasswordValid) {
                    Toast.makeText(getApplicationContext(), "Please enter a password >5 characters", Toast.LENGTH_SHORT).show();
                }
                else {
                    String verifyResult = db.verifyUser(emailEditText.getText().toString(),
                            passwordEditText.getText().toString(),
                            radioButton.getText().toString());
                    if (!verifyResult.contentEquals("NULL")) {
                        Toast.makeText(getApplicationContext(),
                                "A" + radioButton.getText().toString() + "account already exists with that email",
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        db.insertUser(usernameEditText.getText().toString(),
                                emailEditText.getText().toString(),
                                passwordEditText.getText().toString(),
                                radioButton.getText().toString());
                        Intent mapActivity = new Intent(Register.this, BottomNavigation.class);
                        startActivity(mapActivity);
                    }

                }

            }
        });
    }

    private boolean isUsernameValid(String username) {
        return username != null && username.split(" ").length > 1;
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.length() > 5;
    }

    private boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        if (email.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        } else {
            return false;
        }
    }
}
