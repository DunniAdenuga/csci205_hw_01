/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: Oct 18, 2015
 * Time: 8:25:59 PM
 *
 * Project: csci205_hw_01
 * Package: hw02.fft
 * File: FFTCSVTest
 * Description:
 *
 * ****************************************
 */
package hw02.fft;

import hw02.dsp.AudioProcessor;
import hw02.source.SineTone;
import hw02.source.Tone;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.sound.sampled.AudioInputStream;

/**
 * A test of the FFT algorithm that outputs the results to CSV
 *
 * @author Tim Woodford
 */
public class FFTCSVTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, DFTException {
        byte[] byteArray = getSineData();
        //double[] doubleArray = toDoubleArray(byteArray);
        float[] floatArray = AudioProcessor.byteArrayToFloats(byteArray, 0,
                                                              byteArray.length,
                                                              2);

        Complex[] input = new Complex[floatArray.length];
        for (int i = 0; i < input.length; i++) {
            input[i] = new Complex(floatArray[i], 0);
        }

        Complex[] output = FFT.computeFFT(input);

        float[] frequencies = FFT.frequenciesFFT(44100, output.length);
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(
                "sinefft.csv"))) {
            for (int i = 0; i < frequencies.length; i++) {
                writer.append(Float.toString(frequencies[i])).append(",");
                writer.append(Double.toString(output[i].real)).append(",");
                writer.append(Double.toString(output[i].imaginary));
                writer.newLine();
            }
        }
    }

    /**
     * Read the data from a sine wave into a byte array
     *
     * @see http://www.jsresources.org/examples/AudioDataBuffer.java.html
     * @author Dunni Adenuga
     */
    public static byte[] getSineData() throws IOException {

        Tone tone = new SineTone(10, (float) 0.95);
        AudioInputStream toneStream = tone.getAudioInputStream(5);
        int nBufferSize = 1024 * toneStream.getFormat().getFrameSize();
        ByteArrayOutputStream bae = new ByteArrayOutputStream(nBufferSize);

        System.out.println(nBufferSize);
        System.out.println(nBufferSize / 1024);

        byte[] stuff = new byte[nBufferSize];
        while (true) {
            int nBytesRead = toneStream.read(stuff);
            if (nBytesRead == -1) {
                break;
            }
            bae.write(stuff, 0, nBytesRead);
        }
        byte[] toneData = bae.toByteArray();
        return stuff;
    }
}
