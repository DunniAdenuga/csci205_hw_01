/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: Oct 14, 2015
 * Time: 2:48:57 AM
 *
 * Project: csci205_hw_01
 * Package: hw01.dsp
 * File: ConvolutionBuilderTest
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
public class ConvolutionBuilderTest {

    public ConvolutionBuilderTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of simpleDelay method, of class ConvolutionBuilder.
     */
    @Test
    public void testSimpleDelay() {
        System.out.println("simpleDelay");
        AudioInputStream in = new SineTone(440, 1.0f).getAudioInputStream(1.0f);
        float delayTime = 1 / 44100f;
        float delayMultiplier = 1.0F;
        ConvolveProcessor expResult = null;
        ConvolveProcessor result = ConvolutionBuilder.
                simpleDelay(in, delayTime, delayMultiplier);
        assertEquals(true, Arrays.
                     equals(new float[]{1.0f, 1.0f}, result.convolveVector));
    }

}
