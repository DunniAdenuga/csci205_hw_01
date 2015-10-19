/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: Oct 18, 2015
 * Time: 8:19:26 PM
 *
 * Project: csci205_hw_01
 * Package: hw02.fft
 * File: FFT
 * Description:
 *
 * ****************************************
 */
package hw02.fft;

/**
 * A simple implementation of the Cooley-Tukey (time-decimation) FFT algorithm
 *
 * @author Tim Woodford
 */
public class FFT {

    /**
     * Run the Cooley-Tukey FFT algorithm
     *
     * @param x The input signal over time
     * @return The FFT output
     * @throws DFTException If the size of the input is not a power of 2
     * @author Dunni Adenuga
     */
    public static Complex[] computeFFT(Complex[] x) throws DFTException {
        int N = x.length;

        if (N == 1) {
            return new Complex[]{x[0]};
        }
        if (N % 2 != 0) {
            throw new DFTException("array length is not a power of 2");
        }
        //divide and conquer- fft for real and imaginary
        Complex[] odd = new Complex[N / 2];
        for (int i = 0; i < N / 2; i++) {
            odd[i] = x[(2 * i) + 1];
        }
        Complex[] result1 = computeFFT(odd);

        Complex[] even = new Complex[N / 2];
        for (int i = 0; i < N / 2; i++) {
            even[i] = x[(2 * i)];
        }
        Complex[] result2 = computeFFT(even);

        //combine
        //[Re(x(t))cos(2πtk/n)+Im(x(t))sin(2πtk/n)]+[−Re(x(t))sin(2πtk/n)+Im(x(t))cos(2πtk/n)]i.
        Complex[] answer = new Complex[N];
        for (int i = 0; i < N / 2; i++) {
            double angle = -2 * i * Math.PI / N;
            /**
             * from http://introcs.cs.princeton.edu/java/97data/FFT.java.html
             */
            Complex wk = new Complex(Math.cos(angle), Math.sin(angle));
            answer[i] = result2[i].add(wk.multiply(result1[i]));
            answer[i + N / 2] = result2[i].minus(wk.multiply(result1[i]));
        }
        return answer;
    }

    /**
     * Find the frequencies corresponding to each item in the FFT output
     *
     * @param sampleRate The sample rate of the input
     * @param outputLength The length of the output array (same as the length of
     * the input to the FFT)
     * @return An array of the frequencies corresponding to the FFT outputs
     */
    public static float[] frequenciesFFT(float sampleRate, int outputLength) {
        float[] ret = new float[outputLength];
        float factor = sampleRate / outputLength;
        for (int i = 0; i < outputLength; i++) {
            ret[i] = i * factor;
        }
        return ret;
    }
}
