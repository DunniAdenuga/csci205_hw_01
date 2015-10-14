/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: Oct 14, 2015
 * Time: 2:55:18 AM
 *
 * Project: csci205_hw_01
 * Package: hw01.dsp
 * File: ConvolveProcessorTest
 * Description:
 *
 * ****************************************
 */
package hw01.dsp;

import hw01.source.SineTone;
import java.util.Arrays;
import javax.sound.sampled.AudioInputStream;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author timothy
 */
public class ConvolveProcessorTest {

    public ConvolveProcessorTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of processAudio method, of class ConvolveProcessor.
     */
    @Test
    public void testProcessAudio() {
        System.out.println("processAudio");
        float[] audioData = {1.0f, 0.0f, 0.0f, 0.0f};
        final AudioInputStream input = new SineTone(440, 1.0f).
                getAudioInputStream(1.0f);
        ConvolveProcessor instance = new ConvolveProcessor(input, new float[]{1.0f, 0.5f});
        instance.processAudio(audioData);
        assertEquals(true, Arrays.
                     equals(audioData, new float[]{1.0f, 0.5f, 0.0f, 0.0f}));
    }

}
