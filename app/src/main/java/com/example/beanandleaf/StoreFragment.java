package com.example.beanandleaf;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
