package com.example.nutrismart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.example.nutrismart.ui.add.AddActivity;
import com.example.nutrismart.ui.home.HomeFragment;
import com.example.nutrismart.ui.profile.ProfileFragment;



import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.nutrismart.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //hide title bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //set default fragment
        if(savedInstanceState == null) {
            getSupportFragmentManager().
                    beginTransaction().replace(R.id.content,new HomeFragment()).commit();
        }
        //set background for bottom navigation
        binding.navView.setBackground(null);
        binding.navView.getMenu().getItem(1).setEnabled(false);
        //Navigation meunu
        binding.navView.setOnItemSelectedListener(item -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
           if (item.getItemId() == R.id.navigation_home){
               transaction.replace(binding.content.getId(),new HomeFragment()).commit();
               return true;
           } else if (item.getItemId() == R.id.navigation_profile) {
               transaction.replace(binding.content.getId(),new ProfileFragment()).commit();
               return true;
           }
            return false;
        });
        //FAB Add click event
        binding.fab.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), AddActivity.class);
            startActivity(i);
        });
    }

}