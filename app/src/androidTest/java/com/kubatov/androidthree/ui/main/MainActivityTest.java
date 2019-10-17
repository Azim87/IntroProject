package com.kubatov.androidthree.ui.main;

import android.content.Intent;

import androidx.test.espresso.intent.Intents;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.kubatov.androidthree.R;
import com.kubatov.androidthree.ui.main.forecast.ForecastActivity;
import com.kubatov.androidthree.ui.onboard.OnBoardActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<OnBoardActivity> mActivityTestRule =
            new ActivityTestRule<>(
                    OnBoardActivity.class, true, false);

    @Before
    public void setUp() {
        Intent intent = new Intent();
        mActivityTestRule.launchActivity(intent);
    }


    @Test
    public void testNextButtonClick() {
        onView(withText(R.string.intro_name_1)).check(matches(isDisplayed()));
    }

    @Test
    public void testOBoardActivityFinishIntro() throws InterruptedException {

        onView(withId(R.id.button_next)).perform(click());
        Thread.sleep(200L);

        onView(withId(R.id.button_next)).perform(click());
        Thread.sleep(200L);

        onView(withId(R.id.button_next)).perform(click());
        Thread.sleep(200L);

        Intents.init();

        onView(withId(R.id.button_next)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));

        Intents.release();
        Thread.sleep(300L);


        onView(withId(R.id.edit_text_country)).perform(click(), typeText("Bishkek"), closeSoftKeyboard());
        onView(withId(R.id.refresh_button)).perform(click());

        Thread.sleep(300L);
        onView(withId(R.id.edit_text_country)).check(matches(withText("Bishkek")));
        Thread.sleep(300L);

        Intents.init();
        onView(withId(R.id.forecast_weather)).perform(click());

        intended(hasComponent(ForecastActivity.class.getName()));
        Thread.sleep(300L);
        Intents.release();

    }

    @After
    public void finishActivity(){
        mActivityTestRule.finishActivity();
    }
}
