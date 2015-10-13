/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni, Tim
 * Date: Oct 13, 2015
 * Time: 10:40:22 AM
 *
 * Project: csci205_hw_01
 * Package: hw01.tone
 * File: WavePlayTest
 * Description:
 *
 * ****************************************
 */
package hw01.tone;

import hw01.source.SineTone;
import hw01.source.Tone;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author ia005
 */
public class WavePlayTest {

    public WavePlayTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of playFile method, of class WavePlay.
     */
    @Test(expected = IOException.class)//once IO is thrown line/thread can't work
    public void testPlayFile_String() throws Exception {
        System.out.println("playFile");
        String wavefile = "nothing.wav";
        WavePlay.playFile(wavefile);
    }

    /**
     * Test of playFile method, of class WavePlay.
     */
    @Test(expected = Exception.class)//once IO is thrown line/thread can't work
    public void testPlayFile_Tone_int() throws Exception {
        System.out.println("playFile");
        Tone tone = null;
        int time = 2;
        WavePlay.playFile(tone, time);

    }

    /**
     * Test of playFile method, of class WavePlay.
     */
    @Test(expected = Exception.class)
    public void testPlayFile_AudioInputStream() throws Exception {
        System.out.println("playFile");
        AudioInputStream audio = null;//since this is null, expecting all kinds of Exception
        WavePlay.playFile(audio);

    }

    /**
     * Test of display method, of class WavePlay.
     */
    @Test(expected = IOException.class)
    public void testDisplay_String() throws IOException {
        System.out.println("display");
        String wavefile = "nothing.wav";
        WavePlay.display(wavefile);

    }

    /**
     * Test of display method, of class WavePlay.
     */
    @Test(expected = Exception.class)
    public void testDisplay_AudioInputStream() throws Exception {
        System.out.println("display");
        AudioInputStream audio = null;
        WavePlay.display(audio);

    }

    /**
     * Test of downsample method, of class WavePlay.
     */
    @Test
    public void testDownsample_4args() throws IOException {
        System.out.println("downsample");
        Tone tone = new SineTone(600, (float) 1.0);
        String type = "2";
        int time = 5;
        double freq = 25;
        Tone expResult = new SineTone((float) (600 * 0.75), (float) 1);
        Tone result = WavePlay.downsample(tone, type, time, freq);
        assertEquals(expResult.getFrequency(), result.getFrequency(), 1E-10);

    }

    /**
     * Test of downsample method, of class WavePlay.
     */
    @Test
    public void testDownsample_String_double() throws IOException, UnsupportedAudioFileException {
        System.out.println("downsample");
        String wavfile = "sample14.wav";
        double freq = 25.0;
        File audiofile = new File(wavfile);

        AudioInputStream audio = AudioSystem.getAudioInputStream(audiofile);
        AudioFormat srcFormat = audio.getFormat();
        AudioFormat dstFormat = new AudioFormat(srcFormat.getEncoding(),
                                                (float) (srcFormat.getSampleRate() * (1 - (freq / 100))),
                                                srcFormat.getSampleSizeInBits(),
                                                srcFormat.getChannels(),
                                                srcFormat.getFrameSize(),
                                                srcFormat.getFrameRate(),
                                                srcFormat.isBigEndian());

        AudioInputStream expResult = AudioSystem.getAudioInputStream(dstFormat,
                                                                     audio);
        AudioInputStream result = WavePlay.downsample(wavfile, freq);
        assertEquals(expResult.getFormat().getSampleRate(),
                     result.getFormat().getSampleRate(), 1E-10);

    }

    /**
     * Test of downsample method, of class WavePlay.
     */
    @Test(expected = FileNotFoundException.class)
    public void testDownsample_String_doubleExcep() throws IOException, UnsupportedAudioFileException {
        System.out.println("downsample");
        String wavfile = "test.wav";
        double freq = 25.0;
        File audiofile = new File(wavfile);

        AudioInputStream audio = AudioSystem.getAudioInputStream(audiofile);
    }

    /**
     * Test of saveWav method, of class WavePlay.
     */
    @Test(expected = Exception.class)
    public void testSaveWav() throws Exception {
        System.out.println("saveWav");
        AudioInputStream converted = null;
        String newFile = "";
        WavePlay.saveWav(converted, newFile);

    }

}
