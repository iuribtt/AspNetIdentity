package com.hintdesk.android.aspnetwebapi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ServusKevin on 07.10.2017.
 */

public class StringUtil {
    public static boolean isNullOrWhitespace(String string) {
        return string == null || string.isEmpty() || string.trim().isEmpty();
    }

    public static boolean isEmailAddress(String string)
    {
        if (!isNullOrWhitespace(string))
        {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(string).matches();
        }
        else
            return false;
    }

    public static boolean isMatch(String regex,String text)
    {
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);
            return matcher.matches();
        } catch (RuntimeException e) {
            return false;
        }
    }
}
