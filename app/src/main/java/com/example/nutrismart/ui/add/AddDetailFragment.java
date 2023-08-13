package com.example.nutrismart.ui.add;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import com.example.nutrismart.MainActivity;
import com.example.nutrismart.R;
import com.example.nutrismart.data.api.SpoonacularClient;
import com.example.nutrismart.data.api.SpoonacularInterface;
import com.example.nutrismart.data.repo.NutritionRepo;
import com.example.nutrismart.databinding.FragmentAddDetailBinding;
import com.example.nutrismart.databinding.FragmentRecipeDetailBinding;
import com.example.nutrismart.ui.recipeDetail.RecipeDetailViewModel;
import com.example.nutrismart.utils.HtmlUtils;
import com.example.nutrismart.utils.NutritionUtils;

import org.jsoup.Jsoup;

import java.util.Map;

public class AddDetailFragment extends Fragment implements onWidgetLoaded {

    private NutritionRepo nutritionRepo;
    private RecipeDetailViewModel mViewModel;
    private FragmentAddDetailBinding binding;
    private static String ID;
    private static String itemType;
    private String htmlContent = "Default Content";
    private Map<String, String> nutrients;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_detail, container, false);
        View root = binding.getRoot();
        ID = getArguments().getString("ID");
        itemType = getArguments().getString("itemType");
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setDomStorageEnabled(true);
        binding.webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        nutritionRepo = new NutritionRepo(requireContext());
        nutritionRepo.getFoodNutritionWidget(itemType,ID, this);
        //Add
        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nutritionRepo.insertNutrition(Float.parseFloat(nutrients.get("calories")), Float.parseFloat(nutrients.get("proteinContent")), Float.parseFloat(nutrients.get("fatContent")), Float.parseFloat(nutrients.get("carbohydrateContent")));
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        //Cancel
        binding.fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to viewModel
                getParentFragmentManager().beginTransaction().remove(AddDetailFragment.this).commit();
            }
        });
        return root;
    }

    @Override
    public void onWidgetLoaded(String content) {
        htmlContent = content;
        Log.d("CHECK", "Called onWidget");
        binding.webView.loadDataWithBaseURL(null, HtmlUtils.extractStyleAndFormat(htmlContent),"text/html","UTF-8",null);
        Log.d("STRING",HtmlUtils.extractStyleAndFormat(htmlContent));
        nutrients = NutritionUtils.extractNutritionData(htmlContent);
    }
}