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

import engine.CollisionBoundType;
import engine.PhysicsObject;
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
public abstract class Part extends PhysicsObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5798923597534783069L;
	/**
	 * A PartType denoting this Part object's type.
	 */
	protected PartType type;
	/**
	 * Used in serialNum generation
	 */
	private static int totalParts;
	/**
	 * This Part object's serial number
	 */
	private int serialNum;
	
	protected Shape shape;
	
	protected Part(Vector pos) {
		super(pos);
		this.serialNum = totalParts++;
		this.shape = null;
	}

	/**
	 * This method 'gets' a <code>PartType</code>, which contains relevant 
	 * information for this <code>Part</code>.
	 * 
	 * @return 
	 * 			a <code>PartType</code>
	 */
	public PartType getPartType() {
		return this.type;
	}
	
	/**
	 * Returns the collision-bound type of the Part.
	 * 
	 * @return <code>CollisionBoundType</code> enum value.
	 * @see engine.CollisionBoundType
	 */
	public CollisionBoundType getCollisionBoundType() {
		return this.type.getCollisionBoundType();
	}
	
	/**
	 * Checks to see if this part is the same as the param's reference.
	 * 
	 * @param other
	 *            reference to an Object
	 * @return a boolean representing whether or not the two objects compared
	 *         are the same.
	 */
	public boolean equals(Object other) {
		if (!(other instanceof Part))
			return false;
		return this.serialNum == ((Part) other).serialNum;
	}

	/**
	 * Gets the serial number of this Part
	 * 
	 * @return int representing the serial number of this Part
	 */
	public int getSerialNum() {
		return this.serialNum;
	}
	
	/**
	 * This class returns a String containing the location of this part.
	 * 
	 * @return - a String describing the location of this part.
	 */
	public String toString() {
		return type.toString();
	}
	
	/**
	 * Returns the position vector of the center of this object.
	 * 
	 * @return Vector describing the center position of this Part.
	 */
	public Vector getCenter() {
		return new Vector(this.position.getX() + this.getWidth() / 2, this.position
				.getY()
				+ this.getHeight() / 2);
	}
	
	public double getWidth() {
		return this.type.getWidth();
	}
	
	public double getHeight() {
		return this.type.getHeight();
	}
	
	public double getMass() {
		return this.type.getMass();
	}
	
	public double getMUk() {
		return this.type.getMUk();
	}
	
	public double getMUs() {
		return this.type.getMUs();
	}
	
	
	
}
