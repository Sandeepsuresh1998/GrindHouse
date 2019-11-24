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
public class MapExistsTest {

    @Rule
    public ActivityTestRule<LandingPage> mActivityTestRule = new ActivityTestRule<>(LandingPage.class);

    @Test
    public void mapExistsTest() {
        //Create an Account
        ViewInteraction appCompatButton = onView(allOf(withId(R.id.link_signup), withText("No account yet? Create one")));
            appCompatButton.perform(click());

        //Setting User Name
        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.name)));
            appCompatEditText.perform(scrollTo(), replaceText("Sam Smith"), closeSoftKeyboard());

        //Setting email
        ViewInteraction appCompatEditText2 = onView(allOf(withId(R.id.email)));
            appCompatEditText2.perform(scrollTo(), replaceText("sam@gmail.com"), closeSoftKeyboard());

        //Setting Password
        ViewInteraction appCompatEditText3 = onView(allOf(withId(R.id.password)));
            appCompatEditText3.perform(scrollTo(), replaceText("smith1"), closeSoftKeyboard());

        //Setting Gender
        ViewInteraction appCompatRadioButton = onView(allOf(withId(R.id.female), withText("Female")));
            appCompatRadioButton.perform(scrollTo(), click());

        //Creating Account
        ViewInteraction appCompatButton2 = onView(allOf(withId(R.id.register), withText("Create My Account")));
            appCompatButton2.perform(scrollTo(), click());

        //Map Population!!!
        ViewInteraction view = onView(
                allOf(withContentDescription("Google Map"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.map),
                                        0),
                                0),
                        isDisplayed()));
        view.check(matches(isDisplayed()));
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
