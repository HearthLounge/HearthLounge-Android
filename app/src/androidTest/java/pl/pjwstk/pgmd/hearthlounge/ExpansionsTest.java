package pl.pjwstk.pgmd.hearthlounge;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import pl.pjwstk.pgmd.hearthlounge.cards.CardListCache;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ExpansionsTest {

    @Rule
    public ActivityTestRule<InitiateApp> mActivityTestRule = new ActivityTestRule<>(InitiateApp.class);

    @Test
    public void expansionsTest() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.button_expansions), isDisplayed()));
        imageButton.perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(allOf(isAssignableFrom(TextView.class),
                withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(withText("Expansions")));

        onView(withId(R.id.expansions_scrollView)).perform(swipeUp());

        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.knights_of_the_frozen_throne), isDisplayed()));
        linearLayout.check(matches(isDisplayed())).perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(allOf(isAssignableFrom(TextView.class),
                withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(withText("Knights of the Frozen Throne")));

        ViewInteraction imageView = onView(
                allOf(withId(R.id.icon_expansions),
                        childAtPosition(
                                allOf(withId(R.id.all_cards),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                2)),
                                0),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

//        ViewInteraction imageView3 = onView(
//                allOf(withId(com.google.android.youtube.R.id.player_control_play_pause_replay_button), withContentDescription("Odtwórz film"),
//                        childAtPosition(
//                                allOf(withId(com.google.android.youtube.R.id.controls_layout),
//                                        childAtPosition(
//                                                IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
//                                                0)),
//                                5),
//                        isDisplayed()));
//        imageView3.check(matches(isDisplayed()));
//
//
//        ViewInteraction imageView4 = onView(
//                allOf(withId(com.google.android.youtube.R.id.fullscreen_button), withContentDescription("Otwórz pełny ekran"),
//                        childAtPosition(
//                                allOf(withId(com.google.android.youtube.R.id.bottom_end_container),
//                                        childAtPosition(
//                                                withId(com.google.android.youtube.R.id.bottom_bar_container),
//                                                1)),
//                                1),
//                        isDisplayed()));
//        imageView4.check(matches(isDisplayed()));

        ViewInteraction linearLayout2 = onView(
                allOf(withId(R.id.all_cards),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                2),
                        isDisplayed()));
        linearLayout2.check(matches(isDisplayed())).perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int count = CardListCache.getInstance().getCardList("Knights of the Frozen Throne").size();

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.text_view_count_cards), withText(count + " results"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText(count + " results")));

        ViewInteraction imageView2 = onView(
                allOf(withId(R.id.image_view_mana_icon),
                        childAtPosition(
                                allOf(withId(R.id.mana_value),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                2)),
                                0),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction linearLayout3 = onView(
                allOf(withId(R.id.mana_value),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                2),
                        isDisplayed()));
        linearLayout3.check(matches(isDisplayed())).perform(click());

        ViewInteraction imageView5 = onView(
                allOf(withId(R.id.five),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                5),
                        isDisplayed()));
        imageView5.check(matches(isDisplayed())).perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.text_view_count_cards), withText("18 results"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("18 results")));

        ViewInteraction imageView6 = onView(
                allOf(withId(R.id.image_view_mana_icon),
                        childAtPosition(
                                allOf(withId(R.id.mana_value),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                2)),
                                0),
                        isDisplayed()));
        imageView6.check(matches(isDisplayed()));

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
