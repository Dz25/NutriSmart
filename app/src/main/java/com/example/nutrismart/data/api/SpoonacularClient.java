package com.example.nutrismart.data.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpoonacularClient {

    private static Retrofit retrofit = null;

    static Retrofit getInstance() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
