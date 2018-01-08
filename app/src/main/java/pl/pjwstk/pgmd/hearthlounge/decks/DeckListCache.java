package pl.pjwstk.pgmd.hearthlounge.decks;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pl.pjwstk.pgmd.hearthlounge.model.DeckFull;

/**
 * Created by Froozy on 28.12.2017.
 */

public class DeckListCache {

    private static FirebaseFirestore fbCloud = FirebaseFirestore.getInstance();

    private static DeckListCache instance;
    private List<DeckFull> deckFullList;

    private List<Map<String,Object>> listOfMaps;
    private List<DeckFull> listOfDeckFull;

    private List<String> listOfDecksId;

    public static DeckListCache getInstance() {
        synchronized (DeckListCache.class) {
            if (instance == null)
                instance = new DeckListCache();
            return instance;
        }
    }

    public DeckListCache() {
        if(deckFullList == null) {
            listOfMaps = new ArrayList<>();
            listOfDeckFull = new ArrayList<>();
            listOfDecksId = new ArrayList<>();
            GetAllDocs();
        }
    }

    public List<DeckFull> MakeDeckList(List<Map<String,Object>> xListOfMaps){
        for(Map map: xListOfMaps){
            listOfDeckFull.add(new DeckFull(map));
        }
        return listOfDeckFull;
    }

    public List<Map<String,Object>> GetAllDocs() {
        fbCloud.collection("/decks").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d("GET_ALL_DECKS", document.getId() + " => " + document.getData());
                                listOfDecksId.add(document.getId());
                                listOfMaps.add(document.getData());
                                listOfDeckFull.add(new DeckFull(listOfMaps.get(listOfMaps.size()-1)));
                            }
                        } else {
                            Log.d("GET_ALL_DECKS", "FAIL FAIL FAIL FAIL ", task.getException());
                        }
                    }
                });
        return listOfMaps;
    }

    public List<DeckFull> getListOfDeckFull() {
        return listOfDeckFull;
    }

    public void setListOfDeckFull(List<DeckFull> listOfDeckFull) {
        this.listOfDeckFull = listOfDeckFull;
    }

    public List<Map<String,Object>> getListOfMaps() {
        return listOfMaps;
    }

    public List<String> getListOfDecksId() {
        return listOfDecksId;
    }

    public void setListOfDecksId(List<String> listOfDecksId) {
        this.listOfDecksId = listOfDecksId;
    }

    public void clear() {
        synchronized (this) {
            listOfMaps.clear();
            this.deckFullList.clear();
        }
    }

    public void reloadDecks(){
        this.clear();
        MakeDeckList(GetAllDocs());
    }
}