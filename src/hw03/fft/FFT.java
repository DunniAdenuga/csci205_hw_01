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
package hw03.fft;

import java.io.IOException;
import java.nio.ByteBuffer;
import javax.sound.sampled.AudioInputStream;

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
     * Run the Cooley-Tukey FFT algorithm
     *
     * @param audio An AudioInputStream with the audio input
     * @return The FFT of the audio
     * @throws IOException If there is a problem with the audio input
     * @throws DFTException If the size of the input is not a power of 2
     * @author Tim Woodford
     */
    public static Complex[] computeFFT(AudioInputStream audio) throws IOException, DFTException {
        // Convert the audio input to the corresponding float values
        int inlen = (int) audio.getFrameLength();
        byte[] abytes = new byte[inlen * audio.getFormat().getFrameSize()];
        audio.read(abytes);
        float[] floatArray = byteArrayToFloats(abytes, 0,
                                               abytes.length,
                                               2);
        // Convert floats to complex & pad to a power of 2
        int p2 = (int) Math.pow(2, Math.ceil(Math.log(inlen) / Math.log(2)));
        Complex[] cmplx = new Complex[p2];
        for (int i = 0; i < inlen; i++) {
            cmplx[i] = new Complex(floatArray[i], 0);
        }
        for (int i = inlen; i < p2; i++) {
            cmplx[i] = new Complex(0, 0);
        }
        return computeFFT(cmplx);
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

    /**
     * Collapse the bytes in the input into an array of floats
     *
     * @param b The bytes in the input
     * @param off The starting offset
     * @param len The length of the input
     * @param frameSize The size of the frame in bytes
     * @return A float array corresponding to the inputs
     */
    public static float[] byteArrayToFloats(byte[] b, int off, int len,
                                            int frameSize) {
        final int total = len / frameSize;
        final float[] ret = new float[total];
        final int bitScale = (int) ((1L << frameSize * 8) - 1);
        final ByteBuffer buf = ByteBuffer.wrap(b, off, len);
        for (int i = 0; i < total; i++) {
            int curr = 0;
            switch (frameSize) {
                case 1:
                    ret[i] = buf.get() / (float) bitScale;
                    break;
                case 2:
                    ret[i] = buf.getShort() / (float) bitScale;
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
        return ret;
    }
}
