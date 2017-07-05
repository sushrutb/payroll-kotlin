/**
 *
 */
package com.service;

import com.domain.PayslipInputObject;
import com.exception.ValidationException;

import java.util.Calendar;

/**
 * Validates pay slip input.
 *
 * @author IamSB
 */
public class PayslipInputValidator {

    /**
     * Validates all fields of input object.
     *
     * @param payslipInput - Input object to validate
     * @return - True if successful.
     * @throws ValidationException - If a validation rule fails. Stops at first validation failure.
     */
    public Boolean validate(PayslipInputObject payslipInput) throws ValidationException {
        validateFirstName(payslipInput.getFirstName());
        validateLastName(payslipInput.getLastName());
        validateAnnualSalary(payslipInput.getAnnualSalary());
        validateSuperRate(payslipInput.getSuperRate());
        vaidatePayPeriod(payslipInput.getStartDatePeriod(), payslipInput.getEndDatePeriod());
        return true;
    }

    /**
     * Last name should not be empty.
     *
     * @param lastName
     */
    private void validateLastName(String lastName) throws ValidationException {
        if (lastName.isEmpty()) {
            throw new ValidationException("Last name can not be empty.");
        }
    }

    /**
     * Annual salary should be positive integer.
     *
     * @param annualSalary
     */
    private void validateAnnualSalary(Integer annualSalary) throws ValidationException {
        if (annualSalary < 0) {
            throw new ValidationException("Annual salary must be greater than 0.");
        }
    }

    /**
     * Super rate should be between 0 - 50 both inclusive.
     *
     * @param superRate
     */
    private void validateSuperRate(Float superRate) throws ValidationException {
        if (superRate < 0 || superRate > 50) {
            throw new ValidationException("Super rate must be between 0 and 50.");
        }
    }

    /**
     * Start date should be before end date.
     *
     * @param startDatePeriod
     * @param endDatePeriod
     */
    private void vaidatePayPeriod(Calendar startDatePeriod, Calendar endDatePeriod) throws ValidationException {
        if (endDatePeriod.compareTo(startDatePeriod) < 0) {
            throw new ValidationException("End date must be before start date.");
        }
    }

    /**
     * @param firstName
     */
    private void validateFirstName(String firstName) throws ValidationException {
        if (firstName.isEmpty()) {
            throw new ValidationException("First name can not be empty.");
        }
    }

}
