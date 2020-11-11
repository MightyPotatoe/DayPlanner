package com.example.dayplanner;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "day_planner.db"; //nazwa bazy danych
    //Tabela activities
    private static final String ACTIVITIES_TABLE_NAME = "ACTIVITIES_TABLE"; //nazwa tabeli w BD
    private static final String ID_COL = "ID"; //nazwa kolumny 0
    private static final String DAY_OF_WEEK_COL = "DAY_OF_WEEK"; //nazwa kolumny 1
    private static final String ACTIVITY_NAME = "ACTIVITY_NAME"; //nazwa kolumny 2
    private static final String START_HOUR_COL = "START_HOUR"; //nazwa kolumny 3
    private static final String END_HOUR_COL = "END_HOUR"; //nazwa kolumny 4
    private static final String DURATION_COL = "DURATION"; //nazwa kolumny 5
    private static final String CATEGORY_COL = "CATEGORY"; //nazwa kolumny 6

    public static final int ACTIVITIES_TABLE_ID_COL_INDEX = 0;
    public static final int ACTIVITIES_TABLE_DAY_OF_WEEK_COL_INDEX = 1;
    public static final int ACTIVITIES_TABLE_ACTIVITY_NAME_INDEX = 2;
    public static final int ACTIVITIES_TABLE_START_HOUR_COL_INDEX = 3;
    public static final int ACTIVITIES_TABLE_END_HOUR_COL_INDEX = 4;
    public static final int ACTIVITIES_TABLE_DURATION_COL_INDEX = 5;
    public static final int ACTIVITIES_TABLE_CATEGORY_COL_INDEX = 6;


    //Tabela kategorii
    private static final String CATEGORIES_TABLE_NAME = "CATEGORIES_TABLE"; //nazwa tabeli w BD
    private static final String CATEGORY_NAME_COL = "CATEGORY_NAME"; //nazwa kolumny 1
    private static final String CATEGORY_ICON_ID = "CATEGORY_ICON_ID"; //nazwa kolumny 2
    private static final String CATEGORY_COLOR = "CATEGORY_COLOR"; //nazwa kolumny 3

    public static final int CATEGORIES_TABLE_ID_COL_INDEX = 0;
    public static final int CATEGORIES_TABLE_CATEGORY_NAME_COL_INDEX = 1;
    public static final int CATEGORIES_TABLE_CATEGORY_ICON_ID_INDEX = 2;
    public static final int CATEGORIES_TABLE_CATEGORY_COLOR_INDEX = 3;


    /**
     * Konstruktor klasy DatabaseHelper
     *
     * @param context - context użycia
     */
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /**
     * Tworzy tabelę o nazwie TABLE_NAME
     * Kolumny:
     * ID
     * DAY_OF_WEEK
     * START_HOUR
     * END_HOUR
     * DURATION
     * CATEGORY
     *
     * * Tworzy tabelę o nazwie TABLE_NAME
     * Kolumny:
     * ID
     * CATEGORIES_TABLE
     * CATEGORY_NAME
     * CATEGORY_ICON_ID
     *
     * * uzupełnia tabelę kategorii wartościami domyślnymi
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_ACTIVITIES_TABLE = "create table " + ACTIVITIES_TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DAY_OF_WEEK_COL + " STRING, "
                + ACTIVITY_NAME + " STRING, "
                + START_HOUR_COL + " STRING, "
                + END_HOUR_COL + " STRING, "
                + DURATION_COL + " INTEGER, "
                + CATEGORY_COL + " STRING)";

        final String CREATE_CATEGORY_TABLE = "create table " + CATEGORIES_TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + CATEGORY_NAME_COL + " STRING, "
                + CATEGORY_ICON_ID + " INTEGER,"
                + CATEGORY_COLOR + " STRING)";


        db.execSQL(CREATE_ACTIVITIES_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);

        //Uzupełnienie tabeli wartościami domyślnymi
        ArrayList<Category> basicCategoriesList = new BasicCategories().getCategoriesList();
        for(Category category: basicCategoriesList){
            insertCategoryToDB(category, db);
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORIES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ACTIVITIES_TABLE_NAME);
        onCreate(db);
    }

    /**
     * Wstawia wartości do bazy CATEGORY
     * @param category - kategoria
     * @return - true - wartość wstawiona popwawnie
     * false - nie udało się wstawić wartości
     */
    public boolean insertCategoryToDB(Category category){
        SQLiteDatabase db = this.getWritableDatabase();
        if(!checkIfCategoryExists(category)){
            return insertCategoryToDB(category, db);
        }
        else {
            return false;
        }

    }
    public boolean insertCategoryToDB(Category category, SQLiteDatabase db){
            final String SQL = "INSERT INTO "+CATEGORIES_TABLE_NAME+"("+CATEGORY_NAME_COL+","+CATEGORY_ICON_ID+","+CATEGORY_COLOR+") VALUES ('"+category.NAME.toUpperCase()+"', '"+category.ICON_ID+"', '"+category.COLOR+"')";
            db.execSQL(SQL);
            //Sprawdzenie czy wartość została dodana
            final String SQL_CHECK = "SELECT * FROM " + CATEGORIES_TABLE_NAME + "\n" +
                    "WHERE " + CATEGORY_NAME_COL + " = '" + category.NAME.toUpperCase() + "'\n" +
                    "AND " + CATEGORY_ICON_ID + " = '" + category.ICON_ID + "'\n" +
                    "AND " + CATEGORY_COLOR + " = '" + category.COLOR + "'";
        Cursor cursor = db.rawQuery(SQL_CHECK, null);
        if(cursor.moveToNext()){
            cursor.close();
            return true;
        }
        else{
            cursor.close();
            return false;
        }
    }

    /**
     * Sprawdza czy kategoria znajduje się już w bazie
     * @param category - kategoria
     * @return true - kategoria nie występuje w bazie (Żaden z paramrtrów nie jest duplikatem)
     * false - przynajmniej jeden parametr jest duplikatem
     */
    public boolean checkIfCategoryExists(Category category){
        final String SQL_QUERY = "SELECT * FROM " + CATEGORIES_TABLE_NAME + "\n" +
                "WHERE " + CATEGORY_NAME_COL + " = '" + category.NAME.toUpperCase() + "'\n" +
                "OR " + CATEGORY_ICON_ID + " = '" + category.ICON_ID + "'\n" +
                "OR " + CATEGORY_COLOR + " = '" + category.COLOR + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SQL_QUERY, null);
        if(cursor.moveToNext()){
            cursor.close();
            return true;
        }
        else{
            cursor.close();
            return false;
        }
    }

    //-------------NIETESTOWANE------------------------
    /**
     * Dodawanie wpisu do bazy danych
     */
    public boolean addActivityToDB(String dayOfWeek, String activityName, String startHour, String endHour, String category){

        System.out.println("Podejmuję próbę dodania wpisu do bazy danych...");
        boolean isDayOfWeekCorrect = isDayCorrect(dayOfWeek);
        boolean isActivityNameCorrect = !activityName.isEmpty();
        boolean isStartDateCorrect = isHourCorrect(startHour);
        boolean isEndDateCorrect = isHourCorrect(endHour);


        return false;
    }




    //-------------WERYFIKACJIA FORMATU---------------------
    /**
     * Sprawdza czy podany dzień[String] odpowiada przedziałowi pn-nd
     * @param dayOfWeek - Dzien tygodnia [String]
     * @return - true: dayOfWeek jest dniem tygodnia
     * false: dayOfWeek nie jest dniem tygodnia
     */
    public boolean isDayCorrect(String dayOfWeek){
        System.out.println("Sprawdzam czy podano popaprawny dzień..." );
        if(dayOfWeek.equals(DayOfWeek.MONDAY.toString())
                || dayOfWeek.equals(DayOfWeek.TUESDAY.toString())
                || dayOfWeek.equals(DayOfWeek.WEDNESDAY.toString())
                || dayOfWeek.equals(DayOfWeek.THURSDAY.toString())
                || dayOfWeek.equals(DayOfWeek.FRIDAY.toString())
                || dayOfWeek.equals(DayOfWeek.SATURDAY.toString())
                || dayOfWeek.equals(DayOfWeek.SUNDAY.toString())){
            System.out.println("Operacja zakończona sukcesem!");
            return true;
        }
        System.out.println("Podany dzień: " + dayOfWeek + " nie spełnia wymogów!");
        return false;
    }

    /**
     * Sprawdza czy podana data spełnia format hh:mm
     * @param hour - godzina[String]
     * @return true: podana wartość spełnia warunki
     * false: podana wartość nie spełnia warunków
     */
    public boolean isHourCorrect(String hour){
        System.out.println("Sprawdzam czy podano popaprawną godzinę..." );
        String regexForTime = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$";
        Pattern hourPattern = Pattern.compile(regexForTime);
        Matcher matcher = hourPattern.matcher(hour);
        if(matcher.matches()){
            System.out.println("Operacja zakończona sukcesem!");
            return true;
        }
        System.out.println("Podana godzina: " + hour + " nie spełnia wymogów!");
        return false;
    }

    public Cursor getCategoryFromCategoryDB(String categoryName){
        SQLiteDatabase db = this.getReadableDatabase();
        final String SQL_QUERY = "SELECT * FROM " + CATEGORIES_TABLE_NAME +
                " WHERE "+CATEGORY_NAME_COL+" = '"+categoryName+"'";
        Cursor cursor = db.rawQuery(SQL_QUERY, null);
        if(cursor.moveToNext()){
            return cursor;
        }
        else{
            cursor.close();
            return null;
        }
    }

}
