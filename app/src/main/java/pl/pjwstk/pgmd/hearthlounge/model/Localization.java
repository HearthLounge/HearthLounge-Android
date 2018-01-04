package pl.pjwstk.pgmd.hearthlounge.model;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.Map;

/**
 * Created by Froozy on 03.01.2018.
 */

public class Localization {

    private String uid;
    private String username;
    private long rank;

    private LatLng latLng;
    private double lat, lng;

    public Localization(String uid, String username, long rank, LatLng latlng){

        this.uid = uid;
        this.username = username;
        this.rank = rank;
        this.latLng = latlng;
        lat = latlng.latitude;
        lng = latlng.longitude;
    }

    public Localization(Map<String, Object> map){

        uid = (String) map.get("uid");
        uid = (String) map.get("username");
        rank = (long) map.get("rank");

        //long tempLat = (long) map.get("lan");
        //double tempLng = (double) map.get("lng");

        String tempLat2 = String.valueOf(map.get("lat"));
        Double tempLat = Double.valueOf(tempLat2);
        Log.d("Localization const", Double.toString(tempLat));
        lat = tempLat;

        String tempLng2 = String.valueOf(map.get("lng"));
        Double tempLng = Double.valueOf(tempLng2);
        Log.d("Localization const", Double.toString(tempLng));
        lng = tempLng;

//        long tempLat = (long) map.get("lat");
//        long tempLng = (long) map.get("lng");




//        lat = Double.parseDouble(tempLat);
//        lng = Double.parseDouble(tempLng);
        this.setLatLng(lat,lng);

    }

    Localization(Localization localization){




    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public void setLatLng(double lat, double lng){

        this.latLng = new LatLng(lat,lng);
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
        if(getLng() != 0){
            setLatLng(new LatLng(this.getLat(), this.getLng()));
        }
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
        if(getLat() != 0){
            setLatLng(this.getLat(), this.getLng());
        }
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getRank() {
        return rank;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }
}
