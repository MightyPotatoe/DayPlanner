package com.example.dayplanner;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Paint;

import androidx.annotation.Nullable;

import java.time.DayOfWeek;
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

    //Tabela kategorii
    private static final String CATEGORIES_TABLE_NAME = "CATEGORIES_TABLE"; //nazwa tabeli w BD
    private static final String CATEGORY_NAME_COL = "CATEGORY_NAME"; //nazwa kolumny 1
    private static final String CATEGORY_ICON_ID = "CATEGORY_ICON_ID"; //nazwa kolumny 2

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
                + CATEGORY_ICON_ID + " INTEGER)";


        db.execSQL(CREATE_ACTIVITIES_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORIES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ACTIVITIES_TABLE_NAME);
        onCreate(db);
    }

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

    







    public String getCategoryNameFromCategoryDB(String categoryName){
        SQLiteDatabase db = this.getWritableDatabase();
        final String SQL_QUERY = "SELECT * FROM " + CATEGORIES_TABLE_NAME +
                " WHERE "+CATEGORY_NAME_COL+" = '"+categoryName+"'";
        Cursor cursor = db.rawQuery(SQL_QUERY, null);
        if(cursor.moveToNext()){
            String result = cursor.getString(1);
            cursor.close();
            return result;
        }
        return null;
    }



