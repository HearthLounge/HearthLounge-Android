package pl.pjwstk.pgmd.hearthlounge;

/**
 * Created by Maciek Dembowski on 16.12.2017.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
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

import pl.pjwstk.pgmd.hearthlounge.authentication.SignUp;
import pl.pjwstk.pgmd.hearthlounge.model.Card;
import pl.pjwstk.pgmd.hearthlounge.model.Deck;
import pl.pjwstk.pgmd.hearthlounge.model.DeckFull;

public class CardListCache extends AsyncTask<String, String, List<Card> > {

    private static CardListCache instance;
    private List<Card> primaryCardList;

    protected CardListCache() {
        if(primaryCardList == null){
            this.primaryCardList = doInBackground();
        } else {
            this.primaryCardList = new LinkedList<>();
        }

    }

    public static CardListCache getInstance() {
        synchronized (CardListCache.class) {
            if (instance == null)
                instance = new CardListCache();
            return instance;
        }
    }

    public void addList(List<Card> cards) {
        synchronized (this) {
            if (!this.primaryCardList.contains(cards)) {
                List<Card> newCardList = new LinkedList<>();
                this.primaryCardList.addAll(newCardList);
            }
            this.primaryCardList.addAll(cards);
        }
    }

    public List<Card> getCardList(String value) {
        synchronized (this) {
            List<Card> temp = new LinkedList<>();
            if (value != null && (value.contains("Mage") || value.contains("Rogue") || value.contains("Paladin") || value.contains("Druid") || value.contains("Shaman") || value.contains("Warlock") || value.contains("Priest") || value.contains("Warrior") || value.contains("Hunter") || value.contains("Neutral"))){
                for(Card cards: primaryCardList){
                    if (cards.getPlayerClass().equals(value) && !(cards.getType().equals("Hero") && cards.getCost() == 0)) {
                        temp.add(cards);
                    }
                }
                return temp;
            } else if (value != null && (value.contains("Goblins vs Gnomes") || value.contains("The Grand Tournament") || value.contains("Whispers of the Old Gods") || value.contains("Mean Streets of Gadgetzan") || value.contains("Journey to Un'Goro") || value.contains("Knights of the Frozen Throne") ||  value.contains("Kobolds & Catacombs") ||  value.contains("Naxxramas") ||  value.contains("Blackrock Mountain") ||  value.contains("The League of Explorers") ||  value.contains("One Night in Karazhan"))){ //
                for(Card cards: primaryCardList){
                    if (cards.getCardSet().equals(value) && !(cards.getType().equals("Hero") && cards.getCost() == 0)) {
                        temp.add(cards);
                    }
                }
                return temp;
            }
//            else if (value != null && (value.contains(""))){ //
//                for(Card cards: primaryCardList){
//                    if (cards.getCardId().equals()) {
//                        temp.add(cards);
//                    }
//                }
//                return temp;
//            }

            else if (value != null && (value.contains(""))){ //
                List<DeckFull> decksFull = DeckListCache.getInstance().getListOfDeckFull();
                DeckFull tempDeckFull = new DeckFull();
                for(DeckFull full :decksFull){
                    if(full.getDeckId().equals(value)){
                        tempDeckFull = full;
                    }
                }
                Deck deckToShow = tempDeckFull.getDeck();
                for (String title : deckToShow.getCardsId()) {
                    for(Card cards: primaryCardList){
                        if (cards.getName().equals(title)) {
                            if(deckToShow.getCardsAmount().get(title) == 2){
                                temp.add(cards);
                            }
                            temp.add(cards);
                        }
                    }
                }
                Collections.sort(temp, new Comparator<Card>() {
                    public int compare(Card a, Card b) {
                        return Long.compare(a.getCost(), b.getCost()); // return a.cos - b.cos
                    }
                });
                return temp;
            }
            else
                return this.primaryCardList;
        }
    }

    @Override
    protected List<Card> doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String result = "";

        try {
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

            final List<Card> cardList = new LinkedList<>();
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

//            // MISSIONS
//            for (int i = 0; i < missions.length(); i++) {
//                JSONObject finalObject = missions.getJSONObject(i);
//                Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
//                cardList.add(cardModel);
//            }
//
//            // CREDITS
//            for (int i = 0; i < credits.length(); i++) {
//                JSONObject finalObject = credits.getJSONObject(i);
//                Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
//                cardList.add(cardModel);
//            }

            Collections.sort(cardList, new Comparator<Card>() {
                public int compare(Card a, Card b) {
                    return Long.compare(a.getCost(), b.getCost()); // return a.cos - b.cos
                }
            });

            return cardList;

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


    // Reszte można usunąć później

    //    public List<Card> getCardList() {
//        synchronized (this) {
//            return this.primaryCardList;
//        }
//    }

//    public void add(Card card) {
//        synchronized (this) {
//            if (!this.primaryCardList.contains(card)) {
//                Card newCardList = new Card();
//                this.primaryCardList.add(newCardList);
//            }
//            this.primaryCardList.add(card);
//        }
//    }
//
//    public Card search(int manaValue) {
//        synchronized (this) {
//            List<Card> list = instance.primaryCardList;
//            for (Card card : list) {
//                if (card.getCost() == manaValue)
//                    return card;
//            }
//            return null;
//        }
//    }
//
//    public void addToCache(List<Card> addedElements) {
//        synchronized (this) {
//            for (Card card : addedElements) {
//                List<Card> newElements = addedElements;
//                if (this.primaryCardList.contains(card)) {
//                    List<Card> targetList = this.primaryCardList;
//                    targetList.addAll(newElements);
//                } else {
////                    Card newList = new Card();
////                    this.primaryCardList.add(newList);
//                    this.primaryCardList.addAll(new ArrayList<Card>());
//                    this.primaryCardList.addAll(newElements);
//                }
//            }
//        }
//    }
//
//    public Card get(int manaValue) {
//        synchronized (this) {
//            return this.primaryCardList.get(manaValue);
//        }
//    }
//
//    public void clear() {
//        synchronized (this) {
//            this.primaryCardList.clear();
//        }
//    }
}






