package com.example.beanandleaf;

import android.graphics.Bitmap;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import database.DatabaseHelper;

import static org.junit.Assert.assertEquals;

public class MerchantAddMenuItemUnitTest {
    @BeforeClass
    public static void setup() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        if (db.verifyUser("m@usc.ed", "hellll", "Merchant").contentEquals("NULL")) {
            db.insertUser("Merchant A", "m@usc.ed", "hellll", "Merchant", "Male" );
        }

        int userId = db.getUserId("m@usc.ed", "Merchant");
        Bitmap image = Bitmap.createBitmap(30, 40, Bitmap.Config.ARGB_8888);
        //Inserting starbucks
        db.insertStore(userId, (float) 34.024120, (float) -118.278170, "Starbucks1", image);
        int starbucksId = db.getStoreId("Starbucks1");

        db.updateStoreVerification(starbucksId);
        //Inserting and verifying dulce1
        db.insertStore(userId, (float) 34.026550, (float) -118.285300, "Dulce1", image);
        int dulceId = db.getStoreId("Dulce1");
        db.updateStoreVerification(dulceId);
    }

    @AfterClass
    public static void breakdown() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        db.removeUser("m@usc.ed", "Merchant");
    }

    @Test
    public void merchantAddMenuItem() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        db.insertUser("Merchant Smith", "m@gmail.com", "merchant", "Merchant", "Male");
        db.insertStore(1, (float)34.026270,(float)-118.284020, "Starbucks", null);
        db.insertMenuItem(1, "Chai Tea Latte", "400", "small", "100", "4", "02:00:00")

        boolean menuItemTest = db.checkMenuItemNameExists(1, "Chai Tea Latte");
        assertEquals(true, menuItemTest);
    }

    @Test public void merchantRemoveMenuItem() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        db.removeMenuItem(1, "Chai Tea Latte", "small");
        boolean menuItemTest = db.checkMenuItemNameExists(1, "Chai Tea Latte");
        assertEquals(false, menuItemTest);
    }

}
