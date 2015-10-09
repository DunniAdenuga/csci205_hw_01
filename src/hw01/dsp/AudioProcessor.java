/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: Oct 8, 2015
 * Time: 2:25:40 PM
 *
 * Project: csci205_hw_01
 * Package: hw01.dsp
 * File: AudioProcessor
 * Description:
 *
 * ****************************************
 */
package hw01.dsp;

import java.io.FilterInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

/**
 * A signal processing filter that operates on a 1-dimensional audio input
 *
 * @author Tim Woodford
 */
public abstract class AudioProcessor extends FilterInputStream {

    protected int index = 0;
    private byte[] prev;
    protected final AudioFormat format;
    private final int bitScale;

    /**
     * Create a new audio processor with the specified input source
     *
     * @param in The input source
     * @param format The input format
     */
    public AudioProcessor(AudioInputStream in) {
        super(in);
        this.format = in.getFormat();
        bitScale = (int) ((1L << format.getSampleSizeInBits() / format.getChannels()) - 1);
    }

    /**
     * Collapse the bytes in the input into an array of floats
     *
     * @param b The bytes in the input
     * @param off The starting offset
     * @param len The length of the input
     * @return A float array corresponding to the inputs
     */
    private float[] collapse(byte[] b, int off, int len) {
        final int frameSize = format.getFrameSize() / format.getChannels();
        final int total = len / frameSize;
        final float[] ret = new float[total];
        final ByteBuffer buf = ByteBuffer.wrap(b, off, len);
        for (int i = 0; i < total; i++) {
            int curr = 0;
            switch (frameSize) {
                case 1:
                    ret[i] = buf.get() / (float) bitScale;
                    break;
                case 2:
                    ret[i] = buf.getShort() / (float) bitScale;
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
        return ret;
    }

    /**
     * Convert a float array back to bytes
     *
     * @param b The output byte array
     * @param off The offset to begin at
     * @param len The length of the data to write, in bytes
     * @param data The float array data
     */
    private void expand(byte[] b, int off, int len, float[] data) {
        final int frameSize = format.getFrameSize() / format.getChannels();
        int total = len / frameSize;
        ByteBuffer buf = ByteBuffer.wrap(b, off, len);
        for (int i = 0; i < total; i++) {
            int curr = (int) (data[i] * bitScale);
            switch (frameSize) {
                case 1:
                    buf.put((byte) curr);
                    break;
                case 2:
                    buf.putShort((short) curr);
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (len % format.getFrameSize() != 0) {
            throw new UnsupportedOperationException(
                    "Each frame of data must be read together");
        }
        len = in.read(b, off, len);
        if (len != -1) {
            float[] audioData = collapse(b, off, len);
            processAudio(audioData);
            expand(b, off, len, audioData);
            index += len;
        }
        return len;
    }

    @Override
    public int read() throws IOException {
        throw new UnsupportedOperationException(
                "Single-byte reads not supported");
    }

    /**
     * Get an audio stream corresponding to this input stream
     *
     * @param samples The length in samples
     * @return An <code>AudioInputStream</code> corresponding to this filter
     */
    public AudioInputStream getAudioStream(int samples) {
        return new AudioInputStream(this, this.format, samples);
    }

    /**
     * Processes a set of frames and updates their values in-place
     *
     * @param audioData The batch of audio to process and update
     */
    protected abstract void processAudio(float[] audioData);

}
