package com.example.beanandleaf;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
public class MerchantAddStoreTest {


    @BeforeClass
    public static void setup() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        if (!db.verifyUser("m@gmail.com","merchant","Merchant").contentEquals("NULL")) {
            db.removeUser("m@gmail.com", "Merchant");
        }
    }

    @AfterClass
    public static void breakdown() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        db.removeUser("m@gmail.com","Merchant");
    }

    @Rule
    public ActivityTestRule<LandingPage> mActivityTestRule = new ActivityTestRule<>(LandingPage.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE");

    @Test
    public void merchantAddStoreTest() {

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.link_signup), withText("No account yet? Create one")));
        appCompatButton.perform(click());

        //Set Name
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.name)));
        appCompatEditText.perform(scrollTo(), replaceText("Merchant One"), closeSoftKeyboard());

        //Set Email
        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.email)));
        appCompatEditText2.perform(scrollTo(), replaceText("m@gmail.com"), closeSoftKeyboard());

        //Set Password
        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.password)));
        appCompatEditText3.perform(scrollTo(), replaceText("merchant"), closeSoftKeyboard());

        //Set as Merchant
        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.password), withText("merchant")));
        appCompatEditText4.perform(pressImeActionButton());

        //Verify that Merchant Button Exists
        ViewInteraction radioButton = onView(
                allOf(withId(R.id.Merchant)));
        radioButton.check(matches(isDisplayed()));

        //Click Merchant
        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.Merchant), withText("Merchant")));
        appCompatRadioButton.perform(scrollTo(), click());

        //Create Account
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.register), withText("Create My Account")));
        appCompatButton2.perform(scrollTo(), click());

        //Enter Store
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_store), withContentDescription("Store")));
        bottomNavigationItemView.perform(click());

        ViewInteraction button = onView(
                allOf(withId(R.id.add_store)));
        button.check(matches(isDisplayed()));

        //Add the Store
        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.add_store), withText("Add Store")));
        appCompatButton3.perform(scrollTo(), click());

        //Enter Details
        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.storename_edit)));
        appCompatEditText5.perform(scrollTo(), replaceText("Starbucks"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.location_lat_edit)));
        appCompatEditText6.perform(scrollTo(), replaceText("34.024120"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.location_long_edit)));
        appCompatEditText7.perform(scrollTo(), replaceText("-118.278170"), closeSoftKeyboard());

        //Supply Proof and Get Verification
        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.add_new_store), withText("Add New Store")));
        appCompatButton4.perform(scrollTo(), click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.uploadButton), withText("Upload Proof of Store Ownership")));
        appCompatButton5.perform(click());


    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }



}
