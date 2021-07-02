package com.samsung.erp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samsung.erp.entities.Currency;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class HelperTest {
    @Test
    void getSlugFromText() {
        String slug = Helper.getSlugFromText("Doi   thay DOI khi ta doi thay ../12 ");

        assertTrue(slug != null);
        assertTrue(slug.equals("doi-thay-doi-khi-ta-doi-thay-12"));
//        System.out.println(slug);
    }

    @Test
    void test() {
        ObjectMapper objectMapper = new ObjectMapper();
        String currenciesString = "[" +
                "{\"code\":\"VND\",\"currency\":\"Vietnam Dong\",\"icon\":\"vnd\",\"rate\":23350.0}," +
                "{\"code\":\"USD\",\"currency\":\"US Dollar\",\"icon\":\"usd\",\"rate\":1.0}" + "]";


        try {
            List<Currency> currencies = objectMapper.readValue(currenciesString, new TypeReference<List<Currency>>(){});


            System.out.println(currencies.get(0).getCode());
            System.out.println(currencies.get(0).getCurrency());
            System.out.println(currencies.get(0).getIcon());
            System.out.println(currencies.get(0).getRate());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testRegex() {
        Pattern pattern = Pattern.compile("((Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) \\d{4})", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher("Apr 1988 lmn May 1967 xyz Dec 1953 kkk");

        if (matcher != null) {
            String newText = matcher.replaceAll("MMM YYYY");

            System.out.println(newText);
//            while (matcher.find()) {
//                System.out.println(matcher.group(0));
//                System.out.println(matcher.group(1));
//                System.out.println(matcher.group(2));
//            }
        }
        else {
            System.out.println("=== Not found");
        }
    }

    @Test
    void testRegex2() {
        String text = "[name:NYK][distance:1100][name:CLT][distance:2300][name:KTY][distance:3540]";
        String newText = text.replaceAll("\\[name:([A-Z]{3})]\\[distance:(\\d{4})]", "[$1:$2]");

        System.out.println(newText);
    }
}