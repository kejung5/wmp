package com.example.demo.utils;

public class StringUtil {

    public static String removeTag(String str) {
        return str.replaceAll("(<([^>]+)>)", "");
    }

    public static String getNumber(String str) {
        return str.replaceAll("[^0-9]", "");
    }

    public static String getEnglish(String str) {
        return str.replaceAll("[^a-zA-Z]", "");
    }

    public static String getNumberWithEnglish(String str) {
        return str.replaceAll("[^0-9a-zA-Z]", "");
    }

}
