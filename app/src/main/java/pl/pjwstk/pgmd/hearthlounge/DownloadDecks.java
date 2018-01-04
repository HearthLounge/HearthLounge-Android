//package pl.pjwstk.pgmd.hearthlounge;
//
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import java.util.List;
//
//import pl.pjwstk.pgmd.hearthlounge.model.DeckFull;
//
///**
// * Created by Maciek Dembowski on 31.12.2017.
// */
//
//public class DownloadDecks extends AppCompatActivity {
//
//    private ProgressBar progressBar;
//    private TextView progressText;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.initiate_app_decks);
//
////        ImageView logoHearthLounge = (ImageView)findViewById(R.id.logo_hearthlounge);
//        TextView textHearthLounge = (TextView)findViewById(R.id.text_hearthlounge);
//        textHearthLounge.setText("HearthLounge Decks");
////        Animation transition = AnimationUtils.loadAnimation(this, R.anim.transition);
////        logoHearthLounge.startAnimation(transition);
////        textHearthLounge.startAnimation(transition);
//
//        progressBar = (ProgressBar)findViewById(R.id.startProgressBar);
//        progressBar.getProgressDrawable().setColorFilter(
//                Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
//        progressText = (TextView)findViewById(R.id.text_progessBar);
//
//        new JSONTask().execute();
//    }
//
//    public class JSONTask extends AsyncTask<String, Integer, List<DeckFull> > {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
////            dialog.show();
////            alertDialog.show();
//            progressBar.setProgress(0);
//        }
//
//        @Override
//        protected List<DeckFull> doInBackground(String... params) {
//
//            for(int i = 0; i < DeckListCache.getInstance().getListOfDeckFull().size(); i++) {
//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                publishProgress(i); // progress iteruje do 1477 czyli tyle ile jest kart w api
//            }
//
//            return DeckListCache.getInstance().getListOfDeckFull();
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//            int countDecks = values[0] + 1;
//            int allDecks = DeckListCache.getInstance().getListOfDeckFull().size();
//            progressBar.setProgress(countDecks);
//            progressBar.setMax(allDecks);
//            progressText.setText("Loading: " + countDecks + " / " + allDecks + " decks");
//        }
//
//        @Override
//        protected void onPostExecute(final List<DeckFull> result) {
//            super.onPostExecute(result);
////            dialog.dismiss();
////            alertDialog.dismiss();
//            Intent turboIntent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(turboIntent);
//        }
//    }
//
//    @Override
//    public void onBackPressed() {}
//}
