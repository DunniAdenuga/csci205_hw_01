/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: Oct 14, 2015
 * Time: 3:30:49 PM
 *
 * Project: csci205_hw_01
 * Package: hw02.fft
 * File: ComplexTest
 * Description:
 *
 * ****************************************
 */
package hw02.fft;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author timothy
 */
public class ComplexTest {

    public ComplexTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of add method, of class Complex.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        Complex other = new Complex(3, 2);
        Complex instance = new Complex(4, 4);
        Complex expResult = new Complex(7, 6);
        Complex result = instance.add(other);
        assertEquals(expResult, result);
    }

    /**
     * Test of multiply method, of class Complex.
     */
    @Test
    public void testMultiply_Complex() {
        System.out.println("multiply");
        Complex other = new Complex(3, 3);
        Complex instance = new Complex(2, 4);
        Complex expResult = new Complex(-6, 18);
        Complex result = instance.multiply(other);
        assertEquals(expResult, result);
    }

    /**
     * Test of multiply method, of class Complex.
     */
    @Test
    public void testMultiply_double() {
        System.out.println("multiply");
        double scalar = 2.0;
        Complex instance = new Complex(2, 3);
        Complex expResult = new Complex(4, 6);
        Complex result = instance.multiply(scalar);
        assertEquals(expResult, result);
    }

}
