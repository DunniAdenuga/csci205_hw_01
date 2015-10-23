/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Project: csci205
 * Package: hw3.utility

 * File: AudioUtility
 * Author: Prof. Brian King
 *
 * Description:
 * Utility class to assist with audio processing. It  provides helper methods
 * to gather and print information about .wav files, and print information
 * related to the underlying Java sound system
 *
 * Some of the code here was obtained from online resources, including:
 * - Java 8 Sound API
 * - Java Sound Tutorial - https://docs.oracle.com/javase/tutorial/sound/
 * - Java Sound Resources http://www.jsresources.org
 * ****************************************
 */
package hw03.utility;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author brk009
 */
public class AudioUtility {

    /**
     * Print out the audio file format information pulled from an actual WAV
     * file
     *
     * @param inFile - A File handle to a WAV file
     *
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     * @throws java.io.IOException
     */
    public static void printAudioFileFormatInfo(File inFile) throws UnsupportedAudioFileException, IOException {
        AudioFileFormat aff = AudioSystem.getAudioFileFormat(inFile);
        AudioFormat format = aff.getFormat();
        System.out.println(
                "---------------------------------------------------------------------------");
        System.out.println("Source: " + inFile.getName());
        System.out.println("Type: " + aff.getType());
        System.out.println("# of frames: " + aff.getFrameLength());
        System.out.println(
                "---------------------------------------------------------------------------");
        String strAudioLength = null;
        if (aff.getFrameLength() != AudioSystem.NOT_SPECIFIED) {
            strAudioLength = "" + aff.getFrameLength() + " frames, " + aff.getFrameLength() * format.getFrameSize() + " bytes, " + (aff.getFrameLength() / format.getFrameRate()) + " seconds";
        } else {
            strAudioLength = "unknown";
        }
        System.out.println("Length of audio data: " + strAudioLength);
        String strFileLength = null;
        if (aff.getByteLength() != AudioSystem.NOT_SPECIFIED) {
            strFileLength = "" + aff.getByteLength() + " bytes";
        } else {
            strFileLength = "unknown";
        }
        System.out.println(
                "Total length of file (including headers): " + strFileLength);
    }

    /**
     * Print out the information stored in an AudioFormat object
     *
     * @param format - An AudioFormat object
     */
    public static void printAudioFormat(AudioFormat format) {
        System.out.println(
                "---------------------------------------------------------------------------");
        System.out.println("AudioFormat: " + format);
        System.out.println("size of frame (bytes): " + format.getFrameSize());
        System.out.println("Frame rate (hz): " + format.getFrameRate());
        System.out.println("Sample rate (hz): " + format.getSampleRate());
        System.out.println("Sample size (bits): " + format.getSampleSizeInBits());
        System.out.println("Number of channels: " + format.getChannels());
        System.out.println(
                "---------------------------------------------------------------------------");
    }

    /**
     * Utility file to play an AudioInputStream using the Java sound system.
     * This code is modeled off code from the Java Sound tutorial, as well as
     * <a href=http://www.jsresources.org>jsresources.org</a>
     *
     * @param in - AudioInputStream instance that contains data to be played by
     * the Java Sound system
     *
     * @throws LineUnavailableException
     * @throws IOException
     */
    public static void playAudioInputStream(AudioInputStream in) throws LineUnavailableException, IOException {
        DataLine.Info info;
        info = new DataLine.Info(Clip.class, in.getFormat());
        Mixer mixer = AudioSystem.getMixer(null);
        Clip clip = (Clip) mixer.getLine(info);
        clip.open(in);
        //            clip.open(audioInStream);
        clip.start();
        try {
            while (!clip.isRunning()) {
                Thread.sleep(10);
            }
            while (clip.isRunning()) {
                Thread.sleep(10);
            }
        } catch (InterruptedException interruptedException) {
        }
        clip.close();

    }
}
