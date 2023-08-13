package com.example.nutrismart.data.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpoonacularClient {

    private static SpoonacularClient instance = null;
    private SpoonacularInterface spoonacularInterface;

    private SpoonacularClient() {
        //TODO: make it into multi converter
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

         Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com")
                .addConverterFactory(new JsonOrPojoConverterFactory())
                .client(client)
                .build();
         spoonacularInterface = retrofit.create(SpoonacularInterface.class);
    }

    public static synchronized SpoonacularClient getInstance(){
        if (instance == null) {
            instance = new SpoonacularClient();
        }
        return instance;
    }

    public SpoonacularInterface getAPI(){
        return spoonacularInterface;
    }

}
