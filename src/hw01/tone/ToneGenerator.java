/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: 05-Oct-2015
 * Time: 08:28:54
 *
 * Project: csci205_hw_01
 * Package: hw01
 * File: ToneGenerator
 * Description:
 *
 * ****************************************
 */
package hw01.tone;

import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author tww014
 */
public class ToneGenerator {
    /**
     *
     * @param args
     * @throws LineUnavailableException
     * @author Tim Woodford
     * @throws java.io.IOException
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     */
    public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException, InterruptedException {
        Clip output = AudioSystem.getClip();
        //output.open(AudioSystem.getAudioInputStream(new File("0001.wav")));
        SourceDataLine datLine = AudioSystem.getSourceDataLine(new AudioFormat(
                44100, 16, 1, true, true));
        SineTone tone = new SineTone(5000, 0.5f);
        output.start();
        tone.writeLoop(datLine);
        Thread.sleep(4000);
        output.stop();
        output.close();
    }
}
