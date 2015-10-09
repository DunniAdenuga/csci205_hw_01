/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: Oct 8, 2015
 * Time: 3:00:11 PM
 *
 * Project: csci205_hw_01
 * Package: hw01.dsp
 * File: ConvolutionBuiler
 * Description:
 *
 * ****************************************
 */
package hw01.dsp;

import java.io.InputStream;
import javax.sound.sampled.AudioFormat;

/**
 * Builds discrete convolution-based filters
 *
 * @author tww014
 */
public class ConvolutionBuilder {

    /**
     * Create a new convolution-based delay that includes a single echo
     *
     * @param in The input stream to use for input
     * @param format The audio format
     * @param delayTime The number of seconds between the initial impulse and
     * the echo
     * @param delayMultiplier The volume percentage of the echo
     * @return A <code>ConvolutionProcessor</code> that produces a single echo
     */
    public static ConvolveProcessor simpleDelay(InputStream in,
                                                AudioFormat format,
                                                float delayTime,
                                                float delayMultiplier) {
        int sampleDelay = (int) (format.getFrameRate() * delayTime);
        float[] convolveVector = new float[sampleDelay + 1];
        convolveVector[0] = 1.0f;
        convolveVector[sampleDelay] = delayMultiplier;
        return new ConvolveProcessor(in, format, convolveVector);
    }

}
