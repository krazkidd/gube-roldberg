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

import java.awt.Shape;
import java.io.Serializable;

/**
 * A <code>PhysicsObject</code> is a primitive component of the physics engine.
 * It has properties 
 * 
 * @author Mark Ross
 * @author Duncan Krassikoff
 */
/*+----------------------------------------------------------------------
||
||  Class 
||
||         Author:  
||
||        Purpose:  
||
||  Inherits From:  
||
||     Interfaces:  
||
|+-----------------------------------------------------------------------
||
||      Constants:  
||
|+-----------------------------------------------------------------------
||
||   Constructors:  
||
||  Class Methods:  
||
||  Inst. Methods:  
||
++-----------------------------------------------------------------------*/
public abstract class PhysicsObject implements Serializable {

	// attributes ///////////////////////////////////////////
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5556115885905092728L;
	/**
	 * This represents the <code>Part</code>'s center of mass, and is used as a point of
	 * reference for the <code>Part</code>'s position in the world.
	 */
	protected Vector position;
	/**
	 * A <code>Vector</code> containing this <code>Part</code>'s velocity.
	 */
	protected Vector velocity;
	/**
	 * A <code>Vector</code> containing this <code>Part</code>'s acceleration.
	 */
	protected Vector acceleration;
	
	protected CollisionBoundType collisionBoundType;
	
	//protected Shape shape;

	
	// constructors /////////////////////////////////////////
	
	/**
	 * Creates a new <Code>Part</code> at the given position.
	 * 
	 * @param pos
	 *            the initial position of the <code>Part</code>
	 */
	protected PhysicsObject(Vector pos) {
		this.position = pos;
		this.velocity = new Vector(0.0, 0.0);
		this.acceleration = new Vector(0.0, 0.0);
	}

	
	// behaviors ///////////////////////////////////////////
	
	/**
	 * This method 'sets' the private instance variable Point point.
	 * 
	 * @param aPoint
	 *            a Point object.
	 */
	public void setPosition(Vector aPoint) {
		this.position = aPoint;
	}

	/**
	 * This method 'sets' the private instance variable Vector velocity, which
	 * stores this Object's velocity.
	 * 
	 * @param aVelocity
	 *            a Vector object
	 */
	public void setVelocity(Vector aVelocity) {
		this.velocity = aVelocity;
	}

	/**
	 * This method 'sets' the private instance variable Vector acceleration,
	 * which stores this Object's acceleration.
	 * 
	 * @param aAcceleration
	 *            a Vector Object
	 */
	public void setAcceleration(Vector aAcceleration) {
		this.acceleration = aAcceleration;
	}



	/**
	 * Returns the position vector of this Part.
	 * 
	 * @return <code>Vector</code> describing the position of this Part.
	 */
	public Vector getPosition() {
		return this.position;
	}

	/**
	 * Returns the velocity vector of the Part.
	 * 
	 * @return <code>Vector</code> describing the Part's velocity.
	 */
	public Vector getVelocity() {
		return this.velocity;
	}

	/**
	 * Returns the acceleration vector of the Part.
	 * 
	 * @return <code>Acceleration</code> describing the Part's acceleration.
	 */
	public Vector getAcceleration() {
		return this.acceleration;
	}

	/**
	 * Returns the height of this Part Object
	 * 
	 * @return - a double representing the height of this object.
	 */
	public abstract double getHeight();

	/**
	 * Returns the width of this Part Object
	 * 
	 * @return - a double representing the width of this object.
	 */
	public abstract double getWidth();

	/**
	 * Returns the mass of this Part Object
	 * 
	 * @return - a double representing the Mass of this object.
	 */
	public abstract double getMass();

	/**
	 * Returns the MUk of this Part Object
	 * 
	 * @return - a double representing the MUk of this Part.
	 */
	public abstract double getMUk();

	/**
	 * Returns the MUs of this Part Object.
	 * 
	 * @return - a double representing the MUs of this object.
	 */
	public abstract double getMUs();
	
	public abstract Vector getCenter();
	
	/**
	 * 
	 * @return
	 */
	public abstract Shape getShape();
	
	public CollisionBoundType getCollisionBoundType() {
		return this.collisionBoundType;
	}
	
}
