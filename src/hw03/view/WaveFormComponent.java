/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: 23-Oct-2015
 * Time: 08:29:45
 *
 * Project: csci205_hw_01
 * Package: hw03.view
 * File: WaveFormComponent
 * Description:
 *
 * ****************************************
 */
package hw03.view;

import hw03.model.AudioModel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;

/**
 *
 * @author tww014
 */
public class WaveFormComponent extends JComponent {
    private final AudioModel audio;

    public WaveFormComponent(AudioModel audio) {
        this.audio = audio;
    }

    public AudioModel getAudioModel() {
        return audio;
    }

    @Override
    public void paintComponent(Graphics g) {
        // Turn on antialiasing if available
        if (g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                 RenderingHints.VALUE_ANTIALIAS_ON);
        }
        double[] samples = audio.getWaveData();
        final int height = getBounds().height;
        final int width = getBounds().width;
        g.setColor(Color.blue);
        int samplesPerLine = samples.length / width;
        double scaleFactor = (double) height / samplesPerLine;
        for (int line = 0; line < width; line++) {
            double value = 0;
            for (int sample = 0; sample < samplesPerLine; sample++) {
                value += Math.abs(samples[line * samplesPerLine + sample]);
            }
            g.drawLine(line, height, line, (int) (height - scaleFactor * value));
        }
    }
}
