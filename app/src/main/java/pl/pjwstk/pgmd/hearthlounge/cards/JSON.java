package pl.pjwstk.pgmd.hearthlounge.cards;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import pl.pjwstk.pgmd.hearthlounge.MainActivity;
import pl.pjwstk.pgmd.hearthlounge.R;
import pl.pjwstk.pgmd.hearthlounge.model.Card;

/**
 * Created by Maciek Dembowski on 17.12.2017.
 */

public class JSON extends AppCompatActivity {

    private final String URL = "https://omgvamp-hearthstone-v1.p.mashape.com/cards?collectible=1";

    private ProgressBar progressBar;
    private TextView progressText;

    private ProgressDialog dialog;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initiate_app);

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

            for(int i = 0; i < CardListCache.getInstance().getCardList(null).size(); i++) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                progressBar.incrementProgressBy(i);
//                publishProgress(progressBar.getProgress()); // te metowy pokazują progress do 100%
                publishProgress(i); // progress iteruje do 1477 czyli tyle ile jest kart w api
            }

            return CardListCache.getInstance().getCardList(null);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int countCards = values[0] + 1;
            int allCards = CardListCache.getInstance().getCardList(null).size();
            progressBar.setProgress(countCards);
            progressBar.setMax(allCards); // ustawiam max pojemność progressbar-u czyli do 1477kart
            progressText.setText("Loading: " + countCards + " / " + allCards + " cards");
        }

        @Override
        protected void onPostExecute(final List<Card> result) {
            super.onPostExecute(result);
//            dialog.dismiss();
//            alertDialog.dismiss();
            Intent turboIntent = new Intent(getApplicationContext(), MainActivity.class); // DownloadDecks
            startActivity(turboIntent);
        }
    }
}