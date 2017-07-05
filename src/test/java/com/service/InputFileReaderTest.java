/**
 *
 */
package com.service;

import com.domain.PayslipInputObject;
import com.exception.ValidationException;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Test: com.service.InputFileReader
 *
 * @author IamSB
 */
public class InputFileReaderTest {

    /**
     * Test method for
     * {@link com.service.InputFileReader#parseAndValidate(String)}
     * .
     */
    @Test
    public void testParseAndValidate() throws Exception {
        InputFileReader fileReader = new InputFileReader();
        String filename = getClass().getClassLoader().getResource("data/input.csv").getPath();
            List<PayslipInputObject> inputObjects = fileReader.parseAndValidate(filename);

            assertThat(inputObjects.size()).isEqualTo(2);

            PayslipInputObject firstRecord = inputObjects.get(0);
            assertThat(firstRecord.getFirstName()).isEqualTo("David");
            assertThat(firstRecord.getLastName()).isEqualTo("Rudd");
            assertThat(firstRecord.getAnnualSalary()).isEqualTo(60050);
            assertThat(firstRecord.getSuperRate()).isEqualTo(9f);
            assertThat(firstRecord.getEndDatePeriod()).isNotNull();
            assertThat(firstRecord.getEndDatePeriod().get(Calendar.DAY_OF_MONTH)).isEqualTo(31);
            assertThat(firstRecord.getEndDatePeriod().get(Calendar.MONTH)).isEqualTo(2);
            assertThat(firstRecord.getEndDatePeriod().get(Calendar.YEAR)).isEqualTo(2012);
            assertThat(firstRecord.getStartDatePeriod()).isNotNull();
            assertThat(firstRecord.getStartDatePeriod().get(Calendar.DAY_OF_MONTH)).isEqualTo(1);
            assertThat(firstRecord.getStartDatePeriod().get(Calendar.MONTH)).isEqualTo(2);
            assertThat(firstRecord.getStartDatePeriod().get(Calendar.YEAR)).isEqualTo(2012);

            PayslipInputObject secondRecord = inputObjects.get(1);
            assertThat(secondRecord.getFirstName()).isEqualTo("Ryan");
            assertThat(secondRecord.getLastName()).isEqualTo("Chen");
            assertThat(secondRecord.getAnnualSalary()).isEqualTo(120000);
            assertThat(secondRecord.getSuperRate()).isEqualTo(10f);
            assertThat(secondRecord.getEndDatePeriod()).isNotNull();
            assertThat(secondRecord.getEndDatePeriod().get(Calendar.DAY_OF_MONTH)).isEqualTo(31);
            assertThat(secondRecord.getEndDatePeriod().get(Calendar.MONTH)).isEqualTo(2);
            assertThat(secondRecord.getEndDatePeriod().get(Calendar.YEAR)).isEqualTo(2012);
            assertThat(secondRecord.getStartDatePeriod()).isNotNull();
            assertThat(secondRecord.getStartDatePeriod().get(Calendar.DAY_OF_MONTH)).isEqualTo(1);
            assertThat(secondRecord.getStartDatePeriod().get(Calendar.MONTH)).isEqualTo(2);
            assertThat(secondRecord.getStartDatePeriod().get(Calendar.YEAR)).isEqualTo(2012);
    }

    /**
     * Test method for
     * {@link com.service.InputFileReader#parseAndValidate(String)}
     * .
     */
    @Test
    public void testParseAndValidateInvalidEndDate() {
        InputFileReader fileReader = new InputFileReader();
        String filename = getClass().getClassLoader().getResource("data/input_invalid_date.csv").getPath();
        assertThatThrownBy(() -> {
            fileReader.parseAndValidate(filename);
        }).isInstanceOf(ValidationException.class).hasMessageContaining("line 2");
    }

    /**
     * Test method for
     * {@link com.service.InputFileReader#parseAndValidate(String)}
     * .
     */
    @Test
    public void testParseAndValidateEmptySalary() {
        InputFileReader fileReader = new InputFileReader();
        String filename = getClass().getClassLoader().getResource("data/input_empty_salary.csv").getPath();
        assertThatThrownBy(() -> {
            fileReader.parseAndValidate(filename);
        }).isInstanceOf(ValidationException.class).hasMessageContaining("Annual salary is empty");
    }

    /**
     * Test method for
     * {@link com.service.InputFileReader#parseAndValidate(String)}
     * .
     */
    @Test
    public void testParseAndValidateEmptySuperRate() {
        InputFileReader fileReader = new InputFileReader();
        String filename = getClass().getClassLoader().getResource("data/input_empty_sr.csv").getPath();
        assertThatThrownBy(() -> {
            fileReader.parseAndValidate(filename);
        }).isInstanceOf(ValidationException.class).hasMessageContaining("Super rate is empty");
    }

    /**
     * Test method for
     * {@link com.service.InputFileReader#parseAndValidate(String)}
     * .
     */
    @Test
    public void testParseAndValidateInvalidNumberOfParams() {
        InputFileReader fileReader = new InputFileReader();
        String filename = getClass().getClassLoader().getResource("data/input_invalid_number_of_params.csv").getPath();
        assertThatThrownBy(() -> {
            fileReader.parseAndValidate(filename);
        }).isInstanceOf(ValidationException.class).hasMessageContaining("Input record should have 5 values");
    }
}
