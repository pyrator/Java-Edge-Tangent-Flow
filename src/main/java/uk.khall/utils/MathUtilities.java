package uk.khall.utils;/*
 * @(#)MathUtilities.java    2.3.2   11 September 2004
 *
 * Copyright 2004
 * College of Computer and Information Science
 * Northeastern University
 * Boston, MA  02115
 *
 * The Java Power Tools software may be used for educational
 * purposes as long as this copyright notice is retained intact
 * at the top of all source files.
 *
 * To discuss possible commercial use of this software, 
 * contact Richard Rasala at Northeastern University, 
 * College of Computer and Information Science,
 * 617-373-2462 or rasala@ccs.neu.edu.
 *
 * The Java Power Tools software has been designed and built
 * in collaboration with Viera Proulx and Jeff Raab.
 *
 * Should this software be modified, the words "Modified from 
 * Original" must be included as a comment below this notice.
 *
 * All publication rights are retained.  This software or its 
 * documentation may not be published in any media either
 * in whole or in part without explicit permission.
 *
 * This software was created with support from Northeastern 
 * University and from NSF grant DUE-9950829.
 */


/**
 * <p>The class <code>MathUtilities</code> collects several useful
 * static mathematical functions.  The class may be viewed as an
 * extension of the tools found in <code>java.lang.Math</code>.</p>
 *
 * @author Richard Rasala
 * @version 2.3.2
 * @since   1.2
 */
public class MathUtilities {

    // Random numbers in a specified range

    /**
     * Return a random int r in the range min <= r <= max.
     */
    public static int randomInt(int min, int max) {
        // To prevent overflow in the calculation, switch to long
        // Also, sort the parameters
        long a;
        long b;

        if (min <= max) {
            a = min;
            b = max;
        } else {
            a = max;
            b = min;
        }

        long r = a + (long) ((b - a + 1) * Math.random());

        // To prevent round off problems, make sure that r <= b
        if (r > b)
            r = b;

        // Now return the int result
        return (int) r;
    }


    /**
     * Return a random double r in the range min <= r <= max.
     */
    public static double randomDouble(double min, double max) {
        // Sort the parameters
        double a;
        double b;

        if (min <= max) {
            a = min;
            b = max;
        } else {
            a = max;
            b = min;
        }

        // Obtain a random value between 0 and 1
        double t = Math.random();

        // To prevent overflow, perform affine interpolation to
        // obtain a random value between a and b
        double r = (1 - t) * a + t * b;

        // To prevent round off problems, make sure that a <= r <= b
        if (r < a)
            r = a;
        else if (r > b)
            r = b;

        // Now return the result
        return r;
    }


    // Trigonometric functions in degrees

    /**
     * Return the sine of the given angle specified in degrees.
     */
    public static double sindeg(double degrees) {
        return Math.sin(Math.toRadians(degrees));
    }


    /**
     * Return the cosine of the given angle specified in degrees.
     */
    public static double cosdeg(double degrees) {
        return Math.cos(Math.toRadians(degrees));
    }


    /**
     * Return the tangent of the given angle specified in degrees.
     */
    public static double tandeg(double degrees) {
        return Math.tan(Math.toRadians(degrees));
    }


    /**
     * Return the arc sine in degrees of the specified input value
     * in the range -1 to +1.
     */
    public static double asindeg(double value) {
        return Math.toDegrees(Math.asin(value));
    }


    /**
     * Return the arc cosine in degrees of the specified input value
     * in the range -1 to +1.
     */
    public static double acosdeg(double value) {
        return Math.toDegrees(Math.acos(value));
    }


    /**
     * Return the arc tangent in degrees of the specified input value.
     */
    public static double atandeg(double value) {
        return Math.toDegrees(Math.atan(value));
    }


    /**
     * Return the polar angle in degrees of the point (x, y) where the
     * y-coordinate is given first and the x-coordinate second.
     *
     * @param y the y-coordinate of the point
     * @param x the x-coordinate of the point
     */
    public static double atan2deg(double y, double x) {
        return Math.toDegrees(Math.atan2(y, x));
    }


    // Greatest Common Divisor

    /**
     * <p>Returns the greatest common divisor of the int inputs a and b.</p>
     *
     * <p>The return value will be positive or zero and will be zero only if
     * both a and b are zero.</p>
     */
    public static int GCD(int a, int b) {
        if (b == 0)
            return Math.abs(a);

        return GCD(b, a % b);
    }


    /**
     * <p>Returns the greatest common divisor of the long inputs a and b.</p>
     *
     * <p>The return value will be positive or zero and will be zero only if
     * both a and b are zero.</p>
     */
    public static long GCD(long a, long b) {
        if (b == 0)
            return Math.abs(a);

        return GCD(b, a % b);
    }


    // Least Common Multiple

    /**
     * <p>Returns the least common multiple of the int inputs a and b.</p>
     *
     * <p>The return value will be positive or zero and will be zero only if
     * both a and b are zero.</p>
     */
    public static int LCM(int a, int b) {
        if ((a == 0) && (b == 0))
            return 0;

        a = Math.abs(a);

        b = Math.abs(b);

        return a * (b / GCD(a, b));
    }


    /**
     * <p>Returns the least common multiple of the long inputs a and b.</p>
     *
     * <p>The return value will be positive or zero and will be zero only if
     * both a and b are zero.</p>
     */
    public static long LCM(long a, long b) {
        if ((a == 0) && (b == 0))
            return 0;

        a = Math.abs(a);

        b = Math.abs(b);

        return a * (b / GCD(a, b));
    }


    // Modulus

    /**
     * <p>Returns the modulus of int number relative to int base.</p>
     *
     * <p>Returns n such that n is congruent to number modulo base
     * and 0 &lt;= n &lt; Math.abs(base).</p>
     *
     * <p>Throws <code>ArithmeticException</code> if base is zero.</p>
     */
    public static int modulus(int number, int base) {
        if (base == 0)
            throw new ArithmeticException
                    ("Error in modulus: base is zero");

        base = Math.abs(base);

        number = number % base;

        if (number < 0)
            number += base;

        return number;
    }


    /**
     * <p>Returns the modulus of long number relative to long base.</p>
     *
     * <p>Returns n such that n is congruent to number modulo base
     * and 0 &lt;= n &lt; Math.abs(base).</p>
     *
     * <p>Throws <code>ArithmeticException</code> if base is zero.</p>
     */
    public static long modulus(long number, long base) {
        if (base == 0)
            throw new ArithmeticException
                    ("Error in modulus: base is zero");

        base = Math.abs(base);

        number = number % base;

        if (number < 0)
            number += base;

        return number;
    }

    /**
     * Check whether two floating point values match with a given precision.
     * @param a
     * @param b
     * @param delta
     * @return
     */
    public static  boolean almostEqual(double a, double b, double delta) {
        return Math.abs(a - b)<=delta;
    }
}