package com.example.dayplanner;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class FirstFragment extends Fragment {

    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DatabaseHelper(getContext());


        LinearLayout contentLayout = view.findViewById(R.id.contentLayout);



        /*RelativeLayout graphlayout = view.findViewById(R.id.graphLayout);
        LinearLayout legendLayout = view.findViewById(R.id.legendLayout);


        //Generowanie wykresu
        CircularProgressBar progressBar = new CircularProgressBar(getContext(), 200, 100, R.color.colorPrimary);
        progressBar.setProgress(100);
        graphlayout.addView(progressBar);

        CircularProgressBar progressBar2 = new CircularProgressBar(getContext(), 200, 100, R.color.colorPrimaryDark);
        progressBar2.setProgress(80);
        graphlayout.addView(progressBar2);

        CircularProgressBar progressBar3 = new CircularProgressBar(getContext(), 200, 100, R.color.colorSecondary);
        progressBar3.setProgress(40);
        graphlayout.addView(progressBar3);

        LegendElement legendElement = new LegendElement(getContext(), R.color.colorPrimary, "- sport");
        LegendElement legendElement2 = new LegendElement(getContext(), R.color.colorPrimary, "- rozrywka");
        LegendElement legendElement3 = new LegendElement(getContext(), R.color.colorPrimary, "- nauka");

        legendLayout.addView(legendElement);
        legendLayout.addView(legendElement2);
        legendLayout.addView(legendElement3);

        //Tworzenie linii czasu
        final int ICON_SIZE = 32;
        final int MARGIN_START = 8;*/


        TimeEventElement singleActionElement1 = new TimeEventElement(getContext(), R.drawable.alarm_icon,  "06:30", "Pobudka");
        contentLayout.addView(singleActionElement1);

        TimeEventElement timeEventElement = new TimeEventElement(getContext(), R.drawable.bath_icon,"06:45", "Prysznic");
        contentLayout.addView(timeEventElement);

        TimeEventElement timeEventElement2 = new TimeEventElement(getContext(), R.drawable.work_icon,"07:00", "Praca");
        contentLayout.addView(timeEventElement2);

        SingleActionElement timeEventElement3 = new SingleActionElement(getContext(), R.drawable.food_icon,"08:00", "Sniadanie");
        contentLayout.addView(timeEventElement3);

        SingleActionElement timeEventElement4 = new SingleActionElement(getContext(), R.drawable.food_icon,"10:00", "Drugie sniadanie");
        contentLayout.addView(timeEventElement4);

        SingleActionElement timeEventElement5 = new SingleActionElement(getContext(), R.drawable.food_icon,"14:00", "Obiad");
        contentLayout.addView(timeEventElement5);

        TimeEventElement timeEventElement6 = new TimeEventElement(getContext(), R.drawable.dog_icon,"15:00", "Spacer z psem");
        contentLayout.addView(timeEventElement6);

        TimeEventElement timeEventElement7 = new TimeEventElement(getContext(), R.drawable.book_icon,"17:00", "Nauka/Projekty");
        contentLayout.addView(timeEventElement7);

        SingleActionElement timeEventElement8 = new SingleActionElement(getContext(), R.drawable.food_icon,"17:30", "Podwieczorek");
        contentLayout.addView(timeEventElement8);

        SingleActionElement timeEventElement9 = new SingleActionElement(getContext(), R.drawable.dog_icon,"18:30", "Spacer z psem");
        contentLayout.addView(timeEventElement9);

        TimeEventElement timeEventElement10 = new TimeEventElement(getContext(), R.drawable.game_icon,"19:00", "Rozwrywka");
        contentLayout.addView(timeEventElement10);

        SingleActionElement timeEventElement11 = new SingleActionElement(getContext(), R.drawable.food_icon,"20:00", "Kolacja");
        contentLayout.addView(timeEventElement11);

        TimeEventElement timeEventElement12 = new TimeEventElement(getContext(), R.drawable.sport_icon,"22:30", "Medytacja");
        contentLayout.addView(timeEventElement12);

        TimeEventElement timeEventElement13 = new TimeEventElement(getContext(), R.drawable.sleep_icon,"23:00", "Spanko");
        contentLayout.addView(timeEventElement13);


        final ScrollView scrollView = view.findViewById(R.id.contentRelativeLayout);
        scrollView.bringToFront();


    }

    private final void focusOnView(final ScrollView scrollView, final TimeEventElement timeEventElement){
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, timeEventElement.getTop());
            }
        });
    }

    public int dp(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }




}