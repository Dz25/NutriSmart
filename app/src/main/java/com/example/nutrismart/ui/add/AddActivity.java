package com.example.nutrismart.ui.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.nutrismart.databinding.ActivityAddBinding;

//TODO: Make this into an activity
public class AddActivity extends AppCompatActivity {

    private ActivityAddBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddViewModel addViewModel =
                new ViewModelProvider(this).get(AddViewModel.class);

        binding = ActivityAddBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }


}