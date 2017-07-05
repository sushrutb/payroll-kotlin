/**
 *
 */
package com.api;

import com.MainClass;
import com.helper.Helper;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Test : MYOBMainClass.
 *
 * @author IamSB
 */
public class MainClassTest {

    /**
     * Test method for
     * {@link MainClass#validateArguments(java.lang.String[])}.
     */
    @Test
    public void testCheckInputLengthValid() {
        String inputFilename = getClass().getClassLoader().getResource("data/input.csv").getPath();
        String outputFilename = Helper.getOutputFilename("output");

        String args[] = {inputFilename, outputFilename};
        Boolean result = new MainClass().validateArguments(args);
        assertThat(result).isTrue();
    }

    /**
     * Test method for
     * {@link MainClass#validateArguments(java.lang.String[])}.
     */
    @Test
    public void testCheckInputLengthInvalid() {
        String inputFilename = getClass().getClassLoader().getResource("data/input.csv").getPath();

        String args[] = {inputFilename};
        assertThatThrownBy(() -> {
            new MainClass().validateArguments(args);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Input and/or output filename missing");
    }

    /**
     * Test method for
     * {@link MainClass#generatePayslip(java.lang.String[])}.
     */
    @Test
    public void testGeneratePayslip() {
        String inputFilename = getClass().getClassLoader().getResource("data/input.csv").getPath();

        // Record 1 - David,Rudd,60050,9%,01 March - 31 March
        // Record 2 - Ryan,Chen,120000,10%,01 March - 31 March
        String outputFilename = Helper.getOutputFilename("output");
        String[] args = {inputFilename, outputFilename};
        try {
            new MainClass().generatePayslip(args);

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
        } catch (IOException e) {
            fail("Error in verifying output file.");
        }
    }

}
