package pl.pjwstk.pgmd.hearthlounge.model;

import android.content.Context;

import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;

/**
 * Created by Froozy on 14.12.2017.
 */


    //PROTOTYPE!!!1


public class MyApplicationContext extends DrawerMenu {

    private static Context context;

    public void onCreate() {
        MyApplicationContext.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplicationContext.context;
    }
}
