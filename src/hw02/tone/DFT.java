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

import hw02.dsp.AudioProcessor;
import hw02.fft.Complex;
import hw02.source.SineTone;
import hw02.source.Tone;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
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
 * @author Dunni Adenuga
 * @see http://introcs.cs.princeton.edu/java/97data/FFT.java.html
 * @see
 * http://www.nayuki.io/res/how-to-implement-the-discrete-fourier-transform/Dft.java
 */
public class DFT {
    public static void main(String[] args) throws DFTException, IOException {
        Tone tone = new SineTone(600, (float) 0.95);
        getPeak(tone, 5);
    }

    /**
     * Perform Fast Fourier
     *
     * @param x - input
     * @return
     * @throws DFTException
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
        Complex[] even = new Complex[N / 2];
        for (int i = 0; i < N / 2; i++) {
            even[i] = x[(2 * i)];
        }
        Complex[] result2 = computeFFT(even);

        Complex[] odd = new Complex[N / 2];
        for (int i = 0; i < N / 2; i++) {
            odd[i] = x[(2 * i) + 1];
        }
        Complex[] result1 = computeFFT(odd);

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
     * Read tone into memory in a bytes array
     *
     * @param tone - tone to be read in
     * @return - array of bytes
     * @see http://www.jsresources.org/examples/AudioDataBuffer.java.html
     */
    public static byte[] getByteArray(Tone tone) throws IOException {

        AudioInputStream toneStream = tone.getAudioInputStream(1);
        ByteArrayOutputStream bae = new ByteArrayOutputStream();
        int nBufferSize = 1024 * tone.getAudioFormat().getFrameSize();
        //System.out.println(tone.getAudioFormat().getSampleRate());
        //System.out.println(nBufferSize);
        //System.out.println(nBufferSize/ 1024);

        byte[] stuff = new byte[nBufferSize];
        while (true) {
            int nBytesRead = toneStream.read(stuff);
            if (nBytesRead == -1) {
                break;
            }
            bae.write(stuff, 0, nBytesRead);
        }
        //byte[] toneData = bae.toByteArray();
        return stuff;
    }

    public static void getPeak(Tone tone, int numPeak) throws DFTException, IOException {

        byte[] byteArray = getByteArray(tone);
        float[] floatArray = AudioProcessor.byteArrayToFloats(byteArray, 0,
                                                              byteArray.length,
                                                              2);
        Complex[] input = new Complex[floatArray.length];

        for (int i = 0; i < input.length; i++) {
            input[i] = new Complex(floatArray[i], 0);
        }

        Complex[] output = computeFFT(input);
        double mag = 0;
        double[] magnitude = new double[output.length / 2];
        double[] freq = new double[output.length / 2];
        double peakFreq = 0.0;
        for (int i = 0; i < output.length / 2; i++) {
            //System.out.print(output[i].magnitude());
            /*if (output[i].magnitude() > mag) {
             mag = output[i].magnitude();
             peakFreq
             = i * 44100 / output.length;*/

            //}
            //System.out.print("stuff");
            magnitude[i] = output[i].magnitude();
            //http://stackoverflow.com/questions/7674877/how-to-get-frequency-from-fft-result

        }
        Arrays.sort(magnitude);
        for (int i = 0; i < magnitude.length / 2; i++) {
            if (output[i].magnitude() > mag) {
                mag = output[i].magnitude();
                peakFreq
                = i * 44100 / output.length;

            }
            freq[i] = i * 44100 / output.length;
        }
        System.out.print("Peak values are: ");
        for (int i = 0; i < numPeak; i++) {
            if (i < magnitude.length) {
                System.out.print(freq[i] + " Hz ");
            } else {
                break;
            }
        }
        System.out.println();

        System.out.println("Peak frequency is: " + peakFreq + " Hz");
    }

}
