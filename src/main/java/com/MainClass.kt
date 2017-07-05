/**
 *
 */
package com

import com.api.PayslipGeneratorService

/**
 * Main Class to run from command line.
 *
 * @author IamSB
 */
class MainClass {

    /**
     * Validate input and output filenames are provided.
     *
     * @param args
     */
    fun validateArguments(args: Array<String>):Boolean {
        if (args.size != 2) {
            throw IllegalArgumentException("Input and/or output filename missing.")
        }
        return true
    }

    /**
     * Generate payslips by calling PayslipGeneratorService api.
     *
     * @param args
     */
    fun generatePayslip(args: Array<String>) {
        val inputFilename = args[0]
        val outputFilename = args[1]
        println("Reading input from : " + inputFilename)
        println("Writing output to : " + outputFilename)

        try {
            PayslipGeneratorService().generatePayslips(inputFilename, outputFilename)
            println("Output file $outputFilename has been successfully generated.")
        } catch (e: Exception) {
            System.err.println(e.message)
        }
    }

    /**
     * Main runner.
     *
     * @param args - should contains 2 parameters - input file name - output file
     *             name
     */
    fun main(args: Array<String>) {

        val mainClass = MainClass()

        if (mainClass.validateArguments(args)) {
            mainClass.generatePayslip(args)
        }
    }

}
