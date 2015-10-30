/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: Oct 29, 2015
 * Time: 6:59:13 PM
 *
 * Project: csci205_hw_01
 * Package: hw03.utility
 * File: Utility
 * Description:
 *
 * ****************************************
 */
package hw03.utility;

import hw03.model.AudioChannel;
import hw03.model.AudioModel;
import hw03.model.SampleSizeType;
import hw03.model.WaveForm;
import hw03.model.WaveFormException;
import hw03.view.WaveFormComponent;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author ia005
 */
public class Utility {

    public static WaveFormComponent generateWaveFormComponent(float freq,
                                                              float sampleRate,
                                                              double length) throws WaveFormException {
        WaveForm newWave = new WaveForm(freq, sampleRate,
                                        SampleSizeType.SIXTEEN_BIT, length);
        return generateWaveFormComponent(newWave);
    }

    public static WaveFormComponent generateWaveFormComponent(WaveForm newWave) {
        AudioModel model = new AudioModel(newWave);
        model.setChannel(AudioChannel.LEFT);
        return new WaveFormComponent(model);
    }

    public static WaveFormComponent generateWaveFormComponent(File file) throws WaveFormException, UnsupportedAudioFileException, IOException {
        WaveForm newWave = new WaveForm(file);
        return generateWaveFormComponent(newWave);
    }

}
