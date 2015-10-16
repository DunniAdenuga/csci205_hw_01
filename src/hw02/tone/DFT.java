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
import hw02.source.SineTone;
import hw02.source.Tone;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.sound.sampled.AudioInputStream;

/**
 *
 * @author Dunni Adenuga
 */
class DFTException extends Exception {
    DFTException(String message) {
        super(message);
    }
}

/**
 *
 * @author ia005
 * @see http://introcs.cs.princeton.edu/java/97data/FFT.java.html
 * @see
 * http://www.nayuki.io/res/how-to-implement-the-discrete-fourier-transform/Dft.java
 */
public class DFT {
    public static void main(String[] args) throws DFTException, IOException {
        getPeak();
    }

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

    //read into memory in bytes
    /**
     * @see http://www.jsresources.org/examples/AudioDataBuffer.java.html
     */
    public static byte[] tryStuff() throws IOException {

        Tone tone = new SineTone(440, (float) 0.95);
        AudioInputStream toneStream = tone.getAudioInputStream(5);
        ByteArrayOutputStream bae = new ByteArrayOutputStream();
        int nBufferSize = 1024;//* tone.getAudioFormat().getFrameSize();
        /**
         * System.out.println(nBufferSize); System.out.println(nBufferSize /
         * 1024);
         */
        byte[] stuff = new byte[nBufferSize];
        while (true) {
            int nBytesRead = toneStream.read(stuff);
            if (nBytesRead == -1) {
                break;
            }
            bae.write(stuff, 0, nBytesRead);
        }
        byte[] toneData = bae.toByteArray();
        return toneData;
    }

    /**
     *
     * @param byteArray
     * @see
     * http://stackoverflow.com/questions/15533854/converting-byte-array-to-double-array
     * @return
     */
    public static double[] toDoubleArray(byte[] byteArray) {
        int times = Double.SIZE / Byte.SIZE;
        double[] doubles = new double[byteArray.length / times];
        for (int i = 0; i < doubles.length; i++) {
            doubles[i] = ByteBuffer.wrap(byteArray, i * times, times).getDouble();
        }
        return doubles;
    }

    public static void getPeak() throws DFTException, IOException {
        /**
         * Tone tone = new SineTone(440, (float) 0.95); int time = 5;//ask user
         * ffor number*
         */
        byte[] byteArray = tryStuff();
        double[] doubleArray = toDoubleArray(byteArray);
        Complex[] input = new Complex[doubleArray.length];
        System.out.println(doubleArray.length);
        for (int i = 0; i < input.length; i++) {
            input[i] = new Complex(doubleArray[i], 0);
        }

        Complex[] output = computeFFT(input);
        double magnitude = 0;
        double freq = 0;
        for (int i = 0; i < output.length; i = i + 2) {
            if (output[i].magnitude() > magnitude) {
                magnitude
                = output[i].magnitude();
                freq = i * 44100 / output.length;
                //http://stackoverflow.com/questions/7674877/how-to-get-frequency-from-fft-result
            }
            System.out.println("Peak frequency is: " + freq);

        }
    }
}
