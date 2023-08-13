package com.example.nutrismart.data.repo;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.nutrismart.data.api.SpoonacularClient;
import com.example.nutrismart.data.api.SpoonacularInterface;
import com.example.nutrismart.data.database.NutritionDatabase;
import com.example.nutrismart.data.database.daos.NutritionDao;
import com.example.nutrismart.data.models.Nutrition;
import com.example.nutrismart.ui.add.onWidgetLoaded;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NutritionRepo {

    private String key = "ee580c83cd6d45e5b345dc0c55251655";
    private NutritionDao nutritionDao;
    private SpoonacularInterface spoonacularInterface;
    private LiveData<List<Nutrition>> nutritions;


    public NutritionRepo(Context context){
        NutritionDatabase db = NutritionDatabase.getDatabase(context);
        nutritionDao = db.nutritionDao();
        spoonacularInterface = SpoonacularClient.getInstance().getAPI();
    }

    //Insert
    public void insertNutrition(float calories, float protein, float carb, float fat){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Nutrition nutrition = new Nutrition(LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)),calories, protein, carb, fat);
                nutritionDao.insertNutrition(nutrition);
            }
        });
    }

    //TODO: Delete temp data for testing purpose
    public LiveData<List<Nutrition>> getNutritionByDate(String date){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                nutritions = nutritionDao.getNutritionByDate(date);
            }
        });
        return nutritions;
    }

    //return the number of deleted entity
    public void deleteNutritionById(long id){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Nutrition temp = nutritionDao.getNutritionById(id).getValue();

            }
        });
        Nutrition temp = nutritionDao.getNutritionById(id).getValue();
    }

    public void getFoodNutritionWidget(String itemType, String id, onWidgetLoaded onWidgetLoaded){
        Call<String> call;
        if (itemType.equalsIgnoreCase("recipes")){
            call  = spoonacularInterface.getRecipesNutritionWidget(id, key, true);
        }else{
            call = spoonacularInterface.getFoodNutritionWidget(itemType, id , key, true);
        }
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String res = response.body();
                    Log.d("INFO", "On Res is called");
                    onWidgetLoaded.onWidgetLoaded(res);

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    call.cancel();
                }
            });
    }



}
