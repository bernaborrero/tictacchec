package com.deltagames.tictacchec.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.deltagames.tictacchec.Model.Board.Board;
import com.deltagames.tictacchec.Model.Board.Coordinates;
import com.deltagames.tictacchec.Model.Board.Moves;
import com.deltagames.tictacchec.Model.CustomImageView;
import com.deltagames.tictacchec.Model.Pieces.Piece;
import com.deltagames.tictacchec.Model.Players.Arnold;
import com.deltagames.tictacchec.Model.Players.HumanPlayer;
import com.deltagames.tictacchec.Model.Players.Player;
import com.deltagames.tictacchec.R;


public class GameActivity extends BaseActivity {

    public static final String GAME_MODE = "GAME_MODE";

    public enum GameMode {
        COMPUTER, PERSON
    }

    private Player enemy, player;
    private CustomImageView[][] cells;
    private Board board;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        board= new Board();
        Intent intent = getIntent();
        GameMode gameMode = (GameMode) intent.getSerializableExtra(GAME_MODE);
        initPlayers(GameMode.PERSON,gameMode);
        board.set(player.getPieces());
        initCells();
        checkBoard();
    }


    private void initPlayers(GameMode gmPlayer, GameMode gmEnemy){
        player = createPlayer(gmPlayer);
        enemy= createPlayer(gmEnemy);
        player.createPieces();
    }

    private Player createPlayer(GameMode gameMode){
        if(gameMode==GameMode.COMPUTER){
            return new Arnold();
        }else{
            return new HumanPlayer();
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
        cells[1][0].setCoordinates(new Coordinates(1,0));
        cells[1][0].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[1][0].setBackgroundResource(R.drawable.brownbackground);
        addActionListener(cells[1][0]);
        cells[2][0]= (CustomImageView) findViewById(R.id.imageView20);
        cells[2][0].setCoordinates(new Coordinates(2,0));
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
        cells[2][1].setCoordinates(new Coordinates(2,1));
        cells[2][1].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[2][1].setBackgroundResource(R.drawable.brownbackground);
        addActionListener(cells[2][1]);
        cells[3][1]= (CustomImageView) findViewById(R.id.imageView31);
        cells[3][1].setCoordinates(new Coordinates(3,1));
        cells[3][1].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[3][1].setBackgroundResource(R.drawable.redbackground);
        addActionListener(cells[3][1]);

        cells[0][2]= (CustomImageView) findViewById(R.id.imageView02);
        cells[0][2].setCoordinates(new Coordinates(0,2));
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
        cells[0][3].setCoordinates(new Coordinates(0,3));
        cells[0][3].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[0][3].setBackgroundResource(R.drawable.brownbackground);
        addActionListener(cells[0][3]);
        cells[1][3]= (CustomImageView) findViewById(R.id.imageView13);
        cells[1][3].setCoordinates(new Coordinates(1,3));
        cells[1][3].setScaleType(ImageView.ScaleType.FIT_XY);
        cells[1][3].setBackgroundResource(R.drawable.redbackground);
        addActionListener(cells[1][3]);
        cells[2][3]= (CustomImageView) findViewById(R.id.imageView23);
        cells[2][3].setCoordinates(new Coordinates(2,3));
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
                    if(piece.getColor()==player.getColor()){
                        checkPreviousPiece(piece);
                    }else{
                        board.set(piece, piece.getCoordinates().getX(),piece.getCoordinates().getY());
                        cells[prevCoords.getX()][prevCoords.getY()].setImageResource(0);
                        previousPiece=null;

                    }
                }

            }

            /*
    sets the previous piece with the last piece touched by the user
    and calculates it's possible moves
     */
            private void checkPreviousPiece(Piece piece){
                if(piece.getColor()==player.getColor()){
                    previousPiece=piece;
                    prevCoords=piece.getCoordinates();
                    validMoves=previousPiece.getValidMoves(board);
                }
            }
        });
    }


    private void checkBoard(){
        for(Piece piece : player.getPieces()){
            cells[piece.getCoordinates().getX()][piece.getCoordinates().getY()].setImageResource(piece.getImagePath());
            Log.i("tag",String.valueOf(piece.getImagePath()));
        }

    }

}
