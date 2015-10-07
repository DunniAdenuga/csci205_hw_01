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
import java.nio.ByteBuffer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author tww014
 */
public class ToneGenerator {
    /*/**
     *
     * @param args
     * @throws LineUnavailableException
     * @author Tim Woodford
     * @throws java.io.IOException
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     */

    public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException, InterruptedException {
        //Clip output = AudioSystem.getClip();
        //output.open(AudioSystem.getAudioInputStream(new File("0001.wav")));
        SourceDataLine output = AudioSystem.getSourceDataLine(new AudioFormat(
                44100, 16, 1, true, true));
        Tone tone = new SawtoothTone(440, 1.0f);
        output.start();
        tone.writeLoop(output);
        Thread.sleep(4000);
        output.drain();
        output.close();

        final int SAMPLING_RATE = 44100;            // Audio sampling rate
        final int SAMPLE_SIZE = 2;                  // Audio sample size in bytes

        SourceDataLine line;
        double fFreq = 440;                         // Frequency of sine wave in hz

        //Position through the sine wave as a percentage (i.e. 0 to 1 is 0 to 2*PI)
        double fCyclePosition = 0;
        //Open up audio output, using 44100hz sampling rate, 16 bit samples, mono, and big 
        // endian byte ordering
        AudioFormat format = new AudioFormat(SAMPLING_RATE, 16, 1, true, true);
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("Line matching " + info + " is not supported.");
            throw new LineUnavailableException();
        }

        line = (SourceDataLine) AudioSystem.getLine(info);
        line.open(format);
        line.start();
        line.addLineListener(new LineListener() {
            @Override
            public void update(LineEvent event) {
                System.out.println(event);
            }
        });

        // Make our buffer size match audio system's buffer
        ByteBuffer cBuf = ByteBuffer.allocate(line.getBufferSize());

        int ctSamplesTotal = (int) (format.getFrameRate() * 5);         // Output for roughly 5 seconds
        float sampleRate = line.getFormat().getFrameRate();

        tone.writeLoop(line);

      //Done playing the whole waveform, now wait until the queued samples finish 
        //playing, then clean up and exit
        line.drain();
        line.close();
    }
}
