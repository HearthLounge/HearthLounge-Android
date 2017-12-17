package pl.pjwstk.pgmd.hearthlounge;

/**
 * Created by Maciek Dembowski on 16.12.2017.
 */

import java.util.*;

import pl.pjwstk.pgmd.hearthlounge.model.Card;

public class CardListCache {

    private static CardListCache instance;
    private List<Card> cardList;

    protected CardListCache() {
        this.cardList = new ArrayList<>();
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
            if (!this.cardList.contains(cards)) {
                List<Card> newCardList = new ArrayList<>();
                this.cardList.addAll(newCardList);
            }
            this.cardList.addAll(cards);
        }
    }

    public List<Card> getList() {
        synchronized (this) {
            return this.cardList;
        }
    }

    // Reszte można usunąć później

    public void add(Card card) {
        synchronized (this) {
            if (!this.cardList.contains(card)) {
                Card newCardList = new Card();
                this.cardList.add(newCardList);
            }
            this.cardList.add(card);
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
                if (this.cardList.contains(card)) {
                    List<Card> targetList = this.cardList;
                    targetList.addAll(newElements);
                } else {
//                    Card newList = new Card();
//                    this.cardList.add(newList);
                    this.cardList.addAll(new ArrayList<Card>());
                    this.cardList.addAll(newElements);
                }
            }
        }
    }

    public Card get(int manaValue) {
        synchronized (this) {
            return this.cardList.get(manaValue);
        }
    }

    public void clear() {
        synchronized (this) {
            this.cardList.clear();
        }
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
