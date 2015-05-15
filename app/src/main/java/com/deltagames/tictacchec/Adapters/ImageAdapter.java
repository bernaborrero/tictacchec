package com.deltagames.tictacchec.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.deltagames.tictacchec.Model.Board.Board;
import com.deltagames.tictacchec.Model.Pieces.Piece;
import com.deltagames.tictacchec.Model.Utils.TranslateUtils;
import com.deltagames.tictacchec.R;

/**
 * Created by Maxi on 14/05/2015.
 */
public class ImageAdapter extends BaseAdapter {
    private Context context;
    private Board board;
    public ImageAdapter(Context context, Board board){this.context=context; this.board=board;}

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);

        if (convertView == null) {

            convertView = new View(context);
            convertView = inflater.inflate(R.layout.image_layout, null);
        }
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewItem);
            Piece piece = board.get(TranslateUtils.translate(position));
            if(piece!=null){
                imageView.setImageResource(piece.getImagePath());

            }else{
                imageView.setImageResource(0);
            }


        return convertView;
    }
}
