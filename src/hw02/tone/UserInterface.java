/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 Tim Woodford
 * Date: Oct 8, 2015
 * Time: 3:56:59 PM
 *
 * Project: csci205_hw_01


 * Package: hw02.tone

 * File: UserInterface
 * Description:
 *
 * ****************************************
 */
package hw02.tone;

import hw02.dsp.AudioProcessor;
import hw02.dsp.ConvolutionBuilder;
import hw02.dsp.VolumeControl;
import hw02.fft.DFTException;
import hw02.fft.FFT;
import hw02.source.SawtoothTone;
import hw02.source.SineTone;
import hw02.source.SquareTone;
import hw02.source.Tone;
import hw02.source.TriangleTone;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author ia005
 */
public class UserInterface {

    /**
     * @param args the command line arguments
     */
    /**
     * Start off
     *
     * @param args
     * @throws IOException
     * @throws InterruptedException
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     * @throws javax.sound.sampled.LineUnavailableException
     */
    public static void main(String[] args) throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException, DFTException {
        Tone tone = null;
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to THE TONE");
        System.out.print(
                "Do you want to generate a tone or use an existing wavefile ? Enter 1 to generate or 2 to use existing. ");
        String reply = input.next();
        if (reply.equals("1")) {
            generate();
        }

        if (reply.equals("2")) {
            existing();
        }

    }

