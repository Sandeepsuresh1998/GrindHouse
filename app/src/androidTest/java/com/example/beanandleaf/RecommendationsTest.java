package com.example.beanandleaf;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import java.util.ArrayList;

import database.DatabaseHelper;

import static org.junit.Assert.*;
import model.Store;

public class RecommendationsTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.beanandleaf", appContext.getPackageName());

    }

    @Test //Check whether adding an order updates caffiene
    public void checkCaffeineLevels() {

        //Connect to database
        final DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        db.removeUser("testemail@gmail.com", "Customer"); //Used to reset info

        ArrayList<Store> stores = db.getStores();
        for (int i = 0; i < stores.size(); i++) {
            System.out.println(stores.get(i).getStoreID());
        }

        //Insert user into database to create orders from him
        db.insertUser("Test Smith", "testemail@gmail.com", "test1", "Customer", "Male");

        //Get userId
        int userId = db.getUserId("testemail@gmail.com", "Customer");

        System.out.println(userId);

        //Add orders
//



    }
}
