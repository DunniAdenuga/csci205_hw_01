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
 *
 * @author tww014
 */
public class Complex {
    public final double real;
    public final double imaginary;

    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public Complex add(Complex other) {
        return new Complex(real + other.real, imaginary + other.imaginary);
    }

    public Complex multiply(Complex other) {
        // (a + bi)*(c + di) = a*c + (c*b + a*d)i - b*d
        return new Complex(real * other.real - imaginary * other.imaginary,
                           real * other.imaginary + imaginary * other.real);
    }

    public Complex multiply(double scalar) {
        return new Complex(scalar * real, scalar * imaginary);
    }
}
