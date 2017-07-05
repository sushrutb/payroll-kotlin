/**
 *
 */
package com.helper;

import com.domain.PayslipInputObject;
import com.utils.Utils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.fail;

/**
 * Provides helper utilities to aid test data creation.
 *
 * @author IamSB
 */
public class Helper {

    /**
     * Populate a payslip object with valid data.
     *
     * @return
     */
    public static PayslipInputObject populatePayslipObject() {
        PayslipInputObject inputObject = new PayslipInputObject("Sushrut", "Bidwai");
        inputObject.setAnnualSalary(60050);
        inputObject.setSuperRate(9f);
        inputObject.setPayPeriod("01 March - 31 March");
        try {
            inputObject.setStartDatePeriod(Utils.parseInputDate("01 March 2012"));
            inputObject.setEndDatePeriod(Utils.parseInputDate("31 March 2012"));
        } catch (ParseException e1) {
            fail("Should not throw ParseException");
        }
        return inputObject;
    }

    /**
     * Parses output CSV file.
     *
     * @param outputFilename - name of output file.
     * @return - List of CSV Records.
     * @throws IOException
     */
    public static List<CSVRecord> parseOutputFile(String outputFilename) throws IOException {
        List<CSVRecord> records = new ArrayList<CSVRecord>();
        Reader in = new FileReader(outputFilename);

        Iterable<CSVRecord> recordsIter = CSVFormat.DEFAULT.withDelimiter(',').withTrim().parse(in);
        recordsIter.forEach(record -> {
            records.add(record);
        });

        in.close();

        return records;
    }

    /**
     * Provides full path to output file that will be created in payslip
     * generation process. Since all developers can have different setups, this
     * method relies on having a file input.csv under testing resources.
     *
     * @param outputname - Name of output file
     * @return - Full path of outputfile.
     */
    public static String getOutputFilename(String outputname) {
        // make it easier for all developers to run this on varied system setups
        // Since output file does not exist, we can find its path.
        // Hence constructing it based on input file path.
        String filename = Helper.class.getClassLoader().getResource("data/input.csv").getPath();
        String outputfilename = filename.replace("input", outputname);
        return outputfilename;
    }

}
