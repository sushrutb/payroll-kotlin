/**
 *
 */
package com.domain;


/**
 * Metadata which stores tax table information.<br>
 * Ideally tax bracets should be loaded from config tables or config files.<br>
 * Hence implemented as Singleton.
 *
 * @author IamSB
 */
object TaxTable {

    init {
        loadTaxTableConfiguration()
    }

    var taxBracetsByYear: kotlin.collections.MutableMap<Integer, kotlin.collections.List<TaxBracet>>? = null


    /**
     * Loads tax table configuration data from config files.<br>
     * Hard coded for now.
     */
    private fun loadTaxTableConfiguration() {

        var taxBracetsFor2012: kotlin.collections.MutableList<TaxBracet> = mutableListOf()

        taxBracetsFor2012.add(TaxBracet(2012, 0, 18200, 0, 0f))
        taxBracetsFor2012.add(TaxBracet(2012, 18200, 37000, 0, 19f));
        taxBracetsFor2012.add(TaxBracet(2012, 37000, 80000, 3572, 32.5f));
        taxBracetsFor2012.add(TaxBracet(2012, 80000, 180000, 17547, 37f));
        taxBracetsFor2012.add(TaxBracet(2012, 180000, null, 54547, 45f));
        this.taxBracetsByYear = mutableMapOf(Pair(Integer(2012), taxBracetsFor2012))
    }

    fun getTaxBracetsForYear(year: Integer): List<TaxBracet>? {
        return taxBracetsByYear!![year]
    }
}

