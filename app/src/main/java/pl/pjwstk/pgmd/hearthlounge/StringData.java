//package pl.pjwstk.pgmd.hearthlounge;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.support.v7.app.AppCompatActivity;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.LinkedList;
//import java.util.List;
//
//import pl.pjwstk.pgmd.hearthlounge.model.Card;
//
///**
// * Created by Maciek Dembowski on 06.01.2018.
// */
//
//public class StringData extends AppCompatActivity {
//
//    private static StringData instance;
//
//    String[] playerClass= getResources().getStringArray(R.array.PlayerClass);
//    private List<String> strings;
//
//    protected StringData() {
//         this.strings = new ArrayList<>(Arrays.asList(playerClass));
//         this.playerClass = getResources().getStringArray(R.array.PlayerClass);
//    }
//
//    public static StringData getInstance() {
//        synchronized (StringData.class) {
//            if (instance == null) {
//                instance = new StringData();
//            }
//            return instance;
//        }
//    }
//
//    Resources resources = getResources();
//    @Override
//    public Resources getResources() {
//        return resources;
//    }
//
//    public void setResources(Resources resources) {
//        this.resources = resources;
//    }
//
//
//    public List<String> getPlayerClass() {
//        return strings;
//    }
//
//    public void setPlayerClass(List<String> strings) {
//        this.strings = strings;
//    }
//
//    public String[] getPlayerClasss() {
//        return playerClass;
//    }
//
//    public void setPlayerClasss(String[] playerClass) {
//        this.playerClass = playerClass;
//    }
//}
