package pl.pjwstk.pgmd.hearthlounge;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import pl.pjwstk.pgmd.hearthlounge.model.RedditModel;

/**
 * Created by Maciek Dembowski on 02.01.2018.
 */

public class RedditCache extends AsyncTask<String, String, List<RedditModel> > {

    private static RedditCache instance;
    private List<RedditModel> redditList;

    protected RedditCache() {
        if(redditList == null){
            this.redditList = doInBackground();
        } else {
            this.redditList = new LinkedList<>();
        }

    }

    public static RedditCache getInstance() {
        synchronized (RedditCache.class) {
            if (instance == null)
                instance = new RedditCache();
            return instance;
        }
    }

    public void addList(List<RedditModel> reddit) {
        synchronized (this) {
            if (!this.redditList.contains(reddit)) {
                List<RedditModel> newRedditList = new LinkedList<>();
                this.redditList.addAll(newRedditList);
            }
            this.redditList.addAll(reddit);
        }
    }

    public List<RedditModel> getRedditList() {
        synchronized (this) {
            return this.redditList;
        }
    }

    @Override
    protected List<RedditModel> doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL("https://www.reddit.com/r/hearthstone/hot.json");
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader((new InputStreamReader(stream)));

            StringBuffer buffer = new StringBuffer();

            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();

            JSONObject object = new JSONObject(finalJson);
            JSONObject getObject = object.getJSONObject("data");
            JSONArray getArray = getObject.getJSONArray("children");

            final List<RedditModel> redditList = new LinkedList<>();
            Gson gson = new Gson();
            for(int i = 0; i < getArray.length(); i++) {
                JSONObject objects = getArray.getJSONObject(i).getJSONObject("data");
                RedditModel redditModel = gson.fromJson(objects.toString(), RedditModel.class);
                redditList.add(redditModel);
            }

            return redditList;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
