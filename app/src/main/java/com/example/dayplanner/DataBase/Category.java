package com.example.dayplanner.DataBase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.dayplanner.R;

import java.util.Objects;

@Entity
public class Category {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "category_name")
    public String name;

    @ColumnInfo(name = "icon_id")
    public int iconId;

    @ColumnInfo(name = "color_hex")
    public String colorHex;

    public Category(String name, int iconId, String colorHex) {
        this.name = name;
        this.iconId = iconId;
        this.colorHex = colorHex;
    }

    public static Category[] populateData() {
        return new Category[] {
                new Category("HYGIENE", R.drawable.bath_icon, "#448AFF"),
                new Category("WORK", R.drawable.work_icon, "#9C27B0"),
                new Category("ANIMALS", R.drawable.dog_icon, "#3F51B5"),
                new Category("LEARNING", R.drawable.book_icon, "#E91E63"),
                new Category("GAMES", R.drawable.game_icon, "#4CAF50"),
                new Category("SPORTS", R.drawable.sport_icon, "#009688"),
                new Category("REST", R.drawable.sleep_icon, "#1021EA")
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return iconId == category.iconId &&
                Objects.equals(name, category.name) &&
                Objects.equals(colorHex, category.colorHex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, iconId, colorHex);
    }
}

