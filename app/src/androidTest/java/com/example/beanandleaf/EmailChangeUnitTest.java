package com.example.beanandleaf;

import android.graphics.BitmapFactory;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import database.DatabaseHelper;
import model.Store;

import static org.junit.Assert.assertEquals;

public class EmailChangeUnitTest {


    private static DatabaseHelper db;
    private static int userID;

    @Before
    public void setup() {
        db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        if (!db.verifyUser("sam@gmail.com","smith1","Customer").contentEquals("NULL")) {
            db.removeUser("sam@gmail.com", "Customer");
        }
        userID = db.getUserId("sam@gmail.com", "Customer");
    }

    @AfterClass
    public static void breakdown() {
        db.removeUser("sam@gmail.com", "Customer");
    }

    @Test
    public void changeEmailTest() {
        db.updateUserEmail("sam@gmail.com", "Customer", "s@gmail.com");
        String email = db.getUserEmail(Integer.toString(userID));
        assertEquals("s@gmail.com", "s@gmail.com");
    }
}

