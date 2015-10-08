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
import java.nio.ByteBuffer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

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
        bitScale = (int) ((1L << format.getSampleSizeInBits() / format.getChannels()) - 1);
    }

    protected float[] collapse(byte[] b, int off, int len) {
        final int frameSize = format.getFrameSize() / format.getChannels();
        final int total = len / frameSize;
        final float[] ret = new float[total];
        final ByteBuffer buf = ByteBuffer.wrap(b);
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

    protected void expand(byte[] b, int off, int len, float[] data) {
        final int frameSize = format.getFrameSize() / format.getChannels();
        int total = len / frameSize;
        ByteBuffer buf = ByteBuffer.wrap(b);
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
            throw new RuntimeException("FDSJLFDKJSLKJFL");
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

    public AudioInputStream getAudioStream(int samples) {
        return new AudioInputStream(this, this.format, samples);
    }

    protected abstract void processAudio(float[] audioData);

}
