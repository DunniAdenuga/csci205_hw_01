/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Tim Woodford, Dunni
 * Date: Oct 6, 2015
 * Time: 8:42:28 PM
 *
 * Project: csci205_hw_01
 * Package: hw02.tone
 * File: SquareTone
 * Description:
 *
 * ****************************************
 */
package hw02.source;

/**
 * Tone generator for a square wave
 *
 * @see http://mathworld.wolfram.com/SquareWave.html
 * @author Tim Woodford
 */
public class SquareTone extends Tone {

    /**
     * Create a new square-wave tone generator
     *
     * @param frequency The frequency of the output wave in hertz
     * @param amplitude The amplitude of the output, on a scale of 0.0-1.0
     */
    public SquareTone(float frequency, float amplitude) {
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
        return time > 1 / (2 * getFrequency()) ? getAmplitude() : 0;
    }

}
