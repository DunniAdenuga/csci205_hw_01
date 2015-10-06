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
package hw01.tone;

/**
 * Plays a sine-wave tone
 *
 * @author tww014
 */
public class SineTone extends Tone {

    public SineTone(float frequency, float amplitude) {
        super(frequency, amplitude);
    }

    @Override
    public double getSample(double time) {
        return getAmplitude() * Math.sin(2 * Math.PI * time * getFrequency());
    }

}
