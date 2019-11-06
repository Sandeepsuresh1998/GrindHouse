package com.example.beanandleaf;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.beanandleaf.ui.login.LoginActivity;

public class LandingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        final Button loginButton = findViewById(R.id.link_login);
        final Button registerButton = findViewById(R.id.link_signup);
        final Button adminButton = findViewById(R.id.link_admin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginActivity = new Intent(LandingPage.this, LoginActivity.class);
                startActivity(loginActivity);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerActivity = new Intent(LandingPage.this, Register.class);
                startActivity(registerActivity);
            }
        });
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adminLoginActivity = new Intent(LandingPage.this, AdminLogin.class);
                startActivity(adminLoginActivity);
            }
        });
    }
}
