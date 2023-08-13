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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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
    private int calorieNeed;
    private String diet;
    private String exclude;
    private List<Nutrition> nutritionList;
    private HomeViewModel homeViewModel;

    private ArrayList<PieEntry> pieEntries;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View root = binding.getRoot();

        //create viewModel
        homeViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(HomeViewModel.class);
//        homeViewModel.getNutritionList().observe(getViewLifecycleOwner(), _nutritionList -> {
//            nutritionList = new ArrayList<>(_nutritionList);
//            calculatePieChart();
//        });

        //populate rec meals
        spoonacularInterface = SpoonacularClient.getInstance().getAPI();
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("com.example.nutrismart_preferences", MODE_PRIVATE);
        int age = Integer.parseInt(sharedPref.getString("age","20"));
        float height = Float.parseFloat(sharedPref.getString("height", "150"));
        float weight = Float.parseFloat(sharedPref.getString("weight", "50"));
        String gender = sharedPref.getString("gender","male");
        String expend = sharedPref.getString("expenditure", "very");
        diet = String.join(",",sharedPref.getStringSet("diets",new HashSet<>(Arrays.asList("vegan"))));
        exclude = String.join(",",sharedPref.getStringSet("intolerances",new HashSet<>()));
        calorieNeed = NutritionUtils.calculateCalorieNeed(age,height,weight,gender,expend);

        //Chart
        initChart();
        //TODO: Get data from viewModel
        showChart();

        //Generate meal plan daily recycler view
        layoutManager = new LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false);
        binding.recyclerView.setLayoutManager(layoutManager);
        fetchMeals();


        return root;
    }

    private void calculatePieChart() {
        //process pieEntries then put it here
        binding.chart.notifyDataSetChanged();
    }

    //TODO: move this to ViewModel
    private void fetchMeals() {
        //TODO: move this to Nutrition Repo
        Call<Meals> call = spoonacularInterface.getMealPlan(key,"day",calorieNeed,diet,exclude);
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
        //using percentage as values instead of amount
        binding.chart.setUsePercentValues(true);

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

    }

    private void showChart(){

        pieEntries = new ArrayList<>();

        //initializing data


        //input data and fit data into pie chart entry

        //set color
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#1AA7EC"));
        colors.add(Color.parseColor("#FFFFFF"));

        //collecting the entries with label name
        PieDataSet pieDataSet = new PieDataSet(pieEntries,null);
        //setting text size of the value
        pieDataSet.setValueTextSize(12f);
        //providing color list for coloring different entries
        pieDataSet.setColors(colors);
        //grouping the data set from entry to chart
        PieData pieData = new PieData(pieDataSet);
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(true);
        //set word wrap
        binding.chart.getLegend().setWordWrapEnabled(true);

        binding.chart.setNoDataText("No data exists!");

        //binding.chart.setData(pieData);
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