    /**
     * If audio exists in memory, read in
     *
     * @throws InterruptedException
     * @throws IOException
     */
    public static void existing() throws InterruptedException, IOException, UnsupportedAudioFileException, LineUnavailableException, DFTException {
        Scanner input = new Scanner(System.in);
        String wavFile;

        System.out.print("Please Enter Wave File: ");
        wavFile = input.next();
        try {
            System.out.println("Playing file....");
            WavePlay.playFile(wavFile);

            System.out.println(
                    "Information about your WaveForm is printed below! ");

            WavePlay.display(wavFile);

            int numfreq = askInt(
                    "How many of the top frequencies do you want to find? ",
                    input);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(
                    wavFile));
            displayTopFrequencies(audioIn, numfreq);

            downsampleFile(wavFile);
            delayFile(wavFile);
            volumeFile(wavFile, 5);

        } catch (IllegalArgumentException e) {
            System.out.print("Audio File not supported.");
        }

    }

    /**
     * Ask for the frequency until a properly formatted number is given
     *
     * @return The frequency
     */
    private static float getFrequency() {
        Scanner input = new Scanner(System.in);
        try {
            System.out.print(
                    "Please what's the frequency of the tone to be generated in Hz ? ");
            return Float.parseFloat(input.next());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format!");
            return getFrequency();
        }
    }

    /**
     * Generate new tone and work with it
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public static void generate() throws IOException, InterruptedException, LineUnavailableException, DFTException {
        Scanner input = new Scanner(System.in);
        Tone tone = null;
        int time = 5;
        float freq = getFrequency();
        float amp = getAmplitude(input);
        String typeTone = getToneType(input);
        if (typeTone.equals("1")) {
            tone = new SawtoothTone(freq, amp);
        }
        if (typeTone.equals("2")) {
            tone = new SineTone(freq, amp);
        }
        if (typeTone.equals("3")) {
            tone = new SquareTone(freq, amp);
        }
        if (typeTone.equals("4")) {
            tone = new TriangleTone(freq, amp);
        }

        System.out.print("Please enter the amount of time for playing: ");
        time = Integer.parseInt(input.next());
        System.out.print("Playing generated tone...");
        WavePlay.playFile(tone, time);

        System.out.println(
                "Information about your WaveForm is printed below! ");

        WavePlay.display(tone.getAudioInputStream(time));

        int numfreq = askInt(
                "How many of the top frequencies do you want to find? ",
                input);
        displayTopFrequencies(tone.getAudioInputStream(time), numfreq);

        downsampleTone(tone, typeTone);
        delayTone(tone, time);
        volumeTone(tone, time);
        System.out.println("Enter number of peaks for DFT: ");
        int num = input.nextInt();

    }

    /**
     * Ask the user for a tone type until a valid answer is given
     *
     * @param input The scanner to use for input
     * @return A valid tone type
     */
    private static String getToneType(Scanner input) {
        System.out.print(
                "What type of tone ? enter 1 for SawtoothTone, 2 for SineTone, 3 for SquareTone, 4 for TriangleTone: ");
        String typeTone = input.next();
        try {
            int num = Integer.parseInt(typeTone);
            if (num > 0 && num <= 4) {
                return typeTone;
            } else {
                return getToneType(input);
            }
        } catch (NumberFormatException nfe) {
            return getToneType(input);
        }
    }

    /**
     * Ask the user for an amplitude until a valid answer is given
     *
     * @param input The scanner to use for input
     * @return A valid amplitude
     */
    private static float getAmplitude(Scanner input) {
        try {
            System.out.print(
                    "Please what's the amplitude of the tone to be generated (0.0-1.0) ? ");
            float amp = Float.parseFloat(input.next());
            if (amp >= 0 && amp <= 1) {
                return amp;
            } else {
                System.out.println("Number outside of accepted range");
                return getAmplitude(input);
            }
        } catch (NumberFormatException e) {
            System.out.println("Not a valid number!");
            return getAmplitude(input);
        }
    }

    /**
     * Downsample a wave file
     *
     * @param wavfile - file to be downsampled
     * @throws IOException
     * @throws InterruptedException
     * @throws LineUnavailableException
     */
    public static void downsampleFile(String wavfile) throws IOException, InterruptedException, LineUnavailableException {
        Scanner input = new Scanner(System.in);

        AudioInputStream finalResult = null;
        System.out.print(
                "Please Enter the degree of downsampling in percent: ");
        double deg = Double.parseDouble(input.next());
        finalResult = WavePlay.downsample(wavfile, deg);

        System.out.print(
                "Give a name for the file to save the downsampled version(end in .wav): ");
        String newFile = input.next();
        WavePlay.saveWav(finalResult, newFile);
        System.out.println("Playing downsampled file...");
        WavePlay.playFile(newFile);
    }

    /**
     * Ask a question until the user gives a valid number
     *
     * @param question The question to ask
     * @param input The scanner to use
     * @return A double from user input
     */
    private static double askDouble(String question, Scanner input) {
        System.out.print(question);
        try {
            return Double.parseDouble(input.next());
        } catch (NumberFormatException nfe) {
            System.out.println("Not a valid number!");
            return askDouble(question, input);
        }
    }

    /**
     * Ask a question until the user gives a valid integer
     *
     * @param question The question to ask
     * @param input The scanner to use
     * @return A double from user input
     */
    private static int askInt(String question, Scanner input) {
        System.out.print(question);
        try {
            return Integer.parseInt(input.next());
        } catch (NumberFormatException nfe) {
            System.out.println("Not a valid number!");
            return askInt(question, input);
        }
    }

    /**
     * Downsample a Tone
     *
     * @param tone
     * @param typeTone
     * @throws IOException
     * @throws InterruptedException
     * @throws LineUnavailableException
     */
    public static void downsampleTone(Tone tone, String typeTone) throws IOException, InterruptedException, LineUnavailableException {
        Tone finalResult = null;
        Scanner input = new Scanner(System.in);

        int time = 5;

        double deg = askDouble(
                "Please Enter the degree of downsample in percent: ", input);

        finalResult = WavePlay.downsample(tone, typeTone, time, deg);
        System.out.print(
                "Give a name for the file to save the downsampled version(end in .wav): ");
        String newFile = input.next();
        WavePlay.saveWav(finalResult.getAudioInputStream(time), newFile);
        System.out.println("Playing downsampled file...");
        WavePlay.playFile(finalResult, time);
    }

    /**
     * Add delay to a tone
     *
     * @param tone
     * @param time- amount of time to play new delayed file
     * @throws IOException
     * @throws InterruptedException
     */
    public static void delayTone(Tone tone, int time) throws IOException, InterruptedException {
        Scanner input = new Scanner(System.in);
        float delay = (float) askDouble("Enter Delay Time (in seconds): ", input);

        float delayM = (float) askDouble("Enter delay amplitude mutiplier: ",
                                         input);

        AudioProcessor proc = ConvolutionBuilder.simpleDelay(
                tone.getAudioInputStream(time), delay,
                delayM);
        System.out.println("Playing delayed file...");
        WavePlay.playFile(proc.getAudioStream(44100 * time));
        System.out.print("Delay File saved in delayFile.wav ");
        WavePlay.saveWav(proc.getAudioStream(44100 * time), "delayFile.wav");
    }

    /**
     * Add delay to audio file
     *
     * @param wavFile- audiofile
     * @throws UnsupportedAudioFileException
     * @throws InterruptedException
     * @throws IOException
     */
    public static void delayFile(String wavFile) throws UnsupportedAudioFileException, InterruptedException, IOException {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a delay time: ");
        float delay = Float.parseFloat(input.next());
        System.out.print("Enter delay mutiplier: ");
        float delayM = Float.parseFloat(input.next());
        System.out.print("How Long do you want it played for ? ");
        int time = Integer.parseInt(input.next());
        final AudioInputStream audioIn = AudioSystem.
                getAudioInputStream(new File(wavFile));
        AudioProcessor proc = ConvolutionBuilder.simpleDelay(
                audioIn, delay,
                delayM / 100);
        int samples = (int) (audioIn.getFormat().getFrameRate() * time);
        System.out.print("Playing delayed file...");
        WavePlay.playFile(proc.getAudioStream(samples));
        System.out.println("Delay File saved in delayFile.wav");
        WavePlay.saveWav(proc.getAudioStream(samples), "delayFile.wav");
    }

    /**
     * Add volume to tone
     *
     * @param tone
     * @param time-amount of time to play new tone
     * @throws InterruptedException
     * @throws IOException
     */
    public static void volumeTone(Tone tone, int time) throws InterruptedException, IOException {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a percentage volume adjuster: ");
        float volumeAdjust = Float.parseFloat(input.next());
        AudioInputStream newAudio = new VolumeControl(tone.getAudioInputStream(
                time), volumeAdjust).getAudioStream(
                        44100 * time);
        System.out.print("Playing volume adjusted file...");
        WavePlay.playFile(newAudio);
        System.out.print("Volume Adjusted File saved in volumeAdjusted.wav");
        WavePlay.saveWav(newAudio, "volumeAdjusted.wav");
    }

    /**
     * Add volume to audio wave file
     *
     * @param wavFile
     * @param time-amount of time to play new tone
     * @throws UnsupportedAudioFileException
     * @throws InterruptedException
     * @throws IOException
     */
    public static void volumeFile(String wavFile, int time) throws UnsupportedAudioFileException, InterruptedException, IOException {

        Scanner input = new Scanner(System.in);
        System.out.print("Enter a percentage volume adjuster: ");
        float volumeAdjust = Float.parseFloat(input.next());
        AudioInputStream newAudio = new VolumeControl(
                AudioSystem.getAudioInputStream(new File(wavFile)), volumeAdjust).
                getAudioStream(
                        44100 * time);
        System.out.print("Playing volume adjusted file...");
        WavePlay.playFile(newAudio);
        System.out.print("Volume Adjusted File saved in volumeAdjusted.wav.");
        WavePlay.saveWav(newAudio, "volumeAdjusted.wav");

    }

    /**
     * Display top frequencies from present in an audio input
     *
     * @param audio The audio input
     * @param numFreq The number of frequencies to find
     * @throws java.io.IOException
     * @throws hw02.fft.DFTException
     * @author Tim Woodford
     */
    public static void displayTopFrequencies(AudioInputStream audio, int numFreq) throws IOException, DFTException {
        List<Double> fft = Arrays.stream(FFT.computeFFT(audio)).map(
                cmplx -> cmplx.magnitude()).collect(Collectors.toList());
        final float sampleRate = audio.getFormat().getFrameRate();
        float[] freq = FFT.frequenciesFFT(sampleRate, fft.size());
        double[][] table = new double[fft.size() / 2][2];
        for (int i = 0; i < table.length; i++) {
            table[i][0] = fft.get(i);
            table[i][1] = freq[i];
        }
        Arrays.sort(table,
                    (double[] o1, double[] o2) -> o1[0] < o2[0] ? 1 : -1);
        System.out.println("Frequencies with the highest amplitudes:");
        for (int i = 0; i < 5; i++) {
            System.out.format("%.1f (magnitude: %.1f)\n", table[i][1],
                              table[i][0]);
        }
    }
}
