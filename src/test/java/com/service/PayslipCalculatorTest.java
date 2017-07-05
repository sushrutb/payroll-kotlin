/**
 *
 */
package com.service;

import com.domain.Payslip;
import com.domain.PayslipInputObject;
import com.helper.Helper;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * Test: com.service.PayslipCalculator for varied data sets.
 *
 * @author IamSB
 */
public class PayslipCalculatorTest {

    private PayslipCalculator calculator = new PayslipCalculator();

    /**
     * Test: Income tax for tax bracet (0-18200)<br>
     * Input - AnnualSalary - 18200<br>
     * Calculation : (0 + (18200-0) * 0)/12 = 0<br>
     * <p>
     * Test method for
     * {@link com.service.PayslipCalculator#createPayslip(com.domain.PayslipInputObject)}
     * .
     */
    @Test
    public void testIncomeTaxForFirstTaxBracetAtBoundary() throws Exception {
        PayslipInputObject input = Helper.populatePayslipObject();
        input.setAnnualSalary(18200);
        Payslip payslip = null;
        payslip = calculator.createPayslip(input);
        assertThat(payslip.getIncomeTax()).isEqualTo(0);
    }

    /**
     * Test: Income tax for tax bracet (18201-37000)<br>
     * Input - AnnualSalary - 24000<br>
     * Calculation : (0 + (24000-18200) * 0.19)/12 = 92<br>
     * <p>
     * Test method for
     * {@link com.service.PayslipCalculator#createPayslip(com.domain.PayslipInputObject)}
     * .
     */
    @Test
    public void testIncomeTaxForSecondTaxBracet() throws Exception {

        PayslipInputObject input = Helper.populatePayslipObject();
        input.setAnnualSalary(24000);
        Payslip payslip = null;
        payslip = calculator.createPayslip(input);
        assertThat(payslip.getIncomeTax()).isEqualTo(92);
    }

    /**
     * Test: Income tax for tax bracet (37001-80000)<br>
     * Input - AnnualSalary - 60050<br>
     * Calculation : (3572 + (60050-37000) * 0.325)/12 = 922<br>
     * <p>
     * Test method for
     * {@link com.service.PayslipCalculator#createPayslip(com.domain.PayslipInputObject)}
     * .
     */
    @Test
    public void testIncomeTaxForThirdTaxBracet() throws Exception {
        PayslipInputObject input = Helper.populatePayslipObject();
        input.setAnnualSalary(60050);
        Payslip payslip = null;
        payslip = calculator.createPayslip(input);
        assertThat(payslip.getIncomeTax()).isEqualTo(922);
    }

    /**
     * Test: Income tax for tax bracet (80001-180000)<br>
     * Input - AnnualSalary - 135000<br>
     * Calculation : (17547 + (135000-80000) * 0.37)/12 = 3158<br>
     * <p>
     * Test method for
     * {@link com.service.PayslipCalculator#createPayslip(com.domain.PayslipInputObject)}
     * .
     */
    @Test
    public void testIncomeTaxForFourthTaxBracet() throws Exception {
        PayslipInputObject input = Helper.populatePayslipObject();
        input.setAnnualSalary(135000);
        Payslip payslip = null;
        payslip = calculator.createPayslip(input);
        assertThat(payslip.getIncomeTax()).isEqualTo(3158);
    }

    /**
     * Test: Income tax for tax bracet (180001-)<br>
     * Input - AnnualSalary - 220000<br>
     * Calculation : (54547 + (220000-180000) * 0.45)/12 = 6046<br>
     * <p>
     * Test method for
     * {@link com.service.PayslipCalculator#createPayslip(com.domain.PayslipInputObject)}
     * .
     */
    @Test
    public void testIncomeTaxForFifthTaxBracet() throws Exception {
        PayslipInputObject input = Helper.populatePayslipObject();
        input.setAnnualSalary(220000);
        Payslip payslip = null;
        payslip = calculator.createPayslip(input);
        assertThat(payslip.getIncomeTax()).isEqualTo(6046);
    }

    /**
     * Test: All calculations. Input - AnnualSalary - 60050<br>
     * SuperRate - 9% Pay period - 01 March - 31 March <br>
     * <p>
     * Test method for
     * {@link com.service.PayslipCalculator#createPayslip(com.domain.PayslipInputObject)}
     * .
     */
    @Test
    public void testAllCalculations() throws Exception {
        PayslipInputObject input = Helper.populatePayslipObject();
        input.setAnnualSalary(60050);
        input.setSuperRate(9f);

        Payslip payslip = null;
        payslip = calculator.createPayslip(input);
        assertThat(payslip.getIncomeTax()).isEqualTo(922);
        assertThat(payslip.getGrossIncome()).isEqualTo(5004);
        assertThat(payslip.getNetIncome()).isEqualTo(4082);
        assertThat(payslip.getSuperPayment()).isEqualTo(450);
    }

    /**
     * Test: All calculations when monthly income gets rounded up.<br>
     * <p>
     * Test method for
     * {@link com.service.PayslipCalculator#createPayslip(com.domain.PayslipInputObject)}
     * .
     */
    @Test
    public void testAllCalculationsMonthlyIncomeRoundedUp() throws Exception {
        PayslipInputObject input = Helper.populatePayslipObject();
        input.setAnnualSalary(60056);
        input.setSuperRate(9f);

        Payslip payslip = null;
        payslip = calculator.createPayslip(input);
        assertThat(payslip.getIncomeTax()).isEqualTo(922);
        assertThat(payslip.getGrossIncome()).isEqualTo(5005);
        assertThat(payslip.getNetIncome()).isEqualTo(4083);
        assertThat(payslip.getSuperPayment()).isEqualTo(450);
    }

}
