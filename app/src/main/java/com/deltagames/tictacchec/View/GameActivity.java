package com.deltagames.tictacchec.View;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.deltagames.tictacchec.Model.Board.Board;
import com.deltagames.tictacchec.Model.Board.Coordinates;
import com.deltagames.tictacchec.Model.Board.Move;
import com.deltagames.tictacchec.Model.Board.Moves;
import com.deltagames.tictacchec.Model.CustomImageView;
import com.deltagames.tictacchec.Model.Pieces.Piece;
import com.deltagames.tictacchec.Model.Players.Arnold;
import com.deltagames.tictacchec.Model.Players.HumanPlayer;
import com.deltagames.tictacchec.Model.Players.Player;
import com.deltagames.tictacchec.Model.Utils.Color;
import com.deltagames.tictacchec.R;
import com.deltagames.tictacchec.Utils.RewardsConstants;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;


public class GameActivity extends BaseActivity {

    public static final String GAME_MODE = "GAME_MODE";
    public static final String GAME_CREATE = "GAME_CREATE";
    public static final String GAME_MATCH = "GAME_MATCH";

    public enum GameMode {
        COMPUTER, PERSON
    }

    private Player enemy, player;
    private CustomImageView[][] cells;
    private CustomImageView[][] offSetCells;
    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //setGrid();
        Intent intent = getIntent();
        board = new Board();
        GameMode gameMode = (GameMode) intent.getSerializableExtra(GAME_MODE);
        if (gameMode == GameMode.PERSON) {
            TurnBasedMatch match = (TurnBasedMatch) intent.getParcelableExtra(GAME_MATCH);
            match.getData();
        }


        initPlayers(GameMode.PERSON, gameMode);
        initOffSetCells();
        initCells();

