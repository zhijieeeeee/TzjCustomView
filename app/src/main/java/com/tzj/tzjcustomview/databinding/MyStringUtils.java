package com.tzj.tzjcustomview.databinding;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class MyStringUtils {

    public static String capitalize(final String word) {
        if (word.length() > 1) {
            return String.valueOf(word.charAt(0)).toUpperCase() + word.substring(1);
        }
        return word;
    }

    public static String toString(int i) {
        return String.valueOf(i);
    }
}
