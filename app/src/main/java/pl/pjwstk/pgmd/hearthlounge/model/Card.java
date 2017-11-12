package pl.pjwstk.pgmd.hearthlounge.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Maciek Dembowski on 17.10.2017.
 */

public class Card {

    private String cardId;
    private String dbfId;
    private String name;
    private String cardSet;
    private String type;
    private String faction;
    private String rarity;
    private int cost; // int
    private int attack; // int
    private int health; // int
    private String text;
    private String flavor;
    private String artist;
    private Boolean collectible; // Boolean
    private Boolean elite; // Boolean
    private String playerClass;
    private String img;
    private String imgGold;
    private String locale;
    @SerializedName("mechanics")
    private List<Mechanics> mechanicsList;

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

    public int  getCost() {
//        return (cost != null)
//                ? cost
//                : null;
        return cost;
    }

    public void setCost(int cost) {
//        this.cost = (cost != null)
//                ? cost
//                : null;

        this.cost = cost;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
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

    public String getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
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
        return mechanicsList = null;
    }

    public void setMechanicsList(List<Mechanics> mechanicsList) {
        this.mechanicsList = mechanicsList;
    }

    public static class Mechanics {
        private String name = "";

        public String getMechanics() {
            return name;
        }

        public void setMechanics(String name) {
            this.name = name;
        }
    }
}
