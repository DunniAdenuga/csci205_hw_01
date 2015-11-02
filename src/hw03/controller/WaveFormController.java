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

/**
 * Controller that handles events that have something to with the audio model
 *
 * @author tww014
 */
public class WaveFormController implements ActionListener {
    private final WaveViewer viewer;
    private final AudioModel model;

    public WaveFormController(WaveViewer viewer, AudioModel model) {
        this.viewer = viewer;
        this.model = model;
        viewer.getChannelSelector().addActionListener(this);
        viewer.getPlayPauseButton().addActionListener(this);
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
}
