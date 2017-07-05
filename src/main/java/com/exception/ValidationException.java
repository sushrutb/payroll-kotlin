/**
 *
 */
package com.exception;

/**
 * Exception thrown if input records and file do not match specified file
 * format.
 *
 * @author IamSB
 */
public class ValidationException extends Exception {

    private static final long serialVersionUID = -2600384897919182376L;

    protected Throwable e;
    protected Integer lineNum;
    protected String fileName;

    /**
     * @param message
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param e
     */
    public ValidationException(String message, Throwable e) {
        super(message, e);
    }

    /**
     * @param message  - Error message
     * @param lineNum  - File line number where validation failed.
     * @param fileName - File name
     * @param e        - Root cause, if any.
     */
    public ValidationException(String message, Integer lineNum, String fileName, Throwable e) {
        super(message, e);
        this.lineNum = lineNum;
        this.fileName = fileName;
    }

    /**
     * @param message
     * @param i
     * @param e2
     */
    public ValidationException(String message, int lineNum, ValidationException e) {
        super(message, e);
        this.lineNum = lineNum;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Returns an enriched error message.
     *
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    public String getMessage() {
        StringBuilder messageSB = new StringBuilder();
        messageSB.append(super.getMessage());
        if (lineNum != null) {
            messageSB.append(": line " + lineNum);
        }
        if (fileName != null) {
            messageSB.append(" in " + fileName);
        }
        return messageSB.toString();
    }

}
