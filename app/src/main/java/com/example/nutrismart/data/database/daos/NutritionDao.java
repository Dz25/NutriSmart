package com.example.nutrismart.data.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.nutrismart.data.models.Nutrition;

@Dao
public interface NutritionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertNutrition(Nutrition nutrition);


}
