package pl.pjwstk.pgmd.hearthlounge.reddit;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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


import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import pl.pjwstk.pgmd.hearthlounge.R;
import pl.pjwstk.pgmd.hearthlounge.model.RedditModel;
import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;
import pl.pjwstk.pgmd.hearthlounge.view.TypefaceSpan;

/**
 * Created by Maciek Dembowski on 02.01.2018.
 */

public class Reddit extends DrawerMenu {

    private final String URL = "https://www.reddit.com/r/hearthstone/hot.json";

    private ListView listViewCards;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.reddit, frameLayout);

        SpannableString s = new SpannableString("Reddit ");
        s.setSpan(new TypefaceSpan(this, "belwe_medium.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        toolbar.setTitle(s);

        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait..."); // showing a dialog for loading the data

        listViewCards = (ListView) findViewById(R.id.list_view_reddit_posts);

        new JSONTask().execute(URL);
    }

    public class JSONTask extends AsyncTask<String,String,List<RedditModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<RedditModel> doInBackground(String... params) {
            return RedditCache.getInstance().getRedditList();
        }

        final Animation animationScale = AnimationUtils.loadAnimation(Reddit.this, R.anim.anim_scale);
        @Override
        protected void onPostExecute(final List<RedditModel> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if(result != null) {
                final Reddit.RedditAdapter adapter = new Reddit.RedditAdapter(getApplicationContext(), R.layout.reddit_row, result);
                listViewCards.setAdapter(adapter);
                listViewCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {  // list item click opens a new detailed activity
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        view.startAnimation(animationScale);

                        RedditModel redditModel = result.get(position); // getting the model
                        Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                        httpIntent.setData(Uri.parse(redditModel.getUrl()));
                        startActivity(httpIntent);
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class RedditAdapter extends ArrayAdapter {

        private List<RedditModel> redditList;
        private int resource;
        private LayoutInflater inflater;

        public RedditAdapter(Context context, int resource, List<RedditModel> object) {
            super(context, resource, object);
            redditList = object;
            this.resource = resource;
            inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Reddit.RedditAdapter.ViewHolder holder = null;

            if (convertView == null) {
                holder = new Reddit.RedditAdapter.ViewHolder();
                convertView = inflater.inflate(resource, null);

                holder.text_up_vote = (TextView)convertView.findViewById(R.id.text_up_vote);
                holder.text_comments = (TextView)convertView.findViewById(R.id.text_comments);
                holder.text_title = (TextView)convertView.findViewById(R.id.text_title);
                holder.text_author = (TextView)convertView.findViewById(R.id.text_author);
                holder.text_data = (TextView)convertView.findViewById(R.id.text_data);
                holder.image_domain = (ImageView)convertView.findViewById(R.id.image_domain);

                convertView.setTag(holder);
            } else {
                holder = (Reddit.RedditAdapter.ViewHolder) convertView.getTag();
            }

            final Reddit.RedditAdapter.ViewHolder finalHolder = holder;

            holder.text_up_vote.setText(""+redditList.get(position).getScore());
            holder.text_comments.setText(""+redditList.get(position).getNum_comments());
            holder.text_title.setText(redditList.get(position).getTitle());
            holder.text_author.setText(redditList.get(position).getAuthor());

            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTimeInMillis(redditList.get(position).getCreated_utc()*1000L);
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            format.setCalendar(calendar);
            String result = format.format(calendar.getTime());
            holder.text_data.setText(result);

            if (redditList.get(position).getDomain().equals("self.hearthstone")) {
                holder.image_domain.setImageResource(R.drawable.hs_logo);
            } else if (redditList.get(position).getDomain().equals("i.redd.it")) {
                holder.image_domain.setImageResource(R.drawable.reddit);
            } else if (redditList.get(position).getDomain().equals("clips.twitch.tv")) {
                holder.image_domain.setImageResource(R.drawable.twitch);
            } else if (redditList.get(position).getDomain().equals("youtube.com")) {
                holder.image_domain.setImageResource(R.drawable.youtube);
            } else if (redditList.get(position).getDomain().equals("")) { // "i.imgur.com" i inne google itd
                holder.image_domain.setImageResource(R.drawable.comments);
            }
            holder.image_domain.setColorFilter(getResources().getColor(R.color.light_grey));

            return convertView;
        }

        class ViewHolder{
            private TextView text_up_vote;
            private TextView text_comments;
            private TextView text_title;
            private TextView text_author;
            private TextView text_data;
            private ImageView image_domain;
        }
    }

    // MENU U GÓRY NA PASKU TE TRZY KROPKI :D
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            new JSONTask().execute(URL);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
