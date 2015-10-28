/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: 28-Oct-2015
 * Time: 08:25:54
 *
 * Project: csci205_hw_01
 * Package: hw03.model
 * File: InvalidModelException
 * Description:
 *
 * ****************************************
 */
package hw03.model;

/**
 * Thrown when the <code>AudioModel</code> data is not in a valid state.
 *
 * @author tww014
 */
public class InvalidModelException extends Exception {

    /**
     * Creates a new instance of <code>InvalidModelException</code> without
     * detail message.
     */
    public InvalidModelException() {
    }

    /**
     * Constructs an instance of <code>InvalidModelException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidModelException(String msg) {
        super(msg);
    }
}
