/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: Oct 6, 2015
 * Time: 8:51:33 PM
 *
 * Project: csci205_hw_01
 * Package: hw02.tone
 * File: TriangleTone
 * Description:
 *
 * ****************************************
 */
package hw02.source;

/**
 * A triangle-wave tone generator
 *
 * @author timothy
 */
public class TriangleTone extends Tone {

    /**
     * Create a new triangle-wave tone generator
     *
     * @param frequency The frequency of the output wave in hertz
     * @param amplitude The amplitude of the output, on a scale of 0.0-1.0
     */
    public TriangleTone(float frequency, float amplitude) {
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
        time %= 1 / getFrequency();
        double halfPeriod = 1 / (2 * getFrequency());
        if (time < halfPeriod) {
            return time / halfPeriod;
        } else {
            return 1 - (time - halfPeriod) / halfPeriod;
        }
    }

}
