/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: Oct 6, 2015
 * Time: 8:55:14 PM
 *
 * Project: csci205_hw_01
 * Package: hw01.tone
 * File: SawtoothTone
 * Description:
 *
 * ****************************************
 */
package hw01.tone;

/**
 *
 * @author timothy
 */
public class SawtoothTone extends Tone {

    public SawtoothTone(float frequency, float amplitude) {
        super(frequency, amplitude);
    }

    @Override
    public double getSample(double time) {
        double period = 1/getFrequency();
        return (time % period) / period;
    }
    
}
