/**
 *
 */
package com.domain;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test tax table configuration data.
 *
 * @author IamSB
 */
public class TaxTableTest {

    /**
     * 0 - $18,200     Nil<br>
     * $18,201 - $37,000       19c for each $1 over $18,200<br>
     * $37,001 - $80,000       $3,572 plus 32.5c for each $1 over $37,000<br>
     * $80,001 - $180,000      $17,547 plus 37c for each $1 over $80,000<br>
     * $180,001 and over       $54,547 plus 45c for each $1 over $180,000<br>
     * <p>
     */
    @Test
    public void testGetTaxBracetsForYear() {
        List<TaxBracet> bracets = TaxTable.INSTANCE.getTaxBracetsForYear(2012);
        assertThat(bracets.size()).isEqualTo(5);
        TaxBracet first = bracets.get(0);
        assertThat(first.getBaseIncomeTax()).isEqualTo(0);
        assertThat(first.getIncomeTaxPercent()).isEqualTo(0);
        assertThat(first.getLowerSalaryLimit()).isEqualTo(0);
        assertThat(first.getUpperSalaryLimit()).isEqualTo(18200);

        TaxBracet second = bracets.get(1);
        assertThat(second.getBaseIncomeTax()).isEqualTo(0);
        assertThat(second.getIncomeTaxPercent()).isEqualTo(19f);
        assertThat(second.getLowerSalaryLimit()).isEqualTo(18200);
        assertThat(second.getUpperSalaryLimit()).isEqualTo(37000);

        TaxBracet third = bracets.get(2);
        assertThat(third.getBaseIncomeTax()).isEqualTo(3572);
        assertThat(third.getIncomeTaxPercent()).isEqualTo(32.5f);
        assertThat(third.getLowerSalaryLimit()).isEqualTo(37000);
        assertThat(third.getUpperSalaryLimit()).isEqualTo(80000);

        TaxBracet fourth = bracets.get(3);
        assertThat(fourth.getBaseIncomeTax()).isEqualTo(17547);
        assertThat(fourth.getIncomeTaxPercent()).isEqualTo(37f);
        assertThat(fourth.getLowerSalaryLimit()).isEqualTo(80000);
        assertThat(fourth.getUpperSalaryLimit()).isEqualTo(180000);

        TaxBracet fifth = bracets.get(4);
        assertThat(fifth.getBaseIncomeTax()).isEqualTo(54547);
        assertThat(fifth.getIncomeTaxPercent()).isEqualTo(45f);
        assertThat(fifth.getLowerSalaryLimit()).isEqualTo(180000);
        assertThat(fifth.getUpperSalaryLimit()).isNull();
        ;
    }

}
