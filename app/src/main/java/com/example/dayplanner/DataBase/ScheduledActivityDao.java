package com.example.dayplanner.DataBase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ScheduledActivityDao {

    @Query("select count(1=1)")
    int fakeRead();

    @Query("SELECT * FROM ScheduledActivity")
    List<ScheduledActivity> getAll();

    @Insert
    void insertAll(ScheduledActivity... scheduledActivities);

    @Query("SELECT * FROM ScheduledActivity WHERE activity_name = :activityName LIMIT 1")
    ScheduledActivity findByName(String activityName);

}
