package com.deltagames.tictacchec.Model.Board;

import com.deltagames.tictacchec.Model.Pieces.Piece;

/**
 * Class to manage the board
 * Created by Bernabé Borrero on 24/04/15.
 */
public class Board {

    /**
     * Number of rows and columns of the board
     */
    public static final int ROWS = 4;
    public static final int COLS = 4;
    public static final int DIAGONAL_CELLS = 4; // This value is needed for Arnold, and added here for awareness (ROWS & COLS must be equal)

    /**
     * Convenience method for deciding in which diagonal a Piece is
     */
    public enum Diagonal {
        MAIN_DIAGONAL, REVERSED_DIAGONAL, NO_DIAGONAL
    }

    /**
     * The actual board array
     */
    private Piece[][] board;

    /**
     * position of the board in the screen
     */
    private int positionX;
    private int positionY;
    private int endPositionX;
    private int endPositionY;

    /**
     * size of each cell in the board
     */
    private int cellWidth;
    private int cellHeight;

    /**
     * Basic constructor
     */
    public Board() {
        board = new Piece[COLS][ROWS];
    }


    public Board(int positionX, int positionY, int endPositionX, int endPositionY) {
        this();
        this.positionX = positionX;
        this.positionY = positionY;
        this.endPositionX = endPositionX;
        this.endPositionY = endPositionY;
        this.cellWidth = (endPositionX - positionX) / 4;
        this.cellHeight = (endPositionY - positionY) / 4;
    }

    /**
     * Returns a piece given its x and y position
     *
     * @param x the position in the x axis
     * @param y the position in the y axis
     * @return the Piece in the specified position, or null if the position is empty
     */
    public Piece get(int x, int y) {
        return board[x][y];
    }

    /**
     * Returns a piece given its coordinates
     *
     * @param coordinates the coordinates of the piece to retrieve
     * @return the Piece in the specified coordinates, or null if position is empty
     */
    public Piece get(Coordinates coordinates) {
        return board[coordinates.getX()][coordinates.getY()];
    }

    /**
     * Changes the position of the piece in the board
     *
     * @param piece the Piece to move
     * @param x     the new position in the x axis
     * @param y     the new position in the y axis
     */
    public void set(Piece piece, int x, int y) {
        set(piece, new Coordinates(x, y));
    }

    /**
     * Changes the position of the piece in the board
     *
     * @param piece       the Piece to move
     * @param coordinates the new Coordinates of the piece in the board
     */
    public void set(Piece piece, Coordinates coordinates) {
        board[coordinates.getX()][coordinates.getY()] = piece;
        board[piece.getCoordinates().getX()][piece.getCoordinates().getY()] = null;
        piece.setCoordinates(coordinates);
        piece.getPlayer().emptyMoves();
    }

    /**
     * Checks if a pair of Coordinates are in the bounds of the board
     *
     * @param coordinates the Coordinates to check
     * @return true if the Coordinates are in bounds of the board, false otherwise
     */
    public boolean hasInBounds(Coordinates coordinates) {
        return (coordinates.getX() >= 0 && coordinates.getX() < Board.COLS &&
                coordinates.getY() >= 0 && coordinates.getY() < Board.ROWS);
    }


    /**
     * get the equivalent of the screen coordinates in a position in the board
     *
     * @param screenX
     * @param screenY
     * @return The content of the cell in the board
     */
    public Piece coordinatesToPiece(int screenX, int screenY) {
        if (boardIsTouched(screenX, screenY)) {

            return get(new Coordinates(screenX / cellWidth, screenY / cellHeight));
        } else {
            return null;
        }

    }

    /**
     * check if the user has touched inside the board limits
     *
     * @param screenX
     * @param screenY
     * @return true if the screen coordinates are between the board coordinates
     */
    private boolean boardIsTouched(int screenX, int screenY) {
        if ((screenX > positionX && screenX < endPositionX) && (screenY > positionY && screenY < endPositionY)) {
            return true;
        }

        return false;
    }
}
