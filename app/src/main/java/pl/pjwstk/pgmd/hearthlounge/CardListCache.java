package pl.pjwstk.pgmd.hearthlounge;

/**
 * Created by Maciek Dembowski on 16.12.2017.
 */

import android.content.Intent;
import android.widget.Toast;

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
import java.util.*;

import pl.pjwstk.pgmd.hearthlounge.model.Card;

public class CardListCache {

    private static CardListCache instance;
    private List<Card> cardList;
    private List<Card> primaryCardList;
    public Boolean x = true;

    protected CardListCache() {


        if(x){
            x = false;
            System.out.println("pobieram karty ;)");
            this.primaryCardList = readCardList();

        }
        this.cardList = new ArrayList<>();
    }

    public static CardListCache getInstance() {
        synchronized (CardListCache.class) {
            if (instance == null)
                instance = new CardListCache();
            return instance;
        }
    }

    public List<Card> getPrimaryCardList(){

        return primaryCardList;
    }


    public void addList(List<Card> cards) {
        synchronized (this) {
            if (!this.primaryCardList.contains(cards)) {
                List<Card> newCardList = new ArrayList<>();
                this.primaryCardList.addAll(newCardList);
            }
            this.primaryCardList.addAll(cards);
        }
    }

    public List<Card> getList() {
        synchronized (this) {
            return this.primaryCardList;
        }
    }

    // Reszte można usunąć później

    public void add(Card card) {
        synchronized (this) {
            if (!this.primaryCardList.contains(card)) {
                Card newCardList = new Card();
                this.primaryCardList.add(newCardList);
            }
            this.primaryCardList.add(card);
        }
    }

    public Card search(int manaValue) {
        synchronized (this) {
            List<Card> list = instance.cardList;
            for (Card card : list) {
                if (card.getCost() == manaValue)
                    return card;
            }
            return null;
        }
    }

    public void addToCache(List<Card> addedElements) {
        synchronized (this) {
            for (Card card : addedElements) {
                List<Card> newElements = addedElements;
                if (this.primaryCardList.contains(card)) {
                    List<Card> targetList = this.primaryCardList;
                    targetList.addAll(newElements);
                } else {
//                    Card newList = new Card();
//                    this.primaryCardList.add(newList);
                    this.primaryCardList.addAll(new ArrayList<Card>());
                    this.primaryCardList.addAll(newElements);
                }
            }
        }
    }

    public Card get(int manaValue) {
        synchronized (this) {
            return this.primaryCardList.get(manaValue);
        }
    }

    public void clear() {
        synchronized (this) {
            this.primaryCardList.clear();
        }
    }

    public List<Card> readCardList() {

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            //URL url = new URL(params[0] + params[1] + params[2]);
            //URL url = new URL(URL + HEADER + KEY);

            URL url = new URL("https://omgvamp-hearthstone-v1.p.mashape.com/cards?collectible=1");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("X-Mashape-Key", "T15rGIqg2lmshwDGMsX3mZeWM7vBp1ZmfvVjsnFba6SXP2WK5Q");
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader((new InputStreamReader(stream)));

            StringBuffer buffer = new StringBuffer();

            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);//.append("\n");
            }
            String finalJson = buffer.toString();

            JSONObject parentObject = new JSONObject(finalJson);

            JSONArray basic = parentObject.getJSONArray("Basic");
            JSONArray classic = parentObject.getJSONArray("Classic");
            JSONArray hall_of_fame = parentObject.getJSONArray("Hall of Fame");
            JSONArray naxxramas = parentObject.getJSONArray("Naxxramas");
            JSONArray goblins_vs_gnomes = parentObject.getJSONArray("Goblins vs Gnomes");
            JSONArray blackrock_mountain = parentObject.getJSONArray("Blackrock Mountain");
            JSONArray the_grand_tournament = parentObject.getJSONArray("The Grand Tournament");
            JSONArray the_league_of_explorers = parentObject.getJSONArray("The League of Explorers");
            JSONArray whispers_of_the_old_gods = parentObject.getJSONArray("Whispers of the Old Gods");
            JSONArray one_night_in_karazhan = parentObject.getJSONArray("One Night in Karazhan");
            JSONArray mean_streets_of_gadgetzan = parentObject.getJSONArray("Mean Streets of Gadgetzan");
            JSONArray journey_to_unGoro = parentObject.getJSONArray("Journey to Un'Goro");
            JSONArray knights_of_the_frozen_throne = parentObject.getJSONArray("Knights of the Frozen Throne");
            JSONArray kobolds_and_catacombs = parentObject.getJSONArray("Kobolds & Catacombs");
