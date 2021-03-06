package com.deltagames.tictacchec.Model.Pieces;

import com.deltagames.tictacchec.Model.Board.Coordinates;
import com.deltagames.tictacchec.Model.Players.Player;
import com.deltagames.tictacchec.Model.Utils.Color;
import com.deltagames.tictacchec.R;

/**
 * Class to manage a rook
 * Created by Bernabé Borrero on 23/04/15.
 */
public class Rook extends LinearPiece {


    /**
     * Basic constructor
     *
     * @param coordinates the initial coordinates of the Piece
     * @param color       the Color of the Piece
     */
    public Rook(Player player, Coordinates coordinates, Color color) {
        super(PieceType.ROOK, player, coordinates, color, R.drawable.rook);
        setDirections(new Direction[]{
                Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT
        });
    }

    @Override
    protected void setDirectionCoordinates(Coordinates movingCoordinates, Direction direction) {
        switch (direction) {
            case UP:
                movingCoordinates.setY(movingCoordinates.getY() - 1);
                break;
            case RIGHT:
                movingCoordinates.setX(movingCoordinates.getX() + 1);
                break;
            case DOWN:
                movingCoordinates.setY(movingCoordinates.getY() + 1);
                break;
            case LEFT:
                movingCoordinates.setX(movingCoordinates.getX() - 1);
                break;
        }
    }
}
