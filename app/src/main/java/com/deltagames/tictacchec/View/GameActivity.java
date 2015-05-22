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
import com.deltagames.tictacchec.Model.Board.Moves;
import com.deltagames.tictacchec.Model.CustomImageView;
import com.deltagames.tictacchec.Model.Pieces.Piece;
import com.deltagames.tictacchec.Model.Players.Arnold;
import com.deltagames.tictacchec.Model.Players.HumanPlayer;
import com.deltagames.tictacchec.Model.Players.Player;
import com.deltagames.tictacchec.Model.Utils.Color;
import com.deltagames.tictacchec.R;
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
        initCells();
        initOffSetCells();
        startGame();
        //board.set(player.getPieces());

    }


    private void toggleTurn(){
        changeTurn(player,enemy);
    }

    private void changeTurn(Player player, Player enemy) {
        player.setTurn(false);
        enemy.setTurn(true);
    }

    private void startGame(){
        checkBoard(0,player);
        checkBoard(1,enemy);
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
        offSetCells= new CustomImageView[4][2];
        offSetCells[0][0]= (CustomImageView) findViewById(R.id.imageView_00);
        offSetCells[0][0].setCoordinates(new Coordinates(0, -1));
        offSetCells[0][0].setScaleType(ImageView.ScaleType.FIT_XY);
        offSetCells[0][0].setBackgroundResource(R.drawable.redbackground);
        addOffSetActionListener(offSetCells[0][0]);
        offSetCells[1][0]= (CustomImageView) findViewById(R.id.imageView_10);
        offSetCells[1][0].setCoordinates(new Coordinates(1, -1));
        offSetCells[1][0].setScaleType(ImageView.ScaleType.FIT_XY);
        offSetCells[1][0].setBackgroundResource(R.drawable.brownbackground);
        addOffSetActionListener(offSetCells[1][0]);
        offSetCells[2][0]= (CustomImageView) findViewById(R.id.imageView_20);
        offSetCells[2][0].setCoordinates(new Coordinates(2, -1));
        offSetCells[2][0].setScaleType(ImageView.ScaleType.FIT_XY);
        offSetCells[2][0].setBackgroundResource(R.drawable.redbackground);
        addOffSetActionListener(offSetCells[2][0]);
        offSetCells[3][0]= (CustomImageView) findViewById(R.id.imageView_30);
        offSetCells[3][0].setCoordinates(new Coordinates(3, -1));
        offSetCells[3][0].setScaleType(ImageView.ScaleType.FIT_XY);
        offSetCells[3][0].setBackgroundResource(R.drawable.brownbackground);
        addOffSetActionListener(offSetCells[3][0]);



        offSetCells[0][1]= (CustomImageView) findViewById(R.id.imageView__00);
        offSetCells[0][1].setCoordinates(new Coordinates(0, 4));
        offSetCells[0][1].setScaleType(ImageView.ScaleType.FIT_XY);
        offSetCells[0][1].setBackgroundResource(R.drawable.redbackground);
        addOffSetActionListener(offSetCells[0][1]);
        offSetCells[1][1]= (CustomImageView) findViewById(R.id.imageView__10);
        offSetCells[1][1].setCoordinates(new Coordinates(1, 4));
        offSetCells[1][1].setScaleType(ImageView.ScaleType.FIT_XY);
        offSetCells[1][1].setBackgroundResource(R.drawable.brownbackground);
        addOffSetActionListener(offSetCells[1][1]);
        offSetCells[2][1]= (CustomImageView) findViewById(R.id.imageView__20);
        offSetCells[2][1].setCoordinates(new Coordinates(2, 4));
        offSetCells[2][1].setScaleType(ImageView.ScaleType.FIT_XY);
        offSetCells[2][1].setBackgroundResource(R.drawable.redbackground);
        addOffSetActionListener(offSetCells[2][1]);
        offSetCells[3][1]= (CustomImageView) findViewById(R.id.imageView__30);
        offSetCells[3][1].setCoordinates(new Coordinates(3, 4));
        offSetCells[3][1].setScaleType(ImageView.ScaleType.FIT_XY);
        offSetCells[3][1].setBackgroundResource(R.drawable.brownbackground);
        addOffSetActionListener(offSetCells[3][1]);
    }
    private void initPlayers(GameMode gmPlayer, GameMode gmEnemy){
        player = createPlayer(gmPlayer, Color.WHITE);
        enemy= createPlayer(gmEnemy, Color.BLACK);
        player.createPieces(player.getColor());
        enemy.createPieces(enemy.getColor());
    }

    private Player createPlayer(GameMode gameMode, Color color){
        if(gameMode==GameMode.COMPUTER){
            return new Arnold(color);
        }else{
            return new HumanPlayer(color);
        }
    }

    private void initCells() {
        cells= new CustomImageView[4][4];
        cells[0][0]= (CustomImageView) findViewById(R.id.imageView00);
        cells[0][0].setCoordinates(new Coordinates(0, 0));
        cells[0][0].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[0][0].setBackgroundResource(R.drawable.redbackground);
        addActionListener(cells[0][0]);
        cells[1][0]= (CustomImageView) findViewById(R.id.imageView10);
        cells[1][0].setCoordinates(new Coordinates(1, 0));
        cells[1][0].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[1][0].setBackgroundResource(R.drawable.brownbackground);
        addActionListener(cells[1][0]);
        cells[2][0]= (CustomImageView) findViewById(R.id.imageView20);
        cells[2][0].setCoordinates(new Coordinates(2, 0));
        cells[2][0].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[2][0].setBackgroundResource(R.drawable.redbackground);
        addActionListener(cells[2][0]);
        cells[3][0]= (CustomImageView) findViewById(R.id.imageView30);
        cells[3][0].setCoordinates(new Coordinates(3,0));
        cells[3][0].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[3][0].setBackgroundResource(R.drawable.brownbackground);
        addActionListener(cells[3][0]);

        cells[0][1]= (CustomImageView) findViewById(R.id.imageView01);
        cells[0][1].setCoordinates(new Coordinates(0,1));
        cells[0][1].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[0][1].setBackgroundResource(R.drawable.brownbackground);
        addActionListener(cells[0][1]);
        cells[1][1]= (CustomImageView) findViewById(R.id.imageView11);
        cells[1][1].setCoordinates(new Coordinates(1,1));
        cells[1][1].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[1][1].setBackgroundResource(R.drawable.redbackground);
        addActionListener(cells[1][1]);
        cells[2][1]= (CustomImageView) findViewById(R.id.imageView21);
        cells[2][1].setCoordinates(new Coordinates(2, 1));
        cells[2][1].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[2][1].setBackgroundResource(R.drawable.brownbackground);
        addActionListener(cells[2][1]);
        cells[3][1]= (CustomImageView) findViewById(R.id.imageView31);
        cells[3][1].setCoordinates(new Coordinates(3, 1));
        cells[3][1].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[3][1].setBackgroundResource(R.drawable.redbackground);
        addActionListener(cells[3][1]);

        cells[0][2]= (CustomImageView) findViewById(R.id.imageView02);
        cells[0][2].setCoordinates(new Coordinates(0, 2));
        cells[0][2].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[0][2].setBackgroundResource(R.drawable.redbackground);
        addActionListener(cells[0][2]);
        cells[1][2]= (CustomImageView) findViewById(R.id.imageView12);
        cells[1][2].setCoordinates(new Coordinates(1,2));
        cells[1][2].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[1][2].setBackgroundResource(R.drawable.brownbackground);
        addActionListener(cells[1][2]);
        cells[2][2]= (CustomImageView) findViewById(R.id.imageView22);
        cells[2][2].setCoordinates(new Coordinates(2,2));
        cells[2][2].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[2][2].setBackgroundResource(R.drawable.redbackground);
        addActionListener(cells[2][2]);
        cells[3][2]= (CustomImageView) findViewById(R.id.imageView32);
        cells[3][2].setCoordinates(new Coordinates(3,2));
        cells[3][2].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[3][2].setBackgroundResource(R.drawable.brownbackground);
        addActionListener(cells[3][2]);


        cells[0][3]= (CustomImageView) findViewById(R.id.imageView03);
        cells[0][3].setCoordinates(new Coordinates(0, 3));
        cells[0][3].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[0][3].setBackgroundResource(R.drawable.brownbackground);
        addActionListener(cells[0][3]);
        cells[1][3]= (CustomImageView) findViewById(R.id.imageView13);
        cells[1][3].setCoordinates(new Coordinates(1, 3));
        cells[1][3].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[1][3].setBackgroundResource(R.drawable.redbackground);
        addActionListener(cells[1][3]);
        cells[2][3]= (CustomImageView) findViewById(R.id.imageView23);
        cells[2][3].setCoordinates(new Coordinates(2, 3));
        cells[2][3].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[2][3].setBackgroundResource(R.drawable.brownbackground);
        addActionListener(cells[2][3]);
        cells[3][3]= (CustomImageView) findViewById(R.id.imageView33);
        cells[3][3].setCoordinates(new Coordinates(3,3));
        cells[3][3].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[3][3].setBackgroundResource(R.drawable.redbackground);
        addActionListener(cells[3][3]);
    }

    private Piece previousPiece;
    private Coordinates prevCoords;
    private Moves validMoves;
    private void addActionListener(CustomImageView item){

        item.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            Coordinates coord = ((CustomImageView)v).getCoordinates();
            Piece piece = board.get(coord);
            if(previousPiece==null){
                checkPreviousPiece(piece);
            }else{
                if(piece!=null && piece.getColor()==player.getColor()){
                    checkPreviousPiece(piece);
                }else{
                    if(moveIsAllowed(coord)){
                        board.set(previousPiece, coord);
                        cells[coord.getX()][coord.getY()].setImageResource(previousPiece.getImagePath());
                        if(!board.hasInBounds(prevCoords)){
                            resetOffsetCells();
                        }else{
                            cells[prevCoords.getX()][prevCoords.getY()].setImageResource(0);
                        }
                        previousPiece=null;
                        validMoves=null;
                        //toggleTurn();
                        //enemy.move();
                        //toggleTurn();
                    }


                }


		}

		


            }

            private void resetOffsetCells() {
                boolean found = false;
                int i=0;
                while(i<offSetCells.length && !found){
                    int j=0;
                    while(j<offSetCells[0].length && !found){
                        if(offSetCells[i][j].getCoordinates().compareTo(prevCoords)==0){
                            found=true;
                            offSetCells[i][j].setImageResource(0);
                        }
                        j++;
                    }

                    i++;
                }
            }

            private boolean moveIsAllowed(Coordinates coord) {

                return validMoves.hasCoordinateInMoves(coord);

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


    private void addOffSetActionListener(CustomImageView item){

        item.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                try{
                    Coordinates coord = ((CustomImageView)v).getCoordinates();
                    Piece piece = player.getPiece(coord);
                    if(previousPiece==null){
                        checkPreviousPiece(piece);
                    }else{
                        if(piece!=null && piece.getColor()==player.getColor()){
                            checkPreviousPiece(piece);
                        }
                        }
                }catch(Exception e){
                    Log.i("actionError", e.getMessage());
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
            offSetCells[piece.getCoordinates().getX()][position].setImageResource(piece.getImagePath());
            Log.i("tag",String.valueOf(piece.getImagePath()));
        }

    }

}
