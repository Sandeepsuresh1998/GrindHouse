package com.example.beanandleaf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.beanandleaf.ui.login.LoginActivity;

import java.util.ArrayList;

import database.DatabaseHelper;
import model.Store;

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
        final RadioGroup genderOptionsRadioButton = findViewById(R.id.genderOptions);

        final DatabaseHelper db = new DatabaseHelper(this);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedUserTypeId = userTypeRadioButton.getCheckedRadioButtonId();
                int selectedGenderId = genderOptionsRadioButton.getCheckedRadioButtonId();
                RadioButton userRadio = findViewById(selectedUserTypeId);
                RadioButton genderRadio = findViewById(selectedGenderId);

                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String email = emailEditText.getText().toString().toLowerCase(); //Making the email all lowercase
                String userType = userRadio.getText().toString();
                String gender = genderRadio.getText().toString();

                boolean isUsernameValid = isUsernameValid(username);
                boolean isPasswordValid = isPasswordValid(password);
                boolean isEmailValid = isEmailValid(email);
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
                    String hashedPassword = DatabaseHelper.generateHash(password);
                    String verifyResult = db.verifyUser(email, hashedPassword, userType);
                    if (!verifyResult.contentEquals("NULL")) {
                        Toast.makeText(getApplicationContext(),
                                "A " + userType.toLowerCase() + " account already exists with that email",
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        db.insertUser(username, email, hashedPassword, userType, gender);
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("username", username);
                        editor.putString("password", hashedPassword);
                        editor.putString("email", email);
                        editor.putString("userType", userType);
                        editor.putString("gender", gender);
                        if (userType.contentEquals("Merchant")) {
                            ArrayList<Store> stores = db.getStores(db.getUserId(email, userType));
                            if (!stores.isEmpty())
                                editor.putInt("selectedStore", stores.get(0).getStoreID());
                            else
                                editor.putInt("selectedStore", -1);
                        }
                        editor.commit();
                        Intent i = null;
                        i = new Intent(Register.this, Navigation.class);
                        if (userType.contentEquals("Merchant")) {
                            editor.putBoolean("addStore",false);
                        }

                        if (i != null)
                            startActivity(i);
                    }

                }

            }
        });
    }

    public void sendToLogin(View v) {
       Intent login = new Intent(Register.this, LoginActivity.class);
       startActivity(login);
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
