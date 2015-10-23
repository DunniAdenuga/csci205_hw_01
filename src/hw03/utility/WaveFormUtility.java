/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Brian King
 * Date: Oct 21, 2015
 * Time: 10:46:37 PM
 *
 * Package: hw3.utility
 * File: WaveFormUtility
 *
 * Description:
 * Contains some helper methods for dealing with WaveForm instances
 * ****************************************
 */
package hw3.utility;

import hw3.model.SampleSizeType;
import hw3.model.WaveForm;
import hw3.model.WaveFormException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Utility class that contains static methods that do things with WaveForm
 * instances.
 *
 * @author Prof. Brian King
 */
public class WaveFormUtility {

   /**
     * This is a useful method to extract an array of shorts if your sample size
     * is 16-bit. You cannot extract this data directly from a shortBufferWrapper
     * since the underlying shortBufferWrapper does not have a backing short
     * array, only a byte[].
     *
     * @return - array of short[]
     * @throws WaveFormException if the wav form is not 16-bit
     */
    public static short[] extractShortArray(WaveForm wav) throws WaveFormException {
        if (wav.getSampleSize() != SampleSizeType.SIXTEEN_BIT)
            throw new WaveFormException("extractShortArray: Can not generate short[] from 8-bit data!");

        // Reset the position of the pointer to the beginning
        wav.getShortBufferWrapper().position(0);

        // Allocate the correct size of the short buffer
        short[] buf = new short[wav.getShortBufferWrapper().remaining()];

        // Transfer the data to the short[]
        wav.getShortBufferWrapper().get(buf);

        return buf;
    }

    /**
     * Print out the WaveForm <code>wav</code> to a file <code>sFileName</code>
     * as a comma-separated values (CSV) file. This can readily be read into
     * any spreadsheet program.
     *
     * @param wav WaveForm to be used to generate the CSV file
     * @param sFileName - Name of the CSV file to write
     *
     * @throws FileNotFoundException thrown if the CSV file can not be created
     * for some reason
     */
    public static void writeWavDataAsCSV(WaveForm wav, String sFileName) throws FileNotFoundException, WaveFormException {

        PrintWriter out = new PrintWriter(sFileName);

        // 8-bit audio file? Then write bytes! The ByteBuffer has a backing array
        // of byte[], so we can just retrieve the byte[] array. See the WaveForm
        // class for more info
        if (wav.getSampleSize() == SampleSizeType.EIGHT_BIT) {
            for(byte b : wav.getByteArray()) {
                out.print(b + ",");
            }
        }

        // Otherwise, we're a 16-bit file, so write short data
        else {
            // NOTE - I cannot access the short array because the backing array
            // is based on a byte[], not a short[]. Therefore, I must use the
            // get() method of the ShortBuffer class.
            for(short s : extractShortArray(wav)) {
                out.print(s + ",");
            }
//            wav.getShortBufferWrapper().position(0);
//            while(wav.getShortBufferWrapper().hasRemaining()) {
//                out.print(wav.getShortBufferWrapper().get() + ",");
//            }
//            // Reset the position just incase it's used elsewhere
//            wav.getShortBufferWrapper().position(0);
        }

        out.close();
    }
}
