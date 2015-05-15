package com.deltagames.tictacchec.Model;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.deltagames.tictacchec.Model.Board.Coordinates;

/**
 * custom image view
 */
public class CustomImageView extends ImageView {
    private Coordinates coord;
    public CustomImageView(Context context) {
        super(context);
    }
    public CustomImageView(Context context, Coordinates coord) {
        super(context);
        this.coord=coord;
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width

    }

    public void setCoordinates(Coordinates coord){this.coord=coord;}

    public Coordinates getCoordinates(){
        return this.coord;
    }
}