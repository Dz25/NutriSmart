package com.example.nutrismart.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.nutrismart.R;
import com.example.nutrismart.databinding.FragmentHomeBinding;
import com.example.nutrismart.utils.NutritionCalculator;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private int calorieNeed;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View root = binding.getRoot();
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("com.example.nutrismart_preferences", MODE_PRIVATE);
        int age = Integer.parseInt(sharedPref.getString("age","20"));
        float height = Float.parseFloat(sharedPref.getString("height", "150"));
        float weight = Float.parseFloat(sharedPref.getString("weight", "50"));
        String gender = sharedPref.getString("gender","male");
        String expend = sharedPref.getString("expenditure", "very");

        calorieNeed = NutritionCalculator.calculateCalorieNeed(age,height,weight,gender,expend);


        initChart();
        //TODO: Get data from viewModel
        showChart();

        return root;
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

        ArrayList<PieEntry> pieEntries = new ArrayList<>();

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