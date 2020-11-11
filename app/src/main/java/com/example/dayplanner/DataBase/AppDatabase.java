package com.example.dayplanner.DataBase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(entities = {Category.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract CategoryDao categoryDao();

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
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                getInstance(context).categoryDao().insertAll(Category.populateData());
                            }
                        });
                    }
                })
                .build();
    }

}
