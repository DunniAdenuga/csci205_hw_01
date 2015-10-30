/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: Oct 28, 2015
 * Time: 8:51:15 AM
 *
 * Project: csci205_hw_01
 * Package: hw03.model
 * File: Model
 * Description:
 *
 * ****************************************
 */
package hw03.model;

import hw03.utility.Utility;
import hw03.view.WaveFormComponent;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author ia005
 */
public class Model {

    private float frequency;
    private float sampleRate;
    private double length;
    private File wavFile;
    private WaveFormComponent wave;

    public Model() throws WaveFormException {
        this.frequency = 440;
        this.sampleRate = 16000;
        this.length = 1;
        this.wave = Utility.generateWaveFormComponent(frequency, sampleRate,
                                                      length);
    }

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    public double getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(float sampleRate) {
        this.sampleRate = sampleRate;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public File getWavFile() {
        return wavFile;
    }

    public void setWavFile(File wavFile) {
        this.wavFile = wavFile;
    }

    public WaveFormComponent getWaveTF() throws WaveFormException, UnsupportedAudioFileException, IOException {
        wave = Utility.generateWaveFormComponent(wavFile);
        return wave;
    }

    public WaveFormComponent getWaveTN() throws WaveFormException {
        wave = Utility.generateWaveFormComponent(frequency, sampleRate, length);
        return wave;
    }

    public void setWave(WaveFormComponent wave) {
        this.wave = wave;
    }
}