//                JSONArray tavern_brawl = parentObject.getJSONArray("Tavern Brawl");
            JSONArray hero_skins = parentObject.getJSONArray("Hero Skins");
//                JSONArray missions = parentObject.getJSONArray("Missions");
//                JSONArray credits = parentObject.getJSONArray("Credits");

            List<Card> cardList = new ArrayList<>();
            Gson gson = new Gson();

            // BASIC
            for (int i = 0; i < basic.length(); i++) {
                JSONObject finalObject = basic.getJSONObject(i);
                Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                cardList.add(cardModel);
            }

            // CLASSIC
            for (int i = 0; i < classic.length(); i++) {
                JSONObject finalObject = classic.getJSONObject(i);
                Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                cardList.add(cardModel);
            }

            // HALL OF FAME
            for (int i = 0; i < hall_of_fame.length(); i++) {
                JSONObject finalObject = hall_of_fame.getJSONObject(i);
                Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                cardList.add(cardModel);
            }

            // NAXXRAMAS
            for (int i = 0; i < naxxramas.length(); i++) {
                JSONObject finalObject = naxxramas.getJSONObject(i);
                Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                cardList.add(cardModel);
            }

            // GOBLINS VS GNOMES
            for (int i = 0; i < goblins_vs_gnomes.length(); i++) {
                JSONObject finalObject = goblins_vs_gnomes.getJSONObject(i);
                Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                cardList.add(cardModel);
            }

            // BLACKROCK MOUNTAIN
            for (int i = 0; i < blackrock_mountain.length(); i++) {
                JSONObject finalObject = blackrock_mountain.getJSONObject(i);
                Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                cardList.add(cardModel);
            }

            // THE GRAND TOURNAMENT
            for (int i = 0; i < the_grand_tournament.length(); i++) {
                JSONObject finalObject = the_grand_tournament.getJSONObject(i);
                Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                cardList.add(cardModel);
            }

            // THE LEAGUE OF EXPLORERS
            for (int i = 0; i < the_league_of_explorers.length(); i++) {
                JSONObject finalObject = the_league_of_explorers.getJSONObject(i);
                Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                cardList.add(cardModel);
            }

            // WHISPERS OF THE OLD GODS
            for (int i = 0; i < whispers_of_the_old_gods.length(); i++) {
                JSONObject finalObject = whispers_of_the_old_gods.getJSONObject(i);
                Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                cardList.add(cardModel);
            }

            // ONE NIGHT IN KARAZHAN
            for (int i = 0; i < one_night_in_karazhan.length(); i++) {
                JSONObject finalObject = one_night_in_karazhan.getJSONObject(i);
                Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                cardList.add(cardModel);
            }

            // MEAN STREETS OF GADGETZAN
            for (int i = 0; i < mean_streets_of_gadgetzan.length(); i++) {
                JSONObject finalObject = mean_streets_of_gadgetzan.getJSONObject(i);
                Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                cardList.add(cardModel);
            }

            // JOURNEY TO UN'GORO
            for (int i = 0; i < journey_to_unGoro.length(); i++) {
                JSONObject finalObject = journey_to_unGoro.getJSONObject(i);
                Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                cardList.add(cardModel);
            }

            // KNIGHTS OF THE FROZEN THRONE
            for (int i = 0; i < knights_of_the_frozen_throne.length(); i++) {
                JSONObject finalObject = knights_of_the_frozen_throne.getJSONObject(i);
                Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                cardList.add(cardModel);
            }

            // KOBOLDS & CATACOMBS
            for (int i = 0; i < kobolds_and_catacombs.length(); i++) {
                JSONObject finalObject = kobolds_and_catacombs.getJSONObject(i);
                Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                cardList.add(cardModel);
            }

