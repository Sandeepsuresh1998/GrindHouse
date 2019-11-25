package com.example.beanandleaf;

import androidx.test.espresso.ViewInteraction;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import database.DatabaseHelper;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class EmailChangeTest {

    @BeforeClass
    public static void setup() {
        DatabaseHelper db = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        if (!db.verifyUser("smith98@gmail.com","hell12","Customer").contentEquals("NULL")) {
            db.removeUser("smith98@gmail.com", "Customer");
        }
        if (!db.verifyUser("newsmithnewme@usc.edu","hell12","Customer").contentEquals("NULL")) {
            db.removeUser("newsmithnewme@usc.edu", "Customer");
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

        ViewInteraction appCompatEditText4 = onView(allOf(withId(R.id.email_edit)));
        appCompatEditText4.perform(scrollTo(), replaceText("newsmithnewme@usc.edu"), closeSoftKeyboard());

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
        appCompatEditText5.perform(replaceText("newsmithnewme@usc.edu"), closeSoftKeyboard());

        //Fill in corresponding password
        ViewInteraction appCompatEditText6 = onView(allOf(withId(R.id.password)));
        appCompatEditText6.perform(replaceText("hell12"), closeSoftKeyboard());

        //Try log in
        ViewInteraction appCompatEditText7 = onView(allOf(withId(R.id.password)));
        appCompatEditText7.perform(pressImeActionButton());

        //Look at profile
        bottomNavigationItemView.perform(click());

        //Check if name is changed permanently
        ViewInteraction editText = onView(allOf(withId(R.id.email_edit)));
        editText.check(matches(withText("newsmithnewme@usc.edu")));
    }
}
