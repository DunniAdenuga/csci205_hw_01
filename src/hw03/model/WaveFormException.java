/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Brian King
 * Date: Oct 21, 2015
 * Time: 9:12:34 PM
 *
 * Package: hw3.model
 * File: WaveFormException

 * Description:
 * Very simple class to create a checked exception for dealing with WaveForm
 * problems that may arise
 * ****************************************
 */
package hw03.model;

/**
 * Simple exception to represent possible problems that may arise with the
 * WaveForm class.
 *
 * @author Prof. Brian King
 */
public class WaveFormException extends Exception {

    public WaveFormException(String message) {
        super(message);
    }
}
