package pl.pjwstk.pgmd.hearthlounge.espresso;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.pjwstk.pgmd.hearthlounge.InitiateApp;
import pl.pjwstk.pgmd.hearthlounge.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static pl.pjwstk.pgmd.hearthlounge.config.EspressoTestsMatchers.hasDrawable;
import static pl.pjwstk.pgmd.hearthlounge.config.EspressoTestsMatchers.withDrawable;

/**
 * Created by Maciek Dembowski on 07.01.2018.
 */

@RunWith(AndroidJUnit4.class)
public class InitiateAppTest {

    @Rule
    public ActivityTestRule<InitiateApp> initiateAppActivityTestRule = new ActivityTestRule<InitiateApp>(InitiateApp.class);

    private InitiateApp initiateApp = null;

    @Before
    public void setUp() throws Exception {
        initiateApp = initiateAppActivityTestRule.getActivity();

    }

//    @Test
//    public void checkFirstImageIsCorrect() {
//        // Type text and then press the button.
//        ImageView view = initiateApp.findViewById(R.id.logo_hearthlounge);
//        onView(withId(view.getId())).check(matches(withDrawable(R.drawable.hearthlounge)));
//        onView(withId(view.getId())).check(matches(hasDrawable()));
//    }

    @Test
    public void checkTextIsCorrect () {
        TextView text_hearthlounge = initiateApp.findViewById(R.id.text_hearthlounge);
        onView(withText(text_hearthlounge.getText().toString())).check(ViewAssertions.matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception {
        initiateApp = null;
    }

}