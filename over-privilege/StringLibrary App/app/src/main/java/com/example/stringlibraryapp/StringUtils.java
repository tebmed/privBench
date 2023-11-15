package com.example.stringlibraryapp;

public class StringUtils {
    public static String reverseString(String input) {
        return new StringBuilder(input).reverse().toString();
    }
}


