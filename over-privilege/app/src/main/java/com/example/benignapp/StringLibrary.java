package com.example.benignapp;

public class StringLibrary {
    public static String reverseString(String input) {
        return new StringBuilder(input).reverse().toString();
    }
}
