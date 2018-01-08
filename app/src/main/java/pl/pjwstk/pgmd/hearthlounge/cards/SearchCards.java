package pl.pjwstk.pgmd.hearthlounge.cards;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import pl.pjwstk.pgmd.hearthlounge.R;
import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;

import static android.view.View.*;

/**
 * Created by Maciek Dembowski on 05.01.2018.
 */

public class SearchCards extends DrawerMenu {

    private AutoCompleteTextView edit_card_name;
    private RadioGroup rg_PlayerClassRow1, rg_PlayerClassRow2, rg_PlayerClassRow3;
    private RadioGroup rg_CardsSetColumn1, rg_CardsSetColumn2;
    private RadioGroup rg_Type;
    private RadioGroup rg_Rarity;
    private RadioGroup rg_RaceRow1, rg_RaceRow2;
    private RadioGroup rg_MechanicsColumn1, rg_MechanicsColumn2;

    // MANA // HEALTH // ATTACK --> here

    private ImageButton buttonSearch;
    private String string;

    private boolean isChecking = true;
    private int playerClassCheckedId = R.id.mage;
    private int cardsSetCheckedId = R.id.basic;
    private int typeCheckedId = R.id.minion;
    private int rarityCheckedId = R.id.common;
    private int raceCheckedId = R.id.demon;
    private int mechanicsCheckedId = R.id.battlecry;

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

        // AUTO COMPLETE TEXTVIEW using array.Cards in strings.xml
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

