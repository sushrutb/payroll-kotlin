/**
 *
 */
package com.service

import com.domain.Payslip
import com.domain.PayslipInputObject
import com.domain.TaxBracet
import com.domain.TaxTable
import com.exception.ValidationException

/**
 * Calculates all required payslip fields.
 *
 * @author IamSB
 */
class PayslipCalculator {

    /**
     * Creates and calculates all Payslip fields.
     *
     * @param input - Validated PayslipInputObject
     * @return - Payslip
     * @throws ValidationException - if annual salary does not fit any tax bracets due to wrong
     *                             configuration of taxbracets.
     */
    fun createPayslip(input: PayslipInputObject):Payslip {

        var payslip = Payslip(input.firstName, input.lastName)


        payslip.payPeriod = input.payPeriod

        val monthlySalary = calculateMonthlySalary(input.annualSalary).toInt()

        payslip.grossIncome = monthlySalary.toInt()
        payslip.incomeTax = calculateIncomeTax(input)
        payslip.netIncome = payslip.grossIncome - payslip.incomeTax
        payslip.superPayment = calculateSuperPayment(monthlySalary, input.superRate)

        return payslip
    }

    /**
     * @param monthlySalary
     * @param superRate
     * @return
     */
    fun calculateSuperPayment(monthlySalary: Int, superRate: Float):Int {
        return Math.round(monthlySalary * superRate / 100)
    }

    /**
     * @param annualSalary
     * @return
     */
    fun calculateMonthlySalary(annualSalary: Int): Long {
        val monthlySalary:Double = (annualSalary.toDouble() / 12)
        return Math.round(monthlySalary)
    }

    /**
     * @param input
     * @return
     * @throws ValidationException
     */
    fun calculateIncomeTax(input: PayslipInputObject):Int {
        val annualSalary = input.annualSalary

        // Using hardcoded year.
        // Year should be provided in input file.
        val taxBracets = TaxTable.getTaxBracetsForYear(Integer(2012))
        for (taxBracet in taxBracets!!) {
            if (annualSalary > taxBracet.lowerSalaryLimit && (taxBracet.upperSalaryLimit == null || annualSalary <= taxBracet.upperSalaryLimit)) {
                return calculateIncomeTax(taxBracet, input)
            }
        }
        throw ValidationException("Annual salary does not fall in any  tax bracet, check configuration data for tax bracets.")
    }

    /**
     * @param taxBracet
     * @param input
     * @return
     */
    fun calculateIncomeTax(taxBracet: TaxBracet, input: PayslipInputObject):Int {
        val incomeTax = (taxBracet.baseIncomeTax + (input.annualSalary - taxBracet.lowerSalaryLimit) * taxBracet.incomeTaxPercent / 100) / 12
        return Math.round(incomeTax).toInt()
    }

}
