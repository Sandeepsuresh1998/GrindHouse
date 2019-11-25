package com.example.beanandleaf;

import androidx.test.espresso.ViewInteraction;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import database.DatabaseHelper;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class MerchantViewTest {

    @BeforeClass
    //Same setup as order history test
    public static void setup() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        if (!db.verifyUser("someman@gmail.com","someman","Customer").contentEquals("NULL")) {
            db.removeUser("someman@gmail.com", "Customer");
        } //If my test user already exists I want him/her removed
        //Create a user with an order
        db.insertUser("Someman Smith", "someman@gmail.com", "someman", "Customer", "Male"); //Creating user
        int userId = db.getUserId("someman@gmail.com", "Customer");
        //Create a Merchant who owns a store
        db.insertUser("Example1 Merch", "merch2@gmail.com", "merchant", "Merchant", "Male");
        //Create a store with merchant
        int merchantId = db.getUserId("merch2@gmail.com", "Merchant");
        db.insertStore(merchantId, (float) 34.05678, (float) -118.12345, "Store2", null); //Fake store
        int storeId = db.getStoreId("Store2");
        //Now add Menu item to store
        db.insertMenuItem(storeId, "Coffee", "100", "small", "200", "1.00", "11/24/19");
        //Now let's make the user order said item
        int menuId = db.getMenuItemId(storeId, "Coffee", "small");

        //Now create order
        db.insertOrder(userId, menuId, storeId, 1, 200, 100, "2.00", "Coffee", "11/24/19");

        db.close();
    }

    @Rule
    public ActivityTestRule<LandingPage> mActivityTestRule = new ActivityTestRule<>(LandingPage.class);

    @Test
    public void checkMoneySpent() {
        //Tests whether a merchant has the correct amount of money spent in his restaurant

        //Click log in button
        ViewInteraction appCompatButton3 = onView(allOf(withId(R.id.link_login)));
        appCompatButton3.perform(click());

        //Fill in username with sam@gmail.com
        ViewInteraction appCompatEditText5 = onView(allOf(withId(R.id.username)));
        appCompatEditText5.perform(replaceText("merch2@gmail.com"), closeSoftKeyboard());

        //Fill in corresponding password
        ViewInteraction appCompatEditText6 = onView(allOf(withId(R.id.password)));
        appCompatEditText6.perform(replaceText("merchant"), closeSoftKeyboard());

        //Add User Type
        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.Merchant), withText("Merchant")));
        appCompatRadioButton.perform(click());

        //Try log in
        ViewInteraction appCompatEditText7 = onView(allOf(withId(R.id.password)));
        appCompatEditText7.perform(pressImeActionButton());

        //Go to merchant history
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_merchant_history)));
        bottomNavigationItemView.perform(click());

        //Check that it matches the correct money spent
        ViewInteraction textView = onView(
                allOf(withId(R.id.merchant_money_spent)));
        textView.check(matches(withText("$2.00")));
    }

    //Check that merchant has the right number of orders
    @Test
    public void checkOrderNumber() {

        //Click log in button
        ViewInteraction appCompatButton3 = onView(allOf(withId(R.id.link_login)));
        appCompatButton3.perform(click());

        //Fill in username with sam@gmail.com
        ViewInteraction appCompatEditText5 = onView(allOf(withId(R.id.username)));
        appCompatEditText5.perform(replaceText("merch1@gmail.com"), closeSoftKeyboard());

        //Fill in corresponding password
        ViewInteraction appCompatEditText6 = onView(allOf(withId(R.id.password)));
        appCompatEditText6.perform(replaceText("merchant"), closeSoftKeyboard());

        //Add User Type
        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.Merchant), withText("Merchant")));
        appCompatRadioButton.perform(click());

        //Try log in
        ViewInteraction appCompatEditText7 = onView(allOf(withId(R.id.password)));
        appCompatEditText7.perform(pressImeActionButton());

        //Go to merchant history
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_merchant_history)));
        bottomNavigationItemView.perform(click());

        //Check that number of orders placed is correct
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.merchant_orders_placed)));
        textView2.check(matches(withText("1")));
    }
}
