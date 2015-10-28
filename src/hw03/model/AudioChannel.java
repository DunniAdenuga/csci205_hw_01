/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: 28-Oct-2015
 * Time: 08:05:16
 *
 * Project: csci205_hw_01
 * Package: hw03.model
 * File: AudioChannel
 * Description:
 *
 * ****************************************
 */
package hw03.model;

/**
 *
 * @author tww014
 */
public enum AudioChannel {
    LEFT(0, 1), RIGHT(1, 1), MONO(0, 0);

    /**
     * The offset in samples between the beginning of the data and the first
     * sample from this field
     */
    public final int offset;

    /**
     * The number of audio samples between samples of this channel
     */
    public final int distance;

    private AudioChannel(int offset, int distance) {
        this.offset = offset;
        this.distance = distance;
    }

}
