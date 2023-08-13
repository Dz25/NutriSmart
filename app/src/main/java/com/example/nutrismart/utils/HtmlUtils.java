package com.example.nutrismart.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtils {
    public static String extractStyleAndFormat(String inputHTML) {
        Pattern stylePattern = Pattern.compile("<style>(.*?)</style>", Pattern.DOTALL);
        Matcher styleMatcher = stylePattern.matcher(inputHTML);

        StringBuilder headContent = new StringBuilder();
        StringBuilder bodyContent = new StringBuilder(inputHTML);

        while (styleMatcher.find()) {
            String styleTag = styleMatcher.group();
            String styleContent = styleMatcher.group(1);

            headContent.append(styleTag).append("\n");
            bodyContent = new StringBuilder(bodyContent.toString().replace(styleTag, ""));
        }

        // Clean up potential extra newlines
        headContent = new StringBuilder(headContent.toString().trim());
        bodyContent = new StringBuilder(bodyContent.toString().trim());

        // Create the final formatted HTML
        return "<html><head>" + headContent + "</head><body>" + bodyContent + "</body></html>";
    }
}
