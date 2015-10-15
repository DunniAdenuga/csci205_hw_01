/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: Oct 15, 2015
 * Time: 1:30:06 PM
 *
 * Project: csci205_hw_01
 * Package: hw02.tone
 * File: DFT
 * Description:
 *
 * ****************************************
 */
package hw02.tone;

import hw02.fft.Complex;

/**
 *
 * @author Dunni Adenuga
 */
class DFTException extends Exception {
    DFTException(String message) {
        super(message);
    }
}

public class DFT {

    public Complex[] computeFFT(Complex[] x) throws DFTException {
        int N = x.length;
        if (N % 2 != 0) {
            throw new DFTException("array is not a power of 2");
        }
    }
}
