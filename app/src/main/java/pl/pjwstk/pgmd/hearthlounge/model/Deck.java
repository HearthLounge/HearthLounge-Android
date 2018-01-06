package pl.pjwstk.pgmd.hearthlounge.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Deck {


    Map<String, Card> cards;
    long lenght;
    Map<String,Integer> manaCurve;
    long max;
    Map<String,Integer> types;
    private List<String> cardsId;

    Map<String,Long> cardsAmount;

    public Deck(){}

    public Deck(Object deckObject){

        cardsAmount = new LinkedHashMap<>();
        Map<String, Object> temp = (Map<String,Object>) deckObject;
        this.lenght = (long) temp.get("length");
        cards = MakeCards((Map<String, Object>) temp.get("cards"));
        this.setManaCurve((Map<String, Integer>) temp.get("manaCurve"));
        this.max = (long) temp.get("max");
        this.setTypes((Map<String, Integer>) temp.get("types"));
        Log.d("SIZE OF TEMP ",Integer.toString(temp.size()));
        cardsId = new ArrayList<>();
        setCardsId();
    }

    public void setCardsId(List<String> cardsId) {
        this.cardsId = cardsId;
    }

    public List<String> getCardsId() {
        return cardsId;
    }

    public Map<String, Card> getCards() {
        return cards;
    }

    public void setCards(Map<String, Card> cards) {
        this.cards = cards;
    }

    public long getLenght() {
        return lenght;
    }

    public void setLenght(long lenght) {
        this.lenght = lenght;
    }

    public Map<String, Integer> getManaCurve() {
        return manaCurve;
    }

    public void setManaCurve(Map<String, Integer> manaCurve) {
        this.manaCurve = manaCurve;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public Map<String, Integer> getTypes() {
        return types;
    }

    public void setTypes(Map<String, Integer> types) {
        this.types = types;
    }

    public Map<String, Long> getCardsAmount() {
        return cardsAmount;
    }

    public void setCardsAmount(Map<String, Long> cardsAmount) {
        this.cardsAmount = cardsAmount;
    }




    public void setCardsId() {

        cardsId.clear();
        cards.size();
        Log.d("CARDS SIZE", "size of list: "+ cards.size());
        List<String> list = new ArrayList<>(cards.keySet());
        Log.d("CARDS KEY SIZE", "size of list of keys: "+ list.size());
        for(String title: list){

            cardsId.add(title);

        }
    }

    public Map<String, Card> MakeCards(Map<String, Object> fbDeck){

        Map<String, Card> tempMap = new HashMap<>();
        Set<String> tempList = fbDeck.keySet();
        Log.d("MAKE CARDS", "Set<String> tempList: "+ tempList.size());
        Card tempCard;
        for(String value: tempList){

            tempCard = new Card(fbDeck.get(value));
            Log.d("MAKE CARDS", "adding card: "+ value);
            tempMap.put(value,tempCard);
            cardsAmount.put(value,tempCard.getAmount());    //NEW ONE!!!!
            Log.d("MAKE CARDS", "amount of card: "+ tempCard.getAmount());
        }
        return tempMap;
    }


}