package com.example.beanandleaf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.example.beanandleaf.ui.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import database.DatabaseHelper;
import model.Order;

//implement the interface OnNavigationItemSelectedListener in your activity class
public class BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        //loading the default fragment
        loadFragment(new MapFragment());

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(this);

        //TO DO: THIS NEEDS TO PULL FROM THE DATABASE AND FIND THE CURRENT AMOUNT OF CAFFEINE AND DISPLAY ACCORDINGLY
        Button caffeineCheckButton = findViewById(R.id.caffeine_check_button);
        caffeineCheckButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                String email = pref.getString("email", null);
                String userType = pref.getString("userType", null);
                Integer userID = db.getUserId(email, userType);

                int caffeineToday = getCaffeineFromOrdersToday(db.getUserOrders(userID));
                if(caffeineToday < 350)
                {
                    // Displaying posotioned Toast message
                    Toast t = Toast.makeText(getApplicationContext(),
                            "Your caffeine intake amount today is " + caffeineToday,
                            Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    t.show();
                }
                else if(caffeineToday >= 350 && caffeineToday <= 400)
                {
                    Toast t = Toast.makeText(getApplicationContext(),
                            "Your caffeine intake amount today is " + caffeineToday + ". You're nearing the daily reccommended limit of 400mg.",
                            Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    t.show();
                }
                else
                    {
                        Toast t = Toast.makeText(getApplicationContext(),
                                "You've exceeded the daily recommended amount of caffeine!.",
                                Toast.LENGTH_LONG);
                        t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        t.show();
                    }

            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_map:
                fragment = new MapFragment();
                break;

            case R.id.navigation_profile:
                fragment = new ProfileFragment();
                break;

            case R.id.navigation_history:
                fragment = new HistoryFragment();
                break;

            case R.id.navigation_recommendations:
                fragment = new RecommendationsFragment();
                break;
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_customer, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public void logout(View v) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.putBoolean("loggedIn", false);
        editor.commit();
        Intent logout = new Intent(BottomNavigation.this, LoginActivity.class);
        startActivity(logout);
    }

    private int getCaffeineFromOrdersToday(ArrayList<Order> orders) {
        int caffeineToday = 0;
        int timeDayAgo = (int) (System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1));
        for (Order order : orders) {
            if (order.getOrderTime() - timeDayAgo > 0) {
                caffeineToday += order.getCaffeine();
            }
        }
        return caffeineToday;
    }
}