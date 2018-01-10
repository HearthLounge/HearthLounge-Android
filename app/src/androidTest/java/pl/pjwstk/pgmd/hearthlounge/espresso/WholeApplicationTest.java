package pl.pjwstk.pgmd.hearthlounge.espresso;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.pjwstk.pgmd.hearthlounge.InitiateApp;

/**
 *
 * Before you run this test you must first manually start the application
 * Check if you are logged in.
 * Log out and start the test
 *
 * */

/**
 * Created by Maciek Dembowski on 09.01.2018.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class WholeApplicationTest {

    private LogInLogOutTest logInLogOutTest = new LogInLogOutTest();
    private DeleteAccount deleteAccount = new DeleteAccount();
    private SignUpTest signUpTest = new SignUpTest();

    private BackToMenu backToMenu = new BackToMenu();

    private DeckTest deckTest = new DeckTest();
    private SearchByNameFromArrayTest searchByNameFromArrayTest = new SearchByNameFromArrayTest();
    private SearchByRadioButtonTest searchByRadioButtonTest = new SearchByRadioButtonTest();
    private SearchByPlayerClassTest searchByPlayerClassTest = new SearchByPlayerClassTest();
    private AllCardsTest allCardsTest = new AllCardsTest();
    private ExpansionsTest expansionsTest = new ExpansionsTest();
    private AdventuresTest adventuresTest = new AdventuresTest();
    private PositionTest positionTest = new PositionTest();
    private RedditTest redditTest = new RedditTest();

    @Rule
    public ActivityTestRule<InitiateApp> mActivityTestRule = new ActivityTestRule<>(InitiateApp.class);

    @Test
    public void wholeApplicationTest() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // LOGIN USER
        logInLogOutTest.onStartOpenNav();
        logInLogOutTest.checkIfUserIsLogOut();
        logInLogOutTest.LogIn();

        // DELETE USER AND CREATE UDER AGAIN
        deleteAccount.deleteAccount();
        backToMenu.backToMainMenu();
        signUpTest.signUpTest();

        // LOGIN USER AGAIN
        logInLogOutTest.onStartOpenNav();
        logInLogOutTest.checkIfUserIsLogOut();
        logInLogOutTest.LogIn();

        // OPEN ALL APLICATION TEST
        deckTest.deckTest();
        backToMenu.backToMainMenu();
        searchByNameFromArrayTest.searchByNameFromArrayTest();
        backToMenu.backToMainMenu();
        searchByRadioButtonTest.searchByRadioButton();
        backToMenu.backToMainMenu();
        searchByPlayerClassTest.searchByPlayerClassTest();
        backToMenu.backToMainMenu();
        allCardsTest.allCardsTest();
        backToMenu.backToMainMenu();
        expansionsTest.expansionsTest();
        backToMenu.backToMainMenu();
        adventuresTest.adventuresTest();
        backToMenu.backToMainMenu();
        positionTest.positionTest(); // here may be a problem if the window with permission pops up
                                    // quickly push ALLOW or comment out this line
        // LOGOUT USER
        logInLogOutTest.checkIfUserIsLogged();
        logInLogOutTest.LogOut();

        redditTest.redditTest(); // this is at the end because reddit leaves the application and can not go back to it
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
