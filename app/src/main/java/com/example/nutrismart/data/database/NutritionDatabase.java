package com.example.nutrismart.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.nutrismart.data.database.daos.NutritionDao;
import com.example.nutrismart.data.models.Nutrition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Nutrition.class}, version = 1, exportSchema = false)
public abstract class NutritionDatabase extends RoomDatabase {
    public abstract NutritionDao nutritionDao();
    private static volatile NutritionDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static NutritionDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NutritionDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    NutritionDatabase.class, "nutrition_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
