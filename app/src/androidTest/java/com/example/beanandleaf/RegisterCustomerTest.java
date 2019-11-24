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
        //Click log in button
        ViewInteraction appCompatButton3 = onView(allOf(withId(R.id.link_login)));
        appCompatButton3.perform(click());

        //Fill in username with sam@gmail.com
        ViewInteraction appCompatEditText5 = onView(allOf(withId(R.id.username)));
        appCompatEditText5.perform(replaceText("sam@gmail.com"), closeSoftKeyboard());

        //Fill in corresponding password
        ViewInteraction appCompatEditText6 = onView(allOf(withId(R.id.password)));
        appCompatEditText6.perform(replaceText("smith1"), closeSoftKeyboard());

        //Try log in
        ViewInteraction appCompatEditText7 = onView(allOf(withId(R.id.password)));
        appCompatEditText7.perform(pressImeActionButton());

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
