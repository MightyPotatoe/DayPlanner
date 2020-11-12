package com.example.dayplanner.DataBase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.DayOfWeek;
import java.util.Objects;

@Entity
public class ScheduledActivity {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "activity_name")
    public String name;

    @ColumnInfo(name = "day_of_week")
    public String dayOfWeek;

    @ColumnInfo(name = "starting_time")
    public String startTime;

    @ColumnInfo(name = "ending_time")
    public String endTime;

    @ColumnInfo(name = "category_id")
    public int categoryId;

    public ScheduledActivity(String name, String dayOfWeek, String startTime, String endTime, int categoryId) {
        this.name = name;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.categoryId = categoryId;
    }

    public static ScheduledActivity[] populateData() {
        return new ScheduledActivity[] {
                new ScheduledActivity("Pobudka", DayOfWeek.MONDAY.toString(), "06:30", "06:45", 7),
                new ScheduledActivity("Prysznic", DayOfWeek.MONDAY.toString(),"06:45", "7:00", 1),
                new ScheduledActivity("Praca", DayOfWeek.MONDAY.toString(), "07:00","15:00", 2),
                new ScheduledActivity("Spacer z psem", DayOfWeek.MONDAY.toString(), "15:00", "17:00", 3),
                new ScheduledActivity("Nauka/Projekty", DayOfWeek.MONDAY.toString(), "17:00", "19:00", 4),
                new ScheduledActivity("Rozrywka", DayOfWeek.MONDAY.toString(), "19:00", "22:30", 5),
                new ScheduledActivity("Medytacja", DayOfWeek.MONDAY.toString(), "22:30", "23:00", 6),
                new ScheduledActivity("Spanko", DayOfWeek.MONDAY.toString(), "23:00", null, 7)
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduledActivity that = (ScheduledActivity) o;
        return categoryId == that.categoryId &&
                Objects.equals(name, that.name) &&
                Objects.equals(dayOfWeek, that.dayOfWeek) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dayOfWeek, startTime, endTime, categoryId);
    }
}
