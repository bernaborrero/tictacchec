package com.deltagames.tictacchec.Model.Pieces;

import com.deltagames.tictacchec.Model.Board.Board;
import com.deltagames.tictacchec.Model.Board.Coordinates;
import com.deltagames.tictacchec.Model.Board.Move;
import com.deltagames.tictacchec.Model.Board.Moves;
import com.deltagames.tictacchec.Model.Players.Arnold;
import com.deltagames.tictacchec.Model.Players.HumanPlayer;
import com.deltagames.tictacchec.Model.Players.Player;
import com.deltagames.tictacchec.Model.Utils.Color;

/**
 * Abstract class to manage a single piece
 * Created by Bernabé Borrero on 23/04/15.
 */
public abstract class Piece implements Comparable {

    public enum PieceType {
        BISHOP, KNIGHT, PAWN, ROOK
    }

    private Player player;
    private Coordinates coordinates;
    private Coordinates outterCoordinates;
    private Color color;
    private PieceType pieceType;
    private Moves possibleMoves;
    private boolean inBoard;
    private int imagePath;

    /**
     * Basic constructor
     * @param coordinates the initial coordinates of the Piece
     * @param color the Color of the Piece
     */
    public Piece(PieceType pieceType, Player player, Coordinates coordinates, Color color) {
        this.pieceType = pieceType;
        this.player = player;
        this.coordinates = coordinates;
        this.color = color;
        this.possibleMoves = new Moves();
        this.inBoard = false;
    }

    /**
     * Constructor with image path specification
     * @param coordinates the initial coordinates of the Piece
     * @param color the Color of the Piece
     * @param imagePath path of the image of the texture
     */
    public Piece(PieceType pieceType, Player player, Coordinates coordinates, Color color, int imagePath) {
        this(pieceType, player, coordinates, color);
        this.imagePath = imagePath;
    }

    /**
     * Checks if the piece can move to a given position
     * @param board the current Board
     * @param coordinates the coordinates where the piece wants to be moved
     * @return true if the piece can be moved, false otherwise
     */
    public boolean canMove(Board board, Coordinates coordinates) {
        return getValidMoves(board).hasCoordinateInMoves(coordinates);
    }

    /**
     * Retrieves the possible moves of the piece
     * @param board the current Board
     * @return a TreeMap containing the possible moves
     */
    public Moves getValidMoves(Board board) {
        Moves validMoves = getPossibleMoves();
        if (validMoves.isEmpty() && !isInBoard()) {

            for (int i = 0; i < Board.ROWS; i++) {
                for (int j = 0; j < Board.COLS; j++) {
                    if (board.get(i, j) == null) {
                        Coordinates cords = new Coordinates(i, j);
                        validMoves.add(new Move(this, cords, player.getWeightForCoordinates(cords)));
                    }
                }
            }

            setPossibleMoves(validMoves);
        }

        return getPossibleMoves();
    }

    /**
     * Empty the possible moves of the Piece
     */
    public void emptyMoves() {
        this.possibleMoves.empty();
    }

    /**
     * Checks if a piece can kill another piece
     * ATTENTION: this method does not take in consideration the position of the pieces
     * @param piece the victim Piece
     * @return true if this Piece can kill the other one, false otherwise
     */
    protected boolean canOptToKill(Piece piece) {
        return (piece != null && piece.getColor() != getColor());
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
//        getPlayer().changeWeightForCoordinates(this.getCoordinates(), coordinates); // TODO: fix this!
        this.coordinates = coordinates;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    protected PieceType getPieceType() {
        return pieceType;
    }

    protected void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    protected Moves getPossibleMoves() {
        return possibleMoves;
    }

    protected void setPossibleMoves(Moves possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    public boolean isInBoard() {
        return inBoard;
    }

    public void setInBoard(boolean inBoard) {
        this.inBoard = inBoard;
    }

    public int getImagePath(){
        return this.imagePath;
    }

    public void setImagePath(int path){this.imagePath=path;}

    @Override
    public int compareTo(Object o) {

        Piece other = (Piece) o;

        if (this.getColor() != other.getColor()) {
            return (getColor() == Color.WHITE) ? 1 : -1;
        } else {
            if (this.getPieceType() == other.getPieceType()) {
                return getCoordinates().compareTo(other.getCoordinates());
            }
            else if (this.getPieceType() == PieceType.BISHOP) {
                return 1;
            }
            else if (this.getPieceType() == PieceType.KNIGHT &&
                    other.getPieceType() == PieceType.BISHOP) {
                return -1;
            }
            else if (this.getPieceType() == PieceType.ROOK &&
                    (other.getPieceType() == PieceType.KNIGHT ||
                            other.getPieceType() == PieceType.BISHOP)) {
                return -1;
            }
            else if (this.getPieceType() == PieceType.PAWN &&
                    (other.getPieceType() == PieceType.ROOK ||
                            other.getPieceType() == PieceType.KNIGHT ||
                            other.getPieceType() == PieceType.BISHOP)) {
                return -1;
            }
            else {
                return 1;
            }
        }
    }

    @Override
    public String toString() {
        return getPlayer() + pieceTypeToString() + colorToString() + getCoordinates();
    }

    /**
     * Convert the piece type to a string
     * @return the string representation of the piece type
     */
    private String pieceTypeToString() {
        switch (getPieceType()) {
            case BISHOP:
                return "B";
            case KNIGHT:
                return "K";
            case PAWN:
                return "P";
            case ROOK:
                return "R";
            default:
                return "";
        }
    }

    /**
     * Convert the color to a string
     * @return the string representation of the color
     */
    private String colorToString() {
        switch (getColor()) {
            case WHITE:
                return "W";
            case BLACK:
                return "B";
            default:
                return "";
        }
    }

    /**
     * Convert a string to a Piece
     * @param stringPiece the string representation of a Piece
     * @return a Piece object based on its string representation
     */
    public static Piece stringToPiece(String stringPiece) {

        Piece piece = null;
        Player player = null;
        Color color = null;

        switch (stringPiece.charAt(0) + "") {
            case HumanPlayer.stringPlayer:
                player = new HumanPlayer();
                break;
            case Arnold.stringPlayer:
                player = new Arnold();
                break;
        }

        switch (stringPiece.charAt(2)) {
            case 'W':
                color = Color.WHITE;
                break;
            case 'B':
                color = Color.BLACK;
                break;
        }

        int x = Integer.parseInt(stringPiece.charAt(3) + "");
        int y = Integer.parseInt(stringPiece.charAt(4) + "");
        Coordinates coordinates = new Coordinates(x, y);

        switch (stringPiece.charAt(1)) {
            case 'B':
                piece = new Bishop(player, coordinates, color);
                break;
            case 'K':
                piece = new Knight(player, coordinates, color);
                break;
            case 'P':
                piece = new Pawn(player, coordinates, color);
                break;
            case 'R':
                piece = new Rook(player, coordinates, color);
                break;
        }

        return piece;
    }


}
