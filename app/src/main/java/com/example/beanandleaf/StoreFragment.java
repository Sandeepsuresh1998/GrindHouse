package com.example.beanandleaf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

import database.DatabaseHelper;
import model.Store;

public class StoreFragment extends Fragment {
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
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
        final String email = pref.getString("email", null);
        final String userType = pref.getString("userType", null);
        final Integer userID = db.getUserId(email, userType);

        final ArrayList<Store> stores = db.getStores(userID);
        final ArrayList<String> spinnerArray = new ArrayList<>();
        for (Store s : stores) {
            spinnerArray.add(s.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner sItems = view.findViewById(R.id.store_spinner);
        sItems.setAdapter(adapter);

        final EditText nameEditText = view.findViewById(R.id.storename_edit);
        final EditText latEditText = view.findViewById(R.id.location_lat_edit);
        final EditText lonEditText = view.findViewById(R.id.location_long_edit);

        Store firstStore = null;
        if (!stores.isEmpty()) {
            firstStore = stores.get(0);
            nameEditText.setText(firstStore.getName());
            latEditText.setText(Float.toString(firstStore.getLatitude()));
            lonEditText.setText(Float.toString(firstStore.getLongitude()));
            deleteStoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String selectedStoreName = sItems.getSelectedItem().toString();
                    Store selectedStore = null;
                    for (Store s : stores) {
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
                        if (stores.isEmpty()) {
                            nameEditText.setText(null);
                            latEditText.setText(null);
                            lonEditText.setText(null);
                        }
                        else {
                            selectedStore = stores.get(0);
                            nameEditText.setText(selectedStore.getName());
                            latEditText.setText(Float.toString(selectedStore.getLatitude()));
                            lonEditText.setText(Float.toString(selectedStore.getLongitude()));
                        }
                        msg = "Store successfully removed";
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
                    Store selectedStore = null;
                    for (Store s : stores) {
                        if (selectedStoreName.contentEquals(s.getName()))
                            selectedStore = s;
                    }
                    if (selectedStore != null) {
                        nameEditText.setText(selectedStore.getName());
                        latEditText.setText(Float.toString(selectedStore.getLatitude()));
                        lonEditText.setText(Float.toString(selectedStore.getLongitude()));
                    }
                }
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }


        addStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment addStoreFragment = new AddStoreFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_merchant, addStoreFragment)
                        .commit();
            }
        });
    }

}
