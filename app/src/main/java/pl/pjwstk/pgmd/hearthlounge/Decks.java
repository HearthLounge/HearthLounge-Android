package pl.pjwstk.pgmd.hearthlounge;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import pl.pjwstk.pgmd.hearthlounge.model.DeckFull;
import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;
import pl.pjwstk.pgmd.hearthlounge.view.TypefaceSpan;

/**
 * Created by Maciek Dembowski on 16.10.2017.
 */

public class Decks extends DrawerMenu {

    private ListView listViewCards;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.decks, frameLayout);

        SpannableString s = new SpannableString("Decks " + DeckListCache.getInstance().getListOfDeckFull().size());
        s.setSpan(new TypefaceSpan(this, "belwe_medium.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        toolbar.setTitle(s);

        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait..."); // showing a dialog for loading the data

        listViewCards = (ListView) findViewById(R.id.list_view_decks);

        new JSONTask().execute();
    }

    public class JSONTask extends AsyncTask<String,String,List<DeckFull>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<DeckFull> doInBackground(String... params) {
            return DeckListCache.getInstance().getListOfDeckFull();
        }

        final Animation animationScale = AnimationUtils.loadAnimation(Decks.this, R.anim.anim_scale);
        @Override
        protected void onPostExecute(final List<DeckFull> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if(result != null) {
                final CardsAdapter adapter = new CardsAdapter(getApplicationContext(), R.layout.decks_row, result);
                listViewCards.setAdapter(adapter);
                listViewCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {  // list item click opens a new detailed activity
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        view.startAnimation(animationScale);

                        DeckFull deckModel = result.get(position); // getting the model
                        Intent intent = new Intent(Decks.this, SelectedDeck.class);
                        intent.putExtra("deckModel", new Gson().toJson(deckModel)); // converting model json into string type and sending it via intent
                        startActivity(intent);
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class CardsAdapter extends ArrayAdapter {

        private List<DeckFull> deckList;
        private int resource;
        private LayoutInflater inflater;

        public CardsAdapter(Context context, int resource, List<DeckFull> object) {
            super(context, resource, object);
            deckList = object;
            this.resource = resource;
            inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(resource, null);

                holder.image_view_player_class = (ImageView)convertView.findViewById(R.id.image_view_player_class);
                holder.text_title = (TextView)convertView.findViewById(R.id.text_title);
                holder.text_author = (TextView)convertView.findViewById(R.id.text_author);
                holder.text_archetyp = (TextView)convertView.findViewById(R.id.text_archetyp);
                holder.text_data = (TextView)convertView.findViewById(R.id.text_data);
                holder.text_up_vote = (TextView)convertView.findViewById(R.id.text_up_vote);
                holder.text_down_vote = (TextView)convertView.findViewById(R.id.text_down_vote);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final ViewHolder finalHolder = holder;

            String mDrawableName = deckList.get(position).getPlayerClass();
            int drawable = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
            holder.image_view_player_class.setImageResource(drawable);

//            Long i = 1511634112096L;
//            Date time = new Date(TimeUnit.MILLISECONDS.convert(created, TimeUnit.SECONDS));

//            Collections.sort(deckList, new Comparator<DeckFull>() {
//                public int compare(DeckFull a, DeckFull b) {
//                    return Long.compare(Long.parseLong(a.getCreated()), Long.parseLong(b.getCreated())); // return a.cos - b.cos
//                }
//            });

            Long created = Long.parseLong(deckList.get(position).getCreated());
//            java.util.Date time =new java.util.Date(created*1000L);
//            String t = String.valueOf(time);
//            holder.text_data.setText(t);

            GregorianCalendar current = new GregorianCalendar(); // pobier aktualną date
            GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("US/Central"));
            calendar.setTimeInMillis(created);
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            format.setCalendar(calendar);

//            Date d1 = new Date(calendar.get);
//            long sum = current.getTime() + calendar.getTime();
//
            String result = format.format(calendar.getTime());
            holder.text_data.setText(result);

            holder.text_title.setText(deckList.get(position).getTitle());
            holder.text_author.setText(deckList.get(position).getAuthor());
            holder.text_archetyp.setText(deckList.get(position).getArchetype());
            holder.text_up_vote.setText(""+deckList.get(position).getUpvotes());
            holder.text_down_vote.setText(""+deckList.get(position).getDownvotes());

            return convertView;
        }

        class ViewHolder{
            private ImageView image_view_player_class;
            private TextView text_title;
            private TextView text_author;
            private TextView text_archetyp;
            private TextView text_data;
            private TextView text_up_vote;
            private TextView text_down_vote;
        }
    }

    // MENU U GÓRY NA PASKU TE TRZY KROPKI :D
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            new JSONTask().execute();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}