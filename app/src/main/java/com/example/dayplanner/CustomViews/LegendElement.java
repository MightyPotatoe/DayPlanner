package com.example.dayplanner.CustomViews;

import android.content.Context;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dayplanner.R;

public class LegendElement extends LinearLayout {


    public LegendElement(Context context, int colorId, String text) {
        super(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(dp(0),dp(4),dp(0),dp(0));
        this.setLayoutParams(layoutParams);
        this.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout legendBlock = new LinearLayout(context);
        LinearLayout.LayoutParams boxParams = new LinearLayout.LayoutParams(dp(16), dp(16));
        legendBlock.setLayoutParams(boxParams);
        legendBlock.setBackgroundColor(getResources().getColor(colorId));
        this.addView(legendBlock);

        TextView textView = new TextView(context);
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textViewParams.setMargins(dp(4),dp(0),dp(0),dp(0));
        textView.setLayoutParams(textViewParams);
        textView.setText(text);
        textView.setTextColor(getResources().getColor(R.color.colorSecondaryDark));
        this.addView(textView);
    }

    public int dp(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
