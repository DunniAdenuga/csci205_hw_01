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

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Tim Woodford
 */
public class AudioModel {
    private WaveForm audioData;
    private AudioChannel channel;

    public void setWaveForm(WaveForm audio) {
        audioData = audio;
    }

    public boolean isStereo() {
        return audioData.getFormat().getChannels() > 1;
    }

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
}
