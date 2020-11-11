package com.example.dayplanner.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {

    @Query("select count(1=1)")
    int fakeRead();

    @Query("SELECT * FROM Category")
    List<Category> getAll();

    @Query("SELECT * FROM Category WHERE category_name = :categoryName LIMIT 1")
    Category findByName(String categoryName);

    @Query("SELECT COUNT(*) FROM CATEGORY WHERE category_name = :categoryName OR icon_id = :iconId OR color_hex = :colorHex")
    int checkForDuplicates(String categoryName, int iconId, String colorHex);

    @Insert
    void insertAll(Category... categories);

    @Delete
    void delete(Category category);
}
