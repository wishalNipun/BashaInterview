package com.Basha.Library.util.fuction;

import org.springframework.lang.Nullable;

public class CommonFunctions {
    public static boolean stringIsNullOrEmpty(@Nullable String string) {
        return string == null || string.isEmpty();
    }
}

