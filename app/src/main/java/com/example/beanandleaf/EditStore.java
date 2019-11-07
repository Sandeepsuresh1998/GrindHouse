package com.example.beanandleaf;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import database.DatabaseHelper;
import model.Store;

public class EditStore extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        return inflater.inflate(R.layout.fragment_store, null);
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        final Button addStoreButton = view.findViewById(R.id.add_store);
        final Button deleteStoreButton = view.findViewById(R.id.delete_store);
        final Button updateStoreButton = view.findViewById(R.id.update_store);

        DatabaseHelper db = new DatabaseHelper(getActivity());
        final SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
        final SharedPreferences.Editor editor = pref.edit();
        final String email = pref.getString("email", null);
        final String userType = pref.getString("userType", null);
        final Integer userID = db.getUserId(email, userType);

        final ArrayList<model.Store> stores = db.getStores(userID);
        final ArrayList<String> spinnerArray = new ArrayList<>();
        for (model.Store s : stores) {
            spinnerArray.add(s.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner sItems = view.findViewById(R.id.store_spinner);
        sItems.setAdapter(adapter);

        final EditText nameEditText = view.findViewById(R.id.storename_edit);
        final EditText latEditText = view.findViewById(R.id.location_lat_edit);
        final EditText lonEditText = view.findViewById(R.id.location_long_edit);
        final TextView verifStatusText = view.findViewById(R.id.verification);

        model.Store firstStore = null;
        if (!stores.isEmpty()) {
            firstStore = stores.get(0);
            nameEditText.setText(firstStore.getName());
            latEditText.setText(Float.toString(firstStore.getLatitude()));
            lonEditText.setText(Float.toString(firstStore.getLongitude()));
            if (firstStore.isVerified()) {
                verifStatusText.setText("Verified");
                verifStatusText.setTextColor(Color.parseColor("#18E71A"));
            }
            else {
                verifStatusText.setText("Not Verified");
                verifStatusText.setTextColor(Color.parseColor("#7E0C1A"));
            }
            deleteStoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String selectedStoreName = sItems.getSelectedItem().toString();
                    model.Store selectedStore = null;
                    for (model.Store s : stores) {
                        if (s.getName().contentEquals(selectedStoreName)) {
                            selectedStore = s;
                        }
                    }
                    DatabaseHelper db = new DatabaseHelper(getActivity());
                    String msg = null;
                    if (db.removeStore(userID, Float.toString(selectedStore.getLatitude()), Float.toString(selectedStore.getLongitude()), selectedStore.getName())) {
                        stores.remove(selectedStore);
                        spinnerArray.remove(selectedStoreName);
                        ArrayAdapter<String> newAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
                        sItems.setAdapter(newAdapter);
                        db.removeStoreMenu(selectedStore.getStoreID());
                        if (stores.isEmpty()) {
                            nameEditText.setText(null);
                            latEditText.setText(null);
                            lonEditText.setText(null);
                            verifStatusText.setText("");
                            editor.putInt("selectedStore", -1);
                            editor.commit();
                        }
                        else {
                            selectedStore = stores.get(0);
                            nameEditText.setText(selectedStore.getName());
                            latEditText.setText(Float.toString(selectedStore.getLatitude()));
                            lonEditText.setText(Float.toString(selectedStore.getLongitude()));
                            editor.putInt("selectedStore", selectedStore.getStoreID());
                            editor.commit();
                            if (selectedStore.isVerified()) {
                                verifStatusText.setText("Verified");
                                verifStatusText.setTextColor(Color.parseColor("#18E71A"));
                            }
                            else {
                                verifStatusText.setText("Not Verified");
                                verifStatusText.setTextColor(Color.parseColor("#7E0C1A"));
                            }
                        }
                        msg = "EditStore successfully removed";
                    }
                    else {
                        msg = "Error: store was not removed";
                    }
                    Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_LONG).show();


                }
            });

            sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedStoreName = parent.getItemAtPosition(position).toString();
                    model.Store selectedStore = null;
                    for (model.Store s : stores) {
                        if (selectedStoreName.contentEquals(s.getName()))
                            selectedStore = s;
                    }
                    if (selectedStore != null) {
                        nameEditText.setText(selectedStore.getName());
                        latEditText.setText(Float.toString(selectedStore.getLatitude()));
                        lonEditText.setText(Float.toString(selectedStore.getLongitude()));
                        editor.putInt("selectedStore", selectedStore.getStoreID());
                        editor.commit();
                        if (selectedStore.isVerified()) {
                            verifStatusText.setText("Verified");
                            verifStatusText.setTextColor(Color.parseColor("#18E71A"));
                        }
                        else {
                            verifStatusText.setText("Not Verified");
                            verifStatusText.setTextColor(Color.parseColor("#7E0C1A"));
                        }
                    }
                }
                public void onNothingSelected(AdapterView<?> parent) {
                    // IGNORE
                }
            });

            updateStoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String selectedStoreName = sItems.getSelectedItem().toString();
                    model.Store selectedStore = null;
                    for (model.Store s : stores) {
                        if (s.getName().contentEquals(selectedStoreName)) {
                            selectedStore = s;
                        }
                    }
                    String newName = nameEditText.getText().toString();
                    String newLat = latEditText.getText().toString();
                    String newLong = lonEditText.getText().toString();

                    DatabaseHelper db = new DatabaseHelper(getActivity());
                    ArrayList<String> updates = new ArrayList<>();


                    boolean error = false;

                    if (!newName.contentEquals(selectedStore.getName())) {
                        if (newName != null) {
                            updates.add("name");
                            if (!db.updateStoreName(userID, Float.toString(selectedStore.getLatitude()), Float.toString(selectedStore.getLongitude()), newName))
                                error = true;
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "EditStore name cannot be empty", Toast.LENGTH_LONG).show();
                            nameEditText.setText(selectedStore.getName());
                        }

                    }
                    if (!newLat.contentEquals(Float.toString(selectedStore.getLatitude()))) {
                        if (isValidCoord(newLat)) {
                            updates.add("latitude");
                            if (!db.updateStoreLat(userID, Float.toString(selectedStore.getLatitude()), Float.toString(selectedStore.getLongitude()), newLat))
                                error = true;
                        }
                        else {
                            Toast.makeText(getActivity().getApplicationContext(), "Please enter a valid latitude with at least four digits after the decimal", Toast.LENGTH_LONG).show();
                            latEditText.setText(Float.toString(selectedStore.getLatitude()));
                        }

                    }
                    if (!newLong.contentEquals(Float.toString(selectedStore.getLongitude()))) {
                        if (isValidCoord(newLong)) {
                            updates.add("longitude");
                            if (!db.updateStoreLong(userID, Float.toString(selectedStore.getLatitude()), Float.toString(selectedStore.getLongitude()), newLong))
                                error = true;
                        }
                        else {
                            Toast.makeText(getActivity().getApplicationContext(), "Please enter a valid longitude with at least four digits after the decimal", Toast.LENGTH_LONG).show();
                            lonEditText.setText(Float.toString(selectedStore.getLongitude()));
                        }

                    }
                    editor.commit();
                    String updateString;
                    if (updates.size() == 1) {
                        updateString = "Your store's " + updates.get(0) + " has been updated";
                    }
                    else if (updates.size() == 2) {
                        updateString = "Your store's " + updates.get(0) + " and " + updates.get(1) + " have been updated";
                    }
                    else if (updates.size() == 3) {
                        updateString = "Your store's name, latitude, and longitude have been updated";
                    }
                    else {
                        updateString = "No updates have been made";
                    }
                    if (!error) {
                        Toast.makeText(getActivity().getApplicationContext(), updateString, Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getActivity().getApplicationContext(), "Error: please try again later", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            verifStatusText.setText("");
        }


        addStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment addStoreFragment = new AddStore();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_merchant, addStoreFragment)
                        .commit();
            }
        });
    }

    private boolean isValidCoord(String s) {
        if (!s.matches("[-+]?[0-9]*\\.?[0-9]+"))
            return false;
        String[] split = s.split("\\.");
        if (split.length != 2 || split[1].length() < 4)
            return false;
        return true;
    }

}
