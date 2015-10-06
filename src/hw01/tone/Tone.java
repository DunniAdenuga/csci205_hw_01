/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: 05-Oct-2015
 * Time: 08:44:03
 *
 * Project: csci205_hw_01
 * Package: hw01
 * File: Tone
 * Description:
 *
 * ****************************************
 */
package hw01.tone;

import javax.sound.sampled.SourceDataLine;

/**
 * A generator for a tone of a specific wave type, frequency, and amplitude
 *
 * @author Tim Woodford
 */
public abstract class Tone {
    /**
     * The frequency of the output in hertz
     */
    private final float frequency;

    /**
     * The amplitude of the value, on a scale of 0.0-1.0
     */
    private final float amplitude;

    /**
     * Sample rate in samples per second
     */
    private final int sampleRate;

    public Tone(float frequency, float amplitude, int sampleRate) {
        this.frequency = frequency;
        this.amplitude = amplitude;
        this.sampleRate = sampleRate;
    }

    public float getAmplitude() {
        return amplitude;
    }

    public float getFrequency() {
        return frequency;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public void writeLoop(SourceDataLine out) {
        int bufSize = out.getBufferSize();
        while (true) {
            out.write(getSampleData(bufSize), 0, bufSize);
        }
    }

    public abstract byte[] getSampleData(int length);
}
