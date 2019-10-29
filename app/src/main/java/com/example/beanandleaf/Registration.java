package com.example.beanandleaf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.beanandleaf.ui.login.LoginActivity;

import database.DatabaseHelper;

public class Registration extends AppCompatActivity {



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
                    Intent mapActivity = new Intent(Registration.this, BottomNavigation.class);
                    startActivity(mapActivity);
                }

            }
        });



    }
}