//-----------------------------------------------------------------------------------------------------------------------------------------
   /* *//**
     * Pobiera liczbe godzin przepracowanych danego dnia
     * @param date_yyyy_MM_dd - data z której zostanie zliczona suma godzin pracy
     * @return liczba godzin przepracowanych danego dnia
     *//*
    public int getHoursWorkedByDay(String date_yyyy_MM_dd){
        //--Inicjowanie bazy danych
        SQLiteDatabase db = this.getWritableDatabase();
        //--Zwrocenie sumy przepracowanych godzin dnia dzisiejszego
        final String SQL_QUERY = "SELECT SUM("+TIME_WORKED_COL+") " +
                "FROM "+TABLE_NAME+" " +
                "WHERE "+DATE_COL+" = '"+date_yyyy_MM_dd+"'";
        Cursor cursor = db.rawQuery(SQL_QUERY, null);
        if(cursor.moveToNext()){
            int result = cursor.getInt(0);
            cursor.close();
            return result;
        }
        return 0;
    }

    *//**
     *Usuwa wszystkie rekordy z bazy danych working_hours z podanego dnia
     * @param date_yyyy_MM_dd - data w formacie yyyy-MM-dd
     *//*
    public void deleteAllRecordsFromTheDay(String date_yyyy_MM_dd){
        //--Inicjowanie bazy danych
        SQLiteDatabase db = this.getWritableDatabase();
        final String SQL_QUERY = "DELETE FROM "+TABLE_NAME+
                " WHERE "+DATE_COL+" = '"+date_yyyy_MM_dd+"'";
        db.execSQL(SQL_QUERY);
    }

    *//**
     * Dodanie nowego wiersza do bazy danych working_hours
     *
     * @param date_yyyyMMdd - dzien dodania
     * @param timeStart_HHmm - czas rozpoczecia sesji
     * @param timeEnd_HHmm - czas zakonczenia sesji
     * @param timeWorkedInMinutes - czas przepracowanyu w sesji w minutach
     * @return true/false
     *//*
    public void insertHolidayToHolidayTable(String date_yyyyMMdd, String timeStart_HHmm, String timeEnd_HHmm, int timeWorkedInMinutes) {
            SQLiteDatabase db = this.getWritableDatabase();
            final String SQL_QUERY = "INSERT INTO " + TABLE_NAME +
                    "(" + DATE_COL + ", " + START_HOUR_COL +", " + END_HOUR_COL +", " + TIME_WORKED_COL + ")" +
                    "VALUES('" + date_yyyyMMdd + "', '" + timeStart_HHmm +"', '" + timeEnd_HHmm +"', '" + timeWorkedInMinutes + "')";
            db.execSQL(SQL_QUERY);
    }


    *//**
     * Pobranie godziny rozpoczęcia pierwszej sesji w danym dniu
     * @param date_yyyy_MM_dd - data w formacie yyyy-MM-dd
     * @return
     *//*
    public String getStartHourOfDate(String date_yyyy_MM_dd){
        //--Inicjowanie bazy danych
        SQLiteDatabase db = this.getWritableDatabase();
        //--Zwrocenie ostatniej otwartej sesji
        final String SQL_QUERY = "SELECT * from "+TABLE_NAME+" where "+DATE_COL+" = '"+date_yyyy_MM_dd+"' ORDER BY "+START_HOUR_COL+" ASC;";
        Cursor cursor = db.rawQuery(SQL_QUERY, null);
        if(cursor.moveToNext()){
            return cursor.getString(2);
        }
        return null;
    }

    *//**
     * Pobranie godziny rozpoczęcia ostatniej sesji w danym dniu
     * @param date_yyyy_MM_dd - data w formacie yyyy-MM-dd
     * @return
     *//*
    public String getLastSessionStartHourOfDate(String date_yyyy_MM_dd){
        //--Inicjowanie bazy danych
        SQLiteDatabase db = this.getWritableDatabase();
        //--Zwrocenie ostatniej otwartej sesji
        final String SQL_QUERY = "SELECT * from "+TABLE_NAME+" where "+DATE_COL+" = '"+date_yyyy_MM_dd+"' ORDER BY "+START_HOUR_COL+" DESC;";
        Cursor cursor = db.rawQuery(SQL_QUERY, null);
        if(cursor.moveToNext()){
            String result = cursor.getString(2);
            cursor.close();
            System.out.println("Pobrałem z bazy " + TABLE_NAME + " ostatnią godzinę rozpoczęcia pracy: " + result);
            return result;
        }
        return null;
    }

    public void printRecordsFromDay(String date_yyyy_MM_dd){
        //--Inicjowanie bazy danych
        SQLiteDatabase db = this.getWritableDatabase();
        //--Zwrocenie wszystkich wartosci
        final String SQL_QUERY = "SELECT * from "+TABLE_NAME+" where "+DATE_COL+" = '"+date_yyyy_MM_dd+"' ORDER BY "+START_HOUR_COL+" DESC;";
        Cursor cursor = db.rawQuery(SQL_QUERY, null);
        while(cursor.moveToNext()){
            System.out.println("ID: " + cursor.getString(0) + " | DATE: " + cursor.getString(1) + " | START_HOUR: " +
                    cursor.getString(2) + " | END_HOUR " + cursor.getString(3) + " | TIME_WORKED: " + cursor.getString(4));
        }
        cursor.close();
    }

    //----------------HOLIDAY DB-------------------
    *//**
     * Pobiera nazwę święta z dnia podanego jako paramter
     *
     * @param date_yyyyMMdd - data w formacie yyyy-MM-dd
     * @return String - nazwa święta
     *//*
    public String getHolidayName(String date_yyyyMMdd) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + HOLIDAY_NAME +
                " FROM " + HOLIDAYS_TABLE_NAME + "" +
                " WHERE " + HOLIDAYS_DATE_COL + " = '" + date_yyyyMMdd + "'", null);
        if (!cursor.moveToNext()) {
            return null;
        }
        String result = cursor.getString(0);
        cursor.close();
        db.close();
        return result;
    }

    *//**
     * Sprawdza czy w dacie podanej jako argument występuje jakieś święto
     *
     * @param date_yyyyMMdd - data w formacie yyyy-MM-dd
     * @return true/false
     *//*
    public boolean isDayHoliday(String date_yyyyMMdd) {
        String holidayName = getHolidayName(date_yyyyMMdd);
        return holidayName != null;
    }

    *//**
     * Dodanie nowego święta do bazy danych
     *
     * @param date_yyyyMMdd - dzien dodania
     * @param holidayName   - nazwa swieta
     * @return true/false
     *//*
    public boolean insertHolidayToHolidayTable(String date_yyyyMMdd, String holidayName) {
        if (!isDayHoliday(date_yyyyMMdd)) {
            SQLiteDatabase db = this.getWritableDatabase();
            final String SQL_QUERY = "INSERT INTO " + HOLIDAYS_TABLE_NAME +
                    "(" + HOLIDAYS_DATE_COL + ", " + HOLIDAY_NAME + ")" +
                    "VALUES('" + date_yyyyMMdd + "', '" + holidayName + "')";
            db.execSQL(SQL_QUERY);
            return true;
        } else return false;
    }

    *//**
     * Dodanie nowego święta do bazy danych
     *
     * @param date_yyyyMMdd - dzien dodania
     *//*
    public void deleteHolidayFromHolidayTable(String date_yyyyMMdd) {
        SQLiteDatabase db = this.getWritableDatabase();
        final String SQL_QUERY = "DELETE FROM " + HOLIDAYS_TABLE_NAME +
                " WHERE " + HOLIDAYS_DATE_COL + "= '" + date_yyyyMMdd + "'";
        System.out.println("Executing SQL_QUERY: " + SQL_QUERY);
        db.execSQL(SQL_QUERY);
    }*/

}
