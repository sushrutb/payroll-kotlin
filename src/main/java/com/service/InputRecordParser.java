/**
 *
 */
package com.service;

import com.domain.DomainKt;
import com.domain.PayslipInputObject;
import com.exception.ValidationException;
import com.utils.Utils;
import org.apache.commons.csv.CSVRecord;

import java.text.ParseException;

/**
 * Parse and validate input CSV record.
 *
 * @author IamSB
 */
public class InputRecordParser {

    /**
     * Parse input record
     *
     * @param record - CSV record.
     * @return
     * @throws Exception - if input record does not contain
     */
    public PayslipInputObject parse(CSVRecord record) throws ValidationException {

        PayslipInputObject inputObject = new PayslipInputObject(record.get(0), record.get(1));

        try {
            inputObject.setAnnualSalary(Integer.parseInt(record.get(2)));
        } catch (NumberFormatException e) {
            throw new ValidationException("Annual salary is empty.", e);
        }

        try {
            inputObject.setSuperRate(Float.parseFloat(record.get(3).replace("%", "")));
        } catch (NumberFormatException e) {
            throw new ValidationException("Super rate is empty.", e);
        }

        inputObject.setPayPeriod(record.get(4));

        findStartAndEndDatePeriod(inputObject);

        return inputObject;
    }

    /**
     * Parse start and end date from pay period. <br>
     * Input format is - 01 March - 31 March <br>
     * StartDay StartMonth - EndDay EndMonth <br>
     *
     * @param inputObject
     * @throws ValidationException - If record does not match correct specification for dates.
     */
    protected void findStartAndEndDatePeriod(PayslipInputObject inputObject) throws ValidationException {

        // Start and end dates in input are separated with -
        String[] payPeriodFragment = inputObject.getPayPeriod().split("-");
        if (payPeriodFragment.length != 2) {
            // Does not contain both start and end date or separator in missing.
            throw new ValidationException("Input record does not match format, please check pay period input.");
        }

        // Using hardcoded year.
        String startDatePeriod = payPeriodFragment[0].trim() + " " + DomainKt.FINANCIAL_YEAR;
        try {
            inputObject.setStartDatePeriod(Utils.parseInputDate(startDatePeriod));
        } catch (ParseException e) {
            throw new ValidationException("Start date does not match required format.", e);
        }

        // Using hardcoded year.
        String endDatePeriod = payPeriodFragment[1].trim() + " " + DomainKt.FINANCIAL_YEAR;
        try {
            inputObject.setEndDatePeriod(Utils.parseInputDate(endDatePeriod));
        } catch (ParseException e) {
            throw new ValidationException("End date does not match required format.", e);
        }
    }

}
