/**
 *
 */
package com.service;

import com.domain.Payslip;
import com.helper.Helper;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * Test: com.service.OutputFilePrinter
 *
 * @author IamSB
 */
public class OutputFilePrinterTest {

    private OutputFilePrinter outputFilePrinter = new OutputFilePrinter();

    /**
     * Test method for
     * {@link com.service.OutputFilePrinter#printOutputFile(String, List
     * <Payslip>)} .
     */
    @Test
    public void testOutputPrinterWithOnePayslip() {
        List<Payslip> payslips = new ArrayList<Payslip>();
        Payslip payslip = new Payslip("David", "Rudd");
        payslip.setPayPeriod("01 March - 31 March");
        payslip.setGrossIncome(5004);
        payslip.setIncomeTax(922);
        payslip.setNetIncome(4082);
        payslip.setSuperPayment(450);

        payslips.add(payslip);

        String outputFilename = Helper.getOutputFilename("output");

        outputFilePrinter.printOutputFile(outputFilename, payslips);

        File outputFile = new File(outputFilename);

        assertThat(outputFile.exists()).isTrue();
        assertNumberOfLinesInOutputFile(1, outputFile);
        try {
            List<CSVRecord> records = Helper.parseOutputFile(outputFilename);
            assertThat(records.size()).isEqualTo(1);
            CSVRecord firstRecord = records.get(0);
            assertThat(firstRecord.size()).isEqualTo(6);
            assertThat(firstRecord.get(0)).isEqualTo("David Rudd");
            assertThat(firstRecord.get(1)).isEqualTo("01 March - 31 March");
            assertThat(firstRecord.get(2)).isEqualTo("5004");
            assertThat(firstRecord.get(3)).isEqualTo("922");
            assertThat(firstRecord.get(4)).isEqualTo("4082");
            assertThat(firstRecord.get(5)).isEqualTo("450");
        } catch (IOException e) {
            fail("Should not throw IOException");
        }
    }

    /**
     * Test method for
     * {@link com.service.OutputFilePrinter#printOutputFile(String, List
     * <Payslip>)} .
     */
    @Test
    public void testOutputPrinterWithTwoPayslip() {
        List<Payslip> payslips = new ArrayList<Payslip>();

        // Payslip 1
        Payslip payslip = new Payslip("David", "Rudd");
        payslip.setPayPeriod("01 March - 31 March");
        payslip.setGrossIncome(5004);
        payslip.setIncomeTax(922);
        payslip.setNetIncome(4082);
        payslip.setSuperPayment(450);

        // Payslip 2
        Payslip payslip2 = new Payslip("Ryan", "Chen");
        payslip2.setPayPeriod("01 March - 31 March");
        payslip2.setGrossIncome(10000);
        payslip2.setIncomeTax(2696);
        payslip2.setNetIncome(7304);
        payslip2.setSuperPayment(1000);

        payslips.add(payslip);
        payslips.add(payslip2);

        String outputFilename = Helper.getOutputFilename("output2");

        new OutputFilePrinter().printOutputFile(outputFilename, payslips);

        File outputFile = new File(outputFilename);

        assertThat(outputFile.exists()).isTrue();
        assertNumberOfLinesInOutputFile(2, outputFile);
    }

    /**
     * Helper method which to test number of lines in a file.
     *
     * @param expectedLines - Expected number of lines.
     * @param outputFile
     */
    private void assertNumberOfLinesInOutputFile(int expectedLines, File outputFile) {
        LineNumberReader lineReader = null;
        try {
            lineReader = new LineNumberReader(new FileReader(outputFile));
            lineReader.skip(Long.MAX_VALUE);
            assertThat(lineReader.getLineNumber()).isEqualTo(expectedLines);

        } catch (FileNotFoundException e) {
            fail("Output file was not created.");
        } catch (IOException e) {
            fail("Error in reading output file.");
        } finally {
            if (lineReader != null) {
                try {
                    lineReader.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }

    }

}
