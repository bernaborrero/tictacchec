package com.deltagames.tictacchec.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.deltagames.tictacchec.R;


public class MainMenuActivity extends Activity {

    private TextView playWithComputerButton, playWithPersonButton, exitButton;
    private View.OnClickListener mainMenuListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setUpGUI();
    }

    /**
     * Set up the GUI elements of the main menu
     */
    private void setUpGUI() {
        playWithComputerButton = (TextView) findViewById(R.id.playWithComputerButton);
        playWithPersonButton = (TextView) findViewById(R.id.playWithPersonButton);
        exitButton = (TextView) findViewById(R.id.exitButton);

        setUpListeners();
    }

    /**
     * Set up the listeners of the main menu
     */
    private void setUpListeners() {
        mainMenuListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.playWithComputerButton:
                        Intent computerIntent = new Intent(getBaseContext(), GameActivity.class);
                        computerIntent.putExtra(GameActivity.GAME_MODE, GameActivity.GameMode.COMPUTER);
                        startActivity(computerIntent);
                        break;
                    case R.id.playWithPersonButton:
                        Intent personIntent = new Intent(getBaseContext(), GameActivity.class);
                        personIntent.putExtra(GameActivity.GAME_MODE, GameActivity.GameMode.PERSON);
                        startActivity(personIntent);
                        break;
                    case R.id.exitButton:
                        finish();
                }
            }
        };

        playWithComputerButton.setOnClickListener(mainMenuListener);
        playWithPersonButton.setOnClickListener(mainMenuListener);
        exitButton.setOnClickListener(mainMenuListener);
    }

}
