package pl.pjwstk.pgmd.hearthlounge;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import pl.pjwstk.pgmd.hearthlounge.initiateApp.InitiateApp;


/**
 * Created by Maciek Dembowski on 07.01.2018.
 */
class InitiateAppTest {

    @Rule
    public ActivityTestRule<InitiateApp> initiateAppActivityTestRule = new ActivityTestRule<InitiateApp>(InitiateApp.class);

    private InitiateApp initiateApp = null;

    @Before
    public void setUp() throws Exception {
        initiateApp = initiateAppActivityTestRule.getActivity();

    }

    @Test
    public void onCreate() {
        View view = initiateApp.findViewById(R.id.logo_hearthlounge);


    }

    @After
    public void tearDown() throws Exception {
        initiateApp = null;
    }

}