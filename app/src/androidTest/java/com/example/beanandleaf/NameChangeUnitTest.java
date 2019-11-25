package com.example.beanandleaf;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import database.DatabaseHelper;

import static org.junit.Assert.assertEquals;

public class NameChangeUnitTest {
    @BeforeClass
    public static void setup() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        if (!db.verifyUser("smith98@gmail.com","hell12","Customer").contentEquals("NULL")) {
            db.removeUser("smith98@gmail.com", "Customer");
        }
    }

    @AfterClass
    public static void breakdown() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        if (!db.verifyUser("smith98@gmail.com","hell12","Customer").contentEquals("NULL")) {
            db.removeUser("smith98@gmail.com", "Customer");
        }
    }

    @Test
    public void checkNameChange() {
        //Register and insert original user
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        db.insertUser("Sam Smith", "smith98@gmail.com", "hell12", "Customer", "Male");
        db.updateUserName("smith98@gmail.com", "Customer", "Sam Suresh");
        String newName = db.getUserName("smith98@gmail.com", "Customer");
        assertEquals("Sam Suresh", newName);
    }


}