//                // TAVERN BRAWL
//          for (int i = 0; i < tavern_brawl.length(); i++) {
//              JSONObject finalObject = tavern_brawl.getJSONObject(i);
//              Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
//              cardList.add(cardModel);
//          }

            // HERO SKINS
            for (int i = 0; i < hero_skins.length(); i++) {
                JSONObject finalObject = hero_skins.getJSONObject(i);
                Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                cardList.add(cardModel);
            }

//                // MISSIONS
//                for (int i = 0; i < missions.length(); i++) {
//                    JSONObject finalObject = missions.getJSONObject(i);
//                    Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
//                    cardList.add(cardModel);
//                }
//
//                // CREDITS
//                for (int i = 0; i < credits.length(); i++) {
//                    JSONObject finalObject = credits.getJSONObject(i);
//                    Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
//                    cardList.add(cardModel);
//                }

//                JSONObject mainObject = new JSONObject(finalJson);
//
//                JSONObject posts = mainObject.getJSONObject("cost");
//
//                Map<String, Card> map = new HashMap<String,Card>();
//                map.put(posts, mainObject);
//
//                ArrayList<String> list = new ArrayList<String>(map.keySet());
//
//                System.out.println(list);
            return cardList;
            //return cardList;

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

//
//package pl.pjwstk.pgmd.hearthlounge;
//
///**
// * Created by Maciek Dembowski on 16.12.2017.
// */
//
//        import com.nostra13.universalimageloader.utils.L;
//
//        import java.util.*;
//
//        import pl.pjwstk.pgmd.hearthlounge.model.Card;
//
//public class CardListCache {
//
//    private static CardListCache instance;
//    private Map<Integer, ArrayList<Card>> map;
//
//    private CardListCache() {
//        this.map = new HashMap<Integer, ArrayList<Card>>();
//    }
//
//    public void put(Integer cacheKey, Card value) {
//        synchronized (this) {
//            if (!this.map.containsKey(cacheKey)) {
//                this.map.put(cacheKey, new ArrayList<Card>());
//            }
//            this.map.get(cacheKey).add(value);
//        }
//    }
//
//    public Card search(Integer cacheKey) {
//        synchronized (this) {
//            ArrayList<Card> list = instance.map.get(cacheKey);
//            for (Card card : list) {
//                if (card.getCost() == cacheKey)
//                    return card;
//            }
//            return null;
//        }
//    }
//
//    public void addToCache(Map<Integer, ArrayList<Card>> addedElements) {
//        synchronized (this) {
//            for (Integer key : addedElements.keySet()) { // wyciagamy
//                // wszystkie
//                // klucze
//                ArrayList<Card> newElements = addedElements.get(key);
//                if (this.map.containsKey(key)) {
//                    ArrayList<Card> targetList = this.map.get(key);
//                    targetList.addAll(newElements);
//                } else {
//                    this.map.put(key, new ArrayList<Card>());
//                    this.map.get(key).addAll(newElements);
//                }
//            }
//        }
//    }
//
//    public static CardListCache getInstance() {
//        synchronized (CardListCache.class) {
//            if (instance == null)
//                instance = new CardListCache();
//            return instance;
//        }
//    }
//
//    public ArrayList<Card> get(Integer cacheKey) {
//        synchronized (this) {
//            return this.map.get(cacheKey);
//        }
//    }
//
//    public Map<Integer, ArrayList<Card>> getInnerMap() {
//        synchronized (this) {
//            return this.map;
//        }
//    }
//
//    public void clear() {
//        synchronized (this) {
//            this.map.clear();
//        }
//    }
//
//}
//
//
//
//
//
