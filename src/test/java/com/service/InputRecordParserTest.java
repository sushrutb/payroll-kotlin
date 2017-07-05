/**
 *
 */
package com.service;

import com.domain.PayslipInputObject;
import com.exception.ValidationException;
import org.junit.Test;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.fail;

/**
 * Test : com.service.InputRecordParser
 *
 * @author IamSB
 */
public class InputRecordParserTest {

    private InputRecordParser parser = new InputRecordParser();

    /**
     * Test: parsing of valid start and end date.<br>
     * <p>
     * Test method for
     */
    @Test
    public void testStartAndEndDateParsing() {
        PayslipInputObject inputObject = new PayslipInputObject("Sushrut", "Bidwai");
        inputObject.setPayPeriod("01 March - 31 March");
        try {
            parser.findStartAndEndDatePeriod(inputObject);
        } catch (ValidationException e) {
            fail("Should not throw validation exception. " + e.getMessage());
        }
        assertThat(inputObject.getStartDatePeriod()).isNotNull();
        assertThat(inputObject.getEndDatePeriod()).isNotNull();

        Calendar startDate = inputObject.getStartDatePeriod();
        assertThat(startDate.get(Calendar.DAY_OF_MONTH)).isEqualTo(1);
        assertThat(startDate.get(Calendar.MONTH)).isEqualTo(2);
        assertThat(startDate.get(Calendar.YEAR)).isEqualTo(2012);

        Calendar endDate = inputObject.getEndDatePeriod();
        assertThat(endDate.get(Calendar.DAY_OF_MONTH)).isEqualTo(31);
        assertThat(endDate.get(Calendar.MONTH)).isEqualTo(2);
        assertThat(endDate.get(Calendar.YEAR)).isEqualTo(2012);
    }

    /**
     * Test: Wrong start date format.<br>
     * <p>
     * Test method for
     */
    @Test
    public void testStartAndendDateParsingWithIncorrectStartDateFormat() {
        PayslipInputObject inputObject = new PayslipInputObject("Sushrut", "Bidwai");
        inputObject.setPayPeriod("01 Marc - 31 March");

        assertThatThrownBy(() -> {
            parser.findStartAndEndDatePeriod(inputObject);
        }).isInstanceOf(ValidationException.class).hasMessageContaining("Start");
    }

    /**
     * Test: Wrong end date format. <br>
     * <p>
     * Test method for
     */
    @Test
    public void testStartAndendDateParsingWithIncorrectEndDateFormat() {
        InputRecordParser parser = new InputRecordParser();
        PayslipInputObject inputObject = new PayslipInputObject("Sushrut", "Bidwai");
        inputObject.setPayPeriod("01 March - March 31");

        assertThatThrownBy(() -> {
            parser.findStartAndEndDatePeriod(inputObject);
        }).isInstanceOf(ValidationException.class).hasMessageContaining("End");
    }
}
