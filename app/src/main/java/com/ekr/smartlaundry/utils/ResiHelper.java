package com.ekr.smartlaundry.utils;

import java.util.Random;

public class ResiHelper {
    public static String getRandomString(int i) {
        final String characters = "ABCDEFqrstuvGHIPQRSTUVWXYZ12345678JKLMNO90abcdklmnopwxyz";
        StringBuilder result = new StringBuilder();
        while (i > 0) {
            Random random = new Random();
            result.append(characters.charAt(random.nextInt(characters.length())));
            i--;
        }
        return result.toString();
    }
}
