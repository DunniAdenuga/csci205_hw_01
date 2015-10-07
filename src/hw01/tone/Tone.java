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

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
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

    public float getAmplitude() {
        return amplitude;
    }

    public float getFrequency() {
        return frequency;
    }

    /**
     * Writes audio data to a <code>SourceDataLine</code>, using a linear PCM
     *
     * @param out THE <code>SourceDataLine</code> to write to
     * @throws UnsupportedOperationException If the audio format is not
     * supported
     */
    public void writeLoop(SourceDataLine out) {
        float sampleRate = out.getFormat().getFrameRate();
        int bits = out.getFormat().getSampleSizeInBits();
        if (out.getFormat().getChannels() != 1) {
            throw new UnsupportedOperationException(
                    "SourceDataLine must have 1 channel");
        }
        if (out.getFormat().getFrameSize() > 4) {
            throw new UnsupportedOperationException(
                    "Output audio format maximum precision is 32 bits");
        }
        //int outSize = 22050 
        for(int i=0; i<1000; i++) {
            int outSize = out.available() / 2;
            byte[] array = getSampleData(outSize, out.getFormat());
            out.write(array, 0, array.length);
            while (out.getBufferSize()/2 < out.available())   
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Tone.class.getName()).log(Level.SEVERE, null, ex);
                } 
        }
    }

    public byte[] getSampleData(int length, AudioFormat outFormat) {
        int bytesPerFrame = outFormat.getFrameSize();
        byte[] ret = new byte[length * bytesPerFrame];
        float sampleRate = outFormat.getSampleRate();
        int bitScale = (int) (1L << outFormat.getSampleSizeInBits() - 1);
        for (int t = 0; t < length; t++) {
            writeBits(ret, t, (int) (bitScale * getSample(t / sampleRate)),
                    bytesPerFrame);
        }
        return ret;
    }

    private static void writeBits(byte[] array, int position, int data,
            int length) {
        int start = length * position;
        for (int i = length - 1; i >= 0; i--) {
            array[start + i] = (byte) (data & 0xff);
            data >>= 8;
        }
    }

    public abstract double getSample(double time);
}
