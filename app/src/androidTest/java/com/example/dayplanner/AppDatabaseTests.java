package com.example.dayplanner;

import android.content.Context;
import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.dayplanner.DataBase.AppDatabase;
import com.example.dayplanner.DataBase.Category;
import com.example.dayplanner.DataBase.ScheduledActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.DayOfWeek;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AppDatabaseTests {

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
    //Dodawanie kategorii do abzy
    @Test()
    public void InicjalizowanieBazyDanych(){
        final AppDatabase db = AppDatabase.getInstance(mainActivity.getBaseContext());
        final Category existingCategory = Category.populateData()[0];


        //-- 1) Brak duplikatów - pozytywne dodanie
        Category category = new Category("asdfgasg", 123, "#sadas");
        Assert.assertEquals(0, db.insertCategoryToDb(category));

        //-- 2) Kategoria nie znajduje się w bazie - nazwa jako duplikat - błąd
        category = new Category(existingCategory.name, 1, "x");
        assertEquals(1, db.insertCategoryToDb(category));

        //-- 3) Kategoria nie znajduje się w bazie - ikona jako duplikat
        category = new Category("example", existingCategory.iconId, "x");
        assertEquals(1, db.insertCategoryToDb(category));

        //-- 4) Kategoria nie znajduje się w bazie - kolor jako duplikat
        category = new Category("secondExample", 2, existingCategory.colorHex);
        assertEquals(1, db.insertCategoryToDb(category));

    }

    //-----------------TEST 3------------------------------
    //Dodawanie aktywności do bazy - poprawność dnia tygodnia
    @Test()
    public void DodawanieActivityDoBazyDzienTygodnia(){
        final AppDatabase db = AppDatabase.getInstance(mainActivity.getBaseContext());



        //-- 1) niepoprawny dzien tygodnia
        final ScheduledActivity scheduledActivityWrongDay = new ScheduledActivity(
                "Nazwy czynności",
                "wrongDay",
                "7:00",
                "10:00",
                1);
        Assert.assertEquals(1, db.insertActivityToDb(scheduledActivityWrongDay));

        //-- 1) Poprawny dzien tygodnia
        final ScheduledActivity scheduledActivityCorrectDay = new ScheduledActivity(
                "Nazwy czynności",
                DayOfWeek.MONDAY.toString(),
                "7:00",
                "10:00",
                1);
        Assert.assertNotEquals(1, db.insertActivityToDb(scheduledActivityCorrectDay));
    }

    //-----------------TEST 4------------------------------
    //Dodawanie aktywności do bazy - poprawność godziny rozpoczęcia
    @Test()
    public void DodawanieActivityDoBazyGodzinaRozpoczecia(){
        final AppDatabase db = AppDatabase.getInstance(mainActivity.getBaseContext());

        //-- 1) niepoprawna godzina rozpoczecia
        final ScheduledActivity scheduledActivityWrongStartHour = new ScheduledActivity(
                "Nazwy czynności",
                DayOfWeek.THURSDAY.toString(),
                "NiepoprawnaGodzina",
                "10:00",
                1);
        Assert.assertEquals(2, db.insertActivityToDb(scheduledActivityWrongStartHour));

        final ScheduledActivity scheduledActivityWrongStartHour2 = new ScheduledActivity(
                "Nazwy czynności",
                DayOfWeek.THURSDAY.toString(),
                "24:00",
                "10:00",
                1);
        Assert.assertEquals(2, db.insertActivityToDb(scheduledActivityWrongStartHour2));

        final ScheduledActivity scheduledActivityWrongStartHour3 = new ScheduledActivity(
                "Nazwy czynności",
                DayOfWeek.THURSDAY.toString(),
                "19:61",
                "10:00",
                1);
        Assert.assertEquals(2, db.insertActivityToDb(scheduledActivityWrongStartHour3));

        //-- 1) Poprawna godzina
        final ScheduledActivity scheduledActivityCorrectStartHour = new ScheduledActivity(
                "Nazwy czynności",
                DayOfWeek.FRIDAY.toString(),
                "07:00",
                "10:00",
                1);
        Assert.assertEquals(0, db.insertActivityToDb(scheduledActivityCorrectStartHour));
    }

    //-----------------TEST 5------------------------------
    //Dodawanie aktywności do bazy - poprawność godziny zakonczenia
    @Test()
    public void DodawanieActivityDoBazyGodzinaZakonczenia(){
        final AppDatabase db = AppDatabase.getInstance(mainActivity.getBaseContext());

        //-- 1) niepoprawna godzina rozpoczecia
        final ScheduledActivity scheduledActivityWrongEndHour = new ScheduledActivity(
                "Nazwy czynności",
                DayOfWeek.THURSDAY.toString(),
                "10:00",
                "NiepoprawnaGodzina",
                1);
        Assert.assertEquals(3, db.insertActivityToDb(scheduledActivityWrongEndHour));

        final ScheduledActivity scheduledActivityWrongEndHour2 = new ScheduledActivity(
                "Nazwy czynności",
                DayOfWeek.THURSDAY.toString(),
                "10:00",
                "24:00",
                1);
        Assert.assertEquals(3, db.insertActivityToDb(scheduledActivityWrongEndHour2));

        final ScheduledActivity scheduledActivityWrongEndHour3 = new ScheduledActivity(
                "Nazwy czynności",
                DayOfWeek.THURSDAY.toString(),
                "10:00",
                "19:61",
                1);
        Assert.assertEquals(3, db.insertActivityToDb(scheduledActivityWrongEndHour3));

        //-- 1) Poprawna godzina
        final ScheduledActivity scheduledActivityCorrectEndHour = new ScheduledActivity(
                "Nazwy czynności",
                DayOfWeek.THURSDAY.toString(),
                "07:00",
                "10:00",
                1);
        Assert.assertEquals(0, db.insertActivityToDb(scheduledActivityCorrectEndHour));
    }


    //-----------------TEST 5------------------------------
    //Dodawanie aktywności do bazy - poprawność godziny zakonczenia
    @Test()
    public void DodawanieActivityDoBazyGodzinaStartWiekszaNizStop(){
        final AppDatabase db = AppDatabase.getInstance(mainActivity.getBaseContext());

        //-- 1) niepoprawna godzina rozpoczecia
        final ScheduledActivity scheduledActivityWrongEndHour = new ScheduledActivity(
                "Nazwy czynności",
                DayOfWeek.THURSDAY.toString(),
                "10:00",
                "08:00",
                1);
        Assert.assertEquals(4, db.insertActivityToDb(scheduledActivityWrongEndHour));

        final ScheduledActivity scheduledActivityWrongEndHour2 = new ScheduledActivity(
                "Nazwy czynności",
                DayOfWeek.THURSDAY.toString(),
                "12:00",
                "10:00",
                1);
        Assert.assertEquals(4, db.insertActivityToDb(scheduledActivityWrongEndHour2));

        //-- 1) Poprawna godzina
        final ScheduledActivity scheduledActivityCorrectEndHour = new ScheduledActivity(
                "Nazwy czynności",
                DayOfWeek.THURSDAY.toString(),
                "10:00",
                "10:00",
                1);
        Assert.assertEquals(0, db.insertActivityToDb(scheduledActivityCorrectEndHour));
    }


    //-----------------TEST 6------------------------------
    public void SprawdzenieCzyActivityNieKolidujeZinnym(){
        final AppDatabase db = AppDatabase.getInstance(mainActivity.getBaseContext());
        //Utworzenie danych w tabeli testowej - wtorek
        ScheduledActivity[] list = new ScheduledActivity[10];
        list[0] = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "06:00", "07:00", 1);
        list[1] = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "07:00", "08:00", 1);
        list[2] = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "08:00", "09:00", 1);
        list[2] = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "09:15", "09:15", 1);
        list[2] = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "09:45", "09:45", 1);
        list[3] = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "10:00", "11:00", 1);
        list[4] = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "11:00", "12:00", 1);
        list[5] = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "12:00", "13:00", 1);
        list[6] = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "13:00", "14:00", 1);
        list[7] = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "14:00", "15:00", 1);
        list[8] = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "15:00", "16:00", 1);
        db.scheduledActivityDao().insertAll(list);

        ScheduledActivity incorrectActivity = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "09:00", "17:00", 1);
        Assert.assertEquals(5, db.insertActivityToDb(incorrectActivity));

        ScheduledActivity incorrectActivity2 = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "09:00", "10:00", 1);
        Assert.assertEquals(5, db.insertActivityToDb(incorrectActivity2));


        ScheduledActivity correctActivity = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "09:00", "09:59", 1);
        Assert.assertEquals(0, db.insertActivityToDb(correctActivity));
        ScheduledActivity correctActivity2 = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "09:00", "10:00", 1);
        Assert.assertEquals(0, db.insertActivityToDb(correctActivity2));
        ScheduledActivity correctActivity3 = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "04:00", "06:00", 1);
        Assert.assertEquals(0, db.insertActivityToDb(correctActivity3));
        ScheduledActivity correctActivity4 = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "19:00", "23:00", 1);
        Assert.assertEquals(0, db.insertActivityToDb(correctActivity4));
    }

    //-----------------TEST 6------------------------------
    public void SprawdzenieCzyPodanakategoriaIstnieje(){
        final AppDatabase db = AppDatabase.getInstance(mainActivity.getBaseContext());

        ScheduledActivity incorrectActivity = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "09:00", "17:00", 500);
        Assert.assertEquals(6, db.insertActivityToDb(incorrectActivity));

        ScheduledActivity correctActivity = new ScheduledActivity("Nazwa", DayOfWeek.TUESDAY.toString(), "09:00", "17:00", 3);
        Assert.assertEquals(0, db.insertActivityToDb(correctActivity));
    }
}