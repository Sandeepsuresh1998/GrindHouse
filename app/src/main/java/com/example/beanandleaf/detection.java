package com.example.beanandleaf;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.app.AlertDialog;

import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

public class detection extends AppCompatActivity {
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection);

        button = (Button) findViewById(R.id.locationButton);
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
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }



}
