/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: 28-Oct-2015
 * Time: 00:28:18
 *
 * Project: csci205_hw_01
 * Package: hw03.view
 * File: TestWaveFormComponent
 * Description:
 *
 * ****************************************
 */
package hw03.view;

import hw03.model.SampleSizeType;
import hw03.model.WaveForm;
import hw03.model.WaveFormException;
import javax.swing.JFrame;

/**
 *
 * @author tww014
 */
public class TestWaveFormComponent {

    /**
     * @param args the command line arguments
     * @throws hw03.model.WaveFormException
     */
    public static void main(String[] args) throws WaveFormException {
        WaveForm wf = new WaveForm(500, 44100, SampleSizeType.EIGHT_BIT,
                                   5000.0 / 44100);
        JFrame fr = new JFrame("Wave Component Test");
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.add(new WaveFormComponent(wf));
        fr.pack();
        fr.setVisible(true);
    }

}
