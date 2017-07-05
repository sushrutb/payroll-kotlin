/**
 *
 */
package com.api

import com.domain.Payslip
import com.service.InputFileReader
import com.service.OutputFilePrinter
import com.service.PayslipCalculator
import java.util.*

/**
 * API for generating payslips.
 *
 * @author IamSB
 */
class PayslipGeneratorService {

    // Input file reader service.
    val inputFileReader: InputFileReader = InputFileReader()

    // Output file printer service.
    val outputFilePrinter: OutputFilePrinter = OutputFilePrinter()

    // Payslip calculator service.
    val payslipCalculator: PayslipCalculator = PayslipCalculator()

    /**
     * Parse and validate input file<br>
     * Generate payslips for each parsed record<br>
     * Print output file with generated payslips<br>
     *
     * @param inputFilename  - Input file name
     * @param outputFilename - Output file name
     */
    fun generatePayslips(inputFilename: String, outputFilename: String) {
        // Read, parse, validate input file.
        val inputRecords = inputFileReader.parseAndValidate(inputFilename)

        // For each parse records, generate payslip.
        var payslips = ArrayList<Payslip>()

        inputRecords.forEach {
            inputRecord ->
            payslips.add(payslipCalculator.createPayslip(inputRecord))
        }

        // Print generated payslips to outputfile.
        outputFilePrinter.printOutputFile(outputFilename, payslips)
    }

}
