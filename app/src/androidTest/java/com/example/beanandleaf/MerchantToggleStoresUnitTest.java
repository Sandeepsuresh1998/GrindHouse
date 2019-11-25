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

public class MerchantToggleStoresUnitTest {

    private static DatabaseHelper db;
    private static int userID;

    @Before
    public void setup() {
        db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        if (!db.verifyUser("sam@gmail.com","smith1","Customer").contentEquals("NULL")) {
            db.removeUser("sam@gmail.com", "Customer");
        }
        userID = db.getUserId("sam@gmail.com", "Customer");
        db.removeStores(userID);
    }

    @AfterClass
    public static void breakdown() {
        db.removeUser("sam@gmail.com", "Customer");
        db.removeStores(userID);
    }

    @Test
    public void noToggleTest() {
        ArrayList<Store> stores = db.getStores(userID);
        int numStores = stores.size();
        assertEquals(0, numStores);
    }

    @Test
    public void oneStoreToggleTest() {
        db.insertStore(userID,34.024120F, -118.278170F, "Starbucks", BitmapFactory.decodeFile("logo-web.png"));
        ArrayList<Store> stores = db.getStores(userID);
        int numStores = stores.size();
        assertEquals(1, numStores);
    }

    @Test
    public void twoStoreToggleTest() {
        db.insertStore(userID,34.024120F, -118.278170F, "Starbucks", BitmapFactory.decodeFile("logo-web.png"));
        db.insertStore(userID,34.017520F, -118.282660F, "Coffee Bean & Tea Leaf", BitmapFactory.decodeFile("logo-web.png"));
        ArrayList<Store> stores = db.getStores(userID);
        int numStores = stores.size();
        assertEquals(2, numStores);
    }
}
