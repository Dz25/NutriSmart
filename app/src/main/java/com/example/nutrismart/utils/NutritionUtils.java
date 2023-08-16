package com.example.nutrismart.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

public class NutritionUtils {

    public static float calculateCalorieNeed(int age, float height, float weight, String gender, String expend) {

        float calories = (float) ((10 * weight) + (6.25 * height) - (5 * age));
        float mtpl;
        switch (expend){
            case "sedentary" : mtpl = 1.2f;
            case "lightly" : mtpl = 1.375f;
            case "moderately" : mtpl = 1.55f;
            case "very" : mtpl = 1.725f;
            case "extremely" : mtpl = 1.9f;
            default: mtpl = 1.0f;
        }
        if (gender.equals("male")) {
            calories += 5;
        } else {
            calories -= 161;
        }
        calories = Math.round(calories * mtpl);
        return  calories;
    }

    public static Map<String, Float> calculateMacronutrients(float totalCalories) {
        Map<String, Float> macronutrients = new HashMap<>();

        // Calculate macronutrient distribution percentages
        float carbPercentage = 0.50f;
        float proteinPercentage = 0.25f;
        float fatPercentage = 0.25f;

        // Calculate grams of each macronutrient
        float carbGrams = (carbPercentage * totalCalories) / 4;
        float proteinGrams = (proteinPercentage * totalCalories) / 4;
        float fatGrams = (fatPercentage * totalCalories) / 9;

        // Populate the map
        macronutrients.put("carbs", carbGrams);
        macronutrients.put("proteins", proteinGrams);
        macronutrients.put("fats", fatGrams);

        return macronutrients;
    }


    public static float calculateBMI(float height, float weight){
        return Math.round(weight/(height * height ));
    }

    public static Map<String,String> extractNutritionData(String htmlContent){
        Map<String,String> extractedData = new HashMap<>();

        Document doc = Jsoup.parse(htmlContent);

        Elements nutritionItems = doc.select("[itemprop]");

        for (Element item : nutritionItems) {
            String propName = item.attr("itemprop");
            String propValue = item.text().replaceAll("[^0-9]", "");
            extractedData.put(propName, propValue);
        }

        return extractedData;
    }
}
