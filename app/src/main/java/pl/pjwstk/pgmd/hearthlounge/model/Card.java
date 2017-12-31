package pl.pjwstk.pgmd.hearthlounge.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

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

//    public Card(){}
//
//    public Card(String cardId, String dbfId, String name, String cardSet, String type, String faction, String rarity, String cost, String attack, String health, String text, String flavor, String artist, Boolean collectible, String elite, String playerClass, String img, String imgGold, String locale, List<Mechanics> mechanicsList) {
//        this.cardId = cardId;
//        this.dbfId = dbfId;
//        this.name = name;
//        this.cardSet = cardSet;
//        this.type = type;
//        this.faction = faction;
//        this.rarity = rarity;
//        this.cost = cost;
//        this.attack = attack;
//        this.health = health;
//        this.text = text;
//        this.flavor = flavor;
//        this.artist = artist;
//        this.collectible = collectible;
//        this.elite = elite;
//        this.playerClass = playerClass;
//        this.img = img;
//        this.imgGold = imgGold;
//        this.locale = locale;
//        this.mechanicsList = mechanicsList;
//    }

    public Card(Object obj){

        //amount
        Map<String, Object> tempCard = (Map<String, Object>) obj;
        this.cardId = (String) tempCard.get("cardId");
        this.cost = (long) tempCard.get("cost");
        this.rarity = (String) tempCard.get("rarity");
        this.cardSet = (String) tempCard.get("cardSet");
        this.type = (String) tempCard.get("type");
        this.amount = (long) tempCard.get("amount");
        Log.d("CARD MAKER", this.getCardId());

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



}
