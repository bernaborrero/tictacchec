package com.deltagames.tictacchec.Model.Utils;

import com.deltagames.tictacchec.Model.Board.Coordinates;

/**
 * Created by Maxi on 13/05/2015.
 */
public class TranslateUtils {

    public TranslateUtils(){}

    public static Coordinates translate(int position){
        if(position<4){
            return new Coordinates(position,0);
        }else if(position<8){
            return new Coordinates(position-4,1);
        }else if(position<12){
            return new Coordinates(position-8,2);
        }else{
            return new Coordinates(position-12,3);
        }
    }

    public static int translat(Coordinates coord){
        return coord.getY()*4+coord.getX();
    }

}
