/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2015
 *
 * Name: Brian King
 * Date: Oct 21, 2015
 * Time: 9:05:50 PM

 * Package: hw03.model
 * File: WaveForm
 *
 * Description:
 * The WaveForm class is a key class that represents a waveform. A waveform
 * can be read in from a WAV file, or generated using a specific tone
 * ****************************************
 */
package hw03.model;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * WaveForm - a class designed to represent a waveform. WaveForm instances can
 * be instantiated in several ways. See the individual constructors for more
 * detail
 *
 * @author brk009
 */
public class WaveForm {

    /**
     * Lowest level data in the waveform
     */
    private byte[] b;

    /**
     * A ByteBuffer wrapper for the underlying byte array b
     */
    private ByteBuffer byteBufferWrapper;

    /**
     * A ShortBuffer wrapper for the underlying byte array b. This is important
     * for 16-bit sample size data
     */
    private ShortBuffer shortBufferWrapper;

    /**
     * AudioFormat of the waveform
     */
    private AudioFormat format;

    /**
     * sample size - 8-bits or 16-bits
     */
    private SampleSizeType sampleSize;

    /**
     * Create a new WaveForm instance from an audio .WAV file
     *
     * @param inFile
     */
    public WaveForm(File inFile) throws UnsupportedAudioFileException, IOException, WaveFormException {
        this(AudioSystem.getAudioInputStream(inFile));
    }

    /**
     * Create a new WaveForm instance from an AudioInputStream
     *
     * @param inStream
     */
    public WaveForm(AudioInputStream inStream) throws IOException, WaveFormException {
        // Get the AudioFormat object
        this.format = inStream.getFormat();

        initBuffers(
                (int) inStream.getFrameLength() * inStream.getFormat().getFrameSize());

        // The hard work -- read in the array of bytes from the AudioInputStream!
        inStream.read(b);

        // Update the sample size depending on the actual sample size reported
        // from th AudioFormat instance
        this.sampleSize = SampleSizeType.numBitsToSampleSize(
                this.format.getSampleSizeInBits());
    }

    /**
     * Generate a MONO sinusoidal wave at a fixed frequency. Makes for a good
     * test for the DFT!
     *
     * @param freq of tone to generate (Hz)
     * @param sampleRate sample rate of waveform to generate (Hz)
     * @param sampleSize Number of bits per sample
     * @param lengthInSec Duration of waveform (sec)
     *
     * @return A buffered AudioInputStream object
     */
    public WaveForm(float freq, float sampleRate, SampleSizeType sampleSize,
                    double lengthInSec) throws WaveFormException {
        // We are hard coding a mono wave file
        this.format = new AudioFormat(sampleRate,
                                      sampleSize.getSampleSizeInBits(), 1,
                                      true, false);
        this.sampleSize = sampleSize;

        // We'll generate a mono tone
        int bytesPerFrame = sampleSize.getSampleSizeInBytes() * this.format.getChannels();
        double sampleDepth = Math.pow(2, sampleSize.getSampleSizeInBits() - 1);
        int numFrames = (int) (lengthInSec * sampleRate);
        initBuffers(numFrames * bytesPerFrame);
        for (int i = 0; i < numFrames; i++) {
            double sampleTimeStamp = i / sampleRate;
            double sampVal = Math.sin(2 * Math.PI * freq * sampleTimeStamp);
            short val = (short) Math.round(sampVal * sampleDepth * 0.95);
            if (this.sampleSize == SampleSizeType.EIGHT_BIT) {
                this.byteBufferWrapper.put((byte) val);
            } else {
                this.shortBufferWrapper.put(val);
            }
        }
    }

    /**
     * Create a new waveform from a ShortBuffer. A new internal copy of the
     * buffer is created, leaving the original alone. The sample depth is
     * assumed to be 16-bit.
     *
     * @param sb
     * @param format
     */
    public WaveForm(ShortBuffer sb, AudioFormat format) {
        this.format = new AudioFormat(format.getSampleRate(),
                                      format.getSampleSizeInBits(),
                                      format.getChannels(), true,
                                      format.isBigEndian());
        this.sampleSize = SampleSizeType.SIXTEEN_BIT;
        initBuffers(sb.limit() * 2);
        this.shortBufferWrapper.put(sb);
    }

    /**
     * Create a new WaveForm from a ByteBuffer. A new internal copy of the
     * buffer is created, leaving the original alone. The sample depth is
     * assumed to be 8-bit.
     *
     * @param b
     * @param format
     */
    public WaveForm(ByteBuffer b, AudioFormat format) throws WaveFormException {
        this.format = new AudioFormat(format.getSampleRate(),
                                      format.getSampleSizeInBits(),
                                      format.getChannels(), true,
                                      format.isBigEndian());
        this.sampleSize = SampleSizeType.EIGHT_BIT;
        if (!b.hasArray()) {
            throw new WaveFormException(
                    "WaveForm(ByteBuffer, AudioFormat) - ByteBuffer has no backing array!");
        }

        // Initialize the buffers
        initBuffers(b.limit());

        // Copy the array over to our waveform
        for (int i = 0; i < b.limit(); i++) {
            this.b[i] = b.get(i);
        }
    }

    /**
     * A private helper method for the constructors. It does nothing more than
     * sets up the different buffer types required for the program.
     *
     * @param newBufLen - length of internal buffer in bytes
     */
    private void initBuffers(int newBufLen) {
        // OK, now let's wrap it with a ByteBuffer and ShortBuffer
        this.b = new byte[newBufLen];
        this.byteBufferWrapper = ByteBuffer.wrap(b);
        if (this.format.isBigEndian()) {
            this.byteBufferWrapper.order(ByteOrder.BIG_ENDIAN);
        } else {
            this.byteBufferWrapper.order(ByteOrder.LITTLE_ENDIAN);
        }
        this.shortBufferWrapper = this.byteBufferWrapper.asShortBuffer();
    }

    /**
     * ********************************************************************
     * Getter methods
     * ********************************************************************
     */
    public AudioFormat getFormat() {
        return format;
    }

    public byte[] getByteArray() {
        return b;
    }

    public ByteBuffer getByteBufferWrapper() {
        return byteBufferWrapper;
    }

    public ShortBuffer getShortBufferWrapper() {
        return shortBufferWrapper;
    }

    public AudioInputStream getAudioInputStream() {
        InputStream in = new BufferedInputStream(new ByteArrayInputStream(
                this.b));
        AudioInputStream audioIn = new AudioInputStream(in, this.format,
                                                        this.b.length / (format.getFrameSize()));
        return audioIn;
    }

    public SampleSizeType getSampleSize() {
        return this.sampleSize;
    }

    /**
     * Return the length of samples taken in this WaveForm. This is essentially
     * the number of frames in the total sample
     *
     * @return
     */
    public int getSampleLength() {
        return this.b.length / this.format.getFrameSize();
    }
}
