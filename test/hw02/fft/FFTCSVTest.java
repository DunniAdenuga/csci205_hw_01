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

import hw02.source.SineTone;
import java.io.BufferedWriter;
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
        AudioInputStream audio = new SineTone(10, (float) 0.95).getAudioInputStream(
                40);

        Complex[] output = FFT.computeFFT(audio);

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
}
