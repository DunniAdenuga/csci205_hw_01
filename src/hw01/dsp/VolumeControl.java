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
package hw01.dsp;

import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

/**
 *
 * @author timothy
 */
public class VolumeControl extends AudioProcessor {

    private final float scaleFactor;

    public VolumeControl(InputStream in, AudioFormat format, float scaleFactor) {
        super(in, format);
        this.scaleFactor = scaleFactor;
    }

    public VolumeControl(AudioInputStream stream, float scaleFactor) {
        this(stream, stream.getFormat(), scaleFactor);
    }

    @Override
    protected void processAudio(float[] audioData) {
        for (int i = 0; i < audioData.length; i++) {
            audioData[i] *= scaleFactor;
        }
    }
}
