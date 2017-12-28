package pl.pjwstk.pgmd.hearthlounge;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import pl.pjwstk.pgmd.hearthlounge.model.Card;

/**
 * Created by Maciek Dembowski on 17.12.2017.
 */

public class JSON extends AppCompatActivity {

    private final String URL = "https://omgvamp-hearthstone-v1.p.mashape.com/cards?collectible=1";

    private ProgressBar progressBar;
//    private int progressStatus = 0;
    private TextView progressText;
    private Handler handler = new Handler();

    private ProgressDialog dialog;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initiate_app);

        ImageView logoHearthLounge = (ImageView)findViewById(R.id.logo_hearthlounge);
        TextView textHearthLounge = (TextView)findViewById(R.id.text_hearthlounge);
        Animation transition = AnimationUtils.loadAnimation(this, R.anim.transition);
        logoHearthLounge.startAnimation(transition);
        textHearthLounge.startAnimation(transition);

        progressBar = (ProgressBar)findViewById(R.id.startProgressBar);
        progressBar.getProgressDrawable().setColorFilter(
                Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
        progressText = (TextView)findViewById(R.id.text_progessBar);

        //Metoda 1
//        dialog = new ProgressDialog(this);
//        View customTitleView = getLayoutInflater().inflate(R.layout.initiate_app, null);
//        dialog.setCustomTitle(customTitleView);
//        dialog.setIndeterminate(false);
//        dialog.setCancelable(false);
//        dialog.setMessage("Loading. Please wait..."); // showing a dialog for loading the data


        //Metoda 2
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//// ...Irrelevant code for customizing the buttons and title
//        LayoutInflater inflater = this.getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.initiate_app, null);
//        dialogBuilder.setView(dialogView);
//        alertDialog = dialogBuilder.create();

        new JSONTask().execute(URL);
    }

    public class JSONTask extends AsyncTask<String, Integer, List<Card> > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            dialog.show();
//            alertDialog.show();
        }

        @Override
        protected List<Card> doInBackground(String... params) {

            for(int i=0; i<=100; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
            }
//            publishProgress(CardListCache.getInstance().getCardList(null).size());

            return CardListCache.getInstance().getCardList(null);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
            progressText.setText(values[0] + "%");
        }

        @Override
        protected void onPostExecute(final List<Card> result) {
            super.onPostExecute(result);
//            dialog.dismiss();
//            alertDialog.dismiss();
            Intent turboIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(turboIntent);
        }
    }

    @Override
    public void onBackPressed() {}

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_refresh) {
//            new JSONTask().execute(URL);
//
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}