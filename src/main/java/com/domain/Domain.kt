/**
 *
 */
package com.domain;

import java.util.*

/**
 * Domain object for Payslip.
 *
 * @author IamSB
 *
 */
data class Payslip(val firstName: String, val lastName: String) {
    var grossIncome: Int = 0
    var superPayment: Int = 0
    var incomeTax: Int = 0
    var netIncome: Int = 0
    var payPeriod: String = ""

}

data class PayslipInputObject(val firstName: String, val lastName: String) {
    var annualSalary: Int = 0
    var superRate: Float = 0f
    var payPeriod: String = ""
    var startDatePeriod: Calendar? = null
    var endDatePeriod: Calendar? = null
}

data class TaxBracet(
        val year: Int,
        val lowerSalaryLimit: Int,
        val upperSalaryLimit: Int?,
        val baseIncomeTax: Int,
        val incomeTaxPercent: Float
)

const val FINANCIAL_YEAR: Int = 2012
