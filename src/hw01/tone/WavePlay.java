package hw01.tone;

/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 * Tim Woodward
 * Date: Oct 5, 2015
 * Time: 8:41:24 AM
 *
 * Project: csci205_hw_01
 * Package:
 * File: WavePlay
 * Description:
 *
 * ****************************************
 */
import com.sun.media.sound.WaveFileWriter;
import hw01.source.Tone;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class WavePlay {
    /**
     * @see <a
     * href="http://stackoverflow.com/questions/2416935/how-to-play-wav-files-with-java"
     * @author Dunni
     */
    public static void playFile(String wavefile) throws InterruptedException {

        File audioFile = new File(wavefile);
        AudioInputStream audio;
        Clip clip;
        AudioFormat format;
        try {
            audio = AudioSystem.getAudioInputStream(audioFile);
            format = audio.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);

            clip.open(audio);
            clip.start();
            Thread.sleep(4000);
            clip.drain();

            /**
             * while (clip.isRunning() == false) { clip.stop(); }*
             */
            clip.close();

        } catch (UnsupportedAudioFileException e) {
            System.out.print("File not Supported");
        } catch (LineUnavailableException e) {
            System.out.print("Clip could not be created.");
        } catch (IOException e) {
            System.out.print("Problem.");
        }

    }

    /**
     *
     * @param tone- tone to be played
     * @param time- length of time to be played
     * @throws InterruptedException - if user interrupts play
     */
    public static void playFile(Tone tone, int time) throws InterruptedException {
        Clip clip;
        AudioInputStream audio;

        try {
            audio = tone.getAudioInputStream(time);
            DataLine.Info info = new DataLine.Info(Clip.class,
                                                   audio.getFormat());

            clip = (Clip) AudioSystem.getLine(info);

            clip.open(audio);
            clip.start();

            Thread.sleep(4000);
            //while (Thread.isInterrupted() == false) {
            clip.drain();
            //}
            /**
             * while (clip.isRunning() == false) { clip.stop(); }*
             */
            //clip.close();
        } catch (LineUnavailableException e) {
            System.out.print("Clip could not be created.");
        } catch (IOException e) {
            System.out.print("Problem.");
        }
    }

    /**
     * Display info about wavefile
     *
     * @param wavefile - file
     */
    public static void display(String wavefile) {
        File audioFile = new File(wavefile);
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(audioFile);
            AudioFileFormat info = AudioSystem.getAudioFileFormat(audioFile);
            AudioFormat mainInfo = info.getFormat();
            System.out.println("Number of Channels: " + mainInfo.getChannels());
            System.out.println(
                    "Bits per sample: " + mainInfo.getSampleSizeInBits());
            System.out.println(
                    "Number of frames per second: " + mainInfo.getFrameRate());
            System.out.println(
                    "Length of waveform in frames" + audio.getFrameLength());
            System.out.println(
                    "Length of waveform in bytes " + mainInfo.getFrameSize() * mainInfo.getFrameRate());
            if (mainInfo.isBigEndian()) {
                System.out.println("Byte Order: Big-Endian ");
            } else {
                System.out.println("Byte Order: Little-Endian ");
            }
        } catch (UnsupportedAudioFileException ex) {
            System.out.print("Audio File Unsupported.");
        } catch (IOException ex) {

        }
    }

    /**
     * Display info about audioInputStream
     *
     * @param audio- AudioInputStream
     * @throws IOException
     */
    public static void display(AudioInputStream audio) throws IOException {
        /*atch (IOException ex) {
         System.out.print("IO Exception issue.");*/
        //}

        AudioFormat mainInfo = audio.getFormat();
        System.out.println("Number of Channels: " + mainInfo.getChannels());
        System.out.println(
                "Bits per sample: " + mainInfo.getSampleSizeInBits());
        System.out.println(
                "Number of frames per second: " + mainInfo.getFrameRate());
        System.out.println(
                "Length of waveform in frames: " + mainInfo.getFrameRate());
        System.out.println(
                "Length of waveform in bytes " + mainInfo.getFrameSize());
        if (mainInfo.isBigEndian()) {
            System.out.println("Byte Order: Big-Endian ");
        } else {
            System.out.println("Byte Order: Little-Endian ");
        }
    }

    /**
     * Downsample(reduce just sample frequency)
     *
     * @param audio- audio too be downsampled
     * @param freq- percentage of downsampling
     * @return output of downsampling
     * @see <a href
     * ="http://stackoverflow.com/questions/15410725/java-resample-wav-soundfile-without-third-party-library"
     */
    public static AudioInputStream downsample(AudioInputStream audio,
                                              double freq) {
        AudioInputStream converted = null;
        AudioFormat srcFormat = audio.getFormat();
        AudioFormat dstFormat = new AudioFormat(srcFormat.getEncoding(),
                                                (float) (srcFormat.getSampleRate() * (freq / 100)),
                                                srcFormat.getSampleSizeInBits(),
                                                srcFormat.getChannels(),
                                                srcFormat.getFrameSize(),
                                                srcFormat.getFrameRate(),
                                                srcFormat.isBigEndian());
        converted = AudioSystem.getAudioInputStream(dstFormat, audio);
        return converted;
    }

    /**
     * Downsample(reduce just sample frequency)
     *
     * @param wavfile- String wavefile to be downsampled
     * @param freq- percentage of downsampling
     * @return output of downsampling
     */
    public static AudioInputStream downsample(String wavfile, double freq) {
        AudioInputStream audio = null;
        AudioInputStream converted = null;
        try {
            File audiofile = new File(wavfile);

            audio = AudioSystem.getAudioInputStream(audiofile);
            AudioFormat srcFormat = audio.getFormat();
            AudioFormat dstFormat = new AudioFormat(srcFormat.getEncoding(),
                                                    (float) (srcFormat.getSampleRate() * (freq / 100)),
                                                    srcFormat.getSampleSizeInBits(),
                                                    srcFormat.getChannels(),
                                                    srcFormat.getFrameSize(),
                                                    srcFormat.getFrameRate(),
                                                    srcFormat.isBigEndian());

            converted = AudioSystem.getAudioInputStream(dstFormat, audio);

        } catch (UnsupportedAudioFileException ex) {
            System.out.print("AudioFile Not Suported. Downsample Error.");
        } catch (IOException ex) {
            System.out.print("AudioFile Not Suported. Downsample Error.");
        }
        return converted;
    }

    /**
     *
     * @param converted - audioInputStream to be saved in wave file
     * @param newFile - name of wave file we want to save to
     * @throws IOException
     */
    public static void saveWav(AudioInputStream converted, String newFile) throws IOException {
        File output;
        WaveFileWriter writer;

        try {

            output = new File(newFile);
            writer = new WaveFileWriter();
            writer.write(converted, Type.WAVE, output);
        } catch (IOException e) {
            System.out.print("IOException error.");
        }
    }

}
