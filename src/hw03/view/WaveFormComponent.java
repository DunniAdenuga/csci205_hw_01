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

import hw03.model.SampleSizeType;
import hw03.model.WaveForm;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import javax.swing.JComponent;

/**
 *
 * @author tww014
 */
public class WaveFormComponent extends JComponent {
    private final WaveForm wave;

    public WaveFormComponent(WaveForm wave) {
        this.wave = wave;
        if (wave.getFormat().getChannels() != 1) {
            throw new RuntimeException(); // TODO
        }
        setSize(wave.getSampleLength(), 100);
    }

    @Override
    public void paintComponent(Graphics g) {
        // Turn on antialiasing if available
        if (g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                 RenderingHints.VALUE_ANTIALIAS_ON);
        }
        ByteBuffer bbuf = wave.getByteBufferWrapper();
        bbuf.rewind();
        ShortBuffer sbuf = wave.getShortBufferWrapper();
        sbuf.rewind();
        SampleSizeType sst = wave.getSampleSize();
        final int height = getBounds().height;
        final int width = getBounds().width;
        int scaleFactor = height / (sst == SampleSizeType.EIGHT_BIT ? Byte.MAX_VALUE : Short.MAX_VALUE);
        g.setColor(Color.blue);
        int samplesPerLine = wave.getSampleLength() / width;
        for (int line = 0; line < width; line++) {
            int value = averageSamples(samplesPerLine, sst, bbuf, sbuf);
            g.drawLine(line, height, line,
                       height - scaleFactor * value);
        }
    }

    public int averageSamples(int samplesPerLine, SampleSizeType sst,
                              ByteBuffer bbuf, ShortBuffer sbuf) {
        int value = 0;
        for (int sample = 0; sample < samplesPerLine; sample++) {
            value += Math.abs(
                    sst == SampleSizeType.EIGHT_BIT ? bbuf.get() : sbuf.get());
        }
        return value / samplesPerLine;
    }
}
