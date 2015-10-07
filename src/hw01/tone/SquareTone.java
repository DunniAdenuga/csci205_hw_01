/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Tim Woodford, Dunni
 * Date: Oct 6, 2015
 * Time: 8:42:28 PM
 *
 * Project: csci205_hw_01
 * Package: hw01.tone
 * File: SquareTone
 * Description:
 *
 * ****************************************
 */
package hw01.tone;

/**
 *
 * @author Tim Woodford
 */
public class SquareTone extends Tone {

    public SquareTone(float frequency, float amplitude) {
        super(frequency, amplitude);
    }

    @Override
    public double getSample(double time) {
        time %= 1 / getFrequency();
        return time > 1 / (2*getFrequency()) ? getAmplitude() : 0;
    }
    
}
