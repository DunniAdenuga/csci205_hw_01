/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: 01-Nov-2015
 * Time: 20:14:36
 *
 * Project: csci205_hw_01
 * Package: hw03.controller
 * File: WaveFormController
 * Description:
 *
 * ****************************************
 */
package hw03.controller;

import hw03.model.AudioChannel;
import hw03.model.AudioModel;
import hw03.view.WaveViewer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Controller that handles events that have something to with the audio model
 *
 * @author tww014
 */
public class WaveFormController implements ActionListener, ChangeListener {
    private final WaveViewer viewer;
    private final AudioModel model;

    public WaveFormController(WaveViewer viewer, AudioModel model) {
        this.viewer = viewer;
        this.model = model;
        attachListeners(viewer);
    }

    private void attachListeners(WaveViewer viewer1) {
        viewer1.getChannelSelector().addActionListener(this);
        viewer1.getPlayPauseButton().addActionListener(this);
        viewer1.getZoomSlider().addChangeListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewer.getChannelSelector()) {
            model.setChannel(
                    (AudioChannel) viewer.getChannelSelector().getSelectedItem());
            viewer.revalidate();
            viewer.repaint();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == viewer.getZoomSlider()) {
            // Zoom level is fetched directly from the slider's model
            viewer.wfc.revalidate();
            viewer.wfc.repaint();
            viewer.revalidate();
            viewer.repaint();
        }
    }
}
