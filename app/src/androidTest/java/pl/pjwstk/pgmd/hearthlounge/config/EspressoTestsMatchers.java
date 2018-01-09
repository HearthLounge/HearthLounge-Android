package pl.pjwstk.pgmd.hearthlounge.config;

import android.view.View;

import org.hamcrest.Matcher;

import pl.pjwstk.pgmd.hearthlounge.config.DrawableMatcher;

/**
 * Created by Maciek Dembowski on 08.01.2018.
 */

public class EspressoTestsMatchers {

    public static Matcher<View> withDrawable(final int resourceId) {
        return new DrawableMatcher(resourceId);
    }

    public static Matcher<View> noDrawable() {
        return new DrawableMatcher(DrawableMatcher.EMPTY);
    }

    public static Matcher<View> hasDrawable() {
        return new DrawableMatcher(DrawableMatcher.ANY);
    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
}
