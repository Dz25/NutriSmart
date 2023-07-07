package com.example.nutrismart.utils;

import java.util.HashMap;
import java.util.Map;

public class NutritionCalculator {
    private static final Map<String, Float> multilplier = new HashMap<String, Float>() {{
        put("sedentary ", 1.2f);
        put("lightly", 1.375f);
        put("moderately", 1.55f);
        put("very", 1.725f);
        put("extremely", 1.9f);
    }};

    public static int calculateCalorieNeed(int age, float height, float weight, String gender, String expend) {
        float calories = (float) ((10 * weight) + (6.25 * height) - (5 * age));
        float mtpl = multilplier.get(expend);
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
}
