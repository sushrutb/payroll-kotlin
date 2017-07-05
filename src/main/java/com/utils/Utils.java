/**
 *
 */
package com.utils;

import com.Configuration;

import java.text.ParseException;
import java.util.Calendar;

/**
 * Provides utility functions.
 *
 * @author IamSB
 */
public class Utils {

    /**
     * Parse input date as per configured format.
     *
     * @param date - String date to parse.
     * @return - Parsed date.
     * @throws ParseException - If input date format does not match configuration.
     */
    public static Calendar parseInputDate(String date) throws ParseException {

        Calendar cal = Calendar.getInstance();
        cal.setTime(Configuration.INPUT_DATE_FORMAT.parse(date));
        return cal;
    }
}
