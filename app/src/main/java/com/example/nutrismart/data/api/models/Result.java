package com.example.nutrismart.data.api.models;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("id")
    long id;
    @SerializedName("name")
    String name;
    @SerializedName("image")
    String image;
    @SerializedName("link")
    String link;
}
