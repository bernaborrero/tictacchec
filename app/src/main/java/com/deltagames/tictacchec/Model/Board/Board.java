package com.deltagames.tictacchec.Model.Board;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.deltagames.tictacchec.Model.Pieces.Piece;

/**
 * Class to manage the board
 * Created by Bernabé Borrero on 24/04/15.
 */
public class Board {

    private static final String BOARD_ONGOING_GAME_FILE = "BOARD_ONGOING_GAME_FILE";
    private static final String BOARD_ONGOING_GAME_STATE = "BOARD_ONGOING_GAME_STATE";

    /**
     * Number of rows and columns of the board
     */
    public static final int BOARD_ROWS = 6;
    public static final int BOARD_COLS = 4;

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
        board = new Piece[BOARD_COLS][BOARD_ROWS];
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

        Log.d("NEW COORDINATES", "X: " + coordinates.getX() + ", Y: " + coordinates.getY());

        piece.setCoordinates(coordinates);
        piece.getPlayer().emptyMoves();
        if(hasInPlayingBounds(coordinates)){
            piece.setInBoard(true);
        }

        // TODO: move killed piece (if killed!) to reserve
    }

    public void set(Piece[] pieces){
        for(Piece piece: pieces){
            initSet(piece);
        }
    }

    public void initSet(Piece piece){
        board[piece.getCoordinates().getX()][piece.getCoordinates().getY()] = piece;
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
     * Checks if a pair of Coordinates are in the bounds of the playing area of the board
     * @param coordinates the Coordinates to check
     * @return true if the Coordinates are in bounds of the board, false otherwise
     */
    public boolean hasInPlayingBounds(Coordinates coordinates) {
        return (coordinates.getX() >= 0 && coordinates.getX() < BOARD_COLS &&
                coordinates.getY() >= 1 && coordinates.getY() < BOARD_ROWS - 1);
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

    /**
     * Save the state of the board with SharedPreferences
     * @param context the Application Context
     */
    public void save(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(BOARD_ONGOING_GAME_FILE, Context.MODE_PRIVATE);

        String gameState = "";
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                if (board[i][j] != null) {
                    gameState += board[i][j];
                }
            }
        }

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(BOARD_ONGOING_GAME_STATE, gameState);
        editor.apply();
    }

    /**
     * Load the saved game to the current board
     * @param context the Application Context
     */
    public void load(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(BOARD_ONGOING_GAME_FILE, Context.MODE_PRIVATE);
        String gameState = sharedPref.getString(BOARD_ONGOING_GAME_STATE, "");

        Piece[] pieces = new Piece[8];

        for (int i = 0, j = 0; i < 40; i += 5, j++) {
            pieces[j] = Piece.stringToPiece("" +
                    gameState.charAt(i) +
                    gameState.charAt(i + 1) +
                    gameState.charAt(i + 2) +
                    gameState.charAt(i + 3) +
                    gameState.charAt(i + 4)
            );

            this.set(pieces);
        }
    }
}
