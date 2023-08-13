package com.example.nutrismart.data.api.models;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("id")
    public long id;
    @SerializedName("name")
    public String name;
    @SerializedName("image")
    public String image;
    @SerializedName("link")
    public String link;
    public String itemType;
}
