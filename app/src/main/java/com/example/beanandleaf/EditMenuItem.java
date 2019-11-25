package com.example.beanandleaf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import database.DatabaseHelper;
import model.MenuItem;


public class EditMenuItem extends Fragment {

    private int storeID;
    private int numSizes;
    private String itemName;
    private String size1;
    private String size2;
    private String size3;


    public EditMenuItem(int id, String name, String size1) {
        setStoreID(id);
        setItemName(name);
        setSize1(size1);
        setNumSizes(1);
    }

    public EditMenuItem(int id, String name, String size1, String size2) {
        setStoreID(id);
        setItemName(name);
        setSize1(size1);
        setSize2(size2);
        setNumSizes(2);
    }

    public EditMenuItem(int id, String name, String size1, String size2, String size3) {
        setStoreID(id);
        setItemName(name);
        setSize1(size1);
        setSize2(size2);
        setSize3(size3);
        setNumSizes(3);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_menu, null);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        final TextView nameEdit = view.findViewById(R.id.item_name_edit);
        final TextView calSmallEdit = view.findViewById(R.id.menu_calories_for_small_edit);
        final TextView calMediumEdit = view.findViewById(R.id.calories_for_medium_edit);
        final TextView calLargeEdit = view.findViewById(R.id.calories_for_large_edit);
        final TextView cafSmallEdit = view.findViewById(R.id.menu_caffeine_small_edit);
        final TextView cafMediumEdit = view.findViewById(R.id.caffeine_medium_edit);
        final TextView cafLargeEdit = view.findViewById(R.id.caffeine_large_edit);
        final TextView priceSmallEdit = view.findViewById(R.id.menu_price_small_edit);
        final TextView priceMediumEdit = view.findViewById(R.id.price_medium_edit);
        final TextView priceLargeEdit = view.findViewById(R.id.price_large_edit);
        final Button updateItemButton = view.findViewById(R.id.update_item_button);

        nameEdit.setText(itemName);
        final DatabaseHelper db = new DatabaseHelper(getActivity());
        for (int i = 0; i < numSizes; ++i) {
            MenuItem cur;
            String cal, caf, price;
            if (i == 0) {
                cur = db.getMenuItem(storeID, itemName, size1);
                cal = Integer.toString(cur.getCalories());
                caf = Integer.toString(cur.getCaffeine());
                price = String.format("%.2f", cur.getPrice());
                if (size1.contentEquals("Small")) {
                    calSmallEdit.setText(cal);
                    cafSmallEdit.setText(caf);
                    priceSmallEdit.setText(price);
                }
                else if (size1.contentEquals("Medium")) {
                    calMediumEdit.setText(cal);
                    cafMediumEdit.setText(caf);
                    priceMediumEdit.setText(price);
                }
                else {
                    calLargeEdit.setText(cal);
                    cafLargeEdit.setText(caf);
                    priceLargeEdit.setText(price);
                }
            }
            if (i == 1) {
                cur = db.getMenuItem(storeID, itemName, size2);
                cal = Integer.toString(cur.getCalories());
                caf = Integer.toString(cur.getCaffeine());
                price = String.format("%.2f", cur.getPrice());
                if (size2.contentEquals("Small")) {
                    calSmallEdit.setText(cal);
                    cafSmallEdit.setText(caf);
                    priceSmallEdit.setText(price);
                }
                else if (size2.contentEquals("Medium")) {
                    calMediumEdit.setText(cal);
                    cafMediumEdit.setText(caf);
                    priceMediumEdit.setText(price);
                }
                else {
                    calLargeEdit.setText(cal);
                    cafLargeEdit.setText(caf);
                    priceLargeEdit.setText(price);
                }
            }
            if (i == 2) {
                cur = db.getMenuItem(storeID, itemName, size3);
                cal = Integer.toString(cur.getCalories());
                caf = Integer.toString(cur.getCaffeine());
                price = String.format("%.2f", cur.getPrice());
                if (size3.contentEquals("Small")) {
                    calSmallEdit.setText(cal);
                    cafSmallEdit.setText(caf);
                    priceSmallEdit.setText(price);
                }
                else if (size3.contentEquals("Medium")) {
                    calMediumEdit.setText(cal);
                    cafMediumEdit.setText(caf);
                    priceMediumEdit.setText(price);
                }
                else {
                    calLargeEdit.setText(cal);
                    cafLargeEdit.setText(caf);
                    priceLargeEdit.setText(price);
                }
            }
        }



        updateItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdit.getText().toString();
                String calSmall = calSmallEdit.getText().toString();
                String calMedium = calMediumEdit.getText().toString();
                String calLarge = calLargeEdit.getText().toString();
                String cafSmall = cafSmallEdit.getText().toString();
                String cafMedium = cafMediumEdit.getText().toString();
                String cafLarge = cafLargeEdit.getText().toString();
                String priceSmall = priceSmallEdit.getText().toString();
                String priceMedium = priceMediumEdit.getText().toString();
                String priceLarge = priceLargeEdit.getText().toString();
                boolean addSmall = false;
                boolean addMedium = false;
                boolean addLarge = false;
                boolean updateSmall = false;
                boolean updateMedium = false;
                boolean updateLarge = false;
                boolean removeSmall = false;
                boolean removeMedium = false;
                boolean removeLarge = false;
                if (name.contentEquals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter a name for your menu item", Toast.LENGTH_LONG).show();
                    return;
                }

                if (cafSmall.contentEquals("") && !priceSmall.contentEquals("") && !calSmall.contentEquals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter the amount of caffeine in mg for the small size", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!cafSmall.contentEquals("") && priceSmall.contentEquals("") && !calSmall.contentEquals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter a price for the small size", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!cafSmall.contentEquals("") && !priceSmall.contentEquals("") && calSmall.contentEquals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter the number of calories for the small size", Toast.LENGTH_LONG).show();
                    return;
                }
                if (cafSmall.contentEquals("") && priceSmall.contentEquals("") && calSmall.contentEquals("")) {
                    if (checkSizes("Small")) {
                        removeSmall = true;
                    }
                }
                if (!cafSmall.contentEquals("") && !priceSmall.contentEquals("") && !calSmall.contentEquals("")) {
                    if (!isValidInteger(cafSmall)) {
                        Toast.makeText(getActivity().getApplicationContext(), "Please enter a valid caffeine amount in mg for the small size (three digits or less)", Toast.LENGTH_LONG).show();
                    }
                    else if (!isValidDouble(priceSmall)) {
                        Toast.makeText(getActivity().getApplicationContext(), "Please enter a valid price for the small size", Toast.LENGTH_LONG).show();
                    }
                    else if (!isValidInteger(calSmall)) {
                        Toast.makeText(getActivity().getApplicationContext(), "Please enter a valid number of calories for the small size (three digits or less)", Toast.LENGTH_LONG).show();
                    }
                    else if (checkSizes("Small")) {
                        MenuItem item = db.getMenuItem(storeID, itemName, "Small");
                        if (item != null) {
                            if (!itemName.contentEquals(name) ||
                                    !calSmall.contentEquals(Integer.toString(item.getCalories())) ||
                                    !cafSmall.contentEquals(Integer.toString(item.getCaffeine())) ||
                                    !(String.format("%.2f", Double.parseDouble(priceSmall))).contentEquals(String.format("%.2f",item.getPrice()))) {
                                updateSmall = true;
                            }
                        }
                    }
                    else {
                        addSmall = true;
                    }
                }
                if (cafMedium.contentEquals("") && !priceMedium.contentEquals("") && !calMedium.contentEquals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter the amount of caffeine in mg for the medium size", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!cafMedium.contentEquals("") && priceMedium.contentEquals("") && !calMedium.contentEquals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter a price for the medium size", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!cafMedium.contentEquals("") && !priceMedium.contentEquals("") && calMedium.contentEquals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter the number of calories for the medium size", Toast.LENGTH_LONG).show();
                    return;
                }
                if (cafMedium.contentEquals("") && priceMedium.contentEquals("") && calMedium.contentEquals("")) {
                    if (checkSizes("Medium")) {
                        removeMedium = true;
                    }
                }
                if (!cafMedium.contentEquals("") && !priceMedium.contentEquals("") && !calMedium.contentEquals("")) {
                    if (!isValidInteger(cafMedium)) {
                        Toast.makeText(getActivity().getApplicationContext(), "Please enter a valid caffeine amount in mg for the medium size (three digits or less)", Toast.LENGTH_LONG).show();
                    }
                    else if (!isValidDouble(priceMedium)) {
                        Toast.makeText(getActivity().getApplicationContext(), "Please enter a valid price for the medium size", Toast.LENGTH_LONG).show();
                    }
                    else if (!isValidInteger(calMedium)) {
                        Toast.makeText(getActivity().getApplicationContext(), "Please enter a valid number of calories for the medium size (three digits or less)", Toast.LENGTH_LONG).show();
                    }
                    else if (checkSizes("Medium")) {
                        MenuItem item = db.getMenuItem(storeID, itemName, "Medium");
                        if (item != null) {
                            if (!itemName.contentEquals(name) ||
                                    !calMedium.contentEquals(Integer.toString(item.getCalories())) ||
                                    !cafMedium.contentEquals(Integer.toString(item.getCaffeine())) ||
                                    !(String.format("%.2f", Double.parseDouble(priceMedium))).contentEquals(String.format("%.2f",item.getPrice()))) {
                                updateMedium = true;
                            }
                        }
                    }
                    else {
                        addMedium = true;
                    }
                }
                if (cafLarge.contentEquals("") && !priceLarge.contentEquals("") && !calLarge.contentEquals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter the amount of caffeine in mg for the large size", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!cafLarge.contentEquals("") && priceLarge.contentEquals("") && !calLarge.contentEquals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter a price for the large size", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!cafLarge.contentEquals("") && !priceLarge.contentEquals("") && calLarge.contentEquals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter the number of calories for the large size", Toast.LENGTH_LONG).show();
                    return;
                }
                if (cafLarge.contentEquals("") && priceLarge.contentEquals("") && calLarge.contentEquals("")) {
                    if (checkSizes("Large")) {
                        removeLarge = true;
                    }
                }
                if (!cafLarge.contentEquals("") && !priceLarge.contentEquals("") && !calLarge.contentEquals("")) {
                    if (!isValidInteger(cafLarge)) {
                        Toast.makeText(getActivity().getApplicationContext(), "Please enter a valid caffeine amount in mg for the large size (three digits or less)", Toast.LENGTH_LONG).show();
                    }
                    else if (!isValidDouble(priceLarge)) {
                        Toast.makeText(getActivity().getApplicationContext(), "Please enter a valid price for the large size", Toast.LENGTH_LONG).show();
                    }
                    else if (!isValidInteger(calLarge)) {
                        Toast.makeText(getActivity().getApplicationContext(), "Please enter a valid number of calories for the large size (three digits or less)", Toast.LENGTH_LONG).show();
                    }
                    else if (checkSizes("Large")) {
                        MenuItem item = db.getMenuItem(storeID, itemName, "Large");
                        if (item != null) {
                            if (!itemName.contentEquals(name) ||
                                    !calLarge.contentEquals(Integer.toString(item.getCalories())) ||
                                    !cafLarge.contentEquals(Integer.toString(item.getCaffeine())) ||
                                    !(String.format("%.2f", Double.parseDouble(priceLarge))).contentEquals(String.format("%.2f",item.getPrice()))) {
                                updateLarge = true;
                            }
                        }
                    }
                    else {
                        addLarge = true;
                    }
                }

