/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: Oct 8, 2015
 * Time: 2:46:03 PM
 *
 * Project: csci205_hw_01
 * Package: hw01.dsp
 * File: ConvolveProcessor
 * Description:
 *
 * ****************************************
 */
package hw01.tone;

import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import javax.sound.sampled.AudioFormat;

/**
 * Class for finite impulse response filters based on a convolution
 *
 * This can be used for convolution-based reverb if you have an impulse response
 * file
 *
 * @see http://mathworld.wolfram.com/Convolution.html
 * @see http://dspguru.com/dsp/faqs/fir/basics
 * @author Tim Woodford
 */
public class ConvolveProcessor extends AudioProcessor {
    public final float[] convolveVector;
    private final ArrayBlockingQueue<Float> pastData;

    /**
     * Create a <code>ConvolveProcessor</code> with the specified input and
     * convolution function
     *
     * @param in The input stream for audio data
     * @param format The format of the audio data
     * @param convolveVector The convolution function values
     */
    public ConvolveProcessor(InputStream in, AudioFormat format,
                             float[] convolveVector) {
        super(in, format);
        this.convolveVector = convolveVector;
        pastData = new ArrayBlockingQueue<>(convolveVector.length);
        Float[] initialValues = new Float[convolveVector.length];
        Arrays.fill(initialValues, 0.0f);
        pastData.addAll(Arrays.asList(initialValues));
    }

    /**
     * Convolve each point in time with the convolution vector
     *
     * @param audioData The audio data to update
     */
    @Override
    protected void processAudio(float[] audioData) {
        for (int t = 0; t < audioData.length; t++) {
            pastData.remove();
            pastData.add(audioData[t]);
            audioData[t] = convolve();
        }
    }

    /**
     * Convolve the current data queue with the convolution function
     *
     * @return The convolution result for the current time
     */
    private float convolve() {
        float ret = 0;
        int t = convolveVector.length;
        Float[] audio = pastData.toArray(new Float[1]);
        for (int tau = 0; tau < t; tau++) {
            ret += convolveVector[t - tau - 1] * audio[tau];
        }
        return ret;
    }
}
