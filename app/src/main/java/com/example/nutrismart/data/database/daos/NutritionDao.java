package com.example.nutrismart.data.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
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
    public LiveData<List<Nutrition>> getNutritionByDate(String date);
    @Query("SELECT * FROM nutrition WHERE id=:id")
    public LiveData<Nutrition> getNutritionById(long id);
    @Delete
    public int deleteNutrition(Nutrition nutrition);


}
