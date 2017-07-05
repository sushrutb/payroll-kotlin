/**
 *
 */
package com.service;

import com.domain.Payslip;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Output file printing service.
 *
 * @author IamSB
 */
public class OutputFilePrinter {

    private static final CSVFormat outputCSVFormat = CSVFormat.DEFAULT.withTrim().withDelimiter(',');

    /**
     * Creates output file.<br>
     * Prints all payslip records in output file.<br>
     *
     * @param outputFilename
     * @param payslips
     */
    public void printOutputFile(String outputFilename, List<Payslip> payslips) {
        FileWriter writer = null;
        CSVPrinter printer = null;

        try {
            writer = new FileWriter(outputFilename);
            printer = new CSVPrinter(writer, outputCSVFormat);
            for (Payslip payslip : payslips) {
                printOutputRecord(printer, payslip);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error in creating output file.");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
            if (printer != null) {
                try {
                    printer.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    /**
     * Prints output records in order<br>
     * <p>
     * Firstname lastname, Payperiod, Gross Income, Income Tax, Net Income, Super Payment
     *
     * @param printer
     * @param payslip
     * @throws IOException - If there is any error in printing record.
     */
    protected void printOutputRecord(CSVPrinter printer, Payslip payslip) throws IOException {
        printer.print(payslip.getFirstName() + " " + payslip.getLastName());
        printer.print(payslip.getPayPeriod());
        printer.print(payslip.getGrossIncome());
        printer.print(payslip.getIncomeTax());
        printer.print(payslip.getNetIncome());
        printer.print(payslip.getSuperPayment());
        printer.println();
    }

}
