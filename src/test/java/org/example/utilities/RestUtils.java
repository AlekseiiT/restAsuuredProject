package org.example.utilities;

import org.apache.commons.lang3.RandomStringUtils;

public class RestUtils {

    public static String genString() {
        return RandomStringUtils.randomAlphabetic(3, 20);
    }

    public static int genInt() {
        return Integer.parseInt(RandomStringUtils.randomNumeric(1, 20));
    }

}
