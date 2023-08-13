package com.example.nutrismart.ui.recipeDetail;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nutrismart.R;
import com.example.nutrismart.data.api.SpoonacularInterface;
import com.example.nutrismart.databinding.FragmentRecipeDetailBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeDetailFragment extends Fragment {

    private RecipeDetailViewModel mViewModel;
    private FragmentRecipeDetailBinding binding;
    private static String ID;


    public static RecipeDetailFragment newInstance() {
        return new RecipeDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_detail, container, false);
        View root = binding.getRoot();
        ID = getArguments().getString("ID");

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RecipeDetailViewModel.class);
        //mViewModel.getRecipeUrl();
        // TODO: Use the ViewModel
        //get URL
        String url = mViewModel.getRecipeUrl(ID);
        Picasso.get().load(url).placeholder(R.drawable.ic_placeholder).fit().into(binding.recipeCardImageView);
    }

}