package com.gigacal.utils;

public class StringUtil {

    public static boolean isEmpty(String s) {
        return (s == null || s.length() == 0);
    }

    public static boolean equals(String a, String b) {
        if (a == null) {
            return b == null || b.isEmpty();
        }
        else if (b == null) {
            return a.isEmpty();
        }
        else {
            return a.equals(b);
        }
    }
}