        startGame();
        //board.set(player.getPieces());

    }


    private void toggleTurn(){
        if(player.isTurn()){
            changeTurn(player,enemy);
        }else{
            changeTurn(enemy,player);
        }

    }

    private void changeTurn(Player player, Player enemy) {
        player.setTurn(false);
        enemy.setTurn(true);
    }

    private void startGame(){
        checkBoard(0,player);
        checkBoard(5,enemy);
        toggleTurn();
    }

    private void setGrid(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;


        GridLayout grid = (GridLayout)findViewById(R.id.gridLayout);
        ViewGroup.LayoutParams params=grid.getLayoutParams();
        params.width= width-4;
        params.height= width;

        grid.setLayoutParams(params);

    }


    private void initOffSetCells(){
        cells= new CustomImageView[4][6];
        cells[0][0]= (CustomImageView) findViewById(R.id.imageView_00);
        cells[0][0].setCoordinates(new Coordinates(0, 0));
        cells[0][0].setScaleType(ImageView.ScaleType.FIT_XY);
        addOffSetActionListener(cells[0][0]);
        cells[1][0]= (CustomImageView) findViewById(R.id.imageView_10);
        cells[1][0].setCoordinates(new Coordinates(1, 0));
        cells[1][0].setScaleType(ImageView.ScaleType.FIT_XY);
        addOffSetActionListener(cells[1][0]);
        cells[2][0]= (CustomImageView) findViewById(R.id.imageView_20);
        cells[2][0].setCoordinates(new Coordinates(2, 0));
        cells[2][0].setScaleType(ImageView.ScaleType.FIT_XY);
        addOffSetActionListener(cells[2][0]);
        cells[3][0]= (CustomImageView) findViewById(R.id.imageView_30);
        cells[3][0].setCoordinates(new Coordinates(3, 0));
        cells[3][0].setScaleType(ImageView.ScaleType.FIT_XY);
        addOffSetActionListener(cells[3][0]);



        cells[0][5]= (CustomImageView) findViewById(R.id.imageView__00);
        cells[0][5].setCoordinates(new Coordinates(0, 5));
        cells[0][5].setScaleType(ImageView.ScaleType.FIT_XY);
        addOffSetActionListener(cells[0][5]);
        cells[1][5]= (CustomImageView) findViewById(R.id.imageView__10);
        cells[1][5].setCoordinates(new Coordinates(1, 5));
        cells[1][5].setScaleType(ImageView.ScaleType.FIT_XY);
        addOffSetActionListener(cells[1][5]);
        cells[2][5]= (CustomImageView) findViewById(R.id.imageView__20);
        cells[2][5].setCoordinates(new Coordinates(2, 5));
        cells[2][5].setScaleType(ImageView.ScaleType.FIT_XY);
        addOffSetActionListener(cells[2][5]);
        cells[3][5]= (CustomImageView) findViewById(R.id.imageView__30);
        cells[3][5].setCoordinates(new Coordinates(3, 5));
        cells[3][5].setScaleType(ImageView.ScaleType.FIT_XY);
        addOffSetActionListener(cells[3][5]);
    }
    private void initPlayers(GameMode gmPlayer, GameMode gmEnemy){
        player = createPlayer(gmPlayer, Color.WHITE);
        enemy= createPlayer(gmEnemy, Color.BLACK);
        player.createPieces();
        enemy.createPieces();
    }

    private Player createPlayer(GameMode gameMode, Color color){
        if(gameMode==GameMode.COMPUTER){
            return new Arnold(color);
        }else{
            return new HumanPlayer(color);
        }
    }

    private void initCells() {
        cells[0][1]= (CustomImageView) findViewById(R.id.imageView00);
        cells[0][1].setCoordinates(new Coordinates(0, 1));
        cells[0][1].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[0][1].setBackgroundResource(R.drawable.redbackground);
        addActionListener(cells[0][1]);

        cells[1][1]= (CustomImageView) findViewById(R.id.imageView10);
        cells[1][1].setCoordinates(new Coordinates(1, 1));
        cells[1][1].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[1][1].setBackgroundResource(R.drawable.brownbackground);
        addActionListener(cells[1][1]);

        cells[2][1]= (CustomImageView) findViewById(R.id.imageView20);
        cells[2][1].setCoordinates(new Coordinates(2, 1));
        cells[2][1].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[2][1].setBackgroundResource(R.drawable.redbackground);
        addActionListener(cells[2][1]);

        cells[3][1]= (CustomImageView) findViewById(R.id.imageView30);
        cells[3][1].setCoordinates(new Coordinates(3,1));
        cells[3][1].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[3][1].setBackgroundResource(R.drawable.brownbackground);
        addActionListener(cells[3][1]);

        cells[0][2]= (CustomImageView) findViewById(R.id.imageView01);
        cells[0][2].setCoordinates(new Coordinates(0,2));
        cells[0][2].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[0][2].setBackgroundResource(R.drawable.brownbackground);
        addActionListener(cells[0][2]);
        cells[1][2]= (CustomImageView) findViewById(R.id.imageView11);
        cells[1][2].setCoordinates(new Coordinates(1,2));
        cells[1][2].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[1][2].setBackgroundResource(R.drawable.redbackground);
        addActionListener(cells[1][2]);
        cells[2][2]= (CustomImageView) findViewById(R.id.imageView21);
        cells[2][2].setCoordinates(new Coordinates(2, 2));
        cells[2][2].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[2][2].setBackgroundResource(R.drawable.brownbackground);
        addActionListener(cells[2][2]);
        cells[3][2]= (CustomImageView) findViewById(R.id.imageView31);
        cells[3][2].setCoordinates(new Coordinates(3, 2));
        cells[3][2].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[3][2].setBackgroundResource(R.drawable.redbackground);
        addActionListener(cells[3][2]);

        cells[0][3]= (CustomImageView) findViewById(R.id.imageView02);
        cells[0][3].setCoordinates(new Coordinates(0, 3));
        cells[0][3].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[0][3].setBackgroundResource(R.drawable.redbackground);
        addActionListener(cells[0][3]);
        cells[1][3]= (CustomImageView) findViewById(R.id.imageView12);
        cells[1][3].setCoordinates(new Coordinates(1,3));
        cells[1][3].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[1][3].setBackgroundResource(R.drawable.brownbackground);
        addActionListener(cells[1][3]);
        cells[2][3]= (CustomImageView) findViewById(R.id.imageView22);
        cells[2][3].setCoordinates(new Coordinates(2,3));
        cells[2][3].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[2][3].setBackgroundResource(R.drawable.redbackground);
        addActionListener(cells[2][3]);
        cells[3][3]= (CustomImageView) findViewById(R.id.imageView32);
        cells[3][3].setCoordinates(new Coordinates(3,3));
        cells[3][3].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[3][3].setBackgroundResource(R.drawable.brownbackground);
        addActionListener(cells[3][3]);


        cells[0][4]= (CustomImageView) findViewById(R.id.imageView03);
        cells[0][4].setCoordinates(new Coordinates(0, 4));
        cells[0][4].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[0][4].setBackgroundResource(R.drawable.brownbackground);
        addActionListener(cells[0][4]);
        cells[1][4]= (CustomImageView) findViewById(R.id.imageView13);
        cells[1][4].setCoordinates(new Coordinates(1, 4));
        cells[1][4].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[1][4].setBackgroundResource(R.drawable.redbackground);
        addActionListener(cells[1][4]);
        cells[2][4]= (CustomImageView) findViewById(R.id.imageView23);
        cells[2][4].setCoordinates(new Coordinates(2, 4));
        cells[2][4].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[2][4].setBackgroundResource(R.drawable.brownbackground);
        addActionListener(cells[2][4]);
        cells[3][4]= (CustomImageView) findViewById(R.id.imageView33);
        cells[3][4].setCoordinates(new Coordinates(3,4));
        cells[3][4].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[3][4].setBackgroundResource(R.drawable.redbackground);
        addActionListener(cells[3][4]);
    }

    private Piece previousPiece;
    private Coordinates prevCoords;
    private Moves validMoves;
    private boolean toggleIn = true;

    private void addActionListener(CustomImageView item) {

        item.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(player.isTurn()) {
                    Coordinates coord = ((CustomImageView) v).getCoordinates();
                    Piece piece = board.get(coord);

                    if (previousPiece == null) {
                        checkPreviousPiece(piece);
                    } else {
                        if (piece != null && piece.getColor() == player.getColor()) {
                            checkPreviousPiece(piece);
                        } else {
                            if (moveIsAllowed(coord)) {
                                board.set(previousPiece, coord);
                                cells[coord.getX()][coord.getY()].setImageResource(previousPiece.getImagePath());
                                if (!board.hasInPlayingBounds(prevCoords)) {
                                    resetOffsetCells();
                                } else {
                                    cells[prevCoords.getX()][prevCoords.getY()].setImageResource(0);
                                }
                                Coordinates killedCoords = board.getKilledCoords();
                                if (killedCoords != null) {
                                    cells[killedCoords.getX()][killedCoords.getY()].setImageResource(board.get(killedCoords).getImagePath());
                                }
                                board.resetKilledCoords();
                                previousPiece = null;
                                //toggleTurn();
                                //enemy.move(board, player, new Semaphore(1));
                                //toggleTurn();

                                if (toggleIn) {
                                    unlockAchievement(RewardsConstants.Achievements.firstMoveDone);
                                    toggleIn = false;
                                } else {
                                    updateHighScore(RewardsConstants.LeaderBoards.highScore, 40);
                                }

                            }
                        }
                    }
                }
            }

            private boolean resetOffsetCells(int i) {
                boolean found = false;
                    int j = 0;
                    while (j < cells[0].length && !found) {
                        if (cells[j][i]!=null&&cells[j][i].getCoordinates().compareTo(prevCoords) == 0) {
                            found = true;
                            cells[j][i].setImageResource(0);
                        }
                        j++;
                    }

                return found;

            }

            private void resetOffsetCells(){
                boolean done =resetOffsetCells(0);
                if(!done){
                    resetOffsetCells(4);
                }
            }

            private boolean moveIsAllowed(Coordinates coordinates) {
                return validMoves.hasCoordinateInMoves(coordinates);
            }

            /**
             * Sets the previous piece with the last piece touched by the user
             * and calculates it's possible moves
            */
            private void checkPreviousPiece(Piece piece){
                if (piece != null && piece.getColor() == player.getColor()) {
                    previousPiece = piece;
                    prevCoords = piece.getCoordinates();
                    validMoves = previousPiece.getValidMoves(board);
                    Log.d("NUMBER OF VALID MOVES", validMoves.size() + "");

                    for (Object validMove : validMoves) {
                        Move m = (Move) validMove;
                        Log.d("VALID MOVE", "X: " + m.getCoordinates().getX() + ", Y: " + m.getCoordinates().getY());
                    }
                }
	        }
        });
    }

    private void addOffSetActionListener(CustomImageView item) {

        item.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(player.isTurn()) {
                    try {
                        Coordinates coord = ((CustomImageView) v).getCoordinates();
                        Piece piece = player.getPiece(coord);
                        Log.i("piece", piece.toString());
                        if (previousPiece == null) {
                            checkPreviousPiece(piece);
                        } else {
                            if (piece != null && piece.getColor() == player.getColor()) {
                                checkPreviousPiece(piece);
                            }
                        }
                    } catch (Exception e) {
                        Log.i("piece", e.toString());
                    }
                }

            }

            private boolean moveIsAllowed(Piece piece) {
                return validMoves.hasCoordinateInMoves(piece.getCoordinates());
            }

            /*
            sets the previous piece with the last piece touched by the user
            and calculates it's possible moves
             */
            private void checkPreviousPiece(Piece piece){
                if(piece!=null && piece.getColor()==player.getColor()){
                    previousPiece=piece;
                    prevCoords=piece.getCoordinates();
                    validMoves=previousPiece.getValidMoves(board);

                }

            }

        });
    }



    private void checkBoard(int position, Player player){
        for(Piece piece : player.getPieces()){
            cells[piece.getCoordinates().getX()][position].setImageResource(piece.getImagePath());
        }

    }

}
