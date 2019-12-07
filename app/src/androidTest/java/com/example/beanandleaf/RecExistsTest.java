package com.example.beanandleaf;


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
public class RecExistsTest {

    @BeforeClass
    public static void setup() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        if (!db.verifyUser("sam@gmail.com","smith1","Customer").contentEquals("NULL")) {
            db.removeUser("sam@gmail.com", "Customer");
        }
    }

    @AfterClass
    public static void breakdown() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        db.removeUser("sam@gmail.com", "Customer");
    }


    @Rule
    public ActivityTestRule<LandingPage> mActivityTestRule = new ActivityTestRule<>(LandingPage.class);

    @Test
    public void recExistsTest() {

        ViewInteraction appCompatButton = onView(allOf(withId(R.id.link_signup)));
        appCompatButton.perform(click());

        //Fill in Sam Smith
        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.name)));
        appCompatEditText.perform(scrollTo(), replaceText("Sam Smith"), closeSoftKeyboard());

        //Fill in sam@gmail.com
        ViewInteraction appCompatEditText2 = onView(allOf(withId(R.id.email)));
        appCompatEditText2.perform(scrollTo(), replaceText("sam@gmail.com"), closeSoftKeyboard());

        //Fill in password
        ViewInteraction appCompatEditText3 = onView(allOf(withId(R.id.password)));
        appCompatEditText3.perform(scrollTo(), replaceText("smith1"), closeSoftKeyboard());

        //Click done to get rid of keyboard
        ViewInteraction appCompatEditText4 = onView(allOf(withId(R.id.password)));
        appCompatEditText4.perform(pressImeActionButton());

        //Register account
        ViewInteraction appCompatButton2 = onView(allOf(withId(R.id.register)));
        appCompatButton2.perform(scrollTo(), click());


        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_recommendations), withContentDescription("Recs")));
        bottomNavigationItemView.perform(click());

//        ViewInteraction view = onView(
//                allOf(withContentDescription("Google Map"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.mapRec),
//                                        0),
//                                0),
//                        isDisplayed()));
//        view.check(matches(isDisplayed()));
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
