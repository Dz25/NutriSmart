package com.example.nutrismart.utils;

import java.util.HashMap;
import java.util.Map;

public class CalorieCalculator {
    private static final Map<String, Float> multilplier = new HashMap<String, Float>() {{
        put("Sedentary ", 1.2f);
        put("Lightly", 1.375f);
        put("Moderately", 1.55f);
        put("Very", 1.725f);
        put("Extremely", 1.9f);
    }};

    public static float calorieNeed(int age, int height, float weight, String gender, String expend) {
        float calories = (float) ((10 * weight) + (6.25 * height) - (5 * age));
        float mtpl = multilplier.get(expend);
        if (gender.equals("male")) {
            calories += 5;
        } else {
            calories -= 161;
        }
        calories = Math.round(calories * mtpl);
        return calories;
    }
}
