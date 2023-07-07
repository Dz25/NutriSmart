package com.example.nutrismart.ui.add;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutrismart.data.api.SpoonacularClient;
import com.example.nutrismart.data.api.SpoonacularInterface;
import com.example.nutrismart.data.api.models.Result;
import com.example.nutrismart.data.api.models.Results;
import com.example.nutrismart.databinding.ActivityAddBinding;
import com.example.nutrismart.ui.recyclerView.searchRes.SearchResAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//TODO: Make this into an activity
public class AddActivity extends AppCompatActivity {

    private ActivityAddBinding binding;
    private SpoonacularInterface spoonacularInterface;
    private String key = "ee580c83cd6d45e5b345dc0c55251655";
    private List<Result> searchResultList = new ArrayList<>();

    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        spoonacularInterface = SpoonacularClient.getInstance().getAPI();

        //recycler view
        layoutManager = new LinearLayoutManager(this);
        binding.recyclerSearchRes.setLayoutManager(layoutManager);

        binding.searchView.clearFocus();
        //Listen to text and make API call
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //make API call here
                if (newText.length() > 3){
                    fetchRes(newText);
                    return true;
                }
                return false;
            }
        });
    }

    public void fetchRes(String newText){
        Call<Results> call = spoonacularInterface.searchAllFood(key,newText,7);
        call.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                Log.d("NCall", String.valueOf(response.code()));
                Results results = response.body();
                processRes(results);

            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void processRes(Results results) {
        for (int i = 0; i < results.searchResults.size(); i++){
            String name = results.searchResults.get(i).name;
            Log.d("NCall", name);
            if (name.equalsIgnoreCase("Recipes") || name.equalsIgnoreCase("Products") || name.equalsIgnoreCase("Menu Items")){
                for (int j = 0; j < results.searchResults.get(i).results.size(); j++){
                    Result result = results.searchResults.get(i).results.get(j);
                    Log.d("INFO", result.name);
                    searchResultList.add(result);
                }
            }
        }
        //set adapter
        adapter = new SearchResAdapter(AddActivity.this, searchResultList);
        binding.recyclerSearchRes.setAdapter(adapter);
    }

}