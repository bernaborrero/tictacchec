package com.deltagames.tictacchec.Model.Players;

import com.deltagames.tictacchec.Listeners.UserInputListener;
import com.deltagames.tictacchec.Model.Board.Board;
import com.deltagames.tictacchec.Model.Utils.Color;

import java.util.concurrent.Semaphore;

/**
 * The user will play with this Human Player
 * Created by Maxi on 27/04/2015.
 */
public class HumanPlayer extends Player {

    public static final String stringPlayer = "H";
    UserInputListener listener;
    public HumanPlayer(){super();}

    public HumanPlayer(Color color){super(color);}
    @Override
    public void move(Board board, Player enemy, Semaphore blockingSemaphore) {
        if (listener == null) {
            listener = new UserInputListener(this,board);
        }
    }

    @Override
    public String toString() {
        return stringPlayer;
    }
}
