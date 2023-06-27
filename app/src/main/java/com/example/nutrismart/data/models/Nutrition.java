package com.example.nutrismart.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Nutrition {
    @PrimaryKey(autoGenerate = true)
    int id;

    float calories;

    float protein;

    float carb;

    float fat;
}
