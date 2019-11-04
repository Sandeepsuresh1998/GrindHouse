package com.example.beanandleaf;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import database.DatabaseHelper;


public class AddStoreFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_store, null);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        final Button addNewStoreButton = view.findViewById(R.id.add_new_store);
        final EditText storeNameText = view.findViewById(R.id.storename_edit);
        final EditText latText = view.findViewById(R.id.location_lat_edit);
        final EditText lonText = view.findViewById(R.id.location_long_edit);
        addNewStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String storeName = storeNameText.getText().toString();
                String lat = latText.getText().toString();
                String lon = lonText.getText().toString();

                if (storeName.contentEquals("") ||lat.contentEquals("") || lon.contentEquals(""))
                    return;
                if (!isValidCoord(lat)) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter a valid latitude with at least four digits after the decimal", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!isValidCoord(lon)) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter a valid longitude with at least four digits after the decimal", Toast.LENGTH_LONG).show();
                    return;
                }
                DatabaseHelper db = new DatabaseHelper(getActivity());
                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);

                String email = pref.getString("email", null);
                String userType = pref.getString("userType", null);
                int userId = db.getUserId(email, userType);

                if (db.insertStore(userId, Float.parseFloat(lat), Float.parseFloat(lon), storeName)) {
                    Fragment storeFragment = new StoreFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container_merchant, storeFragment)
                            .commit();
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Error: store not added", Toast.LENGTH_SHORT).show();
                }
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
