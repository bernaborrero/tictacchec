package com.deltagames.tictacchec.Model.Players;


import com.deltagames.tictacchec.Model.Board.Board;
import com.deltagames.tictacchec.Model.Board.Coordinates;
import com.deltagames.tictacchec.Model.Board.Move;
import com.deltagames.tictacchec.Model.Board.Moves;
import com.deltagames.tictacchec.Model.Pieces.Bishop;
import com.deltagames.tictacchec.Model.Pieces.Knight;
import com.deltagames.tictacchec.Model.Pieces.Pawn;
import com.deltagames.tictacchec.Model.Pieces.Piece;
import com.deltagames.tictacchec.Model.Pieces.Rook;
import com.deltagames.tictacchec.Model.Utils.Color;

import java.util.concurrent.Semaphore;

/**
 * This class helps to handle a single player
 */
public abstract class Player {

    public static final int PIECES_BY_PLAYER = 4;

    /**
     * The Pieces of the Player
     */
    Piece[] pieces;
    Moves moves;

    private Color color;
    private int activePieces;   // TODO: update activePieces when a piece is put on the board or killed (handle killing logic!)

    /**
     * Current state of the Player's pieces in the board (used by Arnold)
     */
    private int[][] horizontalPositions;
    private int[][] verticalPositions;
    private int[][] diagonalPositions;

    public Player() {
        pieces = new Piece[PIECES_BY_PLAYER];
        activePieces = 0;

        moves = new Moves();

        horizontalPositions = new int[Board.ROWS][Board.COLS];
        verticalPositions = new int[Board.COLS][Board.ROWS];
        diagonalPositions = new int[Board.DIAGONAL_CELLS][2];
    }

    public void setActivePieces(int activePieces) {
        this.activePieces = activePieces;
    }

    /**
     * Retrieves all the Moves of all the Pieces of the current Player
     * @param board the current Board
     * @return all the Moves the Player can do, with all his/her Pieces
     */
    public Moves getMoves(Board board) {
        if (moves.isEmpty()) {
            for (Piece piece : getPieces()) {
                for (Object pieceMove : piece.getValidMoves(board)) {
                    moves.add((Move) pieceMove);
                }
            }
        }

        return moves;
    }

    /**
     * Reset the Moves the Player can do
     */
    public void emptyMoves() {
        moves.empty();
    }

    /**
     * Check if the player has won
     * @return true if the player has won, false otherwise
     */
    public boolean hasWon() {

        if (activePieces == Board.ROWS) {
            return true;
        }

        for (int y = 0; y < Board.ROWS; y++) {
            if (getHorizontalWeight(y) == Board.ROWS) {
                return true;
            }
        }

        for (int x = 0; x < Board.COLS; x++) {
            if (getVerticalWeight(x) == Board.COLS) {
                return true;
            }
        }

        Board.Diagonal[] diagonals = new Board.Diagonal[]{Board.Diagonal.MAIN_DIAGONAL, Board.Diagonal.REVERSED_DIAGONAL};
        for (Board.Diagonal diagonal : diagonals) {
            if (getDiagonalWeight(diagonal) == Board.DIAGONAL_CELLS) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns a value representing the importance of that move in the current state of the private summing board
     * @param coordinates the Coordinates to check
     * @return the importance of a move from any piece to the specified Coordinates
     */
    public int getWeightForCoordinates(Coordinates coordinates) {
        int weight = getHorizontalWeight(coordinates.getY()) + getVerticalWeight(coordinates.getX());

        Board.Diagonal diagonal = coordinates.getDiagonalNumber();
        if (diagonal != Board.Diagonal.NO_DIAGONAL) {
            weight += getDiagonalWeight(diagonal);
        }

        return weight;
    }

    private int getHorizontalWeight(int y) {
        int sum = 0;

        for (int value : getHorizontalPositions(y)) {
            sum += value;
        }

        return sum;
    }

    private int getVerticalWeight(int x) {
        int sum = 0;

        for (int value : getVerticalPositions(x)) {
            sum += value;
        }

        return sum;
    }

    private int getDiagonalWeight(Board.Diagonal diagonal) {
        int sum = 0;

        for (int value : getDiagonalPositions(diagonal)) {
            sum += value;
        }

        return sum;
    }

    /**
     * Change the weight of some Coordinates from origin to destination
     * @param origin the origin position
     * @param destination the destination
     */
    public void changeWeightForCoordinates(Coordinates origin, Coordinates destination) {
        setHorizontalPosition(origin.getX(), origin.getY(), 0);
        setHorizontalPosition(destination.getX(), destination.getY(), 1);

        setVerticalPosition(origin.getX(), origin.getY(), 0);
        setVerticalPosition(destination.getX(), destination.getY(), 1);


        setDiagonalPosition(Board.Diagonal.MAIN_DIAGONAL, origin.getX(), 0);
        setDiagonalPosition(Board.Diagonal.MAIN_DIAGONAL, destination.getX(), 1);

        setDiagonalPosition(Board.Diagonal.REVERSED_DIAGONAL, origin.getX(), 0);
        setDiagonalPosition(Board.Diagonal.REVERSED_DIAGONAL, destination.getX(), 1);
    }

    /**
     * Method to handle the movement logic of a Player
     *
     * @param board             the current Board
     * @param blockingSemaphore a Semaphore to control the state of the game
     */
    public abstract void move(Board board, Player enemy, Semaphore blockingSemaphore);

    public Piece[] getPieces() {
        return pieces;
    }

    public void setPieces(Piece[] pieces) {
        this.pieces = pieces;
    }

    public Color getColor() {
        return this.color;
    }

    private int[] getHorizontalPositions(int y) {
        return horizontalPositions[y];
    }

    private int[] getVerticalPositions(int x) {
        return verticalPositions[x];
    }

    private int[] getDiagonalPositions(Board.Diagonal diagonal) {
        int diagonalNumber = (diagonal == Board.Diagonal.MAIN_DIAGONAL) ? 1 : 0;
        return diagonalPositions[diagonalNumber];
    }

    private void setHorizontalPosition(int x, int y, int value) {
        horizontalPositions[y][x]  = value;
    }

    private void setVerticalPosition(int x, int y, int value) {
        verticalPositions[x][y] = value;
    }

    private void setDiagonalPosition(Board.Diagonal diagonal, int pos, int value) {
        int diagonalNumber = (diagonal == Board.Diagonal.MAIN_DIAGONAL) ? 1 : 0;
        diagonalPositions[diagonalNumber][pos] = value;
    }

    public void createPieces(){
        pieces[0]= new Pawn(this,new Coordinates(),this.getColor());
        pieces[1]= new Bishop(this,new Coordinates(),this.getColor());
        pieces[2]= new Knight(this,new Coordinates(),this.getColor());
        pieces[3]= new Rook(this,new Coordinates(),this.getColor());
    }

}
