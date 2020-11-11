package com.example.dayplanner.CustomViews;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dayplanner.R;

public class SingleActionElement extends LinearLayout {

    public SingleActionElement(Context context, int imageId, String dateText, String activityName) {
        super(context);
        this.setOrientation(LinearLayout.VERTICAL);
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        View activity = inflate(context, R.layout.single_action_layout, null);

        final ImageView imageView = activity.findViewById(R.id.iconIV);
        imageView.setImageResource(imageId);

        final TextView hourTV = activity.findViewById(R.id.hourTV);
        hourTV.setText(dateText);

        final TextView activityTV = activity.findViewById(R.id.valueTV);
        activityTV.setText(activityName);

        this.addView(activity);

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

