package com.example.nutrismart.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

public class NutritionUtils {

    public static int calculateCalorieNeed(int age, float height, float weight, String gender, String expend) {
//        Map<String,Float> multilplier = new HashMap<String, Float>() {{
//            put("sedentary ", 1.2f);
//            put("lightly", 1.375f);
//            put("c", 1.55f);
//            put("very", 1.725f);
//            put("extremely", 1.9f);
//        }};
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
        return (int) calories;
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