                DatabaseHelper db = new DatabaseHelper(getActivity());
                MenuItem item = db.getMenuItem(storeID, itemName, size1);
                if (addSmall) {
                    if (!db.insertMenuItem(storeID, name, calSmall, "Small", cafSmall, priceSmall, item.getTimeCreated())) {
                        Toast.makeText(getActivity().getApplicationContext(), "Error: small menu item not added", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        addSize("Small");
                    }
                }
                else if (updateSmall) {
                    MenuItem smallItem = db.getMenuItem(storeID, itemName, "Small");
                    if (!db.updateMenuItem(smallItem.getID(), name, calSmall, cafSmall, priceSmall)) {
                        Toast.makeText(getActivity().getApplicationContext(), "Error: small menu item not updated", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else if (removeSmall) {
                    if (!db.removeMenuItem(storeID, itemName, "Small")){
                        Toast.makeText(getActivity().getApplicationContext(), "Error: small menu item not removed", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        if (removeSize("Small")) {
                            return;
                        }
                    }
                }
                if (addMedium) {
                    if (!db.insertMenuItem(storeID, name, calMedium, "Medium", cafMedium, priceMedium, item.getTimeCreated())) {
                        Toast.makeText(getActivity().getApplicationContext(), "Error: medium menu item not added", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        addSize("Medium");
                    }
                }
                else if (updateMedium) {
                    MenuItem mediumItem = db.getMenuItem(storeID, itemName, "Medium");
                    if (!db.updateMenuItem(mediumItem.getID(), name, calMedium, cafMedium, priceMedium)) {
                        Toast.makeText(getActivity().getApplicationContext(), "Error: medium menu item not updated", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else if (removeMedium) {
                    if (!db.removeMenuItem(storeID, itemName, "Medium")){
                        Toast.makeText(getActivity().getApplicationContext(), "Error: medium menu item not removed", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        if (removeSize("Medium")) {
                            return;
                        }
                    }
                }
                if (addLarge) {
                    if (!db.insertMenuItem(storeID, name, calLarge, "Large", cafLarge, priceLarge, item.getTimeCreated())) {
                        Toast.makeText(getActivity().getApplicationContext(), "Error: large menu item not added", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        addSize("Large");
                    }
                }
                else if (updateLarge) {
                    MenuItem largeItem = db.getMenuItem(storeID, itemName, "Large");
                    if (!db.updateMenuItem(largeItem.getID(), name, calLarge, cafLarge, priceLarge)) {
                        Toast.makeText(getActivity().getApplicationContext(), "Error: large menu item not updated", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else if (removeLarge) {
                    if (!db.removeMenuItem(storeID, itemName, "Large")){
                        Toast.makeText(getActivity().getApplicationContext(), "Error: large menu item not removed", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        if (removeSize("Large")) {
                            return;
                        }
                    }
                }
                if (!addSmall && !addMedium && !addLarge && !updateSmall && !updateMedium && !updateLarge && !removeSmall && !removeMedium && !removeLarge) {
                    return;
                }
                Toast.makeText(getActivity().getApplicationContext(), name + " updated", Toast.LENGTH_SHORT).show();
                Fragment menuFragment = new EditMenu();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_merchant, menuFragment)
                        .commit();
            }
        });
    }

    private boolean isValidDouble(String s) {
        String regex = "[0-9]*\\.?[0-9]+";
        String[] split = s.split("\\.");
        if (split.length == 1) {
            return s.matches(regex);
        }
        if (split.length != 2)
            return false;
        return s.matches(regex) && split[1].length() <= 2;
    }

    private boolean isValidInteger(String s) {
        String regex = "\\d+";
        return s.matches(regex) && s.length() <= 3;
    }

    public boolean checkSizes(String size) {
        if(size == null) {
            return false;
        }
        if (size1.contentEquals(size)) {
            return true;
        }
        if (size2 != null) {
            if (size2.contentEquals(size)) {
                return true;
            }
        }
        if (size3 != null) {
            if (size3.contentEquals(size)) {
                return true;
            }
        }
        return false;
    }

    private boolean removeSize(String size) {
        DatabaseHelper db = new DatabaseHelper(getActivity());
        if (size1.contentEquals(size)) {
            if (numSizes == 1) {
                if (db.removeMenuItem(storeID, itemName, size)) {
                    Toast.makeText(getActivity().getApplicationContext(), itemName + " removed", Toast.LENGTH_SHORT).show();
                    Fragment menuFragment = new EditMenu();
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container_merchant, menuFragment)
                            .commit();
                    return true;

                }
            }
            else if (numSizes == 2) {
                size1 = size2;
                size2 = null;
            }
            else {
                size1 = size2;
                size2 = size3;
                size3 = null;
            }
        }
        if (size2.contentEquals(size)) {
            if (numSizes == 2) {
                size2 = null;
            }
            else {
                size2 = size3;
                size3 = null;
            }
        }
        if (size3.contentEquals(size)) {
            size3 = null;
        }
        numSizes--;
        return false;
    }

    private void addSize(String size) {
        if (numSizes == 1) {
            size2 = size;
        }
        if (numSizes == 2) {
            size3 = size;
        }
        numSizes++;
    }

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSize1() {
        return size1;
    }

    public void setSize1(String size1) {
        this.size1 = size1;
    }

    public String getSize2() {
        return size2;
    }

    public void setSize2(String size2) {
        this.size2 = size2;
    }

    public String getSize3() {
        return size3;
    }

    public void setSize3(String size3) {
        this.size3 = size3;
    }

    public int getNumSizes() {
        return numSizes;
    }

    public void setNumSizes(int numSizes) {
        this.numSizes = numSizes;
    }
}
