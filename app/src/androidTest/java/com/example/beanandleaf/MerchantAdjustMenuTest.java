package com.example.beanandleaf;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
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
public class MerchantAdjustMenuTest {

    @BeforeClass
    public static void setup() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        if (!db.verifyUser("m@usc.ed", "hellll", "Merchant").contentEquals("NULL")) {
            db.removeUser("m@usc.ed", "Merchant");
        }

        Bitmap image = Bitmap.createBitmap(30, 40, Bitmap.Config.ARGB_8888);
        db.insertStore(1, (float) 34.024120, (float) -118.278170, "Starbucks", image);
        db.updateStoreVerification(1);
        db.insertStore(1, (float) 34.026550, (float) -118.285300, "Dulce", image);
        db.updateStoreVerification(2);
    }

    @AfterClass
    public static void breakdown() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        db.removeUser("m@usc.ed", "Merchant");
    }

    @Rule
    public ActivityTestRule<LandingPage> mActivityTestRule = new ActivityTestRule<>(LandingPage.class);


    @Test
    public void merchantAdjustMenuTest() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.link_signup), withText("No account yet? Create one")));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.name)));
        appCompatEditText.perform(scrollTo(), replaceText("merchant a"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.email)));
        appCompatEditText2.perform(scrollTo(), replaceText("m@usc.ed"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.password)));
        appCompatEditText3.perform(scrollTo(), replaceText("hellll"), closeSoftKeyboard());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.Merchant), withText("Merchant")));
        appCompatRadioButton.perform(scrollTo(), click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.register), withText("Create My Account")));
        appCompatButton2.perform(scrollTo(), click());

//        ViewInteraction bottomNavigationItemView = onView(
//                allOf(withId(R.id.navigation_store), withContentDescription("Store")));
//        bottomNavigationItemView.perform(click());

//        ViewInteraction appCompatButton3 = onView(
//                allOf(withId(R.id.add_store), withText("Add Store")));
//        appCompatButton3.perform(scrollTo(), click());
//
//        ViewInteraction appCompatEditText4 = onView(
//                allOf(withId(R.id.storename_edit)));
//        appCompatEditText4.perform(scrollTo(), replaceText("Starbucks"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText5 = onView(
//                allOf(withId(R.id.location_lat_edit)));
//        appCompatEditText5.perform(scrollTo(), replaceText("34.026410"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText6 = onView(
//                allOf(withId(R.id.location_long_edit)));
//        appCompatEditText6.perform(scrollTo(), replaceText("-118.277470"), closeSoftKeyboard());
//
//        ViewInteraction appCompatButton4 = onView(
//                allOf(withId(R.id.add_new_store), withText("Add New Store")));
//        appCompatButton4.perform(scrollTo(), click());
//
//        ViewInteraction appCompatButton5 = onView(
//                allOf(withId(R.id.uploadButton), withText("Upload Proof of Store Ownership")));
//        appCompatButton5.perform(click());
//
//        ViewInteraction appCompatButton6 = onView(
//                allOf(withId(R.id.submitButton), withText("Submit Store Verification")));
//        appCompatButton6.perform(click());
//
//        ViewInteraction appCompatImageView = onView(
//                allOf(withId(R.id.logout)));
//        appCompatImageView.perform(click());
//
//        ViewInteraction appCompatButton7 = onView(
//                allOf(withId(R.id.link_admin), withText("Admin Access")));
//        appCompatButton7.perform(click());
//
//        ViewInteraction appCompatEditText7 = onView(
//                allOf(withId(R.id.admin_password)));
//        appCompatEditText7.perform(replaceText("Admin123!"), closeSoftKeyboard());
//
//        ViewInteraction appCompatButton8 = onView(
//                allOf(withId(R.id.admin_login), withText("Sign in")));
//        appCompatButton8.perform(click());
//
//        ViewInteraction appCompatButton9 = onView(
//                allOf(withId(R.id.verify_store), withText("Verify Store")));
//        appCompatButton9.perform(scrollTo(), click());
//
//        ViewInteraction appCompatImageView2 = onView(
//                allOf(withId(R.id.admin_logout)));
//        appCompatImageView2.perform(scrollTo(), click());
//
//        ViewInteraction appCompatButton10 = onView(
//                allOf(withId(R.id.link_login), withText("Log In")));
//        appCompatButton10.perform(click());
//
//        ViewInteraction appCompatEditText8 = onView(
//                allOf(withId(R.id.username)));
//        appCompatEditText8.perform(replaceText("m@usc.ed"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText9 = onView(
//                allOf(withId(R.id.password)));
//        appCompatEditText9.perform(replaceText("hellll"), closeSoftKeyboard());
//
//        ViewInteraction appCompatRadioButton2 = onView(
//                allOf(withId(R.id.Merchant), withText("Merchant")));
//        appCompatRadioButton2.perform(click());
//
//        ViewInteraction appCompatButton11 = onView(
//                allOf(withId(R.id.login), withText("Sign in")));
//        appCompatButton11.perform(click());

