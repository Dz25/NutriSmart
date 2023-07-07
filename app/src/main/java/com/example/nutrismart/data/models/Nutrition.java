package com.example.nutrismart.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "nutrition")
public class Nutrition {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String date;

    public float calories;

    public float protein;

    public float carb;

    public float fat;
}
