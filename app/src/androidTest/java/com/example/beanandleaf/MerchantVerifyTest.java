package com.example.beanandleaf;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.test.espresso.ViewInteraction;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import database.DatabaseHelper;
import model.Store;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

// Ethan will finish this
public class MerchantVerifyTest {

    private static DatabaseHelper db;
    private static int userID;

    @Before
    public void setup() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        if (!db.verifyUser("m@gmail.com","merchant","Merchant").contentEquals("NULL")) {
            db.removeUser("m@gmail.com",  "Merchant");
        }
    }

    @After
    public void breakdown() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        db.removeUser("m@gmail.com","Merchant");
    }

    @Rule
    public ActivityTestRule<LandingPage> mActivityTestRule = new ActivityTestRule<>(LandingPage.class);

    @Test
    public void noStoreTest() {

        ViewInteraction appCompatButton = onView(allOf(withId(R.id.link_signup)));
        appCompatButton.perform(click());

        //Fill in Sam Smith
        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.name)));
        appCompatEditText.perform(scrollTo(), replaceText("Merchant One"), closeSoftKeyboard());

        //Fill in sam@gmail.com
        ViewInteraction appCompatEditText2 = onView(allOf(withId(R.id.email)));
        appCompatEditText2.perform(scrollTo(), replaceText("m@gmail.com"), closeSoftKeyboard());

        //Fill in password
        ViewInteraction appCompatEditText3 = onView(allOf(withId(R.id.password)));
        appCompatEditText3.perform(scrollTo(), replaceText("merchant"), closeSoftKeyboard());

        //Click done to get rid of keyboard
        ViewInteraction appCompatEditText4 = onView(allOf(withId(R.id.password)));
        appCompatEditText4.perform(pressImeActionButton());

        // Select merchant
        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.Merchant), withText("Merchant")));
        appCompatRadioButton.perform(scrollTo(), click());

        //Register account
        ViewInteraction appCompatButton2 = onView(allOf(withId(R.id.register)));
        appCompatButton2.perform(scrollTo(), click());

        //Check that the main view comes up
        ViewInteraction view = onView(allOf(withId(R.id.map)));
        view.check(matches(isDisplayed()));
    }

    @Test
    public void oneStoreTest() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        int userID = db.getUserId("m@gmail.com", "Merchant");
        Bitmap icon = BitmapFactory.decodeFile("logo-web.png");
        db.insertStore(userID, 34.024120F, -118.278170F, "Starbucks", icon);
        ArrayList<Store> stores = db.getStores(userID);
        Store store = stores.get(stores.size() - 1);
        db.updateStoreVerification(store.getStoreID());



        ViewInteraction appCompatButton = onView(allOf(withId(R.id.link_signup)));
        appCompatButton.perform(click());

        //Fill in Sam Smith
        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.name)));
        appCompatEditText.perform(scrollTo(), replaceText("Merchant One"), closeSoftKeyboard());

        //Fill in sam@gmail.com
        ViewInteraction appCompatEditText2 = onView(allOf(withId(R.id.email)));
        appCompatEditText2.perform(scrollTo(), replaceText("m@gmail.com"), closeSoftKeyboard());

        //Fill in password
        ViewInteraction appCompatEditText3 = onView(allOf(withId(R.id.password)));
        appCompatEditText3.perform(scrollTo(), replaceText("merchant"), closeSoftKeyboard());

        //Click done to get rid of keyboard
        ViewInteraction appCompatEditText4 = onView(allOf(withId(R.id.password)));
        appCompatEditText4.perform(pressImeActionButton());

        // Select merchant
        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.Merchant), withText("Merchant")));
        appCompatRadioButton.perform(scrollTo(), click());

        //Register account
        ViewInteraction appCompatButton2 = onView(allOf(withId(R.id.register)));
        appCompatButton2.perform(scrollTo(), click());

        //Check that the main view comes up
        ViewInteraction view = onView(allOf(withId(R.id.map)));
        view.check(matches(isDisplayed()));
    }
}
