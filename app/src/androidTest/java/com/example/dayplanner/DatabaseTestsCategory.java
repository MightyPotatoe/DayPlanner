package com.example.dayplanner;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTestsCategory {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private MainActivity mainActivity = null;

    @Before
    public void setUp() throws InterruptedException {
        mainActivity = mainActivityActivityTestRule.getActivity();
        mainActivity.getApplicationContext().deleteDatabase("day_planner.db");
        mainActivity = mainActivityActivityTestRule.getActivity();
        //zamkniecie aplikacji
        mainActivity.finish();
        Intent intent = mainActivity.getIntent();
        mainActivity = mainActivityActivityTestRule.launchActivity(intent);
    }


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.dayplanner", appContext.getPackageName());
    }

    /**
     * 1) Uruchomienie aplikacji
     * 2) Sprawdzenie czy baza została wypełniona wartościami domyślnymi
     */
    @Test()
    public void InicjalizowanieBazyDanych() {
        DatabaseHelper dbHelper = new DatabaseHelper(mainActivity.getBaseContext());

        ArrayList<Category> basicCategoriesList = new BasicCategories().getCategoriesList();
        for(Category category: basicCategoriesList){
            Cursor cursor = dbHelper.getCategoryFromCategoryDB(category.NAME);
            Assert.assertEquals(cursor.getString(DatabaseHelper.CATEGORIES_TABLE_CATEGORY_NAME_COL_INDEX), category.NAME);
        }
    }

    /**
     * Sprawdzenie czy kategoria istnieje
     */
    @Test()
    public void SprawdzenieCzyKategoriaZnajdujeSieNaLiscie(){
        Category newCategory;

        //-- 1) Kategoria znajduje się bazie wszystkie pola
        DatabaseHelper dbHelper = new DatabaseHelper(mainActivity.getBaseContext());
        //Sprawdzenie czy podana kategoria istenieje w bazie danych
        Assert.assertTrue(dbHelper.checkIfCategoryExists(BasicCategories.categoriesList.get(0)));

        //-- 2) Kategoria nie znajduje się w bazie - nazwa jako duplikat
        newCategory = new Category(BasicCategories.categoriesList.get(0).NAME, 1, "RED");
        Assert.assertTrue(dbHelper.checkIfCategoryExists(newCategory));

        //-- 3) Kategoria nie znajduje się w bazie - ikona jako duplikat
        newCategory = new Category( "NOWA KATEGORIA TESTOWA", BasicCategories.categoriesList.get(0).ICON_ID, "RED");
        Assert.assertTrue(dbHelper.checkIfCategoryExists(newCategory));

        //-- 4) Kategoria nie znajduje się w bazie - kolor jako duplikat
        newCategory = new Category( "NOWA KATEGORIA TESTOWA", 1, BasicCategories.categoriesList.get(0).COLOR);
        Assert.assertTrue(dbHelper.checkIfCategoryExists(newCategory));

        //-- 5) Kategoria nie znajduje się w bazie
        newCategory = new Category("NOWA KATEGORIA TESTOWA", 1, "RED");
        Assert.assertFalse(dbHelper.checkIfCategoryExists(newCategory));

    }

    /**
     * Dodanie kategorii nie znajdujacej sie na liscie
     */
    @Test()
    public void DodanieKategoriiNieznajdujacejSieNaLiscie(){
        DatabaseHelper dbHelper = new DatabaseHelper(mainActivity.getBaseContext());
        Category newCategory = new Category("NOWA KATEGORIA TESTOWA", 1, "RED");
        //Sprawdzenie czy podana kategoria istenieje w bazie danych
        Assert.assertFalse(dbHelper.checkIfCategoryExists(newCategory));
        //Dodanie categorii do bazy
        Assert.assertTrue(dbHelper.insertCategoryToDB(newCategory));
        Assert.assertTrue(dbHelper.checkIfCategoryExists(newCategory));
    }
}