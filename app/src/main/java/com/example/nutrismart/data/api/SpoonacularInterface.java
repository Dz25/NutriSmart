package com.example.nutrismart.data.api;

import com.example.nutrismart.data.api.models.Meals;
import com.example.nutrismart.data.api.models.RecipeCard;
import com.example.nutrismart.data.api.models.Results;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SpoonacularInterface {

    //Get Meal Plan
    @GET("/mealplanner/generate")
    @Headers("Content-Type: application/json")
    Call<Meals> getMealPlan(@Query("apiKey") String apiKey, @Query("timeFrame") String timeFrame, @Query("targetCalories") float targetCalories, @Query("diet") String diet, @Query("exclude") String exclude );

    //Search for food
    @GET("/food/search")
    @Headers("Content-Type: application/json")
    Call<Results> searchAllFood(@Query("apiKey") String apiKey, @Query("query") String query, @Query("number") int number);

    //Get Recipe Card
    @GET("/recipes/{id}/card")
    @Headers("Content-Type: application/json")
    Call<RecipeCard> getRecipeCard(@Query("apiKey") String apiKey, @Path("id") String id );
}
