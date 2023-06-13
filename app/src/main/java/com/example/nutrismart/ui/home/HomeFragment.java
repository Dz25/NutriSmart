package com.example.nutrismart.ui.home;

import android.os.Bundle;
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
import com.github.mikephil.charting.animation.Easing;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View root = binding.getRoot();



        return root;
    }
    //Pie chart init
    private void initPieChart() {
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
        binding.chart.setHighlightPerTapEnabled(true);
        //adding animation so the entries pop up from 0 degree
        binding.chart.animateY(1500, Easing.EaseInOutQuad);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}