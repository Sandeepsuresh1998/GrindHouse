package com.example.beanandleaf;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import database.DatabaseHelper;
import model.MenuItem;
import model.Order;


public class AddOrder extends Fragment {


    private int storeID;

    public AddOrder(int storeID) {
        this.storeID = storeID;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_order, null);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        final Button addAnotherButton = view.findViewById(R.id.add_another_button);
        final Button submitOrderButton = view.findViewById(R.id.complete_order_button);

        final Spinner itemSpinner = view.findViewById(R.id.item_spinner);
        final Spinner sizeSpinner = view.findViewById(R.id.size_spinner);
        final Spinner quantSpinner = view.findViewById(R.id.quantity_spinner);

        final DatabaseHelper db = new DatabaseHelper(getActivity());
        final SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref",0);
        final String email = pref.getString("email", null);
        final String userType = pref.getString("userType", null);
        final Integer userID = db.getUserId(email, userType);

        ArrayList<String> quants = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            quants.add(Integer.toString(i));
        }
        ArrayAdapter<String> quantAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, quants);
        quantAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantSpinner.setAdapter(quantAdapter);

        final ArrayList<MenuItem> menu = db.getMenu(storeID);
        ArrayList<String> uniqueNames = new ArrayList<>();
        for (MenuItem item : menu) {
            boolean alreadyAdded = false;
            for (String s : uniqueNames) {
                if (s.contentEquals(item.getName())) {
                    alreadyAdded = true;
                }
            }
            if (!alreadyAdded) {
                uniqueNames.add(item.getName());
            }
        }
        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, uniqueNames);
        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemSpinner.setAdapter(itemAdapter);

        itemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemName = parent.getItemAtPosition(position).toString();
                ArrayList<String> sizes = new ArrayList<>();
                for (MenuItem item : menu) {
                    if (selectedItemName.contentEquals(item.getName())) {
                        sizes.add(item.getSize());
                    }
                }
                ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, sizes);
                sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sizeSpinner.setAdapter(sizeAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // IGNORE
            }
        });

        addAnotherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = itemSpinner.getSelectedItem().toString();
                String size = sizeSpinner.getSelectedItem().toString();
                MenuItem item = db.getMenuItem(storeID, itemName, size);

                String quant = quantSpinner.getSelectedItem().toString();
                if (db.insertOrder(userID, item.getID(), storeID, Integer.parseInt(quant), item.getCaffeine() * Integer.parseInt(quant), item.getCalories() * Integer.parseInt(quant), Double.toString(item.getPrice() * Double.parseDouble(quant)), item.getName(), Long.toString(System.currentTimeMillis()))) {
                    Toast.makeText(getActivity().getApplicationContext(), "Order logged successfully", Toast.LENGTH_LONG).show();
                    Fragment addOrderFragment = new AddOrder(storeID);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container_customer, addOrderFragment)
                            .commit();
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Error: order not recorded", Toast.LENGTH_LONG).show();
                }

            }
        });

        submitOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = itemSpinner.getSelectedItem().toString();
                String size = sizeSpinner.getSelectedItem().toString();
                MenuItem item = db.getMenuItem(storeID, itemName, size);

                String quant = quantSpinner.getSelectedItem().toString();
                if (db.insertOrder(userID, item.getID(), storeID, Integer.parseInt(quant), item.getCaffeine() * Integer.parseInt(quant), item.getCalories() * Integer.parseInt(quant), Double.toString(item.getPrice() * Double.parseDouble(quant)), item.getName(), Long.toString(System.currentTimeMillis()))) {

                    int caffeineToday = getCaffeineFromOrdersToday(db.getUserOrders(userID));
                    String caffeineToast;
                    if(caffeineToday < 300) {
                        caffeineToast = "Your caffeine intake amount today is " + caffeineToday;
                    }
                    else if(caffeineToday >= 300 && caffeineToday < 400) {
                        caffeineToast = "Your caffeine intake amount today is " + caffeineToday + ". You're nearing the daily recommended limit of 400mg.";
                    }
                    else if (caffeineToday == 400) {
                        caffeineToast = "Your caffeine intake amount today is 400 mg. You've reached the daily recommended amount of caffeine.";
                    }
                    else {
                        caffeineToast = "Your caffeine intake amount today is " + caffeineToday + ".You've exceeded the daily recommended amount of caffeine!";
                    }

                    Toast.makeText(getActivity().getApplicationContext(), "Order logged successfully\n" + caffeineToast, Toast.LENGTH_LONG).show();
                    Fragment mapFragment = new Map();
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container_customer, mapFragment)
                            .commit();
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Error: order not recorded", Toast.LENGTH_LONG).show();
                }
            }
        });
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
