package com.example.dayplanner.Utilities;

import java.time.DayOfWeek;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class VerificationMethods {

    //-------------WERYFIKACJIA FORMATU---------------------
    /**
     * Sprawdza czy podany dzień[String] odpowiada przedziałowi pn-nd
     * @param dayOfWeek - Dzien tygodnia [String]
     * @return - true: dayOfWeek jest dniem tygodnia
     * false: dayOfWeek nie jest dniem tygodnia
     */
    public static boolean isDayCorrect(String dayOfWeek){
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
    public static boolean isHourCorrect(String hour){
        System.out.println("Sprawdzam czy podano popaprawną godzinę..." );
        String regexForTime = "^([0-1][0-9]|2[0-3]):[0-5][0-9]$";
        Pattern hourPattern = Pattern.compile(regexForTime);
        Matcher matcher = hourPattern.matcher(hour);
        if(matcher.matches()){
            System.out.println("Operacja zakończona sukcesem!");
            return true;
        }
        System.out.println("Podana godzina: " + hour + " nie spełnia wymogów!");
        return false;
    }



}
