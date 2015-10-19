/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: Oct 18, 2015
 * Time: 8:23:27 PM
 *
 * Project: csci205_hw_01
 * Package: hw02.fft
 * File: DFTException
 * Description:
 *
 * ****************************************
 */
package hw02.fft;

/**
 * An exception thrown when there is a mathematical error in the FFT algorithm
 *
 * @author Dunni Adenuga
 */
public class DFTException extends Exception {

    /**
     * Creates a new instance of <code>DFTException</code> without detail
     * message.
     */
    public DFTException() {
    }

    /**
     * Constructs an instance of <code>DFTException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public DFTException(String msg) {
        super(msg);
    }
}
