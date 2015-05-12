package com.deltagames.tictacchec.Model.Pieces;

import com.deltagames.tictacchec.Model.Board.Board;
import com.deltagames.tictacchec.Model.Utils.Color;
import com.deltagames.tictacchec.Model.Board.Coordinates;
import com.deltagames.tictacchec.Model.Board.Move;
import com.deltagames.tictacchec.Model.Board.Moves;
import com.deltagames.tictacchec.Model.Players.Player;

/**
 * Class to manage the linear pieces (Bishop and Rook)
 * Created by Bernabé Borrero on 25/4/15.
 */
public abstract class LinearPiece extends Piece {

    /**
     * The possible directions of the piece
     */
    protected enum Direction {
        UP, RIGHT, DOWN, LEFT,
        UP_LEFT, UP_RIGHT, DOWN_RIGHT, DOWN_LEFT
    }

    /**
     * The allowed Directions of the Piece
     */
    private Direction[] directions;

    /**
     * Basic constructor
     * @param coordinates the initial coordinates of the Piece
     * @param color the Color of the Piece
     */
    public LinearPiece(Player player, Coordinates coordinates, Color color) {
        super(player, coordinates, color);
        directions = new Direction[] {};
    }

    public LinearPiece(Player player, Coordinates coordinates, Color color, String imagePath) {
        super(player, coordinates, color, imagePath);
        directions = new Direction[] {};
    }


    @Override
    public Moves getValidMoves(Board board) {
        Moves validMoves = super.getValidMoves(board);
        if (validMoves.isEmpty()) {
            // calculate moves
            Coordinates movingCoordinates = Coordinates.copy(getCoordinates());

            for (Direction direction : getDirections()) {
                calculateDirectionMoves(board, validMoves, direction, movingCoordinates);
            }

            setPossibleMoves(validMoves);
        }

        return getPossibleMoves();
    }

    /**
     * Calculates all the possible Moves for a given direction
     * @param board the Board
     * @param validMoves the current valid Moves
     * @param direction the specified Direction
     * @param movingCoordinates convenience temporal Coordinates
     */
    private void calculateDirectionMoves(Board board, Moves validMoves, Direction direction, Coordinates movingCoordinates) {

        setDirectionCoordinates(movingCoordinates, direction);
        boolean collision = false;

        while (board.hasInBounds(movingCoordinates) && !collision) {
            if (this.canOptToKill(board.get(movingCoordinates))) {
                validMoves.add(new Move(this, movingCoordinates, getPlayer().getWeightForCoordinates(movingCoordinates)));
                setDirectionCoordinates(movingCoordinates, direction);
            } else {
                collision = true;
            }
        }
    }

    /**
     * Sets the moving Coordinates according to a given Direction
     * @param movingCoordinates the Coordinates to change
     * @param direction the Direction
     */
    protected abstract void setDirectionCoordinates(Coordinates movingCoordinates, Direction direction);

    public Direction[] getDirections() {
        return directions;
    }

    public void setDirections(Direction[] directions) {
        this.directions = directions;
    }

}
