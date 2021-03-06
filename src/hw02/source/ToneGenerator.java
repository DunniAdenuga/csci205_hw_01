/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: 05-Oct-2015
 * Time: 08:28:54
 *
 * Project: csci205_hw_01
 * Package: hw02

 * File: ToneGenerator
 * Description:
 *
 * ****************************************
 */
package hw02.source;

/**
 *
 * @author tww014
 */
public class ToneGenerator {

    /**
     * Play a tone. Code for opening the <code>SourceDataLine</code> is based on
     * code from www.wolinlabs.com.
     *
     * @see http://www.wolinlabs.com/blog/java.sine.wave.html
     * @param args Command line args
     * @author Tim Woodford
     * @throws java.io.IOException
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     * @throws javax.sound.sampled.LineUnavailableException
     */
    /**
     * public static void main(String[] args) throws LineUnavailableException,
     * IOException, UnsupportedAudioFileException, InterruptedException { Tone
     * tone = new SineTone(440, 1.0f);
     *
     * AudioFormat format = new AudioFormat(44100, 16, 1, true, true);
     * DataLine.Info info = new DataLine.Info(Clip.class, format); InputStream
     * inStr = new VolumeControl(tone.getInputStream(), format, 0.2f);
     * AudioInputStream stream = new AudioInputStream(inStr, format, 44100);
     *
     * try (Clip line = (Clip) AudioSystem.getLine(info)) { line.open(stream);
     * line.start(); Thread.sleep(4000); line.drain(); } }*
     */
}
