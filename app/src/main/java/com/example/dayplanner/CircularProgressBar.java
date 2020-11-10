package com.example.dayplanner;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class CircularProgressBar extends ProgressBar {


    public CircularProgressBar(Context context, int dimension, int maxProgress, int colorId) {

        super(context, null, android.R.attr.progressBarStyleHorizontal);
        int pixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimension, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pixels, pixels);
        this.setLayoutParams(params);
        this.setProgressTintList(ColorStateList.valueOf(getResources().getColor(colorId)));
        //Rect bounds = this.getProgressDrawable().getBounds();
        this.setProgressDrawable(getResources().getDrawable(R.drawable.circle_small));
        //this.getProgressDrawable().setBounds(bounds);
        this.setMax(maxProgress);
        this.setProgress(0);
    }



}
