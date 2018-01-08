package pl.pjwstk.pgmd.hearthlounge.model;

import android.util.Log;

import java.util.Map;

public class DeckFull {

    private String title;
    private String deckId;
    private String playerClass;
    private String archetype;

    private String created;
    private String updated;

    private String deckstring;

    private String author;
    private String authorId;

    private String description;
    private String patch;

    private Deck deck;

    private long views;
    private long votes;
    private long upvotes;
    private long downvotes;
    private long comments;

    private String adventure;
    private String boss;
    private String mode;


    public DeckFull(){

    }


    public DeckFull(Map<String,Object> deck){

        //Add if(!null)

        if(deck.get("title") != null) this.title = deck.get("title").toString();
        if(deck.get("deckId") != null)this.deckId = deck.get("deckId").toString();
        if(deck.get("playerClass") != null)this.playerClass = deck.get("playerClass").toString();
        if(deck.get("archetype") != null)this.archetype = deck.get("archetype").toString();
        if(deck.get("created") != null)this.created = deck.get("created").toString();
        if(deck.get("updated") != null){ this.updated = deck.get("updated").toString(); }
        if(deck.get("deckstring") != null) this.deckstring = deck.get("deckstring").toString();
        if(deck.get("author") != null) this.author = deck.get("author").toString();
        if(deck.get("authorId") != null) this.authorId = deck.get("authorId").toString();
        if(deck.get("description") != null) this.description = deck.get("description").toString();
        if(deck.get("patch") != null) this.patch = deck.get("patch").toString();
        if(deck.get("deck") != null) this.deck = new Deck (deck.get("deck"));
        if(deck.get("views") != null) this.views = (long) deck.get("views");
        if(deck.get("votes") != null){ this.votes = (long) deck.get("votes"); }
        if(deck.get("upvotes") != null)this.upvotes = (long) deck.get("upvotes");
        if(deck.get("downvotes") != null)this.downvotes = (long) deck.get("downvotes");
        if(deck.get("comments") != null) this.comments = (long) deck.get("comments");
        if(deck.get("adventure") != null){ this.adventure = deck.get("adventure").toString();}
        if(deck.get("boss") != null){ this.boss = deck.get("boss").toString();}
        if(deck.get("mode") != null){ this.mode = deck.get("mode").toString();}

//        Log.d("DECKFULL NAME ->", this.getTitle());

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeckId() {
        return deckId;
    }

    public void setDeckId(String deckId) {
        this.deckId = deckId;
    }

    public String getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
    }

    public String getArchetype() {
        return archetype;
    }

    public void setArchetype(String archetype) {
        this.archetype = archetype;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getDeckstring() {
        return deckstring;
    }

    public void setDeckstring(String deckstring) {
        this.deckstring = deckstring;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPatch() {
        return patch;
    }

    public void setPatch(String patch) {
        this.patch = patch;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public long getVotes() {
        return votes;
    }

    public void setVotes(long votes) {
        this.votes = votes;
    }

    public long getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(long upvotes) {
        this.upvotes = upvotes;
    }

    public long getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(long downvotes) {
        this.downvotes = downvotes;
    }

    public long getComments() {
        return comments;
    }

    public void setComments(long comments) {
        this.comments = comments;
    }

    public String getAdventure() {
        return adventure;
    }

    public void setAdventure(String adventure) {
        this.adventure = adventure;
    }

    public String getBoss() {
        return boss;
    }

    public void setBoss(String boss) {
        this.boss = boss;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
