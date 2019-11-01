package com.example.beanandleaf;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

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
        Button btn = findViewById(R.id.testToast);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                // Displaying posotioned Toast message
                Toast t = Toast.makeText(getApplicationContext(),
                        "Your caffeine intake amount is 375 mg. Daily recommended intake does not exceed 400mg.",
                        Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                TextView view = (TextView) t.getView().findViewById(android.R.id.message);
                view.setShadowLayer(0, 0, 0, Color.LTGRAY);
                view.setTextColor(Color.BLACK);
                t.show();
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
}