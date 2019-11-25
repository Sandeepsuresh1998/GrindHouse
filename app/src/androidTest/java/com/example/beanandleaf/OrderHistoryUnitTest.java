package com.example.beanandleaf;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import model.Order;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import database.DatabaseHelper;

public class OrderHistoryUnitTest {
    @BeforeClass
    public static void setup() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        if (!db.verifyUser("m@gmail.com","merchant","Merchant").contentEquals("NULL")) {
            db.removeUser("m@gmail.com", "Merchant");
        }
        if (!db.verifyUser("sureshsa@usc.edu","sandeep","Customer").contentEquals("NULL")) {
            db.removeUser("sureshsa@usc.edu", "Customer");
        }

        int merchantId = db.getUserId("m@gmail.com", "Merchant");
        db.insertStore(merchantId, (float) 34.1234, (float) -118.5678, "CoffeeBean", null);
        int storeId = db.getStoreId("CoffeeBean");

        //Add user
        db.insertUser("Sandeep Suresh", "sureshsa@usc.edu", "sandeep", "Customer", "Male");
        int userId = db.getUserId("sureshsa@usc.edu", "Customer");

        //Add Menu Item
        db.insertMenuItem(storeId, "Coffee", "100", "Small", "200", "2.00", "11/24/19");
        int menuItemId = db.getMenuItemId(storeId, "Coffee", "Small");
        //Add order
        db.insertOrder(userId, menuItemId, storeId, 1, 200, 100, "2.00", "Coffee", "11/25/19");
    }

    @AfterClass
    public static void breakdown() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        if (!db.verifyUser("m@gmail.com","merchant","Merchant").contentEquals("NULL")) {
            db.removeUser("m@gmail.com", "Merchant");
        }
        if (!db.verifyUser("sureshsa@usc.edu","sandeep","Customer").contentEquals("NULL")) {
            db.removeUser("sureshsa@usc.edu", "Customer");
        }
    }

    @Test
    public void checkCustomerMoneySpent() {
        //Adding a store and Merchant
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());

        int userId = db.getUserId("sureshsa@usc.edu", "Customer");
        ArrayList<Order> userOrders = db.getUserOrders(userId);

        int money_spent = 0;
        for(Order o : userOrders) {
            money_spent += o.getPrice();
        }

        boolean success = (money_spent == 2.00);
        assertEquals(true, success);

    }

    @Test
    public void checkCustomerCalories() {

        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        int userId = db.getUserId("sureshsa@usc.edu", "Customer");

        ArrayList<Order> userOrders = db.getUserOrders(userId);

        int calories_consumed = 0;
        for(Order o : userOrders) {
            calories_consumed += o.getCalories();
        }

        boolean success = (calories_consumed == 100);
        assertEquals(true, success);
    }

    @Test
    public void checkCustomerCaffiene() {

        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        int userId = db.getUserId("sureshsa@usc.edu", "Customer");

        ArrayList<Order> userOrders = db.getUserOrders(userId);

        int caffiene_consumed = 0;
        for(Order o : userOrders) {
            caffiene_consumed += o.getCaffeine();
        }

        boolean success = (caffiene_consumed == 200);
        assertEquals(true, success);
    }
}
