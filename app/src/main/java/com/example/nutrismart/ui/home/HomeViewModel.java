package com.example.nutrismart.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.nutrismart.data.models.Nutrition;
import com.example.nutrismart.data.repo.NutritionRepo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private final LiveData<List<Nutrition>> nutritionList;

    private NutritionRepo nutritionRepo;
    public HomeViewModel(Application application) {
       super(application);
       nutritionRepo = new NutritionRepo(application.getApplicationContext());
       nutritionList = nutritionRepo.getNutritionByDate(LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
    }

    public LiveData<List<Nutrition>> getNutritionList(){
        return nutritionList;
    }

}