package com.deltagames.tictacchec.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.deltagames.tictacchec.Model.Players.Arnold;
import com.deltagames.tictacchec.Model.Players.HumanPlayer;
import com.deltagames.tictacchec.Model.Players.Player;
import com.deltagames.tictacchec.R;


public class GameActivity extends Activity {

    public static final String GAME_MODE = "GAME_MODE";

    public enum GameMode {
        COMPUTER, PERSON
    }

    private Player enemy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        GameMode gameMode = (GameMode) intent.getSerializableExtra(GAME_MODE);
        switch (gameMode) {
            case COMPUTER:
                enemy = new Arnold();
                break;
            case PERSON:
                enemy = new HumanPlayer();
                break;
        }
    }

}
