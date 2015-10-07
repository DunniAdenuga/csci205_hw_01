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
package hw01.source;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
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
     * Writes audio data to a <code>SourceDataLine</code>, using a linear PCM
     *
     * @param out The <code>SourceDataLine</code> to write to
     * @param duration The length of the output in seconds
     * @throws UnsupportedOperationException If the audio format is not
     * supported
     */
    public void writeLoop(SourceDataLine out, double duration) {
        checkAudioFormat(out);
        float sampleRate = out.getFormat().getFrameRate();
        int period = (int) (2 * sampleRate / getFrequency()); // Unit: samples
        int samplesWritten = 0;
        while (samplesWritten < duration * sampleRate) {
            int outSize = out.available() / 2;
            // Make outSize a multiple of period length to prevent jumps in output
            outSize -= outSize % period;
            byte[] array = getSampleData(outSize, out.getFormat());
            out.write(array, 0, array.length);
            samplesWritten += array.length;
            // Wait for space to become available
            while (out.available() < out.getBufferSize() / 2) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                }
            }
        }
    }

    /**
     * Check that the audio format is supported as an output
     *
     * @param out The output format
     * @throws UnsupportedOperationException If the audio format is unsupported
     */
    private void checkAudioFormat(SourceDataLine out) throws UnsupportedOperationException {
        if (out.getFormat().getChannels() != 1) {
            throw new UnsupportedOperationException(
                    "SourceDataLine must have 1 channel");
        }
        if (out.getFormat().getFrameSize() > 4) {
            throw new UnsupportedOperationException(
                    "Output audio format maximum precision is 32 bits");
        }
        if (!out.getFormat().isBigEndian()) {
            throw new UnsupportedOperationException(
                    "Output audio format must be big-endian");
        }
        if (out.getFormat().getEncoding() != Encoding.PCM_SIGNED) {
            throw new UnsupportedOperationException(
                    "Output must be linear signed PCM");
        }
    }

    /**
     * Get signed PCM data to write to an audio stream
     *
     * @param length The number of samples to get
     * @param outFormat The audio format to use for output
     * @return An array of bytes representing the requested number of samples
     */
    private byte[] getSampleData(int length, AudioFormat outFormat) {
        int bytesPerFrame = outFormat.getFrameSize();
        byte[] ret = new byte[length * bytesPerFrame];
        float sampleRate = outFormat.getSampleRate();
        int bitScale = (int) (1L << outFormat.getSampleSizeInBits() - 1);
        for (int t = 0; t < length; t++) {
            writeBytes(ret, t, (int) (bitScale * getSample(t / sampleRate)),
                       bytesPerFrame);
        }
        return ret;
    }

    /**
     * Write a certain number of bytes to the output stream
     *
     * This is loosely based on code from the JPEG encoder in Piston's rust
     * image library.
     *
     * @see
     * https://github.com/PistonDevelopers/image/blob/master/src/jpeg/encoder.rs
     *
     * @param array The array to write to
     * @param position The position at which to start
     * @param data The data to write
     * @param length The number of bytes to write
     */
    private static void writeBytes(byte[] array, int position, int data,
                                   int length) {
        int start = length * position;
        for (int i = length - 1; i >= 0; i--) {
            array[start + i] = (byte) (data & 0xff);
            data >>= 8;
        }
    }

    /**
     * Get the sample value at the given time
     *
     * @param time The time since the beginning of the period
     * @return The amplitude-adjusted sample size, on a scale of 0.0-1.0
     */
    public abstract double getSample(double time);
}
