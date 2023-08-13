package com.example.nutrismart.data.repo;

import android.content.Context;
import android.util.Log;

import com.example.nutrismart.data.api.SpoonacularClient;
import com.example.nutrismart.data.api.SpoonacularInterface;
import com.example.nutrismart.data.api.models.RecipeCard;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeRepo {
    private SpoonacularInterface spoonacularInterface;
    private String key = "ee580c83cd6d45e5b345dc0c55251655";

    public RecipeRepo(Context context){
        spoonacularInterface = SpoonacularClient.getInstance().getAPI();
    }

    public String getRecipeCard(String id){
        final String[] url = new String[1];
        Call<RecipeCard> call = spoonacularInterface.getRecipeCard(id, key);
        call.enqueue(new Callback<RecipeCard>() {
            @Override
            public void onResponse(Call<RecipeCard> call, Response<RecipeCard> response) {
                RecipeCard recipeCard = response.body();
                url[0] = recipeCard.url;
                Log.d("HEREEEE", url[0]);
            }

            @Override
            public void onFailure(Call<RecipeCard> call, Throwable t) {
                Log.d("ERR",t.getCause().getMessage());
                call.cancel();
            }
        });
        return url[0];
    }
}
