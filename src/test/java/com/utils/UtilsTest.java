/**
 *
 */
package com.utils;

import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;

import static org.assertj.core.api.Assertions.*;

/**
 * Test: com.utils.Utils
 *
 * @author IamSB
 */
public class UtilsTest {

    /**
     * Test method for {@link com.utils.Utils#parseInputDate(String)} .
     */
    @Test
    public void testParseDate() {
        try {
            Calendar cal = Utils.parseInputDate("01 March 2012");
            assertThat(cal.get(Calendar.MONTH)).isEqualTo(2);
            assertThat(cal.get(Calendar.DAY_OF_MONTH)).isEqualTo(1);
            assertThat(cal.get(Calendar.YEAR)).isEqualTo(2012);
        } catch (ParseException e) {
            fail("Should not throw parse exception");
        }
    }

    /**
     * Test method for {@link com.utils.Utils#parseInputDate(String)} .
     */
    @Test
    public void testParseDateAbbreviatedMonth() {
        try {
            Calendar cal = Utils.parseInputDate("01 Mar 2012");
            assertThat(cal.get(Calendar.MONTH)).isEqualTo(2);
            assertThat(cal.get(Calendar.DAY_OF_MONTH)).isEqualTo(1);
            assertThat(cal.get(Calendar.YEAR)).isEqualTo(2012);
        } catch (ParseException e) {
            fail("Should not throw parse exception");
        }
    }

    /**
     * Invalid date formats.
     * <p>
     * Test method for {@link com.utils.Utils#parseInputDate(String)} .
     */
    @Test
    public void testParseDateIncorrectFormat() {

        // Missing year.
        assertThatThrownBy(() -> {
            Utils.parseInputDate("01 March");
        }).isInstanceOf(ParseException.class);
        // Spelling error in month name.
        assertThatThrownBy(() -> {
            Utils.parseInputDate("01 Marc 2012");
        }).isInstanceOf(ParseException.class);
    }

}
