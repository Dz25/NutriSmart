package com.example.nutrismart.data.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Meals {
    @SerializedName("meals")
    public List<Meal> meals = null;
    public class Meal {
        @SerializedName("id")
        public long id;
        @SerializedName("title")
        public String title;
        @SerializedName("readyInMinutes")
        public int readyInMinutes;
        @SerializedName("sourceUrl")
        public String sourceUrl;
    }
}
