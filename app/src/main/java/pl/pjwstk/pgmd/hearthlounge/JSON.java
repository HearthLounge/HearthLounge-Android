package pl.pjwstk.pgmd.hearthlounge;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import pl.pjwstk.pgmd.hearthlounge.model.Card;

/**
 * Created by Maciek Dembowski on 17.12.2017.
 */

public class JSON extends AppCompatActivity {

    private final String URL = "https://omgvamp-hearthstone-v1.p.mashape.com/cards?collectible=1";
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait..."); // showing a dialog for loading the data

        new JSONTask().execute(URL);
    }

    public class JSONTask extends AsyncTask<String, String, List<Card> > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<Card> doInBackground(String... params) {

            return CardListCache.getInstance().getCardList(null);
        }

        @Override
        protected void onPostExecute(final List<Card> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            Intent turboIntent = new Intent(getApplicationContext(), CardsFilterMenu.class);
            startActivity(turboIntent);
        }
    }

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