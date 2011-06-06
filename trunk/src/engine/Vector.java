/* 
 * Copyright 2008 Mark Ross and Duncan Krassikoff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package engine;

import java.io.Serializable;

/**
 * Represents a vector that has x and y components as well as a magnitude.
 * 
 * @author Mark Ross
 */
/*+----------------------------------------------------------------------
||
||  Class [Class Name] 
||
||         Author:  [Your Name]
||
||        Purpose:  [A description of why this class exists.  For what
||                   reason was it written?  Which jobs does it perform?]
||
||  Inherits From:  [If this class is a subclass of another, name it.
||                   If not, just say "None."]
||
||     Interfaces:  [If any predefined interfaces are implemented by
||                   this class, name them.  If not, ... well, you know.]
||
|+-----------------------------------------------------------------------
||
||      Constants:  [Name all public class constants, and provide a very
||                   brief (but useful!) description of each.]
||
|+-----------------------------------------------------------------------
||
||   Constructors:  [List the names and arguments of all defined
||                   constructors.]
||
||  Class Methods:  [List the names, arguments, and return types of all
||                   public class methods.]
||
||  Inst. Methods:  [List the names, arguments, and return types of all
||                   public instance methods.]
||
++-----------------------------------------------------------------------*/
public class Vector implements Serializable {
	
	// attributes ///////////////////////////////////////////

	private static final long serialVersionUID = 6286483831932297312L;
	private double x;
	private double y;
	private double magnitude;

	
	// constructors /////////////////////////////////////////

	/**
	 * Creates a new Vector with x- and y-components equal to 0.0
	 */
	public Vector() {
		this(0.0, 0.0);
	}

	/**
	 * Creates a new Vector with the given x- and y-components.
	 * 
	 * @param x
	 *            the x-component of the new vector
	 * @param y
	 *            the y-component of the new vector
	 */
	public Vector(double x, double y) {
		setX(x);
		setY(y);
	}

	
	// behaviors ///////////////////////////////////////////

	/**
	 * Gets the x-component of the vector.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Sets the x-component of the vector and also adjusts the magnitude
	 * accordingly.
	 * 
	 * @param x
	 *            the value of the x-component that is to be set.
	 */
	public void setX(double x) {
		this.x = x;
		setMagnitude();
	}

	/**
	 * Gets the y-component of the vector.
	 */
	public double getY() {
		return y;
	}

	/**
	 * Sets the y-component of the vector and also adjusts the magnitude
	 * accordingly.
	 * 
	 * @param y
	 *            the value of the y-component that is to be set.
	 */
	public void setY(double y) {
		this.y = y;
		setMagnitude();
	}

	/**
	 * Gets the magnitude of the vector
	 * 
	 * @return <code>double</code>
	 */
	public double getMagnitude() {
		return magnitude;
	}

	/**
	 * Sets the magnitude of the vector. Notice that this method is private.
	 * This is because the magnitude depends on the value of the x- and
	 * y-components and cannot be set arbitrarily.
	 */
	private void setMagnitude() {
		magnitude = Math.sqrt(Math.pow(x, 2.0) + Math.pow(y, 2.0));
	}

	/**
	 * Adds another vector to this vector.
	 * 
	 * @param other
	 *            the other vector that is to be added.
	 */
	public void add(Vector other) {
		this.setX(x + other.x);
		this.setY(y + other.y);
	}

	/**
	 * Adds a value to only the x-component of this vector.
	 * 
	 * @param x
	 *            the amount that is to be added to the x-component of this
	 *            vector.
	 */
	public void addXComp(double xToAdd) {
		setX(x + xToAdd);
	}

	/**
	 * Adds a value to only the y-component of this vector.
	 * 
	 * @param y
	 *            the amount that is to be added to the y-component of this
	 *            vector.
	 */
	public void addYComp(double yToAdd) {
		setY(y + yToAdd);
	}

	/**
	 * Sets this vector to be the same as the one given.
	 * 
	 * @param v
	 *            the vector with the values that this vector is supposed to
	 *            take
	 */
	public void setVector(Vector v) {
		x = v.x;
		y = v.y;
		magnitude = v.magnitude;
	}

	public String toString() {
		return "x: " + x + " - y: " + y;
	}

	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (!(other instanceof Vector))
			return false;
		return this.x == ((Vector) other).x && this.y == ((Vector) other).y;
	}

	/**
	 * Calculates the dot product of two Vectors.
	 * 
	 * @param v1
	 *            a Vector; order does not matter
	 * @param v2
	 *            a Vector; order does not matter
	 * @return a <code>double</code>
	 */
	public static double dotProduct(Vector v1, Vector v2) {
		return v1.x * v2.x + v1.y * v2.y;
	}

	/**
	 * Projects this Vector onto another.
	 * 
	 * @param b
	 * @return
	 */
	public Vector projectionOnto(Vector b) {
		/*
		 * The formula for projecting vector a onto vector b is: proj.x = ( dp /
		 * (b.xb.x + b.yb.y) ) b.x; proj.y = ( dp / (b.xb.x + b.yb.y) ) b.y;
		 * where dp is the dotprod of a and b: dp = (a.xb.x + a.yb.y) Note that
		 * the result is a vector; also, (b.xb.x + b.yb.y) is simply the length
		 * of b squared. If b is a unit vector, (b.xb.x + b.yb.y) = 1, and thus
		 * a projected onto b reduces to: proj.x = dpb.x; proj.y = dpb.y;
		 * Source: http://www.metanetsoftware.com/technique/tutorialA.html
		 */
		return new Vector((dotProduct(this, b) / (Math.pow(b.getMagnitude(), 2.0))) * b.x, (dotProduct(this, b) / (Math.pow(b.getMagnitude(), 2.0))) * b.y);
	}

	/**
	 * Gets the right-hand normal of this Vector.
	 * 
	 * @return a Vector
	 */
	public Vector getRightHandNormal() {
		return new Vector(y * -1, x);
	}

	/**
	 * Gets the left-hand normal of this Vector.
	 * 
	 * @return a Vector
	 */
	public Vector getLeftHandNormal() {
		return new Vector(y, x * -1);
	}

	/**
	 * @param s
	 * @return
	 */
	public Vector scalarProduct(double s) {
		return new Vector(x * s, y * s);
	}

	/**
	 * Adds two vectors together.
	 * 
	 * @param v1
	 *            a Vector; order does not matter
	 * @param v2
	 *            a Vector; order does not matter
	 * @return a new Vector
	 */
	public static Vector add(Vector v1, Vector v2) {
		return new Vector(v1.x + v2.x, v1.y + v2.y);
	}

	/**
	 * Subtracts two vectors. Vector subtraction is like finding a third vector
	 * that points *from* one Vector *to* the other. TODO
	 * 
	 * @param v1
	 *            the "to" Vector; order is important!
	 * @param v2
	 *            the "from" Vector; order is important!
	 * @return
	 */
	public static Vector subtract(Vector v1, Vector v2) {
		return new Vector(v1.x - v2.x, v1.y - v2.y);
	}

	/**
	 * Gets the unit vector of this Vector.
	 * 
	 * @return a Vector of magnitude 1.0.
	 */
	public Vector getNormalized() {
		return new Vector(x / magnitude, y / magnitude);
	}

}
