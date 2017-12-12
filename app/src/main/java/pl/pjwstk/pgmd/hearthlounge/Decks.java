package pl.pjwstk.pgmd.hearthlounge;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Maciek Dembowski on 13.11.2017.
 */

public class Decks extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private LinearLayout all_cards;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cards_filter_menu);

//        toolbar = (Toolbar)findViewById(R.id.toolbar);
//        //viewPager = (ViewPager)findViewById(R.id.view_pager);
//        tabLayout = (TabLayout)findViewById(R.id.tabs);
//
//        setSupportActionBar(toolbar);
//        setupViewPager(viewPager);
//        tabLayout.setupWithViewPager(viewPager);

//        LinearLayout all_cards = (LinearLayout)findViewById(R.id.all_cards);
//        all_cards.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                v.setBackgroundResource(R.color.sea_color);
//                Intent picture_intent = new Intent(getApplicationContext(),CardsJSON.class);
//                startActivity(picture_intent );
//            }
//        });

        all_cards = (LinearLayout) findViewById(R.id.all_cards);
        all_cards.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.pressed);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    v.setBackgroundResource(R.drawable.normal);
                    Intent startIntent = new Intent(getApplicationContext(),CardsJSON.class); //Do którego ma iść
                    startActivity(startIntent);
                    return true;
                }
                return false;
            }
        });

    }

//    private void  setupViewPager(ViewPager viewPager) {
//        Adapter adapter = new Adapter(getSupportFragmentManager());
////        adapter.addFragment(new TopBarFragment(), "Search");
////        adapter.addFragment(new TopBarFragment(), "All Cards");
//        viewPager.setAdapter(adapter);
//    }
//
//    static class Adapter extends FragmentPagerAdapter {
//        private final List<Fragment> fragments = new ArrayList<>();
//        private final List<String> fragmentTitles = new ArrayList<>();
//
//        public Adapter(FragmentManager fragmentManager) {
//            super(fragmentManager);
//        }
//
//        public void addFragment(Fragment fragment, String title) {
//            fragments.add(fragment);
//            fragmentTitles.add(title);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return fragments.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return fragments.size();
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return fragmentTitles.get(position);
//        }
//    }
}
