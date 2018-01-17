package pl.pjwstk.pgmd.hearthlounge;

import android.app.Application;
import android.test.ApplicationTestCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import pl.pjwstk.pgmd.hearthlounge.cards.CardListCache;
import pl.pjwstk.pgmd.hearthlounge.model.Card;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Maciek Dembowski on 07.01.2018.
 */

class CardListCacheTest {

    private List<Card> temp;
    private List<Card> temp2;

    @BeforeEach
    void onStart() {
        temp = new ArrayList<>();
        temp2 = new ArrayList<>();

        Card card = new Card();
        card.setCardSet("Goblins vs Gnomes");
        card.setName("War Golem");
        card.setType("Minion");
        card.setCost(8);
        temp.add(card);

        Card card2 = new Card();
        card2.setCardSet("Basic");
        card2.setName("Warbot");
        card.setType("Minion");
        card2.setCost(7);
        temp2.add(card);
        temp2.add(card2);

        CardListCache.getInstance().setPrimaryCardList(temp2);
    }

    @Test
    void addList() {
        assertNotNull(temp);
        assertNotNull(temp2);
    }

    @Test
    void getCardList() {
        assertEquals(CardListCache.getInstance().getCardList("Goblins vs Gnomes"),temp);
    }

//    @Test
//    void doInBackground() {
//
//    }

}