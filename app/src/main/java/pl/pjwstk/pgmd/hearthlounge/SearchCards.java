package pl.pjwstk.pgmd.hearthlounge;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;

/**
 * Created by Maciek Dembowski on 05.01.2018.
 */

public class SearchCards extends DrawerMenu {

    private AutoCompleteTextView edit_card_name;



    private ImageButton buttonSearch;
    private String string;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.search_menu, frameLayout);

        String[] Cards = getResources().getStringArray(R.array.Cards);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Cards);
        edit_card_name = (AutoCompleteTextView)findViewById(R.id.edit_card_name);
        edit_card_name.setAdapter(adapter);
        edit_card_name.setThreshold(1);
        edit_card_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                String selection = (String)parent.getItemAtPosition(position);
                setString(selection);
            }
        });




        buttonSearch = (ImageButton) findViewById(R.id.button_search);
        buttonSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.pressed);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    v.setBackgroundResource(R.drawable.frame);

                    Log.d("XXXXXXXXXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX    "+getString());
                    if (getString() != null) {
                        Intent startIntent = new Intent(getApplicationContext(), Cards.class); //Do którego ma iść
                        startIntent.putExtra("StringValue", getString());
                        startIntent.putExtra("Title", getString());
                        startIntent.putExtra("IconID", "All");
                        startIntent.putExtra("drawable", R.drawable.all_cards);
                        startIntent.putExtra("color", R.color.primary_font_color);
                        startActivity(startIntent);
                    } else {
                        Intent startIntent = new Intent(getApplicationContext(), Cards.class); //Do którego ma iść
                        startIntent.putExtra("Title", "All");
                        startIntent.putExtra("IconID", "All");
                        startIntent.putExtra("drawable", R.drawable.all_cards);
                        startIntent.putExtra("color", R.color.primary_font_color);
                        startActivity(startIntent);
                    }

                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.setBackgroundResource(R.drawable.frame);
                    return true;
                }
                return false;
            }
        });
    }
}
