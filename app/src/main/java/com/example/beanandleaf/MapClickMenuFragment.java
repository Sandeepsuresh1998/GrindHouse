package com.example.beanandleaf;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import database.DatabaseHelper;
import model.Store;

public class MapClickMenuFragment extends Fragment {

    private int storeID;

    public MapClickMenuFragment(int storeID) {
        this.storeID = storeID;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map_view_menu, null);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView title = view.findViewById(R.id.map_click_menu_title);
        DatabaseHelper db = new DatabaseHelper(getActivity());
        Store selectedStore = db.getStore(storeID);
        title.setText(selectedStore.getName() + "'s Menu");
    }

}
