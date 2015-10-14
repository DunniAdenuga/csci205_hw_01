/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: Oct 6, 2015
 * Time: 8:55:14 PM
 *
 * Project: csci205_hw_01
 * Package: hw02.tone
 * File: SawtoothTone
 * Description:
 *
 * ****************************************
 */
package hw02.source;

/**
 * A sawtooth tone generator
 *
 * @see http://mathworld.wolfram.com/SawtoothWave.html
 * @author timothy
 */
public class SawtoothTone extends Tone {

    /**
     * Create a new sawtooth tone generator
     *
     * @param frequency The frequency of the output wave in hertz
     * @param amplitude The amplitude of the output, on a scale of 0.0-1.0
     */
    public SawtoothTone(float frequency, float amplitude) {
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
        double period = 1 / getFrequency();
        return (time % period) / period;
    }

}
