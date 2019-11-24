package com.example.beanandleaf;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import database.DatabaseHelper;
import model.Store;

import static org.junit.Assert.*;

public class RecommendationsTest {
    @Before
    public void setup() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        if (db.verifyUser("sam@gmail.com","smith1","Customer").contentEquals("NULL")) {
            db.insertUser("Sam Smith", "sam@gmail.com", "smith1", "Customer", "Male");
        }
    }


    @Test //Check whether adding an order updates caffiene
    public void checkCaffeineLevels() {

        /* moved to setup and using same info as other tests
        //Connect to database
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
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
        */

        //Add orders
//



    }
}
