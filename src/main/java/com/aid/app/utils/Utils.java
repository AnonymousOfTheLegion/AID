package com.aid.app.utils;

import com.aid.app.ParseException;

public class Utils {
    /**
     * Trim url by default delimiter ('/' character)
     * @param url - modified inside method, trims to substring from first delimiter to the end.
     * @return Substring before first delimiter character
     * @throws ParseException - if no delimiter found
     */
    public static String getElementAndTrim(StringBuilder url) throws ParseException {
        return getElementAndTrim(url, '/');
    }

    /**
     * Trims the string in StringBuilder
     * @param url - url to trim
     * @param delimiter - symbol-delimiter
     * @return Returns the last element after {@code delimiter}, the {@code url} string will be trimmed
     */
    public static String getElementAndTrim(StringBuilder url, char delimiter) throws ParseException {
        final int NOT_FOUND = -1;

        int posIndex = url.toString().indexOf(delimiter);
        if (posIndex != NOT_FOUND) {
            String result = url.substring(0, posIndex);
            url.delete(0, posIndex + 1);
            return result;
        }
        throw new ParseException("Could not find delimiter");
    }
}
