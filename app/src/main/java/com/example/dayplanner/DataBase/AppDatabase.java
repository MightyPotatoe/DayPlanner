package com.example.dayplanner.DataBase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.dayplanner.Utilities.Conversions;
import com.example.dayplanner.Utilities.VerificationMethods;

import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = {Category.class, ScheduledActivity.class},  version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract CategoryDao categoryDao();
    public abstract ScheduledActivityDao scheduledActivityDao();

    public synchronized static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        //---PERFORMING FAKE READ TO INITIALIZE DB
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                INSTANCE.categoryDao().fakeRead();
            }
        });
        return INSTANCE;
    }


    private static AppDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class,
                "day_planner_db")
                .allowMainThreadQueries()
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                getInstance(context).categoryDao().insertAll(Category.populateData());
                                getInstance(context).scheduledActivityDao().insertAll(ScheduledActivity.populateData());
                            }
                        });
                    }
                })
                .build();
    }

    /**
     *Dodaje nową kategorię do tabeli
     * @return
     * 1 = W bazie danych występuje duplikat jednego z pól
     * 0 = Wartośc dodana do bazy
     */
    public int insertCategoryToDb(Category category){
        //Sprawdzenie czy w bazie nie występuje duplikat choćby jednego pola
        if(categoryDao().checkForDuplicates(category.name, category.iconId, category.colorHex) > 0){
            return 1;
        }
        //Dodanie pola do bazy
        categoryDao().insertAll(category);
        return 0;
    }

    /**
     *Dodaje nową aktywność do tabeli
     * @return
     * 5 = aktywnosc koliduje z inną
     * 4 = godzina rozpoczecia wieksza niz godzina zakonczenia
     * 3 = niepoprawna godzina zakońćzenia
     * 2 = niepoprawna godzina rozpoczęcia
     * 1 = Podano niepoprawny dzień
     * 0 = Wartośc dodana do bazy
     */
    public int insertActivityToDb(ScheduledActivity scheduledActivity){
        //Sprawdzenie czy podano poprawny dzień tygodnia
        if(!VerificationMethods.isDayCorrect(scheduledActivity.dayOfWeek)){
            return 1;
        }
        //Sprawdzenie poprawności godziny rozpoczęcia aktywności
        if(!VerificationMethods.isHourCorrect(scheduledActivity.startTime)){
            return 2;
        }
        //Sprawdzenie popraności godziny zakońćzenia aktywności
        if(!VerificationMethods.isHourCorrect(scheduledActivity.endTime)){
            return 3;
        }
        if(Conversions.hourToMinutes(scheduledActivity.startTime) > Conversions.hourToMinutes(scheduledActivity.endTime)){
            return 4;
        }
        //Sprawdzenie czy przedział czasowy koliduje z innym - jeżeli jest to aktywność ciągłą
        if(!scheduledActivity.isSingular()){
            List<ScheduledActivity> activitiesBetween = scheduledActivityDao().getActivitiesBetween(scheduledActivity.dayOfWeek, scheduledActivity.startTime, scheduledActivity.endTime);
            for(ScheduledActivity activity: activitiesBetween){
                if(!activity.isSingular()){
                    return 5;
                }
            }
        }
        //Sprawdzenie czy kategoria wystepuje w bazie
        if(categoryDao().findByUid(scheduledActivity.categoryId).size() == 0){
            return 6;
        }


        //Dodanie pola do bazy
        scheduledActivityDao().insertAll(scheduledActivity);
        return 0;
    }


}
