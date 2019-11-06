package com.example.beanandleaf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beanandleaf.ui.login.LoginActivity;

import java.util.ArrayList;

import database.DatabaseHelper;
import model.Store;

public class AdminHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        final TextView storeName = findViewById(R.id.admin_store_name);
        final TextView storeLat = findViewById(R.id.admin_store_lat);
        final TextView storeLon = findViewById(R.id.admin_store_lon);
        final TextView verifStatus = findViewById(R.id.admin_verification);
        final ImageView verifPic = findViewById(R.id.verifPic);
        final Spinner storeDropdown = findViewById(R.id.admin_store_spinner);

        Button verifyButton = findViewById(R.id.verify_store);

        DatabaseHelper db = new DatabaseHelper(this);
        final ArrayList<Store> allStores = db.getStores();
        ArrayList<String> storeNames = new ArrayList<>();
        for (Store store : allStores) {
            storeNames.add(store.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, storeNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storeDropdown.setAdapter(adapter);

        if (allStores.isEmpty()) {
            storeName.setText("");
            storeLat.setText("");
            storeLon.setText("");
            verifStatus.setText("");
            verifPic.setImageBitmap(null);
        }
        else {
            Store firstStore = allStores.get(0);
            storeName.setText(firstStore.getName());
            storeLat.setText(Float.toString(firstStore.getLatitude()));
            storeLon.setText(Float.toString(firstStore.getLongitude()));
            verifPic.setImageBitmap(firstStore.getVerifPic());
            if (firstStore.isVerified()) {
                verifStatus.setText("Verified");
                verifStatus.setTextColor(Color.parseColor("#18E71A"));
            }
            else {
                verifStatus.setText("Not Verified");
                verifStatus.setTextColor(Color.parseColor("#7E0C1A"));
            }
            verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String selectedStoreName = storeDropdown.getSelectedItem().toString();
                    Store selectedStore = null;
                    for (Store store : allStores) {
                        if (store.getName().contentEquals(selectedStoreName)) {
                            selectedStore = store;
                        }
                    }
                    if (!selectedStore.isVerified()) {
                        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                        if (db.updateStoreVerification(selectedStore.getStoreID())) {
                            verifStatus.setText("Verified");
                            verifStatus.setTextColor(Color.parseColor("#18E71A"));
                        }
                    }

                    Toast t= Toast.makeText(getApplicationContext(), selectedStoreName + " has been verified", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }
            });

            storeDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedStoreName = parent.getItemAtPosition(position).toString();
                    Store selectedStore = null;
                    for (Store store : allStores) {
                        if (selectedStoreName.contentEquals(store.getName()))
                            selectedStore = store;
                    }
                    if (selectedStore != null) {
                        storeName.setText(selectedStore.getName());
                        storeLat.setText(Float.toString(selectedStore.getLatitude()));
                        storeLon.setText(Float.toString(selectedStore.getLongitude()));
                        verifPic.setImageBitmap(selectedStore.getVerifPic());
                        if (selectedStore.isVerified()) {
                            verifStatus.setText("Verified");
                            verifStatus.setTextColor(Color.parseColor("#18E71A"));
                        }
                        else {
                            verifStatus.setText("Not Verified");
                            verifStatus.setTextColor(Color.parseColor("#7E0C1A"));
                        }
                    }
                }
                public void onNothingSelected(AdapterView<?> parent) {
                    // IGNORE
                }
            });
        }
    }

    public void logout(View v) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.putBoolean("loggedIn", false);
        editor.commit();
        Intent logout = new Intent(AdminHome.this, LandingPage.class);
        startActivity(logout);
    }
}
