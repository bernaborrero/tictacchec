package com.deltagames.tictacchec.Model.Board;

/**
 * Class to store a pair of coordinates
 * Created by Maxi on 23/04/2015.
 */
public class Coordinates implements Comparable {

    /**
     * The x and y coordinates in the board
     */
    private int x;
    private int y;

    /**
     * Default constructor
     */
    public Coordinates() { }

    /**
     * Basic constructor
     * @param x the position in the x axis
     * @param y the position in the y axis
     */
    public Coordinates(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    /**
     * Set the position of the Coordinates
     * @param x the new position in the x axis
     * @param y the new position in the y axis
     */
    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Retrieve in which diagonal is present the current coordinates
     * @return 0 if they are in the main diagonal, 1 if they are in the reversed diagonal, -1 otherwise
     */
    public Board.Diagonal getDiagonalNumber() {
        if (getX() == getY()) {
            return Board.Diagonal.MAIN_DIAGONAL;
        }
        else if (getX() == (Board.COLS - 1 - getY())) {
            return Board.Diagonal.REVERSED_DIAGONAL;
        }
        else {
            return Board.Diagonal.NO_DIAGONAL;
        }
    }

    /**
     * Returns a copy of the input object
     * @param coordinates the Coordinates to copy
     * @return the new Coordinates objet
     */
    public static Coordinates copy(Coordinates coordinates) {
        return new Coordinates(coordinates.getX(), coordinates.getY());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int compareTo(Object o) {
        Coordinates coordinates = (Coordinates) o;

        if(coordinates.getY() < getY()) {
            return 1;
        }
        else if(coordinates.getY() > getY()) {
            return -1;
        }
        else {
            if(coordinates.getX() < getX()) {
                return 1;
            }
            else if(coordinates.getX() > getX()) {
                return -1;
            }
        }

        return 0;
    }
}
