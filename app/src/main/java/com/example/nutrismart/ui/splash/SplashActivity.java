package com.example.nutrismart.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.nutrismart.MainActivity;
import com.example.nutrismart.databinding.ActivitySplashBinding;

import java.util.HashSet;
import java.util.Set;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        sharedPref = getSharedPreferences("com.example.nutrismart_preferences", MODE_PRIVATE);
        editor = sharedPref.edit();
        setContentView(root);

        //onClickListener for next button
        binding.buttonNext.setOnClickListener(v -> {
            Set<String> intolerances = new HashSet<>();
            Set<String> diets = new HashSet<>();

            String ageStr = binding.editTextAge.getText().toString();
            String heightStr = binding.editTextHeight.getText().toString();
            String weightStr = binding.editTextWeight.getText().toString();

            if (ageStr.equals("") || heightStr.equals("") || weightStr.equals("")){
                Toast.makeText(SplashActivity.this,"Please complete the form",Toast.LENGTH_SHORT).show();
                return;
            } else {
                editor.putString("age",ageStr);
                editor.putString("height",heightStr);
                editor.putString("weight", weightStr);
            }

            editor.putString("gender", binding.spinnerGender.getSelectedItem().toString());

            editor.putString("expenditure",binding.spinnerExpenditure.getSelectedItem().toString().toLowerCase().split(" ")[0]);

            if (binding.checkBoxEgg.isChecked()) intolerances.add("egg");
            if (binding.checkBoxFish.isChecked()) intolerances.add("fish");
            if (binding.checkBoxMilk.isChecked()) intolerances.add("milk");
            if (binding.checkBoxNut.isChecked()) intolerances.add("nut");
            if (binding.checkBoxShellfish.isChecked()) intolerances.add("shellfish");
            if (binding.checkBoxTreeNut.isChecked()) intolerances.add("treeNut");
            if (binding.checkBoxSoy.isChecked()) intolerances.add("soy");
            if (binding.checkBoxWheat.isChecked()) intolerances.add("wheat");
            if (binding.checkBoxSesame.isChecked()) intolerances.add("sesame");
            editor.putStringSet("intolerances",intolerances);

            if (binding.checkBoxGluten.isChecked()) diets.add("glutenFree");
            if (binding.checkBoxVegan.isChecked()) diets.add("vegan");
            if (binding.checkBoxVegeterian.isChecked()) diets.add("vegetarian");
            editor.putStringSet("diets",diets);

            editor.commit();

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //checking if launching the app for the first time
        if (sharedPref.getBoolean("firstTime",true)){
            editor.putBoolean("firstTime",false);
        } else {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

    }
}