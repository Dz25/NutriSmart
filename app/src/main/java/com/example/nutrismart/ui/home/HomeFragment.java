package com.example.nutrismart.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutrismart.R;
import com.example.nutrismart.data.api.SpoonacularClient;
import com.example.nutrismart.data.api.SpoonacularInterface;
import com.example.nutrismart.data.api.models.Meals;
import com.example.nutrismart.data.models.Nutrition;
import com.example.nutrismart.databinding.FragmentHomeBinding;
import com.example.nutrismart.ui.recyclerView.recipeItem.RecipeItemAdapter;
import com.example.nutrismart.utils.NutritionUtils;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private SpoonacularInterface spoonacularInterface;
    private String key = "ee580c83cd6d45e5b345dc0c55251655";
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    private List<Meals.Meal> mealResList = new ArrayList<>();
    private float goalCalorie;
    private String diet;
    private String exclude;
    private List<Nutrition> nutritionList = new ArrayList<>();
    private Map<String, Float> macronutrients = new HashMap<>();
    private Map<String, Float> nutritionMap = new HashMap<>();
    private HomeViewModel homeViewModel;
    private ArrayList<PieEntry> pieEntries = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getNutritionList().observe(getViewLifecycleOwner(), _nutritionList -> {
            nutritionList = _nutritionList;
            initChart();
            calculatePieChart();
            showProgressBar();
            showChart();
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View root = binding.getRoot();

        //populate rec meals
        spoonacularInterface = SpoonacularClient.getInstance().getAPI();
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("com.example.nutrismart_preferences", MODE_PRIVATE);

        //get Pref
        int age = Integer.parseInt(sharedPref.getString("age","20"));
        float height = Float.parseFloat(sharedPref.getString("height", "150"));
        float weight = Float.parseFloat(sharedPref.getString("weight", "50"));
        String gender = sharedPref.getString("gender","male");
        String expend = sharedPref.getString("expenditure", "very");
        diet = String.join(",",sharedPref.getStringSet("diets",new HashSet<>(Arrays.asList("vegan"))));
        exclude = String.join(",",sharedPref.getStringSet("intolerances",new HashSet<>()));
        goalCalorie = NutritionUtils.calculateCalorieNeed(age,height,weight,gender,expend);
        macronutrients = NutritionUtils.calculateMacronutrients(goalCalorie);
        Log.d("info", macronutrients.get("carbs").toString());
        Log.d("info", macronutrients.get("proteins").toString());
        Log.d("info", macronutrients.get("fats").toString());
        //set Max
        binding.carbsBar.setMax(Math.round(macronutrients.get("carbs")));
        binding.proteinsBar.setMax(Math.round(macronutrients.get("proteins")));
        binding.fatsBar.setMax(Math.round(macronutrients.get("fats")));

//        //Pie Chart
//        initChart();
//        //TODO: Get data from viewModel
//        calculatePieChart();
//        showChart();
//
//        //Progress bar
//        showProgressBar();

        //Generate meal plan daily recycler view
        layoutManager = new LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false);
        binding.recyclerView.setLayoutManager(layoutManager);
        fetchMeals();


        return root;
    }

    private void showProgressBar() {

        //setProgress
        float carbsLeft = Math.round(macronutrients.get("carbs") - nutritionMap.get("carb"));
        float proteinsLeft = Math.round(macronutrients.get("proteins") - nutritionMap.get("protein")) ;
        float fatsLeft = Math.round(macronutrients.get("fats") - nutritionMap.get("fat"));

        if (carbsLeft >= 0){
            binding.carbsBar.setProgress(Math.round(nutritionMap.get("carb")));
            binding.carbsLeft.setText(carbsLeft + "g left");
        }else{
            binding.carbsBar.setProgress(binding.carbsBar.getMax());
            binding.carbsLeft.setText(Math.abs(carbsLeft) + "g over");
        }
        if (proteinsLeft >= 0){
            binding.proteinsBar.setProgress(Math.round(nutritionMap.get("protein")));
            binding.proteinsLeft.setText(proteinsLeft + "g left");
        }else{
            binding.proteinsBar.setProgress(binding.proteinsBar.getMax());
            binding.proteinsLeft.setText(Math.abs(proteinsLeft) + "g over");
        }
        if (fatsLeft >= 0){
            binding.fatsBar.setProgress(Math.round(nutritionMap.get("fat")));
            binding.fatsLeft.setText(fatsLeft + "g left");
        }else{
            binding.fatsBar.setProgress(binding.fatsBar.getMax());
            binding.fatsLeft.setText(Math.abs(fatsLeft) + "g over");
        }
    }

    private void calculatePieChart() {
        //process pieEntries then put it here

        nutritionMap.put("calories", 0f);
        nutritionMap.put("protein",0f);
        nutritionMap.put("carb", 0f);
        nutritionMap.put("fat",0f);

       for (int i = 0; i < nutritionList.size(); i++){
           nutritionMap.put("calories",nutritionMap.get("calories") + nutritionList.get(i).calories);
           nutritionMap.put("protein",nutritionMap.get("protein") + nutritionList.get(i).protein);
           nutritionMap.put("carb",nutritionMap.get("carb") + nutritionList.get(i).carb);
           nutritionMap.put("fat",nutritionMap.get("fat") + nutritionList.get(i).fat);
       }

    }

    //TODO: move this to ViewModel
    private void fetchMeals() {
        //TODO: move this to Nutrition Repo
        Call<Meals> call = spoonacularInterface.getMealPlan(key,"day",goalCalorie,diet,exclude);
        call.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(Call<Meals> call, Response<Meals> response) {
                Meals meals = response.body();
                processRes(meals);
            }

            @Override
            public void onFailure(Call<Meals> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void processRes(Meals meals) {
        //TODO: move this to viewModel
        mealResList.clear();
        for (int i =0; i < meals.meals.size(); i++){
            mealResList.add(meals.meals.get(i));
            Log.d("INFO", meals.meals.get(i).title);
        }
        adapter = new RecipeItemAdapter(requireContext(),mealResList);
        binding.recyclerView.setAdapter(adapter);
    }


    //Pie chart init
    private void initChart() {
        //remove the description label on the lower left corner, default true if not set
        binding.chart.getDescription().setEnabled(false);

        //enabling the user to rotate the chart, default true
        binding.chart.setRotationEnabled(true);
        //adding friction when rotating the pie chart
        binding.chart.setDragDecelerationFrictionCoef(0.9f);
        //setting the first entry start from right hand side, default starting from top
        binding.chart.setRotationAngle(0);

        //highlight the entry when it is tapped, default true if not set
        binding.chart.setHighlightPerTapEnabled(false);
        //adding animation so the entries pop up from 0 degree
        binding.chart.animateY(1500, Easing.EaseInOutQuad);

        binding.chart.setCenterTextSize(32);

    }

    private void showChart(){

        //initializing data
        //input data and fit data into pie chart entry
        float needCalorie = goalCalorie - nutritionMap.get("calories");
        if (needCalorie > 0){
            pieEntries.add(new PieEntry(needCalorie,"needed"));
            pieEntries.add(new PieEntry(nutritionMap.get("calories"),"now"));
            binding.chart.setCenterText(needCalorie + " kcal left!");
        }else{
            pieEntries.add(new PieEntry(goalCalorie,"now"));
            binding.chart.setCenterText(needCalorie + " kcal over!");
        }


        //set color
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#A9A9A9"));
        colors.add(Color.parseColor("#1AA7EC"));


        //collecting the entries with label name
        PieDataSet pieDataSet = new PieDataSet(pieEntries,null);
        //setting text size of the value
        pieDataSet.setValueTextSize(12f);
        //providing color list for coloring different entries
        pieDataSet.setColors(colors);
        //grouping the data set from entry to chart
        PieData pieData = new PieData(pieDataSet);
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(false);
        //set word wrap
        binding.chart.getLegend().setWordWrapEnabled(true);

        binding.chart.getLegend().setEnabled(false);
        binding.chart.setNoDataText("No data exists!");
        binding.chart.setData(pieData);
        binding.chart.setDrawEntryLabels(false);
        binding.chart.notifyDataSetChanged();
        binding.chart.invalidate();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}