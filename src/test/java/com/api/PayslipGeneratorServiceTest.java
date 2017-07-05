/**
 *
 */
package com.api;

import com.exception.ValidationException;
import com.helper.Helper;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Test: Integration test for com.api.PayslipGeneratorService.
 *
 * @author IamSB
 */
public class PayslipGeneratorServiceTest {

    private PayslipGeneratorService payslipGeneratorService = new PayslipGeneratorService();

    /**
     * Test: Payslip generation for valid input file
     * /src/test/resource/data/input.csv File contains two records for which
     * payslips will be generated.<br>
     * <p>
     * Test method for
     * {@link com.api.PayslipGeneratorService#generatePayslips(String, String)}
     */
    @Test
    public void testGeneratePayslips() throws Exception {
        String inputFilename = getClass().getClassLoader().getResource("data/input.csv").getPath();

        // Record 1 - David,Rudd,60050,9%,01 March - 31 March
        // Record 2 - Ryan,Chen,120000,10%,01 March - 31 March
        String outputFilename = Helper.getOutputFilename("output");
        payslipGeneratorService.generatePayslips(inputFilename, outputFilename);

        File outputFile = new File(outputFilename);
        assertThat(outputFile.exists()).isTrue();

        List<CSVRecord> outputRecords = Helper.parseOutputFile(outputFilename);
        assertThat(outputRecords.size()).isEqualTo(2);

        CSVRecord firstRecord = outputRecords.get(0);
        assertThat(firstRecord.get(0)).isEqualTo("David Rudd");
        assertThat(firstRecord.get(1)).isEqualTo("01 March - 31 March");
        assertThat(firstRecord.get(2)).isEqualTo("5004");
        assertThat(firstRecord.get(3)).isEqualTo("922");
        assertThat(firstRecord.get(4)).isEqualTo("4082");
        assertThat(firstRecord.get(5)).isEqualTo("450");

        CSVRecord secondRecord = outputRecords.get(1);
        assertThat(secondRecord.get(0)).isEqualTo("Ryan Chen");
        assertThat(secondRecord.get(1)).isEqualTo("01 March - 31 March");
        assertThat(secondRecord.get(2)).isEqualTo("10000");
        assertThat(secondRecord.get(3)).isEqualTo("2696");
        assertThat(secondRecord.get(4)).isEqualTo("7304");
        assertThat(secondRecord.get(5)).isEqualTo("1000");
    }

    /**
     * Test: Payslip generation for invalid input file
     * /src/test/resource/data/input_invalid.csv File contains two records first
     * valid, second invalid end date. <br>
     * <p>
     * Test method for
     * {@link com.api.PayslipGeneratorService#generatePayslips(String, String)}
     */
    @Test
    public void testGeneratePayslipsInvalidEndDate() {
        String inputFilename = getClass().getClassLoader().getResource("data/input_invalid_date.csv").getPath();

        // Record 1 - David,Rudd,60050,9%,01 March - 31 March
        // Record 2 - Ryan,Chen,120000,10%,01 March - March 31
        String outputFilename = Helper.getOutputFilename("output");
        assertThatThrownBy(() -> {
            payslipGeneratorService.generatePayslips(inputFilename, outputFilename);
        }).isInstanceOf(ValidationException.class).hasMessageContaining("End date");

    }

    /**
     * Test: Payslip generation for invalid input file
     * /src/test/resource/data/input_invalid.csv File contains two records first
     * valid, second invalid. <br>
     * <p>
     * Test method for
     * {@link com.api.PayslipGeneratorService#generatePayslips(String, String)}
     */
    @Test
    public void testGeneratePayslipsInvalidWrongNumberOfParameters() {
        String inputFilename = getClass().getClassLoader().getResource("data/input_invalid_number_of_params.csv").getPath();

        // Record 1 - David,Rudd,60050,9%,01 March - 31 March
        // Record 2 - Ryan,Chen,120000,10%,01 March - March 31
        String outputFilename = Helper.getOutputFilename("output");
        assertThatThrownBy(() -> {
            payslipGeneratorService.generatePayslips(inputFilename, outputFilename);
        }).isInstanceOf(ValidationException.class).hasMessageContaining("5 values");

    }
}
