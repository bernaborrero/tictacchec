package com.deltagames.tictacchec.Model.Board;

import com.deltagames.tictacchec.Model.Pieces.Piece;

/**
 * Class to store a single Move
 * Created by Bernabé Borrero on 28/04/15.
 */
public class Move {

    private Piece piece;
    private Coordinates coordinates;
    private int weight;


    public Move(Piece piece, Coordinates coordinates, int weight) {
        this.piece = piece;
        this.coordinates = coordinates;
        this.weight = weight;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


}