        // RADIO BUTTONS FOR PLAYER CLASS
        rg_PlayerClassRow1 = (RadioGroup)findViewById(R.id.playerclass_row1);
        rg_PlayerClassRow1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    rg_PlayerClassRow2.clearCheck();
                    rg_PlayerClassRow3.clearCheck();
                    rg_CardsSetColumn1.clearCheck();
                    rg_CardsSetColumn2.clearCheck();
                    rg_Type.clearCheck();
                    rg_Rarity.clearCheck();
                    rg_RaceRow1.clearCheck();
                    rg_RaceRow2.clearCheck();
                    rg_MechanicsColumn1.clearCheck();
                    rg_MechanicsColumn2.clearCheck();
                    playerClassCheckedId = checkedId;
                }
                isChecking = true;
                showPlayerClass(group);
            }
        });
        rg_PlayerClassRow2 = (RadioGroup)findViewById(R.id.playerclass_row2);
        rg_PlayerClassRow2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    rg_PlayerClassRow1.clearCheck();
                    rg_PlayerClassRow3.clearCheck();
                    rg_CardsSetColumn1.clearCheck();
                    rg_CardsSetColumn2.clearCheck();
                    rg_Type.clearCheck();
                    rg_Rarity.clearCheck();
                    rg_RaceRow1.clearCheck();
                    rg_RaceRow2.clearCheck();
                    rg_MechanicsColumn1.clearCheck();
                    rg_MechanicsColumn2.clearCheck();
                    playerClassCheckedId = checkedId;
                }
                isChecking = true;
                showPlayerClass(group);
            }
        });
        rg_PlayerClassRow3 = (RadioGroup)findViewById(R.id.playerclass_row3);
        rg_PlayerClassRow3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    rg_PlayerClassRow1.clearCheck();
                    rg_PlayerClassRow2.clearCheck();
                    rg_CardsSetColumn1.clearCheck();
                    rg_CardsSetColumn2.clearCheck();
                    rg_Type.clearCheck();
                    rg_Rarity.clearCheck();
                    rg_RaceRow1.clearCheck();
                    rg_RaceRow2.clearCheck();
                    rg_MechanicsColumn1.clearCheck();
                    rg_MechanicsColumn2.clearCheck();
                    playerClassCheckedId = checkedId;
                }
                isChecking = true;
                showPlayerClass(group);
            }
        });

        // RADIO BUTTONS FOR CARDS SET
        rg_CardsSetColumn1 = (RadioGroup)findViewById(R.id.cards_set_column1);
        rg_CardsSetColumn1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    rg_CardsSetColumn2.clearCheck();
                    rg_PlayerClassRow1.clearCheck();
                    rg_PlayerClassRow2.clearCheck();
                    rg_PlayerClassRow3.clearCheck();
                    rg_Type.clearCheck();
                    rg_Rarity.clearCheck();
                    rg_RaceRow1.clearCheck();
                    rg_RaceRow2.clearCheck();
                    rg_MechanicsColumn1.clearCheck();
                    rg_MechanicsColumn2.clearCheck();
                    cardsSetCheckedId = checkedId;
                }
                isChecking = true;
                showCardsSet(group);
            }
        });
        rg_CardsSetColumn2 = (RadioGroup)findViewById(R.id.cards_set_column2);
        rg_CardsSetColumn2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    rg_CardsSetColumn1.clearCheck();
                    rg_PlayerClassRow1.clearCheck();
                    rg_PlayerClassRow2.clearCheck();
                    rg_PlayerClassRow3.clearCheck();
                    rg_Type.clearCheck();
                    rg_Rarity.clearCheck();
                    rg_RaceRow1.clearCheck();
                    rg_RaceRow2.clearCheck();
                    rg_MechanicsColumn1.clearCheck();
                    rg_MechanicsColumn2.clearCheck();
                    cardsSetCheckedId = checkedId;
                }
                isChecking = true;
                showCardsSet(group);
            }
        });

        // RADIO BUTTONS FOR TYPE
        rg_Type = (RadioGroup)findViewById(R.id.type);
        rg_Type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    rg_PlayerClassRow1.clearCheck();
                    rg_PlayerClassRow2.clearCheck();
                    rg_PlayerClassRow3.clearCheck();
                    rg_CardsSetColumn1.clearCheck();
                    rg_CardsSetColumn2.clearCheck();
                    rg_Rarity.clearCheck();
                    rg_RaceRow1.clearCheck();
                    rg_RaceRow2.clearCheck();
                    rg_MechanicsColumn1.clearCheck();
                    rg_MechanicsColumn2.clearCheck();
                    typeCheckedId = checkedId;
                }
                isChecking = true;
                showType(group);
            }
        });

        // RADIO BUTTONS FOR RARITY
        rg_Rarity = (RadioGroup)findViewById(R.id.rarity);
        rg_Rarity.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    rg_PlayerClassRow1.clearCheck();
                    rg_PlayerClassRow2.clearCheck();
                    rg_PlayerClassRow3.clearCheck();
                    rg_CardsSetColumn1.clearCheck();
                    rg_CardsSetColumn2.clearCheck();
                    rg_Type.clearCheck();
                    rg_RaceRow1.clearCheck();
                    rg_RaceRow2.clearCheck();
                    rg_MechanicsColumn1.clearCheck();
                    rg_MechanicsColumn2.clearCheck();
                    rarityCheckedId = checkedId;
                }
                isChecking = true;
                showRarity(group);
            }
        });

        // RADIO BUTTONS FOR RACE
        rg_RaceRow1 = (RadioGroup)findViewById(R.id.race_row1);
        rg_RaceRow1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    rg_PlayerClassRow1.clearCheck();
                    rg_PlayerClassRow2.clearCheck();
                    rg_PlayerClassRow3.clearCheck();
                    rg_CardsSetColumn1.clearCheck();
                    rg_CardsSetColumn2.clearCheck();
                    rg_Type.clearCheck();
                    rg_Rarity.clearCheck();
                    rg_RaceRow2.clearCheck();
                    rg_MechanicsColumn1.clearCheck();
                    rg_MechanicsColumn2.clearCheck();
                    raceCheckedId = checkedId;
                }
                isChecking = true;
                showRace(group);
            }
        });
        rg_RaceRow2 = (RadioGroup)findViewById(R.id.race_row2);
        rg_RaceRow2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    rg_PlayerClassRow1.clearCheck();
                    rg_PlayerClassRow2.clearCheck();
                    rg_PlayerClassRow3.clearCheck();
                    rg_CardsSetColumn1.clearCheck();
                    rg_CardsSetColumn2.clearCheck();
                    rg_Type.clearCheck();
                    rg_Rarity.clearCheck();
                    rg_RaceRow1.clearCheck();
                    rg_MechanicsColumn1.clearCheck();
                    rg_MechanicsColumn2.clearCheck();
                    raceCheckedId = checkedId;
                }
                isChecking = true;
                showRace(group);
            }
        });

        // RADIO BUTTONS FOR MECHANICS
        rg_MechanicsColumn1 = (RadioGroup)findViewById(R.id.mechanics_column1);
        rg_MechanicsColumn1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    rg_PlayerClassRow1.clearCheck();
                    rg_PlayerClassRow2.clearCheck();
                    rg_PlayerClassRow3.clearCheck();
                    rg_CardsSetColumn1.clearCheck();
                    rg_CardsSetColumn2.clearCheck();
                    rg_Type.clearCheck();
                    rg_Rarity.clearCheck();
                    rg_RaceRow1.clearCheck();
                    rg_RaceRow2.clearCheck();
                    rg_MechanicsColumn2.clearCheck();
                    mechanicsCheckedId = checkedId;
                }
                isChecking = true;
                showMechanics(group);
            }
        });
        rg_MechanicsColumn2 = (RadioGroup)findViewById(R.id.mechanics_column2);
        rg_MechanicsColumn2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    rg_PlayerClassRow1.clearCheck();
                    rg_PlayerClassRow2.clearCheck();
                    rg_PlayerClassRow3.clearCheck();
                    rg_CardsSetColumn1.clearCheck();
                    rg_CardsSetColumn2.clearCheck();
                    rg_Type.clearCheck();
                    rg_Rarity.clearCheck();
                    rg_RaceRow1.clearCheck();
                    rg_RaceRow2.clearCheck();
                    rg_MechanicsColumn1.clearCheck();
                    mechanicsCheckedId = checkedId;
                }
                isChecking = true;
                showMechanics(group);
            }
        });

        // MANA // HEALTH // ATTACK --> here

        // SEARCH BUTTON
        buttonSearch = (ImageButton) findViewById(R.id.button_search);
        buttonSearch.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.pressed);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    v.setBackgroundResource(R.drawable.frame);

                    if (getString() != null) {
                        Intent startIntent = new Intent(getApplicationContext(), pl.pjwstk.pgmd.hearthlounge.cards.Cards.class); //Do którego ma iść
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

    public void showPlayerClass(View view) {
        RadioButton radioButtonPlayerClass = null;
        if (playerClassCheckedId == R.id.mage) {
            radioButtonPlayerClass = (RadioButton)findViewById(playerClassCheckedId);
        } else if (playerClassCheckedId == R.id.rogue) {
            radioButtonPlayerClass = (RadioButton)findViewById(playerClassCheckedId);
        } else if (playerClassCheckedId == R.id.paladin) {
            radioButtonPlayerClass = (RadioButton)findViewById(playerClassCheckedId);
        } else if (playerClassCheckedId == R.id.druid) {
            radioButtonPlayerClass = (RadioButton)findViewById(playerClassCheckedId);
        } else if (playerClassCheckedId == R.id.shaman) {
            radioButtonPlayerClass = (RadioButton)findViewById(playerClassCheckedId);
        } else if (playerClassCheckedId == R.id.warlock) {
            radioButtonPlayerClass = (RadioButton)findViewById(playerClassCheckedId);
        } else if (playerClassCheckedId == R.id.priest) {
            radioButtonPlayerClass = (RadioButton)findViewById(playerClassCheckedId);
        } else if (playerClassCheckedId == R.id.warrior) {
            radioButtonPlayerClass = (RadioButton)findViewById(playerClassCheckedId);
        } else if (playerClassCheckedId == R.id.hunter) {
            radioButtonPlayerClass = (RadioButton)findViewById(playerClassCheckedId);
        } else if (playerClassCheckedId == R.id.neutral) {
            radioButtonPlayerClass = (RadioButton)findViewById(playerClassCheckedId);
        }
        setString(radioButtonPlayerClass.getText().toString());
    }

    public void showCardsSet(View view) {
        RadioButton radioButtonCardsSet = null;
        if (cardsSetCheckedId == R.id.basic) {
            radioButtonCardsSet = (RadioButton)findViewById(cardsSetCheckedId);
        } else if (cardsSetCheckedId == R.id.classic) {
            radioButtonCardsSet = (RadioButton)findViewById(cardsSetCheckedId);
        } else if (cardsSetCheckedId == R.id.hall_of_fame) {
            radioButtonCardsSet = (RadioButton)findViewById(cardsSetCheckedId);
        } else if (cardsSetCheckedId == R.id.goblins_vs_gnomes) {
            radioButtonCardsSet = (RadioButton)findViewById(cardsSetCheckedId);
        } else if (cardsSetCheckedId == R.id.the_grand_tournament) {
            radioButtonCardsSet = (RadioButton)findViewById(cardsSetCheckedId);
        } else if (cardsSetCheckedId == R.id.whispers_of_the_old_gods) {
            radioButtonCardsSet = (RadioButton)findViewById(cardsSetCheckedId);
        } else if (cardsSetCheckedId == R.id.mean_streets_of_gadgetzan) {
            radioButtonCardsSet = (RadioButton)findViewById(cardsSetCheckedId);
        } else if (cardsSetCheckedId == R.id.journey_to_ungoro) {
            radioButtonCardsSet = (RadioButton)findViewById(cardsSetCheckedId);
        } else if (cardsSetCheckedId == R.id.knights_of_the_frozen_throne) {
            radioButtonCardsSet = (RadioButton)findViewById(cardsSetCheckedId);
        } else if (cardsSetCheckedId == R.id.kobolds_and_catacombs) {
            radioButtonCardsSet = (RadioButton)findViewById(cardsSetCheckedId);
        } else if (cardsSetCheckedId == R.id.curse_of_naxxramas) {
            radioButtonCardsSet = (RadioButton)findViewById(cardsSetCheckedId);
        } else if (cardsSetCheckedId == R.id.blackrock_mountain) {
            radioButtonCardsSet = (RadioButton)findViewById(cardsSetCheckedId);
        } else if (cardsSetCheckedId == R.id.the_leage_of_explorers) {
            radioButtonCardsSet = (RadioButton)findViewById(cardsSetCheckedId);
        } else if (cardsSetCheckedId == R.id.one_night_in_karazhan) {
            radioButtonCardsSet = (RadioButton)findViewById(cardsSetCheckedId);
        }
        setString(radioButtonCardsSet.getText().toString());
    }

    public void showType(View view) {
        RadioButton radioButtonType = null;
        if (typeCheckedId == R.id.hero) {
            radioButtonType = (RadioButton)findViewById(typeCheckedId);
        } else if (typeCheckedId == R.id.minion) {
            radioButtonType = (RadioButton)findViewById(typeCheckedId);
        } else if (typeCheckedId == R.id.spell) {
            radioButtonType = (RadioButton)findViewById(typeCheckedId);
        } else if (typeCheckedId == R.id.weapon) {
            radioButtonType = (RadioButton)findViewById(typeCheckedId);
        }
        setString(radioButtonType.getText().toString());
    }

    public void showRarity(View view) {
        RadioButton radioButtonRarity = null;
        if (rarityCheckedId == R.id.free) {
            radioButtonRarity = (RadioButton)findViewById(rarityCheckedId);
        } else if (rarityCheckedId == R.id.common) {
            radioButtonRarity = (RadioButton)findViewById(rarityCheckedId);
        } else if (rarityCheckedId == R.id.rare) {
            radioButtonRarity = (RadioButton)findViewById(rarityCheckedId);
        } else if (rarityCheckedId == R.id.epic) {
            radioButtonRarity = (RadioButton)findViewById(rarityCheckedId);
        } else if (rarityCheckedId == R.id.legendary) {
            radioButtonRarity = (RadioButton)findViewById(rarityCheckedId);
        }
        setString(radioButtonRarity.getText().toString());
    }

    public void showRace(View view) {
        RadioButton radioButtonRace = null;
        if (raceCheckedId == R.id.demon) {
            radioButtonRace = (RadioButton)findViewById(raceCheckedId);
        } else if (raceCheckedId == R.id.dregon) {
            radioButtonRace = (RadioButton)findViewById(raceCheckedId);
        } else if (raceCheckedId == R.id.elemental) {
            radioButtonRace = (RadioButton)findViewById(raceCheckedId);
        } else if (raceCheckedId == R.id.mech) {
            radioButtonRace = (RadioButton)findViewById(raceCheckedId);
        } else if (raceCheckedId == R.id.murloc) {
            radioButtonRace = (RadioButton)findViewById(raceCheckedId);
        } else if (raceCheckedId == R.id.beast) {
            radioButtonRace = (RadioButton)findViewById(raceCheckedId);
        } else if (raceCheckedId == R.id.pirate) {
            radioButtonRace = (RadioButton)findViewById(raceCheckedId);
        } else if (raceCheckedId == R.id.totem) {
            radioButtonRace = (RadioButton)findViewById(raceCheckedId);
        }
        setString(radioButtonRace.getText().toString());
    }

    public void showMechanics(View view) {
        RadioButton radioButtonMechanics = null;
        if (mechanicsCheckedId == R.id.adapt) {
            radioButtonMechanics = (RadioButton)findViewById(mechanicsCheckedId);
        } else if (mechanicsCheckedId == R.id.aura) {
            radioButtonMechanics = (RadioButton)findViewById(mechanicsCheckedId);
        } else if (mechanicsCheckedId == R.id.battlecry) {
            radioButtonMechanics = (RadioButton)findViewById(mechanicsCheckedId);
        } else if (mechanicsCheckedId == R.id.charge) {
            radioButtonMechanics = (RadioButton)findViewById(mechanicsCheckedId);
        } else if (mechanicsCheckedId == R.id.combo) {
            radioButtonMechanics = (RadioButton)findViewById(mechanicsCheckedId);
        } else if (mechanicsCheckedId == R.id.deathrattle) {
            radioButtonMechanics = (RadioButton)findViewById(mechanicsCheckedId);
        } else if (mechanicsCheckedId == R.id.discover) {
            radioButtonMechanics = (RadioButton)findViewById(mechanicsCheckedId);
        } else if (mechanicsCheckedId == R.id.divine_shield) {
            radioButtonMechanics = (RadioButton)findViewById(mechanicsCheckedId);
        } else if (mechanicsCheckedId == R.id.enrage) {
            radioButtonMechanics = (RadioButton)findViewById(mechanicsCheckedId);
        } else if (mechanicsCheckedId == R.id.freeze) {
            radioButtonMechanics = (RadioButton)findViewById(mechanicsCheckedId);
        } else if (mechanicsCheckedId == R.id.inspire) {
            radioButtonMechanics = (RadioButton)findViewById(mechanicsCheckedId);
        } else if (mechanicsCheckedId == R.id.jade_golem) {
            radioButtonMechanics = (RadioButton)findViewById(mechanicsCheckedId);
        } else if (mechanicsCheckedId == R.id.lifesteal) {
            radioButtonMechanics = (RadioButton)findViewById(mechanicsCheckedId);
        } else if (mechanicsCheckedId == R.id.overload) {
            radioButtonMechanics = (RadioButton)findViewById(mechanicsCheckedId);
        } else if (mechanicsCheckedId == R.id.poisonous) {
            radioButtonMechanics = (RadioButton)findViewById(mechanicsCheckedId);
        } else if (mechanicsCheckedId == R.id.recruit) {
            radioButtonMechanics = (RadioButton)findViewById(mechanicsCheckedId);
        } else if (mechanicsCheckedId == R.id.quest) {
            radioButtonMechanics = (RadioButton)findViewById(mechanicsCheckedId);
        } else if (mechanicsCheckedId == R.id.secret) {
            radioButtonMechanics = (RadioButton)findViewById(mechanicsCheckedId);
        } else if (mechanicsCheckedId == R.id.silence) {
            radioButtonMechanics = (RadioButton)findViewById(mechanicsCheckedId);
        } else if (mechanicsCheckedId == R.id.stealth) {
            radioButtonMechanics = (RadioButton)findViewById(mechanicsCheckedId);
        } else if (mechanicsCheckedId == R.id.spell_damage) {
            radioButtonMechanics = (RadioButton)findViewById(mechanicsCheckedId);
        } else if (mechanicsCheckedId == R.id.taunt) {
            radioButtonMechanics = (RadioButton)findViewById(mechanicsCheckedId);
        } else if (mechanicsCheckedId == R.id.windfury) {
            radioButtonMechanics = (RadioButton)findViewById(mechanicsCheckedId);
        }
        setString(radioButtonMechanics.getText().toString());
    }
}
