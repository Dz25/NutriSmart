package com.example.nutrismart.data.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Results {
    @SerializedName("searchResults")
    public List<SearchResult> searchResults;
    public class SearchResult{
        @SerializedName("name")
        public String name;
        @SerializedName("results")
        public List<Result> results;
    }
}
