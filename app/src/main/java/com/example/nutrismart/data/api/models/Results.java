package com.example.nutrismart.data.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Results {
    @SerializedName("searchResults")
    List<SearchResult> searchResults;
    public class SearchResult{
        @SerializedName("name")
        String name;
        @SerializedName("results")
        List<Result> results;
    }
}
