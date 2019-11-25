package com.example.beanandleaf;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import database.DatabaseHelper;

import static org.junit.Assert.assertEquals;

public class MerchantAddStoreUnitTest {
    @BeforeClass
    public static void setup() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        if (!db.verifyUser("m@gmail.com","merchant","Merchant").contentEquals("NULL")) {
            db.removeUser("m@gmail.com", "Merchant");
        }
    }

    @AfterClass
    public static void breakdown() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        if (!db.verifyUser("m@gmail.com","merchant","Merchant").contentEquals("NULL")) {
            db.removeUser("m@gmail.com", "Merchant");
        }
    }

    @Test
    public void addStoreCheck() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        db.insertUser("Merchant Suresh", "m@gmail.com", "merchant", "Merchant", "Male");
        int userId = db.getUserId("m@gmail.com", "Merchant");
        db.insertStore(userId, (float) 34.1234, (float) -118.5678, "CoffeeBean", null);
        int storeId = db.getStoreId("CoffeeBean");
        boolean storeAdditionSuccessful = (storeId != -1);
        assertEquals(true, storeAdditionSuccessful);
    }

    @Test
    public void storeRemovalCheck() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        int userId = db.getUserId("m@gmail.com", "Merchant");
        db.removeStore(userId, "34.1234", "-118.5678", "CoffeeBean");
        int storeId = db.getStoreId("CoffeeBean");
        boolean removalSuccssful = (storeId == -1);
        assertEquals(true, removalSuccssful);
    }
}
