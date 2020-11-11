package com.example.dayplanner;

import com.example.dayplanner.Utilities.VerificationMethods;

import org.junit.Assert;
import org.junit.Test;

import java.time.DayOfWeek;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DataVerificationTests {

    //Sprawdzenie poprawności dnia
    @Test
    public void SprawdzeniePoprawnosciDnia() {


        final String MONDAY_CORRECT = DayOfWeek.MONDAY.toString();
        final String TUESDAY_CORRECT = DayOfWeek.TUESDAY.toString();
        final String WEDNESDAY_CORRECT = DayOfWeek.WEDNESDAY.toString();
        final String THURSDAY_CORRECT = DayOfWeek.THURSDAY.toString();
        final String FRIDAY_CORRECT = DayOfWeek.FRIDAY.toString();
        final String SATURDAY_CORRECT = DayOfWeek.SATURDAY.toString();
        final String SUNDAY_CORRECT = DayOfWeek.SUNDAY.toString();

        final String DAY_OF_WEEK_INCORRECT_1 = "1";
        final String DAY_OF_WEEK_INCORRECT_2 = "example";
        final String DAY_OF_WEEK_INCORRECT_3 = "ItsNotAA Valid Day";
        final String DAY_OF_WEEK_INCORRECT_4 = "@#AnotherExample ";
        final String DAY_OF_WEEK_INCORRECT_5 = "monday";
        final String DAY_OF_WEEK_INCORRECT_6 = "tuesday";
        final String DAY_OF_WEEK_INCORRECT_7 = "thursday";
        final String DAY_OF_WEEK_INCORRECT_8 = "1";
        final String DAY_OF_WEEK_INCORRECT_9 = "1";
        final String DAY_OF_WEEK_INCORRECT_10 = "mon";
        final String DAY_OF_WEEK_INCORRECT_11 = "tue";
        final String DAY_OF_WEEK_INCORRECT_12 = "wed";
        final String DAY_OF_WEEK_INCORRECT_13 = "thu";
        final String DAY_OF_WEEK_INCORRECT_14 = "fri";
        final String DAY_OF_WEEK_INCORRECT_15 = "sat";
        final String DAY_OF_WEEK_INCORRECT_16 = "sun";


        Assert.assertTrue(VerificationMethods.isDayCorrect(MONDAY_CORRECT));
        Assert.assertTrue(VerificationMethods.isDayCorrect(TUESDAY_CORRECT));
        Assert.assertTrue(VerificationMethods.isDayCorrect(WEDNESDAY_CORRECT));
        Assert.assertTrue(VerificationMethods.isDayCorrect(THURSDAY_CORRECT));
        Assert.assertTrue(VerificationMethods.isDayCorrect(FRIDAY_CORRECT));
        Assert.assertTrue(VerificationMethods.isDayCorrect(SUNDAY_CORRECT));
        Assert.assertTrue(VerificationMethods.isDayCorrect(SATURDAY_CORRECT));

        Assert.assertFalse(VerificationMethods.isDayCorrect(DAY_OF_WEEK_INCORRECT_1));
        Assert.assertFalse(VerificationMethods.isDayCorrect(DAY_OF_WEEK_INCORRECT_2));
        Assert.assertFalse(VerificationMethods.isDayCorrect(DAY_OF_WEEK_INCORRECT_3));
        Assert.assertFalse(VerificationMethods.isDayCorrect(DAY_OF_WEEK_INCORRECT_4));
        Assert.assertFalse(VerificationMethods.isDayCorrect(DAY_OF_WEEK_INCORRECT_5));
        Assert.assertFalse(VerificationMethods.isDayCorrect(DAY_OF_WEEK_INCORRECT_6));
        Assert.assertFalse(VerificationMethods.isDayCorrect(DAY_OF_WEEK_INCORRECT_7));
        Assert.assertFalse(VerificationMethods.isDayCorrect(DAY_OF_WEEK_INCORRECT_8));
        Assert.assertFalse(VerificationMethods.isDayCorrect(DAY_OF_WEEK_INCORRECT_9));
        Assert.assertFalse(VerificationMethods.isDayCorrect(DAY_OF_WEEK_INCORRECT_10));
        Assert.assertFalse(VerificationMethods.isDayCorrect(DAY_OF_WEEK_INCORRECT_11));
        Assert.assertFalse(VerificationMethods.isDayCorrect(DAY_OF_WEEK_INCORRECT_12));
        Assert.assertFalse(VerificationMethods.isDayCorrect(DAY_OF_WEEK_INCORRECT_13));
        Assert.assertFalse(VerificationMethods.isDayCorrect(DAY_OF_WEEK_INCORRECT_14));
        Assert.assertFalse(VerificationMethods.isDayCorrect(DAY_OF_WEEK_INCORRECT_15));
        Assert.assertFalse(VerificationMethods.isDayCorrect(DAY_OF_WEEK_INCORRECT_16));





    }
}