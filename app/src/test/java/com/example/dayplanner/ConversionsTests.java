package com.example.dayplanner;

import com.example.dayplanner.Utilities.Conversions;

import org.junit.Assert;
import org.junit.Test;

public class ConversionsTests {

    @Test
    public void konwersjaGodzinyNaMinuty() {
        String incorrectHour = "123:43";
        Assert.assertEquals(-1, Conversions.hourToMinutes(incorrectHour));

        Assert.assertEquals(0, Conversions.hourToMinutes("00:00"));

        Assert.assertEquals(12*60 + 36, Conversions.hourToMinutes("12:36"));

        Assert.assertEquals(1*60+15, Conversions.hourToMinutes("01:15"));

        Assert.assertEquals(1*60+15, Conversions.hourToMinutes("1:15"));

        Assert.assertEquals(23*60+59, Conversions.hourToMinutes("23:59"));

    }
}
