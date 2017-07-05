/**
 *
 */
package com.service;

import com.domain.PayslipInputObject;
import com.exception.ValidationException;
import com.helper.Helper;
import com.utils.Utils;
import org.junit.Test;

import java.text.ParseException;

import static org.assertj.core.api.Assertions.*;

/**
 * Test : com.service.PayslipInputValidator
 *
 * @author IamSB
 */
public class PayslipInputValidatorTest {

    private PayslipInputValidator validator = new PayslipInputValidator();

    /**
     * Test method for
     * {@link com.service.PayslipInputValidator#validate(com.domain.PayslipInputObject)}
     * .
     */
    @Test
    public void testValidateWithValidData() {
        PayslipInputObject inputObject = Helper.populatePayslipObject();
        try {
            Boolean valid = validator.validate(inputObject);
            assertThat(valid).isTrue();
        } catch (ValidationException e) {
            fail("should not throw validation exception");
        }
    }

    /**
     * Test method for
     * {@link com.service.PayslipInputValidator#validate(com.domain.PayslipInputObject)}
     * .
     */
    @Test
    public void testValidateWithEndDateBeforeStartDate() {
        PayslipInputObject inputObject = Helper.populatePayslipObject();
        try {
            inputObject.setStartDatePeriod(Utils.parseInputDate("31 March 2012"));
            inputObject.setEndDatePeriod(Utils.parseInputDate("1 March 2012"));
        } catch (ParseException e1) {
            fail("Should not throw ParseException");
        }
        assertThatThrownBy(() -> {
            validator.validate(inputObject);
        }).isInstanceOf(ValidationException.class).hasMessageContaining("End date must be before start date");
    }

    /**
     * Test: Missing first name.
     * <p>
     * Test method for
     * {@link com.service.PayslipInputValidator#validate(com.domain.PayslipInputObject)}
     * .
     */
    @Test
    public void testValidateMissingFirstName() {
        PayslipInputObject inputObject = Helper.populatePayslipObject();
        final PayslipInputObject inputObjectCopy = inputObject.copy("", "");
        assertThatThrownBy(() -> {
            validator.validate(inputObjectCopy);
        }).isInstanceOf(ValidationException.class).hasMessageContaining("First name");
    }

    /**
     * Test: Missing last name.
     * <p>
     * Test method for
     * {@link com.service.PayslipInputValidator#validate(com.domain.PayslipInputObject)}
     * .
     */
    @Test
    public void testValidateMissingLastName() {
        PayslipInputObject inputObject = Helper.populatePayslipObject();
        final PayslipInputObject inputObjectCopy = inputObject.copy(inputObject.getFirstName(), "");

        assertThatThrownBy(() -> {
            validator.validate(inputObjectCopy);
        }).isInstanceOf(ValidationException.class).hasMessageContaining("Last name");
    }

    /**
     * Test: Negative annual salary.
     * <p>
     * Test method for
     * {@link com.service.PayslipInputValidator#validate(com.domain.PayslipInputObject)}
     * .
     */
    @Test
    public void testValidateNegativeAnnualSalary() {
        PayslipInputObject inputObject = Helper.populatePayslipObject();
        inputObject.setAnnualSalary(-100);
        assertThatThrownBy(() -> {
            validator.validate(inputObject);
        }).isInstanceOf(ValidationException.class).hasMessageContaining("salary");
    }

    /**
     * Test: Super rate > 50%.
     * <p>
     * Test method for
     * {@link com.service.PayslipInputValidator#validate(com.domain.PayslipInputObject)}
     * .
     */
    @Test
    public void testValidateSuperRateGreaterThan50() {
        PayslipInputObject inputObject = Helper.populatePayslipObject();

        // Super rate more than 50.
        inputObject.setSuperRate(51f);
        assertThatThrownBy(() -> {
            validator.validate(inputObject);
        }).isInstanceOf(ValidationException.class).hasMessageContaining("Super rate");

        // Negative super rate.
        inputObject.setSuperRate(-5f);
        assertThatThrownBy(() -> {
            validator.validate(inputObject);
        }).isInstanceOf(ValidationException.class).hasMessageContaining("Super rate");
    }

    /**
     * Test: Super rate < 0%.
     * <p>
     * Test method for
     * {@link com.service.PayslipInputValidator#validate(com.domain.PayslipInputObject)}
     * .
     */
    @Test
    public void testValidateSuperRateLessThan0() {
        PayslipInputObject inputObject = Helper.populatePayslipObject();

        // Super rate more than 50.
        inputObject.setSuperRate(-5f);
        assertThatThrownBy(() -> {
            validator.validate(inputObject);
        }).isInstanceOf(ValidationException.class).hasMessageContaining("Super rate");

        // Negative super rate.
        inputObject.setSuperRate(-5f);
        assertThatThrownBy(() -> {
            validator.validate(inputObject);
        }).isInstanceOf(ValidationException.class).hasMessageContaining("Super rate");
    }
}
