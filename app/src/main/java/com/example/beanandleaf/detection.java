package com.example.beanandleaf;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.app.AlertDialog;

import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beanandleaf.ui.login.LoginActivity;

public class detection extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection);

        final Button button = findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openDialog();
            }
        });
    }

    public void openDialog(){
        Context classContext = this;
        System.out.println("in Open Dialog");
        AlertDialog alert = new AlertDialog.Builder(classContext).create();
        alert.setTitle("Location");
        alert.setMessage("You have been detected in X location");
        alert.show();
    }

}
