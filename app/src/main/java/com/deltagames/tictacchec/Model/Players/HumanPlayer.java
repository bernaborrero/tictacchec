package com.deltagames.tictacchec.Model.Players;

import com.badlogic.gdx.Gdx;
import com.deltagames.tictacchec.Listeners.UserInputListener;
import com.deltagames.tictacchec.Model.Board.Board;

import java.util.concurrent.Semaphore;

/**
 * Created by Maxi on 27/04/2015.
 */
public class HumanPlayer extends Player {

    UserInputListener listener;

    @Override
    public void move(Board board, Player enemy, Semaphore blockingSemaphore) {
        if(listener==null){
            listener= new UserInputListener(this,board);
        }
        Gdx.input.setInputProcessor(listener);
    }
}
