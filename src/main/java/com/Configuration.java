/**
 *
 */
package com;

import java.text.SimpleDateFormat;

/**
 * Configurations.<br>
 * <p>
 * Can be easily externalised for more extensibility.<br>
 *
 * @author IamSB
 */
public class Configuration {

    /**
     * Input date format: dd MMM yyyy<br>
     * Examples: 01 March 2012, 01 Mar 2012<br>
     */
    public static final SimpleDateFormat INPUT_DATE_FORMAT = new SimpleDateFormat("dd MMM yyyy");
}
