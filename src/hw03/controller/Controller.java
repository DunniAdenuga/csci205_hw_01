/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: Oct 28, 2015
 * Time: 8:47:04 AM
 *
 * Project: csci205_hw_01
 * Package: hw03.controller
 * File: Controller
 * Description:
 *
 * ****************************************
 */
package hw03.controller;

import hw03.model.Model;
import hw03.model.WaveFormException;
import hw03.view.MainViewer;
import hw03.view.WaveViewer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Join the View to the Model
 *
 * @author Dunni Adenuga
 */
public class Controller implements ActionListener {
    MainViewer theView;
    Model theModel;

    public Controller(MainViewer theView, Model theModel) {
        this.theModel = theModel;
        this.theView = theView;

        updateViewFromModel();
        theView.getNewAction().addActionListener(this);
        theView.getOpenAction().addActionListener(this);
        theView.getExitAction().addActionListener(this);
        theView.getFreqText().addActionListener(this);
        theView.getSampleRateText().addActionListener(this);
        theView.getLengthText().addActionListener(this);
        theView.getPlotButton().addActionListener(this);
        theView.getPlotButton2().addActionListener(this);
    }

    /**
     * Update View From Model
     */
    public void updateViewFromModel() {
        theView.getFreqText().setText(String.valueOf(theModel.getFrequency()));
        theView.getSampleRateText().setText(String.valueOf(
                theModel.getSampleRate()));
        theView.getLengthText().setText(String.valueOf(theModel.getLength()));
    }

    @Override
    /**
     * If there's a change in GUI, an action. do something
     */
    public void actionPerformed(ActionEvent e) {
        ///////////
        if (e.getSource() == theView.getNewAction()) {
            theView.dialogBox();
            updateViewFromModel();
        }
        if (e.getSource() == theView.getOpenAction()) {
            if (theView.chooseFile() == JFileChooser.APPROVE_OPTION) {
                theModel.setWavFile(theView.getChooser().getSelectedFile());
                theView.getFrame().add(theView.getPlotButton2());
                theView.getFrame().setVisible(true);
            }

        }
        //////////
        if (e.getSource() == theView.getExitAction()) {
            theView.exitOption();
        }

        try {
            if (e.getSource() == theView.getFreqText()) {
                theModel.setFrequency(Float.parseFloat(
                        theView.getFreqText().getText()));
                updateViewFromModel();
            }
        } catch (NumberFormatException a) {
            JOptionPane.showMessageDialog(theView, "Number Format Error!",
                                          theView.getFreqText().getText() + " is not a number!",

                                          JOptionPane.ERROR_MESSAGE);
        }
        try {
            if (e.getSource() == theView.getSampleRateText()) {
                theModel.setSampleRate(Float.parseFloat(
                        theView.getSampleRateText().getText()));
            }
        } catch (NumberFormatException b) {
            JOptionPane.showMessageDialog(theView, "Number Format Error!",
                                          theView.getSampleRateText().getText() + " is not a number!",
                                          JOptionPane.ERROR_MESSAGE);
        }

        try {
            if (e.getSource() == theView.getLengthText()) {
                theModel.setLength(Integer.valueOf(
                        theView.getLengthText().getText()));
            }
        } catch (NumberFormatException a) {
            JOptionPane.showMessageDialog(theView, "Number Format Error!",
                                          theView.getLengthText().getText() + " is not a number!",
                                          JOptionPane.ERROR_MESSAGE);
        }

        if (e.getSource() == theView.getPlotButton()) {
            try {
                WaveViewer viewer = new WaveViewer(theModel.getWaveTN());
                viewer.setVisible(true);
            } catch (WaveFormException ex) {
                JOptionPane.showMessageDialog(theView, "Error!",
                                              " WaveForm Error!",
                                              JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == theView.getPlotButton2()) {
            try {
                WaveViewer viewer = new WaveViewer(theModel.getWaveTF());
                viewer.setVisible(true);
            } catch (WaveFormException ex) {
                JOptionPane.showMessageDialog(theView, "Error!",
                                              " WaveForm Error!",
                                              JOptionPane.ERROR_MESSAGE);
            } catch (UnsupportedAudioFileException ex) {
                JOptionPane.showMessageDialog(theView, "Error!",
                                              " AudioFile Error...!",
                                              JOptionPane.ERROR_MESSAGE);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(theView, "Error!",
                                              " IOException Error...!",
                                              JOptionPane.ERROR_MESSAGE);
            }
        }

    }
}
