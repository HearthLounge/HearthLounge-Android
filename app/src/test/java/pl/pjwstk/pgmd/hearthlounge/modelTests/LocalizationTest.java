package pl.pjwstk.pgmd.hearthlounge.modelTests;

import com.google.android.gms.maps.model.LatLng;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import pl.pjwstk.pgmd.hearthlounge.model.Localization;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Froozy on 15.01.2018.
 */
class LocalizationTest {

    Localization localization;

    @BeforeEach void init()
    {
        localization = new Localization();
        LatLng latLngTest = new LatLng(15,30);
        String usernameTest = "Jake";
        localization.setLat(latLngTest.latitude);
        localization.setLng(latLngTest.longitude);
        localization.setUsername(usernameTest);

    }

    @Test
    void localizationFromMapObject(){

        Map<String,Object> obj = new HashMap<>();
        obj.put("username", "Tester");
        obj.put("uid", "QWEASD123456");
        obj.put("rank", 3l);
        obj.put("lat", "15");
        obj.put("lng", "32");

        localization = new Localization(obj);

        assertTrue(localization.getUsername() == "Tester");
        assertTrue(localization.getLat() == 15);
        assertFalse(localization.getLng() == 30);

    }

}