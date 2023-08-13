package com.example.nutrismart.data.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "nutrition")
public class Nutrition {
    @Ignore
    public Nutrition(){}
    public Nutrition(String date, float calories, float protein, float carb, float fat) {
        this.date = date;
        this.calories = calories;
        this.protein = protein;
        this.carb = carb;
        this.fat = fat;
    }


    @PrimaryKey(autoGenerate = true)
    public int id;

    public String date;

    public float calories;

    public float protein;

    public float carb;

    public float fat;
}
