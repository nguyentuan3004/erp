package com.samsung.erp.utils;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class Helper {
    public static String getSlugFromText(String text) {
        if (text == null) return null;

        text = text.trim();

        text = text.toLowerCase(Locale.ROOT);
        text = Pattern.compile("\\s+").matcher(text).replaceAll("-");
        text = Pattern.compile("[^\\w-]").matcher(text).replaceAll("");

        return text;
    }
}