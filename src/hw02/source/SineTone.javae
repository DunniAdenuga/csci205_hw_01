/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: 06-Oct-2015
 * Time: 15:55:40
 *
 * Project: csci205_hw_01
 * Package: hw01.tone
 * File: SineTone
 * Description:
 *
 * ****************************************
 */
package hw01.source;

/**
 * Plays a sine-wave tone
 *
 * @author tww014
 */
public class SineTone extends Tone {

    /**
     * Create a new sine-wave tone generator
     *
     * @param frequency The frequency of the output wave in hertz
     * @param amplitude The amplitude of the output, on a scale of 0.0-1.0
     */
    public SineTone(float frequency, float amplitude) {
        super(frequency, amplitude);
    }

    /**
     * Get the sample value at the given time
     *
     * @param time The time since the beginning of the period
     * @return The amplitude-adjusted sample size, on a scale of 0.0-1.0
     */
    @Override
    public double getSample(double time) {
        return getAmplitude() * Math.sin(2 * Math.PI * time * getFrequency());
    }

}
