package com.example.nutrismart;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.nutrismart.ui.add.AddFragment;
import com.example.nutrismart.ui.home.HomeFragment;
import com.example.nutrismart.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.nutrismart.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        if(savedInstanceState == null) {
            getSupportFragmentManager().
                    beginTransaction().replace(R.id.content,new HomeFragment()).commit();
        }
        binding.navView.setBackground(null);
        binding.navView.getMenu().getItem(1).setEnabled(false);
        binding.navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
            }
        });


    }

}