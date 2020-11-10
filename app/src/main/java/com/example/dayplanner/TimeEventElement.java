package com.example.dayplanner;

import android.content.Context;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.dayplanner.databinding.TimeEventLayoutBinding;

public class TimeEventElement extends LinearLayout {

    TimeEventLayoutBinding binding;


    public TimeEventElement(Context context) {
        super(context);
    }

    public TimeEventElement(Context context, int imageId, String dateText, String activityName) {
        super(context);
        this.setOrientation(LinearLayout.VERTICAL);
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        final View timeEventLayout = inflate(context, R.layout.time_event_layout, null);

        final ImageView imageView = timeEventLayout.findViewById(R.id.iconIV);
        imageView.setImageResource(imageId);

        final TextView hourTV = timeEventLayout.findViewById(R.id.hourTV);
        hourTV.setText(dateText);

        final TextView activityTV = timeEventLayout.findViewById(R.id.valueTV);
        activityTV.setText(activityName);

        this.addView(timeEventLayout);


        final View separatorView = inflate(context, R.layout.empty_event_layout, null);
        this.addView(separatorView);


        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isStrikeThrough(activityTV)){
                    activityTV.setPaintFlags(activityTV.getPaintFlags() &~ Paint.STRIKE_THRU_TEXT_FLAG);
                    hourTV.setPaintFlags(hourTV.getPaintFlags() &~ Paint.STRIKE_THRU_TEXT_FLAG);
                }
                else {
                    activityTV.setPaintFlags(activityTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    hourTV.setPaintFlags(hourTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }

            }
        });
    }


    public static boolean isStrikeThrough(TextView tv) {
        int flags = tv.getPaintFlags();
        flags &= Paint.STRIKE_THRU_TEXT_FLAG;
        return flags == Paint.STRIKE_THRU_TEXT_FLAG;
    }


}
