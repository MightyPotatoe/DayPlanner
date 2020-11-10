package com.example.dayplanner;

import android.content.Context;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class VerticalTimeLine extends LinearLayout {

    public VerticalTimeLine(Context context, int marginStart, int constraintElement) {
        super(context);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(dp(2), LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_TOP, constraintElement);
        layoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, constraintElement);
        layoutParams.setMargins(dp(marginStart),dp(0),dp(0),dp(0));
        this.setLayoutParams(layoutParams);
        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    public int dp(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
