package com.example.nutrismart.data.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.nutrismart.data.models.Nutrition;

import java.util.List;

@Dao
public interface NutritionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertNutrition(Nutrition nutrition);
    @Query("SELECT * FROM nutrition WHERE date =:date")
    public List<Nutrition> getNutritionByDate(String date);

}
