package pl.pjwstk.pgmd.hearthlounge;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;

/**
 * Created by Maciek Dembowski on 27.12.2017.
 */

public class Expansions extends DrawerMenu {

    private LinearLayout goblinsVsGnomes, theGrandTournament, whispersOfTheOldGods,
            meanStreetsOfGadgetzan, journeyToUnGoro, knightsOfTheFrozenThrone, koboldsAndCatacombs;

    private ImageView imageGoblinsVsGnomes, imageTheGrandTournament, imageWhispersOfTheOldGods,
            imageMeanStreetsOfGadgetzan, imageJourneyToUnGoro, imageKnightsOfTheFrozenThrone, imageKoboldsAndCatacombs;

    private TextView textGoblinsVsGnomes, textTheGrandTournament, textWhispersOfTheOldGods,
            textMeanStreetsOfGadgetzan, textJourneyToUnGoro, textKnightsOfTheFrozenThrone, textKoboldsAndCatacombs;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.expansions, frameLayout);


        goblinsVsGnomes = (LinearLayout) findViewById(R.id.goblins_vs_gnomes);
        imageGoblinsVsGnomes = (ImageView) findViewById(R.id.image_goblins_vs_gnomes);
        textGoblinsVsGnomes = (TextView) findViewById(R.id.text_goblins_vs_gnomes);
        goblinsVsGnomes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    imageGoblinsVsGnomes.animate().scaleXBy(0.2f).setDuration(5000).start();
                    imageGoblinsVsGnomes.animate().scaleYBy(0.2f).setDuration(5000).start();
                    textGoblinsVsGnomes.animate().scaleXBy(0.2f).setDuration(5000).start();
                    textGoblinsVsGnomes.animate().scaleYBy(0.2f).setDuration(5000).start();
                    imageGoblinsVsGnomes.setColorFilter(getResources().getColor(R.color.dark_grey));
                    textGoblinsVsGnomes.setTextColor(getResources().getColor(R.color.dark_grey));
                    v.setBackgroundResource(R.drawable.pressed_gvg);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    imageGoblinsVsGnomes.animate().scaleX(1f).setDuration(1000).start();
                    imageGoblinsVsGnomes.animate().scaleY(1f).setDuration(1000).start();
                    textGoblinsVsGnomes.animate().scaleX(1f).setDuration(1000).start();
                    textGoblinsVsGnomes.animate().scaleY(1f).setDuration(1000).start();
                    imageGoblinsVsGnomes.setColorFilter(getResources().getColor(R.color.gvg));
                    textGoblinsVsGnomes.setTextColor(getResources().getColor(R.color.gvg));
                    v.setBackgroundResource(R.drawable.normal);
                    Intent startIntent = new Intent(getApplicationContext(), ExpansionsDetails.class);
                    startIntent.putExtra("Title", "Goblins vs Gnomes");
                    startIntent.putExtra("IconID", "Goblins vs Gnomes");
                    startIntent.putExtra("drawable", R.drawable.goblins_vs_gnomes);
                    startIntent.putExtra("color", R.color.gvg);
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    imageGoblinsVsGnomes.animate().scaleX(1f).setDuration(1000).start();
                    imageGoblinsVsGnomes.animate().scaleY(1f).setDuration(1000).start();
                    textGoblinsVsGnomes.animate().scaleX(1f).setDuration(1000).start();
                    textGoblinsVsGnomes.animate().scaleY(1f).setDuration(1000).start();
                    imageGoblinsVsGnomes.setColorFilter(getResources().getColor(R.color.gvg));
                    textGoblinsVsGnomes.setTextColor(getResources().getColor(R.color.gvg));
                    v.setBackgroundResource(R.drawable.normal);
                    return true;
                }
                return false;
            }
        });

        theGrandTournament = (LinearLayout) findViewById(R.id.the_grand_tournament);
        imageTheGrandTournament = (ImageView) findViewById(R.id.image_the_grand_tournament);
        textTheGrandTournament = (TextView) findViewById(R.id.text_the_grand_tournament);
        theGrandTournament.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    imageTheGrandTournament.animate().scaleXBy(0.2f).setDuration(5000).start();
                    imageTheGrandTournament.animate().scaleYBy(0.2f).setDuration(5000).start();
                    textTheGrandTournament.animate().scaleXBy(0.2f).setDuration(5000).start();
                    textTheGrandTournament.animate().scaleYBy(0.2f).setDuration(5000).start();
                    imageTheGrandTournament.setColorFilter(getResources().getColor(R.color.dark_grey));
                    textTheGrandTournament.setTextColor(getResources().getColor(R.color.dark_grey));
                    v.setBackgroundResource(R.drawable.pressed_tgt);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    imageTheGrandTournament.animate().scaleX(1f).setDuration(1000).start();
                    imageTheGrandTournament.animate().scaleY(1f).setDuration(1000).start();
                    textTheGrandTournament.animate().scaleX(1f).setDuration(1000).start();
                    textTheGrandTournament.animate().scaleY(1f).setDuration(1000).start();
                    imageTheGrandTournament.setColorFilter(getResources().getColor(R.color.tgt));
                    textTheGrandTournament.setTextColor(getResources().getColor(R.color.tgt));
                    v.setBackgroundResource(R.drawable.normal);
                    Intent startIntent = new Intent(getApplicationContext(), ExpansionsDetails.class);
                    startIntent.putExtra("Title", "The Grand Tournament");
                    startIntent.putExtra("IconID", "The Grand Tournament");
                    startIntent.putExtra("drawable", R.drawable.the_grand_tournament);
                    startIntent.putExtra("color", R.color.tgt);
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    imageTheGrandTournament.animate().scaleX(1f).setDuration(1000).start();
                    imageTheGrandTournament.animate().scaleY(1f).setDuration(1000).start();
                    textTheGrandTournament.animate().scaleX(1f).setDuration(1000).start();
                    textTheGrandTournament.animate().scaleY(1f).setDuration(1000).start();
                    imageTheGrandTournament.setColorFilter(getResources().getColor(R.color.tgt));
                    textTheGrandTournament.setTextColor(getResources().getColor(R.color.tgt));
                    v.setBackgroundResource(R.drawable.normal);
                    return true;
                }
                return false;
            }
        });

        whispersOfTheOldGods = (LinearLayout) findViewById(R.id.whispers_of_the_old_gods);
        imageWhispersOfTheOldGods = (ImageView) findViewById(R.id.image_whispers_of_the_old_gods);
        textWhispersOfTheOldGods = (TextView) findViewById(R.id.text_whispers_of_the_old_gods);
        whispersOfTheOldGods.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    imageWhispersOfTheOldGods.animate().scaleXBy(0.2f).setDuration(5000).start();
                    imageWhispersOfTheOldGods.animate().scaleYBy(0.2f).setDuration(5000).start();
                    textWhispersOfTheOldGods.animate().scaleXBy(0.2f).setDuration(5000).start();
                    textWhispersOfTheOldGods.animate().scaleYBy(0.2f).setDuration(5000).start();
                    imageWhispersOfTheOldGods.setColorFilter(getResources().getColor(R.color.dark_grey));
                    textWhispersOfTheOldGods.setTextColor(getResources().getColor(R.color.dark_grey));
                    v.setBackgroundResource(R.drawable.pressed_wotog);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    imageWhispersOfTheOldGods.animate().scaleX(1f).setDuration(1000).start();
                    imageWhispersOfTheOldGods.animate().scaleY(1f).setDuration(1000).start();
                    textWhispersOfTheOldGods.animate().scaleX(1f).setDuration(1000).start();
                    textWhispersOfTheOldGods.animate().scaleY(1f).setDuration(1000).start();
                    imageWhispersOfTheOldGods.setColorFilter(getResources().getColor(R.color.wotog));
                    textWhispersOfTheOldGods.setTextColor(getResources().getColor(R.color.wotog));
                    v.setBackgroundResource(R.drawable.normal);
                    Intent startIntent = new Intent(getApplicationContext(), ExpansionsDetails.class);
                    startIntent.putExtra("Title", "Whispers of the Old Gods");
                    startIntent.putExtra("IconID", "Whispers of the Old Gods");
                    startIntent.putExtra("drawable", R.drawable.whispers_of_the_old_gods);
                    startIntent.putExtra("color", R.color.wotog);
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    imageWhispersOfTheOldGods.animate().scaleX(1f).setDuration(1000).start();
                    imageWhispersOfTheOldGods.animate().scaleY(1f).setDuration(1000).start();
                    textWhispersOfTheOldGods.animate().scaleX(1f).setDuration(1000).start();
                    textWhispersOfTheOldGods.animate().scaleY(1f).setDuration(1000).start();
                    imageWhispersOfTheOldGods.setColorFilter(getResources().getColor(R.color.wotog));
                    textWhispersOfTheOldGods.setTextColor(getResources().getColor(R.color.wotog));
                    v.setBackgroundResource(R.drawable.normal);
                    return true;
                }
                return false;
            }
        });

        meanStreetsOfGadgetzan = (LinearLayout) findViewById(R.id.mean_streets_of_gadgetzan);
        imageMeanStreetsOfGadgetzan= (ImageView) findViewById(R.id.image_mean_streets_of_gadgetzan);
        textMeanStreetsOfGadgetzan = (TextView) findViewById(R.id.text_mean_streets_of_gadgetzan);
        meanStreetsOfGadgetzan.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    imageMeanStreetsOfGadgetzan.animate().scaleXBy(0.2f).setDuration(5000).start();
                    imageMeanStreetsOfGadgetzan.animate().scaleYBy(0.2f).setDuration(5000).start();
                    textMeanStreetsOfGadgetzan.animate().scaleXBy(0.2f).setDuration(5000).start();
                    textMeanStreetsOfGadgetzan.animate().scaleYBy(0.2f).setDuration(5000).start();
                    imageMeanStreetsOfGadgetzan.setColorFilter(getResources().getColor(R.color.dark_grey));
                    textMeanStreetsOfGadgetzan.setTextColor(getResources().getColor(R.color.dark_grey));
                    v.setBackgroundResource(R.drawable.pressed_msog);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    imageMeanStreetsOfGadgetzan.animate().scaleX(1f).setDuration(1000).start();
                    imageMeanStreetsOfGadgetzan.animate().scaleY(1f).setDuration(1000).start();
                    textMeanStreetsOfGadgetzan.animate().scaleX(1f).setDuration(1000).start();
                    textMeanStreetsOfGadgetzan.animate().scaleY(1f).setDuration(1000).start();
                    imageMeanStreetsOfGadgetzan.setColorFilter(getResources().getColor(R.color.msog));
                    textMeanStreetsOfGadgetzan.setTextColor(getResources().getColor(R.color.msog));
                    v.setBackgroundResource(R.drawable.normal);
                    Intent startIntent = new Intent(getApplicationContext(), ExpansionsDetails.class);
                    startIntent.putExtra("Title", "Mean Streets of Gadgetzan");
                    startIntent.putExtra("IconID", "Mean Streets of Gadgetzan");
                    startIntent.putExtra("drawable", R.drawable.mean_streets_of_gadgetzan);
                    startIntent.putExtra("color", R.color.msog);
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    imageMeanStreetsOfGadgetzan.animate().scaleX(1f).setDuration(1000).start();
                    imageMeanStreetsOfGadgetzan.animate().scaleY(1f).setDuration(1000).start();
                    textMeanStreetsOfGadgetzan.animate().scaleX(1f).setDuration(1000).start();
                    textMeanStreetsOfGadgetzan.animate().scaleY(1f).setDuration(1000).start();
                    imageMeanStreetsOfGadgetzan.setColorFilter(getResources().getColor(R.color.msog));
                    textMeanStreetsOfGadgetzan.setTextColor(getResources().getColor(R.color.msog));
                    v.setBackgroundResource(R.drawable.normal);
                    return true;
                }
                return false;
            }
        });

        journeyToUnGoro = (LinearLayout) findViewById(R.id.journey_to_ungoro);
        imageJourneyToUnGoro= (ImageView) findViewById(R.id.image_journey_to_ungoro);
        textJourneyToUnGoro = (TextView) findViewById(R.id.text_journey_to_ungoro);
        journeyToUnGoro.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    imageJourneyToUnGoro.animate().scaleXBy(0.2f).setDuration(5000).start();
                    imageJourneyToUnGoro.animate().scaleYBy(0.2f).setDuration(5000).start();
                    textJourneyToUnGoro.animate().scaleXBy(0.2f).setDuration(5000).start();
                    textJourneyToUnGoro.animate().scaleYBy(0.2f).setDuration(5000).start();
                    imageJourneyToUnGoro.setColorFilter(getResources().getColor(R.color.dark_grey));
                    textJourneyToUnGoro.setTextColor(getResources().getColor(R.color.dark_grey));
                    v.setBackgroundResource(R.drawable.pressed_ungoro);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    imageJourneyToUnGoro.animate().scaleX(1f).setDuration(1000).start();
                    imageJourneyToUnGoro.animate().scaleY(1f).setDuration(1000).start();
                    textJourneyToUnGoro.animate().scaleX(1f).setDuration(1000).start();
                    textJourneyToUnGoro.animate().scaleY(1f).setDuration(1000).start();
                    imageJourneyToUnGoro.setColorFilter(getResources().getColor(R.color.ungoro));
                    textJourneyToUnGoro.setTextColor(getResources().getColor(R.color.ungoro));
                    v.setBackgroundResource(R.drawable.normal);
                    Intent startIntent = new Intent(getApplicationContext(), ExpansionsDetails.class);
                    startIntent.putExtra("Title", "Journey to Un'Goro");
                    startIntent.putExtra("IconID", "Journey to Un'Goro");
                    startIntent.putExtra("drawable", R.drawable.journey_to_ungoro);
                    startIntent.putExtra("color", R.color.ungoro);
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    imageJourneyToUnGoro.animate().scaleX(1f).setDuration(1000).start();
                    imageJourneyToUnGoro.animate().scaleY(1f).setDuration(1000).start();
                    textJourneyToUnGoro.animate().scaleX(1f).setDuration(1000).start();
                    textJourneyToUnGoro.animate().scaleY(1f).setDuration(1000).start();
                    imageJourneyToUnGoro.setColorFilter(getResources().getColor(R.color.ungoro));
                    textJourneyToUnGoro.setTextColor(getResources().getColor(R.color.ungoro));
                    v.setBackgroundResource(R.drawable.normal);
                    return true;
                }
                return false;
            }
        });

        knightsOfTheFrozenThrone = (LinearLayout) findViewById(R.id.knights_of_the_frozen_throne);
        imageKnightsOfTheFrozenThrone= (ImageView) findViewById(R.id.image_knights_of_the_frozen_throne);
        textKnightsOfTheFrozenThrone = (TextView) findViewById(R.id.text_knights_of_the_frozen_throne);
        knightsOfTheFrozenThrone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    imageKnightsOfTheFrozenThrone.animate().scaleXBy(0.2f).setDuration(5000).start();
                    imageKnightsOfTheFrozenThrone.animate().scaleYBy(0.2f).setDuration(5000).start();
                    textKnightsOfTheFrozenThrone.animate().scaleXBy(0.2f).setDuration(5000).start();
                    textKnightsOfTheFrozenThrone.animate().scaleYBy(0.2f).setDuration(5000).start();
                    imageKnightsOfTheFrozenThrone.setColorFilter(getResources().getColor(R.color.dark_grey));
                    textKnightsOfTheFrozenThrone.setTextColor(getResources().getColor(R.color.dark_grey));
                    v.setBackgroundResource(R.drawable.pressed_kotft);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    imageKnightsOfTheFrozenThrone.animate().scaleX(1f).setDuration(1000).start();
                    imageKnightsOfTheFrozenThrone.animate().scaleY(1f).setDuration(1000).start();
                    textKnightsOfTheFrozenThrone.animate().scaleX(1f).setDuration(1000).start();
                    textKnightsOfTheFrozenThrone.animate().scaleY(1f).setDuration(1000).start();
                    imageKnightsOfTheFrozenThrone.setColorFilter(getResources().getColor(R.color.kotft));
                    textKnightsOfTheFrozenThrone.setTextColor(getResources().getColor(R.color.kotft));
                    v.setBackgroundResource(R.drawable.normal);
                    Intent startIntent = new Intent(getApplicationContext(), ExpansionsDetails.class);
                    startIntent.putExtra("Title", "Knights of the Frozen Throne");
                    startIntent.putExtra("IconID", "Knights of the Frozen Throne");
                    startIntent.putExtra("drawable", R.drawable.knights_of_the_frozen_throne);
                    startIntent.putExtra("color", R.color.kotft);
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    imageKnightsOfTheFrozenThrone.animate().scaleX(1f).setDuration(1000).start();
                    imageKnightsOfTheFrozenThrone.animate().scaleY(1f).setDuration(1000).start();
                    textKnightsOfTheFrozenThrone.animate().scaleX(1f).setDuration(1000).start();
                    textKnightsOfTheFrozenThrone.animate().scaleY(1f).setDuration(1000).start();
                    imageKnightsOfTheFrozenThrone.setColorFilter(getResources().getColor(R.color.kotft));
                    textKnightsOfTheFrozenThrone.setTextColor(getResources().getColor(R.color.kotft));
                    v.setBackgroundResource(R.drawable.normal);
                    return true;
                }
                return false;
            }
        });

        koboldsAndCatacombs = (LinearLayout) findViewById(R.id.kobolds_and_catacombs);
        imageKoboldsAndCatacombs= (ImageView) findViewById(R.id.image_kobolds_and_catacombs);
        textKoboldsAndCatacombs= (TextView) findViewById(R.id.text_kobolds_and_catacombs);
        koboldsAndCatacombs.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    imageKoboldsAndCatacombs.animate().scaleXBy(0.2f).setDuration(5000).start();
                    imageKoboldsAndCatacombs.animate().scaleYBy(0.2f).setDuration(5000).start();
                    textKoboldsAndCatacombs.animate().scaleXBy(0.2f).setDuration(5000).start();
                    textKoboldsAndCatacombs.animate().scaleYBy(0.2f).setDuration(5000).start();
                    imageKoboldsAndCatacombs.setColorFilter(getResources().getColor(R.color.dark_grey));
                    textKoboldsAndCatacombs.setTextColor(getResources().getColor(R.color.dark_grey));
                    v.setBackgroundResource(R.drawable.pressed_kandc);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    imageKoboldsAndCatacombs.animate().scaleX(1f).setDuration(1000).start();
                    imageKoboldsAndCatacombs.animate().scaleY(1f).setDuration(1000).start();
                    textKoboldsAndCatacombs.animate().scaleX(1f).setDuration(1000).start();
                    textKoboldsAndCatacombs.animate().scaleY(1f).setDuration(1000).start();
                    imageKoboldsAndCatacombs.setColorFilter(getResources().getColor(R.color.kandc));
                    textKoboldsAndCatacombs.setTextColor(getResources().getColor(R.color.kandc));
                    v.setBackgroundResource(R.drawable.normal);
                    Intent startIntent = new Intent(getApplicationContext(), ExpansionsDetails.class);
                    startIntent.putExtra("Title", "Kobolds & Catacombs");
                    startIntent.putExtra("IconID", "Kobolds & Catacombs");
                    startIntent.putExtra("drawable", R.drawable.kobolds_catacombs);
                    startIntent.putExtra("color", R.color.kandc);
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    imageKoboldsAndCatacombs.animate().scaleX(1f).setDuration(1000).start();
                    imageKoboldsAndCatacombs.animate().scaleY(1f).setDuration(1000).start();
                    textKoboldsAndCatacombs.animate().scaleX(1f).setDuration(1000).start();
                    textKoboldsAndCatacombs.animate().scaleY(1f).setDuration(1000).start();
                    imageKoboldsAndCatacombs.setColorFilter(getResources().getColor(R.color.kandc));
                    textKoboldsAndCatacombs.setTextColor(getResources().getColor(R.color.kandc));
                    v.setBackgroundResource(R.drawable.normal);
                    return true;
                }
                return false;
            }
        });
    }
}
