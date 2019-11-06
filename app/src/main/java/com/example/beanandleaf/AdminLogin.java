package com.example.beanandleaf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beanandleaf.ui.login.LoginActivity;

public class AdminLogin extends AppCompatActivity {

    private static final String ADMIN_PASS = "Admin123!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        final TextView adminPasswordView = findViewById(R.id.admin_password);
        Button loginButton = findViewById(R.id.admin_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = adminPasswordView.getText().toString();
                if (password.contentEquals("")) {
                    return;
                }
                if (!password.contentEquals(ADMIN_PASS)) {
                    Toast t = Toast.makeText(getApplicationContext(), "Admin password is incorrect", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                    return;
                }
                Intent i = new Intent(AdminLogin.this, AdminHome.class);
                startActivity(i);
            }
        });

    }
}
