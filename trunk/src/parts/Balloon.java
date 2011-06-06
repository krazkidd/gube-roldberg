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

/**
 * This class defines a Balloon object which both extends Part and implements
 * Updateable. It is unique in that it is lighter than air starting with a
 * negative acceleration.
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
public class Balloon extends Part implements Updateable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8773091565413010214L;

	/**
	 * Takes in a position to create the balloon at.
	 * 
	 * @param pos the position at which to create the balloon
	 */
	public Balloon(Vector pos) {
		super(pos);
		this.type = PartType.BALLOON;
		this.shape = new Ellipse2D.Double(getPosition().getX(), getPosition().getY(), getPartType().getWidth(), getPartType().getHeight());
	}

	@Override
	public void update(double timeElapsed, Vector gravity) {
		// change this Part's position, velocity, and acceleration vectors
		
		// accel = accel + grav * time
		// TODO add acceleration due to air resistance (here or before/after velocity calcs??)
		Vector lift = new Vector(gravity.getX() * -1.3, gravity.getY() * -1.3);
		acceleration.setX(lift.getX());
		acceleration.setY(lift.getY());
				
		// velocity = velocity + a * t
		velocity.addXComp(acceleration.getX() * timeElapsed);
		velocity.addYComp(acceleration.getY() * timeElapsed); 
		velocity.scalarProduct(0.97); // rough air resistance

		// position = position + v * t + 1/2 * a * t^2
		position.addXComp(velocity.getX() * timeElapsed + 0.5 * acceleration.getX()	* Math.pow(timeElapsed, 2.0));
		position.addYComp(velocity.getY() * timeElapsed + 0.5 * acceleration.getY()	* Math.pow(timeElapsed, 2.0));
	}

	@Override
	public Shape getShape() {
		((Ellipse2D.Double) shape).setFrame(getPosition().getX(), getPosition().getY(), getPartType().getWidth(), getPartType().getHeight());
		return this.shape;
	}

}
