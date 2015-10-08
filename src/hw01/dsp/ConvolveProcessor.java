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
package hw01.dsp;

import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import javax.sound.sampled.AudioFormat;

/**
 * Class for convolution-based filters
 *
 * @see http://mathworld.wolfram.com/Convolution.html
 * @author tww014
 */
public class ConvolveProcessor extends AudioProcessor {
    public final float[] convolveVector;
    private final ArrayBlockingQueue<Float> pastData;

    public ConvolveProcessor(InputStream in, AudioFormat format,
                             float[] convolveVector) {
        super(in, format);
        this.convolveVector = convolveVector;
        pastData = new ArrayBlockingQueue<>(convolveVector.length);
        Float[] initialValues = new Float[convolveVector.length];
        Arrays.fill(initialValues, 0.0f);
        pastData.addAll(Arrays.asList(initialValues));
    }

    @Override
    protected void processAudio(float[] audioData) {
        for (int t = 0; t < audioData.length; t++) {
            pastData.remove();
            pastData.add(audioData[t]);
            audioData[t] = convolve();
        }
    }

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
