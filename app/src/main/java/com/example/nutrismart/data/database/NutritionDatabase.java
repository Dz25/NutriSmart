package com.example.nutrismart.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.nutrismart.data.database.daos.NutritionDao;
import com.example.nutrismart.data.models.Nutrition;

@Database(entities = {Nutrition.class}, version = 1, exportSchema = false)
public abstract class NutritionDatabase extends RoomDatabase {
    public abstract NutritionDao nutritionDao();
}
