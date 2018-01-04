package pl.pjwstk.pgmd.hearthlounge.model;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import pl.pjwstk.pgmd.hearthlounge.MainActivity;
import pl.pjwstk.pgmd.hearthlounge.model.DeckFull;

import static android.content.ContentValues.TAG;

/**
 * Created by Froozy on 28.12.2017.
 */

public class DeckListCache {

    private static FirebaseFirestore fbCloud = FirebaseFirestore.getInstance();

    private CollectionReference fbColDecksRef = fbCloud.collection("decks");
    private DocumentReference fbSingleDeckRef;
    private OnCompleteListener fbOnComLisSingleDoc;
    private static List<Map<String,Object>> listOfMaps;
    private static List<DeckFull> listOfDeckFull;
    CountDownLatch cdl;


    private static DeckListCache instance;
    private List<DeckFull> deckFullList;

    private DeckFull tempDeckFull;

    //CountDownLatch guardian = new CountDownLatch(2);


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
            GetAllDocs();
            //MakeDeckList(listOfMaps);
//            cdl = new CountDownLatch(2);
//            new MyDeckThread(cdl, " Watek_A");
//            new MyDeckThread(cdl, " Watek_B");
//            try {
//                // Oczekiwanie na zdarzenia
//                cdl.await();
//            } catch (InterruptedException exc) {
//                System.out.println(exc);
//            }
//
//        } else {
//            //  ???
//            this.deckFullList = new ArrayList<>();
//        }
        }
    }

    public static String getTitle(int x){

        String newTitle = listOfDeckFull.get(x).getTitle();
        return newTitle;
    }

    public static List<DeckFull> MakeDeckList(List<Map<String,Object>> xListOfMaps){

        List<DeckFull> tempDeckFullList = new ArrayList<>();
        DeckFull toTestDeckFull = new DeckFull();
        Log.d("TITLE OF DECK!!!! ->","Start this shit!!!");
        for(Map map: xListOfMaps){

                listOfDeckFull.add(new DeckFull(map));
        }

        return listOfDeckFull;
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

    public static List<Map<String,Object>> GetAllDocs() {
        fbCloud.collection("/decks").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d("GET_ALL_DECKS", document.getId() + " => " + document.getData());
                                listOfMaps.add(document.getData());
                                listOfDeckFull.add(new DeckFull(listOfMaps.get(listOfMaps.size()-1)));
                            }
                        } else {
                            Log.d("GET_ALL_DECKS", "FAIL FAIL FAIL FAIL ", task.getException());
                        }
                    }
                });
        Log.d("PIPA PIPA PIPA","Wychodze z pobierania listy" );
        return listOfMaps;
    }


}

//    private DeckFull getSingleValue(String deck){
//
//        Log.d("XXXXXXXXXXXXXXX", fbColDecksRef.getId());
//        DocumentReference docRef = fbCloud.collection("decks").document();
//                ///decks/-KzoPVw-yjxnF_Z7x5gv
//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//               tempDeckFull = documentSnapshot.toObject(DeckFull.class);
//                Log.d("XXXXXXXXXXXXXXX", tempDeckFull.getTitle() + " Super tytuł :|");
//            }
//        });
//        return tempDeckFull;
//    }