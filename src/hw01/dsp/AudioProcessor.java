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
import java.io.InputStream;
import java.util.Arrays;
import javax.sound.sampled.AudioFormat;

/**
 *
 * @author timothy
 */
public abstract class AudioProcessor extends FilterInputStream {

    protected int index = 0;
    protected final AudioFormat format;
    private final int bitScale;

    public AudioProcessor(InputStream in, AudioFormat format) {
        super(in);
        this.format = format;
        bitScale = (int) (1L << format.getSampleSizeInBits() - 1);
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
     * @param b The byte array to write to
     * @param position The position at which to start
     * @param data The data to write
     * @param length The number of bytes to write
     */
    public static void writeBytes(byte[] b, int position, int data, int length) {
        int start = length * position;
        for (int i = length - 1; i >= 0; i--) {
            b[start + i] = (byte) (data & 0xff);
            data >>= 8;
        }
    }

    protected float[] collapse(byte[] b, int off, int len) {
        final int frameSize = format.getFrameSize();
        int total = len / frameSize;
        float[] ret = new float[total];
        for (int i = 0; i < total; i++) {
            int curr = 0;
            // Necessary for two's complement...
            if ((b[frameSize * i] & (1 << 8)) != 0) {
                curr = 0xffffffff;
            }
            for (int j = 0; j < frameSize; j++) {
                curr <<= 8;
                curr ^= b[frameSize * i + j] & 0xff;
            }
            ret[i] = curr / (float) bitScale;
        }
        return ret;
    }

    protected void expand(byte[] b, int off, int len, float[] data) {
        final int frameSize = format.getFrameSize();
        int total = len / frameSize;
        for (int i = 0; i < total; i++) {
            int curr = (int) (data[i] * bitScale);
            writeBytes(b, i, curr, frameSize);
        }
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (len % format.getFrameSize() != 0) {
            throw new RuntimeException("FDSJLFDKJSLKJFL");
        }
        len = in.read(b, off, len);
        if (len != -1) {
            float[] audioData = collapse(b, off, len);
            System.out.println(Arrays.toString(audioData));
            processAudio(audioData);
            expand(b, off, len, audioData);
            index += len;
        }
        return len;
    }

    protected abstract void processAudio(float[] audioData);

}
