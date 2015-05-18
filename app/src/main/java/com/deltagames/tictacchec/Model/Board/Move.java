package com.deltagames.tictacchec.Model.Board;

import com.deltagames.tictacchec.Model.Pieces.Piece;
import com.deltagames.tictacchec.Model.Utils.Color;

/**
 * Class to store a single Move
 * Created by Bernabé Borrero on 28/04/15.
 */
public class Move implements Comparable{

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


    @Override
    public int compareTo(Object another) {
        Move other = (Move)another;
        if(this.getPiece().compareTo(other.getPiece())==0&& this.getCoordinates().compareTo(other.getCoordinates())==0){
            return 0;
        }

        if(this.getPiece().getColor()== Color.WHITE){
            (this.getPiece().compareTo(other.getPiece())==-1 && (this.getCoordinates().compareTo(other.getCoordinates())==0 || this.getCoordinates().compareTo(other.getCoordinates())==-1){
                return -1;
            }

            (this.getPiece().equals)
        }else{

        }


    }
}
