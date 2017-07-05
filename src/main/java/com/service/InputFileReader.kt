/**
 *
 */
package com.service

import com.domain.PayslipInputObject
import com.exception.ValidationException
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException

import java.io.Reader
import java.util.*

/**
 * Provide Input File Reading service.
 *
 * @author IamSB
 */
class InputFileReader {

    // Input CSV format.
    val INPUT_CSV_FORMAT: CSVFormat = CSVFormat.DEFAULT.withTrim().withDelimiter(',')

    // Input validator service.
    val payslipInputValidator: PayslipInputValidator = PayslipInputValidator()

    // Input record parser service.
    val inputRecordParser: InputRecordParser = InputRecordParser()

    /**
     * Parses input file using inputCSVFormat.<br>
     *
     * @param inputFilename - Input filename
     * @return List of validated PayslipInputObjects
     */
    fun parseAndValidate(inputFilename: String): List<PayslipInputObject> {

        var inputFileReader: Reader?
        try {
            inputFileReader = FileReader(inputFilename)
        } catch (e: FileNotFoundException) {
            throw RuntimeException("Input file does not exist." + inputFilename, e)
        }

        // Parse input file using common-csv.
        var records: Iterable<CSVRecord>

        try {
            records = INPUT_CSV_FORMAT.parse(inputFileReader)
        } catch (e: IOException) {
            throw RuntimeException("Error in processing file " + inputFilename, e)
        }

        try {
            return converRecordsToInputObjects(records)
        } catch (e: ValidationException) {
            e.setFileName(inputFilename)
            throw e
        } finally {
            try {
                inputFileReader.close()
            } catch (e: IOException) {
                throw RuntimeException("Exception in processing file " + inputFilename, e)
            }
        }
    }

    fun converRecordsToInputObjects(records: Iterable<CSVRecord>): List<PayslipInputObject> {
        var inputObjects = ArrayList<PayslipInputObject>()
        var lineNum = 0

        try {
            for (record in records) {
                validateInputLength(record)
                var payslipInput = inputRecordParser.parse(record)
                payslipInputValidator.validate(payslipInput)
                inputObjects.add(payslipInput)
                lineNum++
            }
        } catch (e: ValidationException) {
            // Add one to line number, so non technical user is not confused.
            throw ValidationException(e.message, lineNum + 1, e)
        }
        return inputObjects
    }

    /**
     * @param input
     * @throws ValidationException
     */
    fun validateInputLength(input: CSVRecord) {
        if (input.size() != 5) {
            throw ValidationException("Validation failed. Input record should have 5 values.")
        }
    }
}
