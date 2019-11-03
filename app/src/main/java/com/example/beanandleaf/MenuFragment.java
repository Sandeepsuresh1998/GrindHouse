package com.example.beanandleaf;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
        final Store selectedStore = db.getStore(storeID);
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
            final ArrayList<MenuItem> menu = db.getMenu(storeID);
            ArrayList<Integer> created = new ArrayList<>();
            for (int i = 0; i < menu.size(); ++i) {
                boolean alreadyCreated = false;
                for (Integer n : created) {
                    if (i == n) {
                        alreadyCreated = true;
                    }
                }
                if (alreadyCreated)
                    continue;

                TableRow row = new TableRow(getActivity());
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setGravity(Gravity.CENTER);
                row.setLayoutParams(lp);
                Button editItemButton = new Button(getActivity());
                Button deleteItemButton = new Button(getActivity());
                TextView itemNameView = new TextView(getActivity());
                TextView descriptionView = new TextView(getActivity());
                itemNameView.setText("\n" + menu.get(i).getName() + "\n");
                MenuItem cur = menu.get(i);
                String prices = "Price = $" + String.format("%.2f",cur.getPrice());
                String caffeine = "Caffeine = " + cur.getCaffeine() + "mg";
                String calories = "Calories = " + cur.getCalories();
                if (i + 1 != menu.size()) {
                    for (int j = i + 1 ; j < menu.size(); ++j) {
                        MenuItem next = menu.get(j);
                        if (next.getTimeCreated().contentEquals(cur.getTimeCreated())) {
                            prices += ", $" + String.format("%.2f",next.getPrice());
                            caffeine += ", " + next.getCaffeine() + "mg";
                            calories += ", " + next.getCalories();
                            created.add(j);
                        }
                    }
                }
                descriptionView.setText(prices + " \n " + caffeine + " \n " + calories);

                editItemButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_edit_green_24dp, 0,0,0);
                editItemButton.setBackgroundColor(Color.TRANSPARENT);
                TableRow.LayoutParams paramsEditButton = new TableRow.LayoutParams(0);
                //paramsEditButton.setMargins(4, 0, 0, 0);
                //paramsEditButton.width = 22;
                //paramsEditButton.height = 12;
                editItemButton.setTag(cur.getID());
                editItemButton.setLayoutParams(paramsEditButton);

                TableRow.LayoutParams paramsNameText = new TableRow.LayoutParams(1);
                itemNameView.setBackgroundResource(R.drawable.black_border);
                itemNameView.setTextAppearance(R.style.fontForMenuTable);
                itemNameView.setPadding(10,10,10,10);
                itemNameView.setGravity(Gravity.CENTER);
                itemNameView.setLayoutParams(paramsNameText);

                TableRow.LayoutParams paramsDescText = new TableRow.LayoutParams(2);
                paramsDescText.width = 125;
                descriptionView.setBackgroundResource(R.drawable.black_border);
                descriptionView.setTextAppearance(R.style.fontForMenuTable);
                descriptionView.setPadding(10,10,10,10);
                descriptionView.setGravity(Gravity.CENTER);
                descriptionView.setLayoutParams(paramsDescText);

                deleteItemButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_delete_red_24dp, 0,0,0);
                deleteItemButton.setBackgroundColor(Color.TRANSPARENT);
                TableRow.LayoutParams paramsDeleteButton = new TableRow.LayoutParams(3);
                //paramsEditButton.setMargins(4, 0, 0, 0);
                //paramsEditButton.width = 20;
                //paramsEditButton.height = 20;
                deleteItemButton.setTag(cur.getID());
                deleteItemButton.setLayoutParams(paramsDeleteButton);
                deleteItemButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int itemID = (int) v.getTag();
                        DatabaseHelper db = new DatabaseHelper(getActivity());
                        MenuItem item = db.getMenuItem(itemID);
                        if (db.removeMenuItem(itemID)) {
                            Toast.makeText(getActivity().getApplicationContext(), item.getName() + " successfully removed", Toast.LENGTH_LONG).show();
                            TableLayout container = (TableLayout) ((TableRow) v.getParent()).getParent();
                            container.removeView((View) v.getParent());
                            container.invalidate();
                        }
                        else {
                            Toast.makeText(getActivity().getApplicationContext(), "Removal error", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                row.addView(editItemButton);
                row.addView(itemNameView);
                row.addView(descriptionView);
                row.addView(deleteItemButton);
                table.addView(row);
            }
        }
    }
}
