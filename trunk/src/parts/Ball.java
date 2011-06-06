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

package parts;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import engine.Updateable;
import engine.Vector;

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
public class Ball extends Part implements Updateable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7331080653246065303L;
	/**
	 * The speed and direction at which this Ball is rolling.
	 */
	private double angularVelocity;

	/**
	 * Creates a new Ball at the given position, of the given type.
	 * 
	 * @param aloc
	 *            the initial position of the new Ball object.
	 * @param aType
	 *            the type of Ball to create.
	 */
	public Ball(Vector aloc, PartType aType) {
		super(aloc);
		this.type = aType;
		this.shape = new Ellipse2D.Double(getPosition().getX(), getPosition().getY(), getPartType().getWidth(), getPartType().getHeight());
	}

	/**
	 * This method 'gets' a double representing the angular velocity of this
	 * Ball Object
	 * 
	 * @return a double
	 */
	public double getAngularVelocity() {
		return this.angularVelocity;
	}

	/**
	 * This method 'sets' the private instance variable angularVelocity, which
	 * represents the angular velocity of the ball.
	 * 
	 * @param anAngularVelocity
	 *            a double
	 */
	public void setAngularVelocity(double anAngularVelocity) {
		this.angularVelocity = anAngularVelocity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.Updateable#update()
	 */
	public void update(double time, Vector gravity) {
		// change this Part's position, velocity, and acceleration vectors
		
		// accel = accel + grav * time
		acceleration.setX(gravity.getX());
		acceleration.setY(gravity.getY());
		
		// velocity = velocity + a * t
		velocity.addXComp(acceleration.getX() * time);
		velocity.addYComp(acceleration.getY() * time); 

		// position = position + v * t + 1/2 * a * t^2
		position.addXComp(velocity.getX() * time + 0.5 * acceleration.getX()	* Math.pow(time, 2.0));
		position.addYComp(velocity.getY() * time + 0.5 * acceleration.getY()	* Math.pow(time, 2.0));
	}

	@Override
	public Shape getShape() {
		((Ellipse2D.Double) shape).setFrame(getPosition().getX(), getPosition().getY(), getPartType().getWidth(), getPartType().getHeight());
		return this.shape;
	}
}
