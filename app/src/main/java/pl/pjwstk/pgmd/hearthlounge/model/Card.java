package pl.pjwstk.pgmd.hearthlounge.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Maciek Dembowski on 17.10.2017.
 */

public class Card {

    private String cardId; // wywalić
    private String dbfId; // wywalić
    private String name;
    private String cardSet;
    private String type;
    private String faction;
    private String rarity;
    private long cost; // int
    private long attack; // int
    private long health; // int
    private String text; // wywalić
    private String flavor;
    private String artist;
    private Boolean collectible; // Boolean // wywalić
    private Boolean elite; // Boolean // wywalić
    private String race;
    private String playerClass;
    private String howToGet;
    private String howToGetGold;
    private String img;
    private String imgGold;
    private String locale; // wywalić
    @SerializedName("mechanics")
    private List<Mechanics> mechanicsList;

    private long amount;
    private String title;

    public Card(){}

    public Card(Object obj){

        //amount
        Map<String, Object> tempCard = (Map<String, Object>) obj;
        this.cardId = (String) tempCard.get("cardId");
        this.cost = (long) tempCard.get("cost");
        this.rarity = (String) tempCard.get("rarity");
        this.cardSet = (String) tempCard.get("cardSet");
        this.type = (String) tempCard.get("type");
        this.amount = (long) tempCard.get("amount");
        this.title = (String) tempCard.get("");
        Log.d("CARD MAKER", this.getCardId() + " in number of "+ this.getAmount());

    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getDbfId() {
        return dbfId;
    }

    public void setDbfId(String dbfId) {
        this.dbfId = dbfId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardSet() {
        return cardSet;
    }

    public void setCardSet(String cardSet) {
        this.cardSet = cardSet;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFaction() {
        return faction;
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public long getCost() {
        //        return (cost != null)
//                ? cost
//                : null;
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public long getAttack() {
        return attack;
    }

    public void setAttack(long attack) {
        this.attack = attack;
    }

    public long getHealth() {
        return health;
    }

    public void setHealth(long health) {
        this.health = health;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Boolean getCollectible() {
        return collectible;
    }

    public void setCollectible(Boolean collectible) {
        this.collectible = collectible;
    }

    public Boolean getElite() {
        return elite;
    }

    public void setElite(Boolean elite) {
        this.elite = elite;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
    }

    public String getHowToGet() {
        return howToGet;
    }

    public void setHowToGet(String howToGet) {
        this.howToGet = howToGet;
    }

    public String getHowToGetGold() {
        return howToGetGold;
    }

    public void setHowToGetGold(String howToGetGold) {
        this.howToGetGold = howToGetGold;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImgGold() {
        return imgGold;
    }

    public void setImgGold(String imgGold) {
        this.imgGold = imgGold;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public List<Mechanics> getMechanicsList() {
        return mechanicsList;
    }

    public void setMechanicsList(List<Mechanics> mechanicsList) {
        this.mechanicsList = mechanicsList;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public static class Mechanics {

        private String name;

        public String getMechanics() {
            return name;
        }

        public void setMechanics(String name) {
            this.name = name;
        }
    }

    public List<String> getMechanicsText(){
        List<String> tempList = new ArrayList<>();
        for(Mechanics mechanics: mechanicsList){
            tempList.add(mechanics.getMechanics());
        }
        return tempList;
    }
}
