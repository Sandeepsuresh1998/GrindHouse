package com.example.beanandleaf;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class EditMenu extends Fragment {

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
            menuTitle.setText(selectedStore.getName() + " Menu");
            addItemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment addMenuItemFragment = new AddMenuItem();
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
                editItemButton.setId(R.id.EditB);
                Button deleteItemButton = new Button(getActivity());
                deleteItemButton.setId(R.id.DeleteB);
                TextView itemNameView = new TextView(getActivity());
                TextView descriptionView = new TextView(getActivity());
                itemNameView.setText("\n" + menu.get(i).getName() + "\n");
                MenuItem cur = menu.get(i);
                String prices = "Price = $" + String.format("%.2f",cur.getPrice());
                String caffeine = "Caffeine = " + cur.getCaffeine() + "mg";
                String calories = "Calories = " + cur.getCalories();
                int otherSizes = 0;
                String otherSize1 = "";
                String otherSize2 = "";
                if (i + 1 != menu.size()) {
                    for (int j = i + 1 ; j < menu.size(); ++j) {
                        MenuItem next = menu.get(j);
                        if (next.getTimeCreated().contentEquals(cur.getTimeCreated())) {
                            otherSizes++;
                            prices += ", $" + String.format("%.2f",next.getPrice());
                            caffeine += ", " + next.getCaffeine() + "mg";
                            calories += ", " + next.getCalories();
                            created.add(j);
                            if (otherSize1.contentEquals("")) {
                                otherSize1 = next.getSize();
                            }
                            else if (otherSize2.contentEquals("")) {
                                otherSize2 = next.getSize();
                            }
                        }
                    }
                }
                final int otherSizesF = otherSizes;
                descriptionView.setText(prices + " \n " + caffeine + " \n " + calories);

                editItemButton.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.ic_edit_green_24dp,0);
                editItemButton.setBackgroundColor(Color.TRANSPARENT);
                TableRow.LayoutParams paramsEditButton = new TableRow.LayoutParams(0);
                editItemButton.setTag(R.id.itemName, cur.getName());
                editItemButton.setTag(R.id.itemSize1, cur.getSize());
                editItemButton.setId(1001 + i);
                if (otherSizes == 1) {
                    editItemButton.setTag(R.id.itemSize2, otherSize1);
                }
                else if (otherSizes == 2) {
                    editItemButton.setTag(R.id.itemSize2, otherSize1);
                    editItemButton.setTag(R.id.itemSize3, otherSize2);
                }
                editItemButton.setLayoutParams(paramsEditButton);

                TableRow.LayoutParams paramsNameText = new TableRow.LayoutParams(1);
                itemNameView.setBackgroundResource(R.drawable.black_border);
                itemNameView.setTextAppearance(R.style.fontForMenuTable);
                itemNameView.setPadding(10,10,10,10);
                itemNameView.setGravity(Gravity.CENTER);
                itemNameView.setLayoutParams(paramsNameText);

                TableRow.LayoutParams paramsDescText = new TableRow.LayoutParams(2);
                descriptionView.setBackgroundResource(R.drawable.black_border);
                descriptionView.setTextAppearance(R.style.fontForMenuTable);
                descriptionView.setPadding(10,10,10,10);
                descriptionView.setGravity(Gravity.CENTER);
                descriptionView.setLayoutParams(paramsDescText);

                deleteItemButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_delete_red_24dp, 0,0,0);
                deleteItemButton.setBackgroundColor(Color.TRANSPARENT);
                TableRow.LayoutParams paramsDeleteButton = new TableRow.LayoutParams(3);
                deleteItemButton.setTag(R.id.itemName, cur.getName());
                deleteItemButton.setTag(R.id.itemSize1, cur.getSize());
                deleteItemButton.setId(101 + i);
                if (otherSizes == 1) {
                    deleteItemButton.setTag(R.id.itemSize2, otherSize1);
                }
                else if (otherSizes == 2) {
                    deleteItemButton.setTag(R.id.itemSize2, otherSize1);
                    deleteItemButton.setTag(R.id.itemSize3, otherSize2);
                }
                deleteItemButton.setLayoutParams(paramsDeleteButton);

                editItemButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment EditMenuItemFragment = null;
                        if (otherSizesF == 0) {
                            EditMenuItemFragment = new EditMenuItem(selectedStore.getStoreID(), (String) v.getTag(R.id.itemName), (String) v.getTag(R.id.itemSize1));
                        }
                        else if (otherSizesF == 1) {
                            EditMenuItemFragment = new EditMenuItem(selectedStore.getStoreID(), (String) v.getTag(R.id.itemName), (String) v.getTag(R.id.itemSize1), (String) v.getTag(R.id.itemSize2));
                        }
                        else if (otherSizesF == 2) {
                            EditMenuItemFragment = new EditMenuItem(selectedStore.getStoreID(), (String) v.getTag(R.id.itemName), (String) v.getTag(R.id.itemSize1), (String) v.getTag(R.id.itemSize2), (String) v.getTag(R.id.itemSize3));
                        }
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container_merchant, EditMenuItemFragment)
                                .commit();
                    }
                });

                deleteItemButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseHelper db = new DatabaseHelper(getActivity());
                        boolean error = false;
                        for (int i = 0; i < otherSizesF + 1; ++i) {
                            String name = (String) v.getTag(R.id.itemName);
                            if (i == 0) {
                                if (!db.removeMenuItem(selectedStore.getStoreID(), name, (String) v.getTag(R.id.itemSize1)))
                                    error = true;
                            }
                            if (i == 1) {
                                if (!db.removeMenuItem(selectedStore.getStoreID(), name, (String) v.getTag(R.id.itemSize2)))
                                    error = true;
                            }
                            if (i == 2) {
                                if (!db.removeMenuItem(selectedStore.getStoreID(), name, (String) v.getTag(R.id.itemSize3)))
                                    error = true;
                            }
                        }
                        if (!error) {
                            Toast.makeText(getActivity().getApplicationContext(), v.getTag(R.id.itemName) + " successfully removed", Toast.LENGTH_LONG).show();
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
