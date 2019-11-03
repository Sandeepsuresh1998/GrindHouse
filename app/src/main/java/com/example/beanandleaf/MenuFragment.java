package com.example.beanandleaf;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import database.DatabaseHelper;
import model.MenuItem;
import model.Store;

public class MenuFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard

        //get StoreID Passed in somehow from User and database.DatabaseHelper.Merchant activites

        //get database menu items

        //sort or display menu items grouped by name but list size,

        //display as table

        return inflater.inflate(R.layout.fragment_menu, null);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        final TextView menuTitle = view.findViewById(R.id.edit_menu_title);
        final Button addItemButton = view.findViewById(R.id.add_menu_item_button);

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
        int storeID = pref.getInt("selectedStore", 0);
        DatabaseHelper db = new DatabaseHelper(getActivity());
        Store selectedStore = db.getStore(storeID);
        if (selectedStore == null) {
            menuTitle.setText("No stores yet");
            addItemButton.setVisibility(View.GONE);
        }
        else {
            menuTitle.setText(selectedStore.getName() + "'s Menu");
            addItemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment addMenuItemFragment = new AddMenuItemFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container_merchant, addMenuItemFragment)
                            .commit();
                }
            });

            TableLayout table = view.findViewById(R.id.menu_table);
            ArrayList<MenuItem> menu = db.getMenu(storeID);
            ArrayList<Integer> created = new ArrayList<>();
            for (int i = 0; i < menu.size(); ++i) {
                TableRow row = new TableRow(getActivity());
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                Button editItemButton = new Button(getActivity());
                Button deleteItemButton = new Button(getActivity());
                TextView itemNameView = new TextView(getActivity());
                TextView descriptionView = new TextView(getActivity());
                itemNameView.setText("\n" + menu.get(i).getName());
                String description = "Flavor = Vanilla \n Price = $4, $5, $6 \n Caffeine = 115mg, 150mg, 190mg";
                descriptionView.setText(description);
                editItemButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_edit_green_24dp, 0,0,0);
                deleteItemButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_delete_red_24dp,0,0,0);

                editItemButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }
    }
}
