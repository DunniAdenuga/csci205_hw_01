/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: 14-Oct-2015
 * Time: 08:44:45
 *
 * Project: csci205_hw_01
 * Package: hw02.fft
 * File: Complex
 * Description:
 *
 * ****************************************
 */
package hw02.fft;

/**
 * A class representing a complex number a+bi
 *
 * @author Tim Woodford
 */
public class Complex {

    /**
     * The real component of the number
     */
    public final double real;

    /**
     * The imaginary component of the number
     */
    public final double imaginary;

    /**
     * Create a complex number with the specified real and imaginary components
     *
     * @param real The real component
     * @param imaginary The imaginary component
     * @auther Tim Woodford
     */
    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    /**
     * @return real part
     * @author Dunni Adenuga
     */
    public double getReal() {
        return this.real;
    }

    /**
     * @return imaginary part
     * @author Dunni Adenuga
     */
    public double getImaginary() {
        return this.imaginary;
    }

    /**
     * Add two complex numbers
     *
     * @param other The other complex number
     * @return The sum
     * @author Tim Woodford
     */
    public Complex add(Complex other) {
        return new Complex(real + other.real, imaginary + other.imaginary);
    }

    /**
     * Subtract 2 complex numbers
     *
     * @ Dunni Adenuga
     */
    public Complex minus(Complex other) {
        return new Complex(real - other.real, imaginary - other.imaginary);
    }

    /**
     * Multiply two complex numbers together
     *
     * @param other The other number to multiply
     * @return The product
     * @author Tim Woodford
     */
    public Complex multiply(Complex other) {
        // (a + bi)*(c + di) = a*c + (c*b + a*d)i - b*d
        return new Complex(real * other.real - imaginary * other.imaginary,
                           real * other.imaginary + imaginary * other.real);
    }

    /**
     * Multiply a complex number by a scalar (real number)
     *
     * @param scalar The scalar multiplier
     * @return The product
     * @author Tim Woodford
     */
    public Complex multiply(double scalar) {
        return new Complex(scalar * real, scalar * imaginary);
    }

    /**
     * Check whether two instances of Complex are equal, defined as the real and
     * imaginary components being equal
     *
     * @param o The other object
     * @return True if the instances are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Complex) {
            Complex other = (Complex) o;
            return real == other.real && imaginary == other.imaginary;
        } else {
            return false;
        }
    }

    /**
     * Magnitude of complex number
     *
     * @author Dunni Adenuga
     */
    public double magnitude() {
        double mag;
        mag = Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
        return mag;
    }
}
