package com.example.dayplanner;

import java.util.ArrayList;

public class BasicCategories {

    private final Category HYGIENE = new Category("HYGIENE", R.drawable.bath_icon, "#448AFF");
    private final Category WORK = new Category("WORK", R.drawable.work_icon, "#9C27B0");
    private final Category ANIMALS = new Category("ANIMALS", R.drawable.dog_icon, "#3F51B5");
    private final Category LEARNING = new Category("LEARNING", R.drawable.book_icon, "#E91E63");
    private final Category GAMES = new Category("GAMES", R.drawable.game_icon, "#4CAF50");
    private final Category SPORT = new Category("SPORTS", R.drawable.sport_icon, "#009688");
    private final Category REST = new Category("REST", R.drawable.sleep_icon, "#1021EA");

    public static ArrayList<Category> categoriesList = new ArrayList<>();

    public BasicCategories() {
        categoriesList.add(HYGIENE);
        categoriesList.add(WORK);
        categoriesList.add(ANIMALS);
        categoriesList.add(LEARNING);
        categoriesList.add(GAMES);
        categoriesList.add(SPORT);
        categoriesList.add(REST);
    }

    public ArrayList<Category> getCategoriesList() {
        return categoriesList;
    }
}
