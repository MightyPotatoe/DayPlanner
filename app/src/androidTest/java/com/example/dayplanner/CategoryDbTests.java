package com.example.dayplanner;

import android.content.Context;
import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.dayplanner.DataBase.AppDatabase;
import com.example.dayplanner.DataBase.Category;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class CategoryDbTests {

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
        Category[] categories = Category.populateData();
        List<Category> result = db.categoryDao().getAll();
        for(int i = 0; i < categories.length; i++){
            Assert.assertEquals(result.get(i), categories[i]);
        }

    }

    //-----------------TEST 3------------------------------
    /**
     * Sprawdzenie czy kategoria istnieje
     */
    @Test()
    public void SprawdzenieCzyKategoriaZnajdujeSieNaLiscie(){
        final AppDatabase db = AppDatabase.getInstance(mainActivity.getBaseContext());
        final Category existingCategory = Category.populateData()[0];
        //-- 1) Kategoria znajduje się w bazie wszystkie pola
        assertEquals(1, db.categoryDao().checkForDuplicates(existingCategory.name, existingCategory.iconId, existingCategory.colorHex));

        //-- 2) Kategoria nie znajduje się w bazie - nazwa jako duplikat
        assertEquals(1, db.categoryDao().checkForDuplicates(existingCategory.name, 1, "x"));

        //-- 3) Kategoria nie znajduje się w bazie - ikona jako duplikat
        assertEquals(1, db.categoryDao().checkForDuplicates("example", existingCategory.iconId, "x"));

        //-- 4) Kategoria nie znajduje się w bazie - kolor jako duplikat
        assertEquals(1, db.categoryDao().checkForDuplicates("secondExample", 2, existingCategory.colorHex));

        //-- 5) Kategoria nie znajduje się w bazie
        assertEquals(0, db.categoryDao().checkForDuplicates("nonExist", 0, "xxx"));
    }

    /**
     * Dodanie kategorii nie znajdujacej sie na liscie
     */
    @Test()
    public void DodanieKategoriiNieznajdujacejSieNaLiscie(){
        final AppDatabase db = AppDatabase.getInstance(mainActivity.getBaseContext());

        Category category = new Category("Nowa Kategortia", 215452, "#FFFFFF");
        //Sprawdzenie czy podana kategoria istenieje w bazie danych
        assertEquals(0, db.categoryDao().checkForDuplicates("nonExist", 0, "xxx"));
        //Dodanie categorii do bazy
        db.categoryDao().insertAll(category);
        //Weryfikacja czy kategoria została dodana
        assertEquals(1, db.categoryDao().checkForDuplicates(category.name, category.iconId, category.colorHex));
    }

}