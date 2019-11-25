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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class NameChangeTest {

    @BeforeClass
    public static void setup() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        if (!db.verifyUser("smith98@gmail.com","hell12","Customer").contentEquals("NULL")) {
            db.removeUser("smith98@gmail.com", "Customer");
        }
    }

    @Rule
    public ActivityTestRule<LandingPage> mActivityTestRule = new ActivityTestRule<>(LandingPage.class);

    @Test
    public void nameChangeTest() {
        ViewInteraction appCompatButton = onView(allOf(withId(R.id.link_signup)));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.name)));
        appCompatEditText.perform(scrollTo(), replaceText("Sam smith"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(allOf(withId(R.id.email)));
        appCompatEditText2.perform(scrollTo(), replaceText("smith98@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(allOf(withId(R.id.password)));
        appCompatEditText3.perform(scrollTo(), replaceText("hell12"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(allOf(withId(R.id.register)));
        appCompatButton2.perform(scrollTo(), click());

        ViewInteraction bottomNavigationItemView = onView(allOf(withId(R.id.navigation_profile)));
        bottomNavigationItemView.perform(click());

        ViewInteraction appCompatEditText4 = onView(allOf(withId(R.id.name_edit)));
        appCompatEditText4.perform(scrollTo(), replaceText("Sam Suresh"), closeSoftKeyboard());

        //Submit name change
        ViewInteraction appCompatButton3 = onView(allOf(withId(R.id.update_profile)));
        appCompatButton3.perform(scrollTo(), click());

        //Log out once we've made the change
        ViewInteraction appCompatImageButton = onView(allOf(withId(R.id.logout)));
        appCompatImageButton.perform(click());

        //Click log in button
        ViewInteraction logInButton = onView(allOf(withId(R.id.link_login)));
        logInButton.perform(click());

        //Fill in username with sam@gmail.com
        ViewInteraction appCompatEditText5 = onView(allOf(withId(R.id.username)));
        appCompatEditText5.perform(replaceText("smith98@gmail.com"), closeSoftKeyboard());

        //Fill in corresponding password
        ViewInteraction appCompatEditText6 = onView(allOf(withId(R.id.password)));
        appCompatEditText6.perform(replaceText("hell12"), closeSoftKeyboard());

        //Try log in
        ViewInteraction appCompatEditText7 = onView(allOf(withId(R.id.password)));
        appCompatEditText7.perform(pressImeActionButton());

        //Look at profile
        bottomNavigationItemView.perform(click());

        //Check if name is changed permanently
        ViewInteraction editText = onView(allOf(withId(R.id.name_edit)));
        editText.check(matches(withText("Sam Suresh")));

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
