package com.example.dayplanner;

import android.content.Context;
import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.dayplanner.DataBase.AppDatabase;
import com.example.dayplanner.DataBase.ScheduledActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.DayOfWeek;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ScheduledActivityDbTests {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private MainActivity mainActivity = null;

    @Before
    public void setUp(){
        mainActivity = mainActivityActivityTestRule.getActivity();
        mainActivity.getApplicationContext().deleteDatabase("day_planner_db");
        mainActivity = mainActivityActivityTestRule.getActivity();
        //zamkniecie aplikacji
        mainActivity.finish();
        Intent intent = mainActivity.getIntent();
        mainActivity = mainActivityActivityTestRule.launchActivity(intent);
    }


    //-----------------TEST 1------------------------------
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.dayplanner", appContext.getPackageName());
    }

    //-----------------TEST 2------------------------------
    /**
     * 1) Uruchomienie aplikacji
     * 2) Sprawdzenie czy baza została wypełniona wartościami domyślnymi
     */
    @Test()
    public void InicjalizowanieBazyDanych(){
        final AppDatabase db = AppDatabase.getInstance(mainActivity.getBaseContext());

        //Sprawdzenie czy wszystkie pola zostały spredefiniowane w bazie
        ScheduledActivity[] scheduledActivities = ScheduledActivity.populateData();
        List<ScheduledActivity> result = db.scheduledActivityDao().getAll();
        for(int i = 0; i < scheduledActivities.length; i++){
            Assert.assertEquals(result.get(i), scheduledActivities[i]);
        }

    }

    //-----------------TEST 3------------------------------
    //Dodanie rekordu do bazy danych
    @Test()
    public void DodanieScheduledActivityDoBazy(){
        final AppDatabase db = AppDatabase.getInstance(mainActivity.getBaseContext());
        ScheduledActivity scheduledActivity = new ScheduledActivity("Czynność", DayOfWeek.MONDAY.toString(), "08:00", "16:00", 1);
        db.scheduledActivityDao().insertAll(scheduledActivity);
        Assert.assertEquals(scheduledActivity, db.scheduledActivityDao().findByName(scheduledActivity.name));
    }

    //-----------------TEST 3------------------------------
    //getActivitiesBetween(DAY, START, STOP)
    @Test
    public void WyszukanieWszystkichAktownysciWPrzedzialeCzasowym(){
        final AppDatabase db = AppDatabase.getInstance(mainActivity.getBaseContext());
        //Utworzenie danych w tabeli testowej - wtorek
        ScheduledActivity[] list = new ScheduledActivity[10];
        list[0] = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "06:00", "07:00", 1);
        list[1] = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "07:00", "08:00", 1);
        list[2] = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "08:00", "09:00", 1);
        list[3] = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "09:00", "10:00", 1);
        list[4] = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "10:00", "11:00", 1);
        list[5] = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "11:00", "12:00", 1);
        list[6] = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "12:00", "13:00", 1);
        list[7] = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "13:00", "14:00", 1);
        list[8] = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "14:00", "15:00", 1);
        list[9] = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "15:00", "16:00", 1);


        db.scheduledActivityDao().insertAll(list);

        List<ScheduledActivity> result = db.scheduledActivityDao().getActivitiesBetween(DayOfWeek.TUESDAY.toString(), "09:00", "13:00");
        Assert.assertEquals(result.get(0), list[3]);
        Assert.assertEquals(result.get(1), list[4]);
        Assert.assertEquals(result.get(2), list[5]);
        Assert.assertEquals(result.get(3), list[6]);
    }


}
