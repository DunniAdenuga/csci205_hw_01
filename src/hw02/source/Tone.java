/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: 05-Oct-2015
 * Time: 08:44:03
 *
 * Project: csci205_hw_01
 * Package: hw02
 * File: Tone
 * Description:
 *
 * ****************************************
 */
package hw02.source;

import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

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
     * Default output format - 44100Hz, 16bits, big endian
     */
    private static final AudioFormat DEFAULT_FORMAT = new AudioFormat(44100, 16,
                                                                      1, true,
                                                                      true);

    /**
     * Create a new tone generator
     *
     * @param frequency The frequency of the output wave in hertz
     * @param amplitude The amplitude of the output, on a scale of 0.0-1.0
     */
    public Tone(float frequency, float amplitude) {
        this.frequency = frequency;
        this.amplitude = amplitude;
    }

    /**
     * Get the output amplitude
     *
     * @return The amplitude of the output, on a scale of 0.0-1.0
     */
    public float getAmplitude() {
        return amplitude;
    }

    /**
     * Get the output frequency
     *
     * @return The frequency of the output wave in hertz
     */
    public float getFrequency() {
        return frequency;
    }

    /**
     * Get the sample value at the given time
     *
     * @param time The time since the beginning of the period
     * @return The amplitude-adjusted sample size, on a scale of 0.0-1.0
     */
    public abstract double getSample(double time);

    /**
     * An InputStream that outputs linear PCM-encoded audio data
     *
     * @author Tim Woodford
     */
    private class ToneInputStream extends InputStream {
        private final AudioFormat outFormat;
        private final int bitScale;
        private int index = 0;

        /**
         * Create a new ToneInputStream that starts at time=0
         *
         * @param format The AudioFormat to use for output
         */
        public ToneInputStream(AudioFormat format) {
            outFormat = format;
            bitScale = (int) ((1L << outFormat.getSampleSizeInBits()) - 1);
        }

        @Override
        public int read() throws IOException {
            // Probably not the best possible performance
            final float time = (index / outFormat.getFrameSize()) / outFormat.getSampleRate();
            final int sample = (int) (bitScale * getSample(time));
            final int shift = (outFormat.getFrameSize() - index % outFormat.getFrameSize() - 1) * 8;
            index++;
            return (sample >> shift) & 0xff;
        }
    }

    /**
     * Get an input stream that outputs Linear PCM-encoded
     *
     * @return LPCM-encoded audio data
     */
    public InputStream getInputStream(AudioFormat format) {
        return new ToneInputStream(format);
    }

    /**
     * Create a new AudioInputStream with LPCM-encoded data
     *
     * @param format The AudioFormat to use
     * @param length The number of samples to output
     * @return An LPCM-encoded AudioInputStream
     */
    public AudioInputStream getAudioInputStream(AudioFormat format, int length) {
        return new AudioInputStream(getInputStream(format), format, length);
    }

    /**
     * Create a new LPCM-encoded AudioInputStream that lasts for a certain
     * amount of time
     *
     * @param time The time to play the tone in seconds
     * @return The LPCM-encoded stream
     */
    public AudioInputStream getAudioInputStream(float time) {
        return getAudioInputStream(DEFAULT_FORMAT,
                                   (int) (time * DEFAULT_FORMAT.getFrameRate()));
    }
}
