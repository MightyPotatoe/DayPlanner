package com.example.dayplanner.Utilities;

public class Conversions {

    public static int hourToMinutes(String hour){
        if(VerificationMethods.isHourCorrect(hour)){
            String[] splitString = hour.split(":");
            return Integer.parseInt(splitString[0])*60 + Integer.parseInt(splitString[1]);
        }
        return -1;
    }
}
