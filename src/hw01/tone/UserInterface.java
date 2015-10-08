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
 * Package: hw01.tone
 * File: UserInterface
 * Description:
 *
 * ****************************************
 */
package hw01.tone;

import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;

/**
 *
 * @author ia005
 */
public class UserInterface {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
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

    public static void existing() throws InterruptedException, IOException {
        Scanner input = new Scanner(System.in);
        String wavFile = null;
        AudioInputStream finalResult = null;
        System.out.print("Please Enter Wave File: ");
        wavFile = input.next();
        try {
            System.out.println("Playing file....");
            WavePlay.playFile(wavFile);

            System.out.println(
                    "Information about your WaveForm is printed below! ");

            WavePlay.display(wavFile);
            System.out.print("Do you want to downsample the tone y | n?");
            if (input.next().equals("y")) {
                System.out.print(
                        "Please Enter the degree of percentage in percent: ");
                int deg = Integer.parseInt(input.next());
                finalResult = WavePlay.downsample(wavFile, deg);

                System.out.print(
                        "Give a name for the file to save the downsampled version: ");
                String newFile = input.next();
                WavePlay.saveWav(finalResult, newFile);
                System.out.println("Playing downsampled file...");
                WavePlay.playFile(newFile);

            }
        } catch (IllegalArgumentException e) {
            System.out.print("Audio File not supported.");
        }
    }

    public static void generate() throws IOException, InterruptedException {
        AudioInputStream finalResult = null;
        Scanner input = new Scanner(System.in);
        Tone tone = null;
        int time = 5;
        System.out.print(
                "Please what's the frequency of the tone to be generated ? ");
        float freq = Float.parseFloat(input.next());
        System.out.print(
                "Please what's the amplitude of the tone to be generated ? ");
        float amp = Float.parseFloat(input.next());
        System.out.print(
                "What type of tone ? enter 1 for SawtoothTone, 2 for SineTone, 3 for SquareTone, 4 for TriangleTone: ");
        String typeTone = input.next();
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
        System.out.print("Do you want to play the Tone ? If yes enter 1: ");
        String play = input.next();
        if (play.equals("1")) {
            System.out.print("Please enter the amount of time for playing: ");
            time = Integer.parseInt(input.next());
            WavePlay.playFile(tone, time);
        }
        System.out.println(
                "Information about your WaveForm is printed below! ");
        WavePlay.display(new AudioInputStream(tone.getInputStream(),
                                              tone.getFormat(), time));
        System.out.print("Do you want to downsample the tone y | n?");
        if (input.next().equals("y")) {
            System.out.print(
                    "Please Enter the degree of percentage in percent: ");
            int deg = Integer.parseInt(input.next());
            finalResult = WavePlay.downsample((new AudioInputStream(
                                               tone.getInputStream(),
                                               tone.getFormat(), time)), deg);
            System.out.print(
                    "Give a name for the file to save the downsampled version: ");
            String newFile = input.next();
            WavePlay.saveWav(finalResult, newFile);
            System.out.println("Playing downsampled file...");
            WavePlay.playFile(newFile);
            System.out.print(
                    "Do you want to add a single delay/echo to sound ? If yes enter y: ");
            if (input.next().equals("y")) {
                System.out.print("Enter Delay Time: ");
            }
        }
    }
}
