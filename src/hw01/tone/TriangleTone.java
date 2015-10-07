/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: Oct 6, 2015
 * Time: 8:51:33 PM
 *
 * Project: csci205_hw_01
 * Package: hw01.tone
 * File: TriangleTone
 * Description:
 *
 * ****************************************
 */
package hw01.tone;

/**
 *
 * @author timothy
 */
public class TriangleTone extends Tone {

    public TriangleTone(float frequency, float amplitude) {
        super(frequency, amplitude);
    }

    @Override
    public double getSample(double time) {
        time %= 1/getFrequency();
        double halfPeriod = 1/(2*getFrequency());
        if (time < halfPeriod) {
            return time / halfPeriod;
        } else {
            return 1 - (time - halfPeriod)/halfPeriod;
        }
    }
    
}
