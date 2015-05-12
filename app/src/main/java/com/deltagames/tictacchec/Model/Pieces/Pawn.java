package com.deltagames.tictacchec.Model.Pieces;

import com.deltagames.tictacchec.Model.Board.Board;
import com.deltagames.tictacchec.Model.Utils.Color;
import com.deltagames.tictacchec.Model.Board.Coordinates;
import com.deltagames.tictacchec.Model.Board.Move;
import com.deltagames.tictacchec.Model.Board.Moves;
import com.deltagames.tictacchec.Model.Players.Player;

/**
 * Class to manage a pawn
 * Created by Bernabé Borrero on 23/04/15.
 */
public class Pawn extends Piece {

    /**
     * Whether the pawn is advancing or receding
     */
    private boolean rightDirection;

    private final static String IMAGE_PATH="img/Pawn.jpg";

    /**
     * Basic constructor
     * @param coordinates the initial coordinates of the Piece
     * @param color the Color of the Piece
     */
    public Pawn(Player player, Coordinates coordinates, Color color) {
        super(player, coordinates, color, IMAGE_PATH);

        setInitialRightDirection(coordinates);
    }

    /**
     * Set the initial right direction.
     * ATTENTION: method only valid for calling from constructor
     * @param coordinates the current coordinates of the piece
     */
    private void setInitialRightDirection(Coordinates coordinates) {
        if(getColor() == Color.WHITE) {
            rightDirection = coordinates.getY() != 0;
        } else {
            rightDirection = coordinates.getY() != Board.ROWS - 1;
        }
    }

    @Override
    public Moves getValidMoves(Board board) {
        Moves validMoves = super.getValidMoves(board);
        if (validMoves.isEmpty()) {
            // calculate moves

            if (getColor() == Color.WHITE && isRightDirection() ||
                    getColor() == Color.BLACK && !isRightDirection()) {
                calculateDownToUpMoves(board, validMoves);
            }
            else {
                calculateUpToDownMoves(board, validMoves);
            }

            setPossibleMoves(validMoves);
        }

        return getPossibleMoves();
    }

    /**
     * Calculates the valid Moves for a piece moving down to up on the board
     * @param board the Board
     * @param validMoves the current valid Moves
     */
    private void calculateDownToUpMoves(Board board, Moves validMoves) {
        boolean collision = false;
        Coordinates coordinates = getCoordinates();
        Coordinates movingCoordinates = new Coordinates();
        int y = coordinates.getY();

        // straight, down to up
        while (y > 0 && !collision) {
            if (board.get(coordinates.getX(), y) == null) {
                Coordinates cords = new Coordinates(coordinates.getX(), y);
                validMoves.add(new Move(this, cords, getPlayer().getWeightForCoordinates(cords)));
            } else {
                collision = true;
            }

            y--;
        }

        // up left kill
        movingCoordinates.set(coordinates.getX() - 1, coordinates.getY() - 1);
        if (board.hasInBounds(movingCoordinates) &&
                this.canOptToKill(board.get(movingCoordinates))) {
            validMoves.add(new Move(this, movingCoordinates, getPlayer().getWeightForCoordinates(movingCoordinates)));
        }

        // up right kill
        movingCoordinates.set(coordinates.getX() + 1, coordinates.getY() -1);
        if (board.hasInBounds(movingCoordinates) &&
                this.canOptToKill(board.get(movingCoordinates))) {
            validMoves.add(new Move(this, movingCoordinates, getPlayer().getWeightForCoordinates(movingCoordinates)));
        }
    }

    /**
     * Calculates the valid Moves for a piece moving up to down on the board
     * @param board the Board
     * @param validMoves the current valid Moves
     */
    private void calculateUpToDownMoves(Board board, Moves validMoves) {
        boolean collision = false;
        Coordinates coordinates = getCoordinates();
        Coordinates movingCoordinates = new Coordinates();
        int y = coordinates.getY();

        // straight, up to down
        while (y < Board.ROWS && !collision) {
            if (board.get(coordinates.getX(), y) == null) {
                Coordinates cords = new Coordinates(coordinates.getX(), y);
                validMoves.add(new Move(this, cords, getPlayer().getWeightForCoordinates(cords)));
            } else {
                collision = true;
            }

            y++;
        }

        // down left kill
        movingCoordinates.set(coordinates.getX() - 1, coordinates.getY() + 1);
        if (board.hasInBounds(movingCoordinates) &&
                this.canOptToKill(board.get(movingCoordinates))) {
            validMoves.add(new Move(this, movingCoordinates, getPlayer().getWeightForCoordinates(movingCoordinates)));
        }

        // down right kill
        movingCoordinates.set(coordinates.getX() + 1, coordinates.getY() + 1);
        if (board.hasInBounds(movingCoordinates) &&
                this.canOptToKill(board.get(movingCoordinates))) {
            validMoves.add(new Move(this, movingCoordinates, getPlayer().getWeightForCoordinates(movingCoordinates)));
        }
    }

    public boolean isRightDirection() {
        return rightDirection;
    }

    public void setRightDirection(boolean rightDirection) {
        this.rightDirection = rightDirection;
    }
}
