/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: 28-Oct-2015
 * Time: 08:02:51
 *
 * Project: csci205_hw_01
 * Package: hw03.model
 * File: AudioModel
 * Description:
 *
 * ****************************************
 */
package hw03.model;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.DefaultComboBoxModel;

/**
 * Model representing the displayed audio data
 *
 * @author Tim Woodford
 */
public class AudioModel {
    protected static final int STARTING_WIDTH = 300;

    private WaveForm audioData;
    private AudioChannel channel;
    private final DefaultBoundedRangeModel rangeModel;
    private Clip audioPlayer;

    public AudioModel(WaveForm audioData) throws LineUnavailableException, IOException {
        this.audioData = audioData;
        this.rangeModel = new DefaultBoundedRangeModel(STARTING_WIDTH, 738,
                                                       STARTING_WIDTH,
                                                       audioData.getSampleLength() / 2);
        preparePlayer();
    }

    /**
     * Prepare player to start again. This means that any old data will be
     * flushed from the stream, and the stream will be reinitialized.
     *
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void preparePlayer() throws IOException, LineUnavailableException {
        DataLine.Info info = new DataLine.Info(Clip.class, audioData.getFormat());
        audioPlayer = (Clip) AudioSystem.getLine(info);
        audioPlayer.flush();
        audioPlayer.open(audioData.getAudioInputStream());
    }

    /**
     * Set the waveform to display
     *
     * @param audio
     */
    public void setWaveForm(WaveForm audio) {
        audioData = audio;
    }

    public boolean isStereo() {
        return audioData.getFormat().getChannels() > 1;
    }

    /**
     * Get the normalized wave data
     *
     * @return The normalized wave data
     * @author Tim Woodford
     */
    public double[] getWaveData() {
        int outLen = audioData.getSampleLength() / (1 + channel.distance);
        double[] out = new double[outLen];
        double normalizationFactor = audioData.getSampleSize() == SampleSizeType.EIGHT_BIT ? Byte.MAX_VALUE : Short.MAX_VALUE;
        if (audioData.getSampleSize() == SampleSizeType.EIGHT_BIT) {
            ByteBuffer buf = audioData.getByteBufferWrapper();
            buf.rewind();
            // Move past the channel offset
            for (int i = 0; i < channel.offset; i++) {
                buf.get();
            }
            for (int sample = 0; sample < outLen; sample++) {
                out[sample] = buf.get() / normalizationFactor;
                // Skip over samples that are not from this channel
                // Does not run after the last channel sample
                for (int i = 0; i < channel.distance && sample != outLen; i++) {
                    buf.get();
                }
            }
        } else if (audioData.getSampleSize() == SampleSizeType.SIXTEEN_BIT) {
            ShortBuffer buf = audioData.getShortBufferWrapper();
            buf.rewind();
            // Move past the channel offset
            for (int i = 0; i < channel.offset; i++) {
                buf.get();
            }
            for (int sample = 0; sample < outLen; sample++) {
                out[sample] = buf.get() / normalizationFactor;
                // Skip over samples that are not from this channel
                // Does not run after the last channel sample
                for (int i = 0; i < channel.distance && sample != outLen; i++) {
                    buf.get();
                }
            }
        }
        return out;
    }

    public void setChannel(AudioChannel channel) {
        this.channel = channel;
    }

    public List<AudioChannel> getValidChannels() {
        int distance = isStereo() ? 1 : 0; // Proper distance value for channel
        return Arrays.stream(AudioChannel.values()).filter(
                chan -> chan.distance == distance).collect(Collectors.toList());
    }

    public ComboBoxModel<AudioChannel> channelModel() {
        return new DefaultComboBoxModel<>(getValidChannels().toArray(
                new AudioChannel[0]));
    }

    /**
     * Get a range model for use with the zoom slider
     *
     * @return A range model
     */
    public DefaultBoundedRangeModel getRangeModel() {
        return rangeModel;
    }

    /**
     * Get the current zoom width
     *
     * @return The zoom width in pixels
     */
    public int getZoom() {
        return rangeModel.getValue();
    }

    /**
     * Start playing the audio
     */
    public void playAudio() {
        audioPlayer.start();
    }

    /**
     * Pause the audio. Note that to truly pause the audio, other components
     * must properly account for pauses.
     */
    public void stopAudio() {
        audioPlayer.stop();
    }

    public Clip getAudioPlayer() {
        return audioPlayer;
    }
}
