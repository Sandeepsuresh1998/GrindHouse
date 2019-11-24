package com.example.beanandleaf;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
public class RegisterCustomerTest {

    @Rule
    public ActivityTestRule<LandingPage> mActivityTestRule = new ActivityTestRule<>(LandingPage.class);

    @Test
    public void RegistrationProfileTest() {
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

        //Nav to profile tab
        ViewInteraction bottomNavigationItemView = onView(allOf(withId(R.id.navigation_profile), withContentDescription("Profile")));
        bottomNavigationItemView.perform(click());

        //Check that the right name shows up
        ViewInteraction editText = onView(
                allOf(withId(R.id.name_edit)));
        editText.check(matches(withText("Sam Smith")));

        //Check right email shows up
        ViewInteraction editText2 = onView(
                allOf(withId(R.id.email_edit)));
        editText2.check(matches(withText("sam@gmail.com")));

        //Check you have option to update profile
        ViewInteraction button = onView(
                allOf(withId(R.id.update_profile)));
        button.check(matches(isDisplayed()));

        //Check you have option to change password
        ViewInteraction button2 = onView(
                allOf(withId(R.id.change_password)));
        button2.check(matches(isDisplayed()));

    }
}
