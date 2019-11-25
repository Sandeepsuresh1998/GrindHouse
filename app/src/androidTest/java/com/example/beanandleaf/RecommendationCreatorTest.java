package com.example.beanandleaf;


import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

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

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecommendationCreatorTest {

    @BeforeClass
    public static void setup() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        if (db.verifyUser("sam@gmail.com","smith1","Customer").contentEquals("NULL")) {
            db.insertUser("Sam Smith", "sam@gmail.com", "smith1", "Customer", "Male" );
        }

        db.insertUser("Merchant A", "m@usc.ed", "hellll", "Merchant", "Male" );
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

        db.insertStore(userId, (float) 34.017520, (float) -118.282660, "coffeeBean1", image);
        int coffeeBean = db.getStoreId("coffeeBean1");
        db.updateStoreVerification(coffeeBean);

        db.insertStore(userId, (float) 34.020035, (float) -118.283444, "LiteraTea1", image);
        int LiteraTea = db.getStoreId("LiteraTea1");
        db.updateStoreVerification(LiteraTea);

        db.insertStore(userId, (float) 34.031966, (float) -118.284216, "DRNK1", image);
        int DRNK = db.getStoreId("DRNK1");
        db.updateStoreVerification(DRNK);


    }

    @AfterClass
    public static void breakdown() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        db.removeUser("sam@gmail.com", "Customer");
        db.removeUser("m@usc.ed", "Merchant");
    }


    @Rule
    public ActivityTestRule<LandingPage> mActivityTestRule = new ActivityTestRule<>(LandingPage.class);

    @Test
    public void recommendationCreatorTest() {
        //Log In
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.link_login), withText("Log In")));
        appCompatButton.perform(click());

        //Add Email
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.username)));
        appCompatEditText.perform(replaceText("sam@gmail.com"));

        //Add Password
        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.password)));
        appCompatEditText2.perform(replaceText("smith1"));

        //Sign In
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.login), withText("Sign in")));
        appCompatButton2.perform(click());

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_recommendations), withContentDescription("Recs")));
        bottomNavigationItemView.perform(click());

        ViewInteraction view = onView(
                allOf(withContentDescription("Google Map")));
        view.check(matches(isDisplayed()));

    }


}
