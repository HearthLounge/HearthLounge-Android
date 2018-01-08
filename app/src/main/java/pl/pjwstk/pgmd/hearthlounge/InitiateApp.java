package pl.pjwstk.pgmd.hearthlounge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import pl.pjwstk.pgmd.hearthlounge.cache.DeckListCache;
import pl.pjwstk.pgmd.hearthlounge.cards.JSON;
import pl.pjwstk.pgmd.hearthlounge.reddit.RedditCache;

/**
 * Created by Maciek Dembowski on 28.12.2017.
 */

public class InitiateApp extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initiate_app);

        ImageView logoHearthLounge = (ImageView)findViewById(R.id.logo_hearthlounge);
        TextView textHearthLounge = (TextView)findViewById(R.id.text_hearthlounge);
        Animation transition = AnimationUtils.loadAnimation(this, R.anim.transition);
        logoHearthLounge.startAnimation(transition);
        textHearthLounge.startAnimation(transition);

        //SPLASH SCREEN
        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(1);  //Delay of 10000 = 10 seconds
                    DeckListCache.getInstance();
                    RedditCache.getInstance().getRedditList();
                    Intent startApp = new Intent(getApplicationContext(), JSON.class);
                    startActivity(startApp);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        welcomeThread.start();
    }
}
