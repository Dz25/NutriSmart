package com.example.nutrismart.ui.recipeDetail;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.nutrismart.data.repo.RecipeRepo;

public class RecipeDetailViewModel extends AndroidViewModel {

    private RecipeRepo recipeRepo;
    public RecipeDetailViewModel(Application application) {
        super(application);
        recipeRepo = new RecipeRepo(application.getApplicationContext());
    }
    public String getRecipeUrl(String id){
        return recipeRepo.getRecipeCard(id);
    }
}