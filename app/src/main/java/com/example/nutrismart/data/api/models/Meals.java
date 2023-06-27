package com.example.nutrismart.data.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Meals {
    @SerializedName("meals")
    List<Meal> meals = null;
    public class Meal {
        @SerializedName("id")
        long id;
        @SerializedName("title")
        String title;
        @SerializedName("readyInMinutes")
        int readyInMinutes;
        @SerializedName("sourceUrl")
        String sourceUrl;
    }
}
