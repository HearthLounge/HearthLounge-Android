package pl.pjwstk.pgmd.hearthlounge.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Froozy on 26.12.2017.
 */

public class Deck {


    Map<String, Card> cards;
    long lenght;
    Map<String,Integer> manaCurve;
    long max;
    Map<String,Integer> types;
    private List<String> cardsId;

    public Deck(){}

    public Deck(Object deckObject){

        Map<String, Object> temp = (Map<String,Object>) deckObject;
        this.lenght = (long) temp.get("length");
        MakeCards((Map<String, Object>) temp.get("cards"));
        this.setManaCurve((Map<String, Integer>) temp.get("manaCurve"));
        this.max = (long) temp.get("max");
        this.setTypes((Map<String, Integer>) temp.get("types"));
        Log.d("SIZE OF TEMP ",Integer.toString(temp.size()));

//        setCardsId();


//        for(int i=0;i<temp.getCards().size();i++){
//
//            this.cards.add(temp.getCards().get(i));
//        }
    }

    public void setCardsId(List<String> cardsId) {
        this.cardsId = cardsId;
    }

    public void setCardsId() {

        Set<String> titles = cards.keySet();
        for(String title: titles){

            this.cardsId.add(title);

        }

    }

    public List<String> getCardsId() {
        return cardsId;
    }

//    public Map<String, Card> getFuckinCardId(Map<String, Object> fb) {
//        List<String> titles = new ArrayList<>();
//        Set<String> titleKey = fb.keySet();
//        for (String title : titleKey) {
//            titles.add(title);
//        }
//        return titles;
//    }

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



    public Map<String, Card> MakeCards(Map<String, Object> fbDeck){

        Map<String, Card> tempMap = new HashMap<>();
        Set<String> tempList = fbDeck.keySet();
        Card tempCard;
        for(String value: tempList){

             tempCard = new Card(fbDeck.get(value));

        }
        return tempMap;
    }


}

//    Deck(List<Card> cardList){
//
//        lenght = cardList.size();
//        for(int i = 0;i<=7;i++){
//            manaCurve.put(Integer.toString(i),0);
//        }
//        int newValue;
//        for(int i = 0;i<lenght;i++){
//
//            newValue = cardList.get(i).getCost();
//            manaCurve.get(newValue);
//
//        }
//
//
//    }