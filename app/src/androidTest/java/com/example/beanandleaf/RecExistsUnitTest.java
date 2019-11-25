package com.example.beanandleaf;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import model.Order;
import model.Store;

import java.util.ArrayList;

import database.DatabaseHelper;

import static org.junit.Assert.assertEquals;

public class RecExistsUnitTest {

    private static DatabaseHelper db;
    private static int userID;

    @Before
    public void setup() {
        db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        if (!db.verifyUser("sams@gmail.com","smith1","Customer").contentEquals("NULL")) {
            db.removeUser("sams@gmail.com", "Customer");
        }
        userID = db.getUserId("sams@gmail.com", "Customer");
        db.removeOrders(userID);

    }

    @AfterClass
    public static void breakdown() {
         db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        db.removeUser("sams@gmail.com", "Customer");
        db.removeStores(userID);
    }

    @Test
    public void checkThatNoRecsExist() {
        db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        db.insertUser("Sam Smith", "sams@gmail.com", "smith1", "Customer", "Male");

        int userID = db.getUserId("sams@gmail.com", "Customer");
        ArrayList <Order> orders = db.getUserOrders (userID);
        boolean works = false;
        if(orders.size() < 4)
        {
            works = false;
        }
        else
        {
            works = true;
        }

        assertEquals(false, works);
    }


}
