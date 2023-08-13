package com.example.nutrismart.data.api;

import com.example.nutrismart.data.api.models.Meals;
import com.example.nutrismart.data.api.models.RecipeCard;
import com.example.nutrismart.data.api.models.Results;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SpoonacularInterface {



    //Get Meal Plan
    @GET("/mealplanner/generate")
    @Headers("Content-Type: application/json")
    @JsonOrPojoConverterFactory.Json
    Call<Meals> getMealPlan(@Query("apiKey") String apiKey, @Query("timeFrame") String timeFrame, @Query("targetCalories") float targetCalories, @Query("diet") String diet, @Query("exclude") String exclude );

    //Search for food
    //Return 3 different kinds: menu items, products, recipe
    @GET("/food/search")
    @Headers("Content-Type: application/json")
    @JsonOrPojoConverterFactory.Json
    Call<Results> searchAllFood(@Query("apiKey") String apiKey, @Query("query") String query, @Query("number") int number);

    //Get Recipe Card
    @GET("/recipes/{id}/card")
    @Headers("Content-Type: application/json")
    @JsonOrPojoConverterFactory.Json
    Call<RecipeCard> getRecipeCard(@Path("id") String id, @Query("apiKey") String apiKey );

    //Get nutrition widget for recipes
    @GET("recipes/{id}/nutritionWidget")
    @Headers("Accept: text/html")
    @JsonOrPojoConverterFactory.Pojo
    Call<String> getRecipesNutritionWidget(@Path("id") String id, @Query("apiKey") String apiKey, @Query("defaultCss") boolean defaultCss);

    //Get nutrition widgets for menu items and products
    @GET("food/{foodType}/{id}/nutritionWidget")
    @Headers("Accept: text/html")
    @JsonOrPojoConverterFactory.Pojo
    Call<String> getFoodNutritionWidget(@Path("foodType") String foodType, @Path("id") String id, @Query("apiKey") String apiKey, @Query("defaultCss") boolean defaultCss);

}
