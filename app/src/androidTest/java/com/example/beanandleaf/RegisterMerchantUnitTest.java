package com.example.beanandleaf;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import database.DatabaseHelper;

import static org.junit.Assert.assertEquals;

public class RegisterMerchantUnitTest {
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
    public void registerOneMerchant() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        db.insertUser("Merchant Smith", "m@gmail.com", "merchant", "Merchant", "Male");

        int merchantId = db.getUserId("m@gmail.com", "Merchant");
        boolean registrationSuccessful = (merchantId != -1);
        assertEquals(true, registrationSuccessful);
    }

    @Test public void removeMerchant() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        db.removeUser("m@gmail.com", "Merchant");
        int merchantId = db.getUserId("m@gmail.com", "Merchant");
        boolean removalSuccessful = (merchantId == -1);
        assertEquals(true, removalSuccessful);
    }

}
