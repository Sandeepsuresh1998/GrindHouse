package com.example.beanandleaf;


import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import database.DatabaseHelper;

import static org.junit.Assert.assertEquals;

public class RegistrationToLoginUnitTest {

    @BeforeClass
    public static void setup() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        if (!db.verifyUser("sam@gmail.com","smith1","Customer").contentEquals("NULL")) {
            db.removeUser("sam@gmail.com", "Customer");
        }
    }

    @AfterClass
    public static void breakdown() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        if (!db.verifyUser("sam@gmail.com","smith1","Customer").contentEquals("NULL")) {
            db.removeUser("sam@gmail.com", "Customer");
        }
        db.close();
    }

    @Test
    public void addingCustomer() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        db.insertUser("Sam smith", "sam@gmail.com", "smith1", "Customer", "Male");
        int userId = db.getUserId("sam@gmail.com", "Customer");
        boolean registrationSuccessful = (userId != -1);
        assertEquals(true, registrationSuccessful);
    }

    @Test
    public void retrieveCustomer() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        int userId = db.getUserId("sam@gmail.com", "Customer");
        boolean removalSuccessful = (userId != -1);
        assertEquals(true, removalSuccessful);
    }
}
