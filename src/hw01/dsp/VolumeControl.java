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

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

/**
 *
 * @author timothy
 */
public class VolumeControl extends FilterInputStream {

    private int index = 0;
    private final AudioFormat format;
    private final int bitScale;
    private final float scaleFactor;

    public VolumeControl(InputStream in, AudioFormat format, float scaleFactor) {
        super(in);
        this.format = format;
        bitScale = (int) (1L << format.getSampleSizeInBits() - 1);
        this.scaleFactor = scaleFactor;
    }

    public VolumeControl(AudioInputStream stream, float scaleFactor) {
        this(stream, stream.getFormat(), scaleFactor);
    }

    @Override
    public int read() {
        throw new RuntimeException("I don't want this to happen");
    }

    private float[] collapse(byte[] b, int off, int len) {
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
            if (ret[i] > 1) {
                System.out.println(curr);
                System.out.println(Integer.toBinaryString(curr));
            }
        }
        return ret;
    }

    private void expand(byte[] b, int off, int len, float[] data) {
        final int frameSize = format.getFrameSize();
        int total = len / frameSize;
        for (int i = 0; i < total; i++) {
            int curr = (int) (data[i] * bitScale);
            writeBytes(b, i, curr, frameSize);
        }
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
    public static void writeBytes(byte[] b, int position, int data,
                                  int length) {
        int start = length * position;
        for (int i = length - 1; i >= 0; i--) {
            b[start + i] = (byte) (data & 0xff);
            data >>= 8;
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
            for (int i = 0; i < audioData.length; i++) {
                audioData[i] *= scaleFactor;
            }
            expand(b, off, len, audioData);
            index += len;
        }
        return len;
    }
}