//        ViewInteraction bottomNavigationItemView2 = onView(
//                allOf(withId(R.id.navigation_menu), withContentDescription("Menu")));
//        bottomNavigationItemView2.perform(click());
//
//        ViewInteraction appCompatButton12 = onView(
//                allOf(withId(R.id.add_menu_item_button), withText("Add Menu Item")));
//        appCompatButton12.perform(scrollTo(), click());
//
//        ViewInteraction appCompatEditText10 = onView(
//                allOf(withId(R.id.item_name_edit)));
//        appCompatEditText10.perform(scrollTo(), replaceText("Matcha"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText11 = onView(
//                allOf(withId(R.id.caffeine_small_edit)));
//        appCompatEditText11.perform(scrollTo(), replaceText("100"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText12 = onView(
//                allOf(withId(R.id.caffeine_medium_edit)));
//        appCompatEditText12.perform(scrollTo(), replaceText("150"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText13 = onView(
//                allOf(withId(R.id.caffeine_large_edit)));
//        appCompatEditText13.perform(scrollTo(), replaceText("200"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText14 = onView(
//                allOf(withId(R.id.price_small_edit)));
//        appCompatEditText14.perform(scrollTo(), replaceText("4"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText15 = onView(
//                allOf(withId(R.id.price_medium_edit)));
//        appCompatEditText15.perform(scrollTo(), replaceText("4.50"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText16 = onView(
//                allOf(withId(R.id.price_large_edit)));
//        appCompatEditText16.perform(scrollTo(), replaceText("5"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText17 = onView(
//                allOf(withId(R.id.calories_for_small_edit)));
//        appCompatEditText17.perform(scrollTo(), replaceText("120"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText18 = onView(
//                allOf(withId(R.id.calories_for_medium_edit)));
//        appCompatEditText18.perform(scrollTo(), replaceText("170"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText19 = onView(
//                allOf(withId(R.id.calories_for_large_edit)));
//        appCompatEditText19.perform(scrollTo(), replaceText("220"), closeSoftKeyboard());
//
//        ViewInteraction appCompatButton13 = onView(
//                allOf(withId(R.id.add_item_button), withText("Add Menu Item")));
//        appCompatButton13.perform(scrollTo(), click());
//
//        //ID ONE
//        ViewInteraction button = onView(
//                allOf(withId(101)));
//        button.perform(click());
//
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
//        //IDK ABOUT THIS
//        ViewInteraction button2 = onView(
//                allOf(withId(1)));
//        button2.perform(click());
//
//        ViewInteraction bottomNavigationItemView3 = onView(
//                allOf(withId(R.id.navigation_store), withContentDescription("Store")));
//        bottomNavigationItemView3.perform(click());
//
//        ViewInteraction appCompatButton15 = onView(
//                allOf(withId(R.id.add_store), withText("Add Store")));
//        appCompatButton15.perform(scrollTo(), click());
//
//        ViewInteraction appCompatEditText22 = onView(
//                allOf(withId(R.id.storename_edit)));
//        appCompatEditText22.perform(scrollTo(), replaceText("Dulce"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText23 = onView(
//                allOf(withId(R.id.location_lat_edit)));
//        appCompatEditText23.perform(scrollTo(), replaceText("34.026550"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText24 = onView(
//                allOf(withId(R.id.location_long_edit)));
//        appCompatEditText24.perform(scrollTo(), replaceText("-118.285300"), closeSoftKeyboard());
//
//        ViewInteraction appCompatButton16 = onView(
//                allOf(withId(R.id.add_new_store), withText("Add New Store")));
//        appCompatButton16.perform(scrollTo(), click());
//
//        ViewInteraction appCompatButton17 = onView(
//                allOf(withId(R.id.uploadButton), withText("Upload Proof of Store Ownership")));
//        appCompatButton17.perform(click());
//
//        ViewInteraction appCompatButton18 = onView(
//                allOf(withId(R.id.submitButton), withText("Submit Store Verification")));
//        appCompatButton18.perform(click());
//
//        ViewInteraction appCompatImageView3 = onView(
//                allOf(withId(R.id.logout)));
//        appCompatImageView3.perform(click());
//
//        ViewInteraction appCompatButton19 = onView(
//                allOf(withId(R.id.link_admin), withText("Admin Access")));
//        appCompatButton19.perform(click());
//
//        ViewInteraction appCompatEditText25 = onView(
//                allOf(withId(R.id.admin_password),
//                        childAtPosition(
//                                allOf(withId(R.id.container),
//                                        childAtPosition(
//                                                withId(android.R.id.content),
//                                                0)),
//                                3),
//                        isDisplayed()));
//        appCompatEditText25.perform(replaceText("Admin123!"), closeSoftKeyboard());
//
//        ViewInteraction appCompatButton20 = onView(
//                allOf(withId(R.id.admin_login), withText("Sign in"),
//                        childAtPosition(
//                                allOf(withId(R.id.container),
//                                        childAtPosition(
//                                                withId(android.R.id.content),
//                                                0)),
//                                4),
//                        isDisplayed()));
//        appCompatButton20.perform(click());
//
//
//        ViewInteraction appCompatSpinner = onView(
//                allOf(withId(R.id.admin_store_spinner)));
//        appCompatSpinner.perform(scrollTo(), click());
//
////        DataInteraction appCompatCheckedTextView = onData(anything())
////                .inAdapterView(allOf(withId(com.example.beanandleaf:color / white),
////        childAtPosition(
////                withId(com.example.beanandleaf:color / white),
////        0))).atPosition(1);
////        appCompatCheckedTextView.perform(click());
//
//
//        ViewInteraction appCompatButton22 = onView(
//                allOf(withId(R.id.verify_store), withText("Verify Store")));
//        appCompatButton22.perform(scrollTo(), click());
//
//        ViewInteraction appCompatImageView4 = onView(
//                allOf(withId(R.id.admin_logout)));
//        appCompatImageView4.perform(scrollTo(), click());
//
//        ViewInteraction appCompatButton23 = onView(
//                allOf(withId(R.id.link_login), withText("Log In")));
//        appCompatButton23.perform(click());
//
//        ViewInteraction appCompatEditText28 = onView(
//                allOf(withId(R.id.username)));
//        appCompatEditText28.perform(replaceText("m@usc.ed"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText29 = onView(
//                allOf(withId(R.id.password)));
//        appCompatEditText29.perform(replaceText("hellll"), closeSoftKeyboard());
//
//        ViewInteraction appCompatRadioButton3 = onView(
//                allOf(withId(R.id.Merchant), withText("Merchant")));
//        appCompatRadioButton3.perform(click());
//
//        ViewInteraction appCompatButton24 = onView(
//                allOf(withId(R.id.login), withText("Sign in")));
//        appCompatButton24.perform(click());
//
//
//
//        ViewInteraction bottomNavigationItemView4 = onView(
//                allOf(withId(R.id.navigation_store), withContentDescription("Store")));
//        bottomNavigationItemView4.perform(click());
//
//        ViewInteraction appCompatSpinner2 = onView(
//                allOf(withId(R.id.store_spinner)));
//        appCompatSpinner2.perform(scrollTo(), click());
//
//        DataInteraction appCompatCheckedTextView2 = onData(anything())
//                .inAdapterView(allOf(withId(com.example.beanandleaf:color/white),
//        childAtPosition(
//                withId(com.example.beanandleaf:color / white),
//        0)))
//.atPosition(1);
//        appCompatCheckedTextView2.perform(click());
//
//        ViewInteraction bottomNavigationItemView5 = onView(
//                allOf(withId(R.id.navigation_map), withContentDescription("Map")));
//        bottomNavigationItemView5.perform(click());
//    }

//    private static Matcher<View> childAtPosition(
//            final Matcher<View> parentMatcher, final int position) {
//
//        return new TypeSafeMatcher<View>() {
//            @Override
//            public void describeTo(Description description) {
//                description.appendText("Child at position " + position + " in parent ");
//                parentMatcher.describeTo(description);
//            }
//
//            @Override
//            public boolean matchesSafely(View view) {
//                ViewParent parent = view.getParent();
//                return parent instanceof ViewGroup && parentMatcher.matches(parent)
//                        && view.equals(((ViewGroup) parent).getChildAt(position));
//            }
//        };
//    }
    }
}
