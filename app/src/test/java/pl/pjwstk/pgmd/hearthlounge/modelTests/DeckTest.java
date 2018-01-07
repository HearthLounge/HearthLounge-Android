package pl.pjwstk.pgmd.hearthlounge.modelTests;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import pl.pjwstk.pgmd.hearthlounge.model.Card;
import pl.pjwstk.pgmd.hearthlounge.model.Deck;

public class DeckTest {

     Deck deck;
     Map<String, Card> mapOfCards;
     Map<String, Object> mapOfObj;

    public Map<String, Card> makeCards(){

        Map<String, Card> testMapOfCards = new LinkedHashMap<>();

        Card cardTest01 = new Card();
        cardTest01.setCardId("cardId01");
        cardTest01.setAmount(1);

        Card cardTest02 = new Card();
        cardTest02.setCardId("cardId01");
        cardTest02.setAmount(2);

        Card cardTest03 = new Card();
        cardTest03.setCardId("cardId01");
        cardTest03.setAmount(2);

        testMapOfCards.put("TestCard01",cardTest01);
        testMapOfCards.put("TestCard02",cardTest02);
        testMapOfCards.put("TestCard03",cardTest03);

        System.out.println("Size of makeCards " + testMapOfCards.size());
        return testMapOfCards;
    }

    public Map<String,Object> makeObjCards(){

        Map<String, Object> testMapOfCards = new LinkedHashMap<>();

        Map<String,Object> obj = new HashMap<>();
        Map<String,Object> obj2 = new HashMap<>();
        Map<String,Object> obj3 = new HashMap<>();

        obj.put("cost", 9l);
        obj.put("type", "minion");

        obj.put("cost", 2l);
        obj.put("type", "spell");

        obj.put("cost", 7l);
        obj.put("type", "minion");

        testMapOfCards.put("TestCard01",obj);
        testMapOfCards.put("TestCard02",obj2);
        testMapOfCards.put("TestCard03",obj3);

        return testMapOfCards;
    }

    @BeforeEach void BEDeckTests(){
        deck = new Deck();
        mapOfCards = makeCards();
        mapOfObj = makeObjCards();
    }

    @Test
    public void TestTest() throws Exception {

        assertEquals(4, 2+2);
    }

    @Test
    public void setCardsIdTest() throws Exception{

        deck.setCards(mapOfCards);
        deck.setCardsId();
        assertTrue(deck.getCardsId().get(0).equals("TestCard01"));
        assertFalse(deck.getCardsId().get(1).equals("TestCard01"));
        assertTrue(deck.getCardsId().contains("TestCard03"));
    }

    @Test
    public void makeCardsTest()throws Exception{

        Deck deckX = new Deck();
        deck.setCards(mapOfCards);
        deck.setCardsId();
        deckX.MakeCards(mapOfObj);
        assertTrue(deck.getCards().size() == deckX.getCards().size());
        assertTrue(deckX.getCards().get("TestCard01").getType().equals("minion"));

    }


}
