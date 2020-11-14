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

    @Query("SELECT * FROM Category WHERE category_name LIKE :categoryName LIMIT 1")
    Category findByName(String categoryName);

    @Query("SELECT * FROM Category WHERE uid = :categoryId")
    List<Category> findByUid(int categoryId);

    @Query("SELECT COUNT(*) FROM CATEGORY WHERE category_name LIKE :categoryName OR icon_id LIKE :iconId OR color_hex LIKE :colorHex")
    int checkForDuplicates(String categoryName, int iconId, String colorHex);

    @Insert
    void insertAll(Category... categories);

    @Delete
    void delete(Category category);
}
