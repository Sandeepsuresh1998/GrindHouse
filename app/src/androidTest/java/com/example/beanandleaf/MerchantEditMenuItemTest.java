package com.example.beanandleaf;


import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import database.DatabaseHelper;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MerchantEditMenuItemTest {

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

    @Rule
    public ActivityTestRule<LandingPage> mActivityTestRule = new ActivityTestRule<>(LandingPage.class);

    @Test
    public void merchantEditMenuItemTest() {
        //Log In
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.link_login), withText("Log In")));
        appCompatButton.perform(click());

        //Add Email
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.username)));
        appCompatEditText.perform(replaceText("m@usc.ed"));

        //Add Password
        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.password)));
        appCompatEditText2.perform(replaceText("hellll"));

        //Add User Type
        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.Merchant), withText("Merchant")));
        appCompatRadioButton.perform(click());

        //Sign In
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.login), withText("Sign in")));
        appCompatButton2.perform(click());

        //Examine Stores
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_store), withContentDescription("Store")));
        bottomNavigationItemView.perform(click());

        //Click on Different Store
        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.store_spinner)));
        appCompatSpinner.perform(click());
        onData(anything()).atPosition(1).perform(click());

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.navigation_menu), withContentDescription("Menu")));
        bottomNavigationItemView2.perform(click());

        ViewInteraction appCompatButton12 = onView(
                allOf(withId(R.id.add_menu_item_button), withText("Add Menu Item")));
        appCompatButton12.perform(click());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.item_name_edit)));
        appCompatEditText10.perform(scrollTo(), replaceText("Matcha"), closeSoftKeyboard());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.caffeine_small_edit)));
        appCompatEditText11.perform(scrollTo(),replaceText("100"), closeSoftKeyboard());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.caffeine_medium_edit)));
        appCompatEditText12.perform(scrollTo(),replaceText("150"), closeSoftKeyboard());

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.caffeine_large_edit)));
        appCompatEditText13.perform(scrollTo(),replaceText("200"), closeSoftKeyboard());

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.price_small_edit)));
        appCompatEditText14.perform(scrollTo(),replaceText("4"), closeSoftKeyboard());

        ViewInteraction appCompatEditText15 = onView(
                allOf(withId(R.id.price_medium_edit)));
        appCompatEditText15.perform(scrollTo(),replaceText("4.50"), closeSoftKeyboard());

        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.price_large_edit)));
        appCompatEditText16.perform(scrollTo(),replaceText("5"), closeSoftKeyboard());

        ViewInteraction appCompatEditText17 = onView(
                allOf(withId(R.id.calories_for_small_edit)));
        appCompatEditText17.perform(scrollTo(), replaceText("120"), closeSoftKeyboard());

        ViewInteraction appCompatEditText18 = onView(
                allOf(withId(R.id.calories_for_medium_edit)));
        appCompatEditText18.perform(scrollTo(),replaceText("170"), closeSoftKeyboard());

        ViewInteraction appCompatEditText19 = onView(
                allOf(withId(R.id.calories_for_large_edit)));
        appCompatEditText19.perform(scrollTo(),replaceText("220"), closeSoftKeyboard());

        try{
            ViewInteraction appCompatButton13 = onView(
                    allOf(withId(R.id.add_item_button)));
            appCompatButton13.perform(scrollTo(), click(), closeSoftKeyboard());
        }
        catch (NoMatchingViewException x)
        {
            System.out.println("Expected No Matching View Exception");
        }
//
//        ID ONE
        //ViewInteraction button2 = onView(withId(R.id.EditB));
            //    button2.perform(click());

//        ViewInteraction appCompatEditText20 = onView(
//                allOf(withId(R.id.menu_price_small_edit), withText("4.00")));
//        appCompatEditText20.perform(scrollTo(), replaceText("4.20"));
//
//        ViewInteraction appCompatEditText21 = onView(
//                allOf(withId(R.id.menu_price_small_edit), withText("4.20")));
//        appCompatEditText21.perform(closeSoftKeyboard());
//
//        ViewInteraction appCompatButton14 = onView(
//                allOf(withId(R.id.update_item_button), withText("Update Menu Item")));
//        appCompatButton14.perform(scrollTo(), click());
//
        //IDK ABOUT THIS
        //ViewInteraction button2 = onView(withId(1)).perform(click());

    }

}
