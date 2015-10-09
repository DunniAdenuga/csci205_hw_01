/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: Oct 7, 2015
 * Time: 11:35:17 PM
 *
 * Project: csci205_hw_01
 * Package: hw01.dsp
 * File: VolumeControl
 * Description:
 *
 * ****************************************
 */
package hw01.tone;

import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

/**
 * Multiplies the amplitude of the signal
 *
 * @author Tim Woodford
 */
public class VolumeControl extends AudioProcessor {

    private final float scaleFactor;

    /**
     * Create a new <code>VolumeControl</code> processor
     *
     * @param in The input data
     * @param format The input format
     * @param scaleFactor The factor to multiply the amplitudes by
     */
    public VolumeControl(InputStream in, AudioFormat format, float scaleFactor) {
        super(in, format);
        this.scaleFactor = scaleFactor;
    }

    /**
     * Create a new <code>VolumeControl</code> processor
     *
     * @param stream The audio input
     * @param scaleFactor The factor to multiply the amplitudes by
     */
    public VolumeControl(AudioInputStream stream, float scaleFactor) {
        this(stream, stream.getFormat(), scaleFactor);
    }

    /**
     * Scales the amplitude by a specified factor
     *
     * @param audioData The audio data to process
     */
    @Override
    protected void processAudio(float[] audioData) {
        for (int i = 0; i < audioData.length; i++) {
            audioData[i] *= scaleFactor;
        }
    }
}
