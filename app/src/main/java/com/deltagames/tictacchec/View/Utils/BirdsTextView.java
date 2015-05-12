package com.deltagames.tictacchec.View.Utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.deltagames.tictacchec.R;

/**
 * Custom TextView with the font Birds Of Paradise
 * Created by Bernabé Borrero on 12/05/15.
 */
public class BirdsTextView extends TextView {


    public BirdsTextView(Context context) {
        super(context);
        init(null);
    }

    public BirdsTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BirdsTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView);
            String fontName = typedArray.getString(R.styleable.CustomTextView_fontName);
            if (fontName != null) {
                Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
                setTypeface(typeface);
            }
            typedArray.recycle();
        }
    }
}
