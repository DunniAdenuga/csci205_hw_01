/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Brian King
 * Date: Oct 21, 2015
 * Time: 9:00:15 PM
 *
 * Package: hw3.model
 * File: SampleSizeType
 * Description:
 * Enumerated type to represent a sample size / depth
 * ****************************************
 */
package hw3.model;

/**
 * SampleSizeType
 *
 * Enumerated type used to represent the possible sample sizes. For now, we are
 * restricting ourselves to only 8-bit and 16-bit data. It is rare to find other
 * sizes in practice. 24-bit is sometimes used in high-end audio recordings,
 * particularly with studio recordings. But, general consumer audio is almost
 * always 16-bit.
 *
 * @author Prof. Brian King
 */
 public enum SampleSizeType {
    EIGHT_BIT(8), SIXTEEN_BIT(16);
    private int sampleSizeInBits;

    SampleSizeType(int sampleSizeInBits) {
        this.sampleSizeInBits = sampleSizeInBits;
    }

    public int getSampleSizeInBits() {
        return this.sampleSizeInBits;
    }

    public int getSampleSizeInBytes() {
        return this.sampleSizeInBits / 8;
    }

    /**
     * Helper method to convert an underlying sample size in bits to the
     * enum value. If the <code>sampleSizeInBits</code> is anything but
     * 8 or 16, then an WaveFormException is thrown.
     *
     * @param sampleSizeInBits - actual bit depth of the sample
     * @return the correct sample size enum based on the bits passed
     *
     * @throws WaveFormException if any unsupported value is passed to the method
     */
    static SampleSizeType numBitsToSampleSize(int sampleSizeInBits) throws WaveFormException {
        if (sampleSizeInBits == 16) {
            return SampleSizeType.SIXTEEN_BIT;
        }
        if (sampleSizeInBits == 8) {
            return SampleSizeType.EIGHT_BIT;
        }
        throw new WaveFormException("Unsupported Sample Size in bits: " + sampleSizeInBits);
    }

}
