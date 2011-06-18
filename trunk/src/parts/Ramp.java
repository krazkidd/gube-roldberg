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

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package parts;

import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

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
public class Ramp extends Part { 

	/**
	 * 
	 */
	private static final long serialVersionUID = -1983714015045750579L;
	/**
	 * The beginning position of rampLongFaceVector.
	 */
	private Vector rampFaceVectorBeginPos;
	/**
	 * Stores the length of the long sides of the ramp as a vector. Oriented
	 * from top to bottom.
	 */
	private Vector rampLongFaceVector;
	/**
	 * Stores the length of the short sides of the ramp as a vector. Oriented
	 * from top to bottom.
	 */
	private Vector rampShortFaceVector;

	/**
	 * Creates a new Ramp at the indicated position.
	 * 
	 * @param pos
	 *            the initial position of the new Ramp.
	 */
	public Ramp(Vector pos, PartType whichFace) {
		super(pos);
		// FIXME
		this.type = whichFace;

		if (this.type == PartType.RAMPRIGHTFACE) {
			rampFaceVectorBeginPos = new Vector(0.0, 0.0); // TODO change these numbers to not be hard-coded; they need to be based on the size given in the enum
			rampLongFaceVector = new Vector(1.00, -0.59);
			rampShortFaceVector = new Vector(0.0, 0.15);
		} else {
			rampFaceVectorBeginPos = new Vector(0.0, 1.00);
			rampLongFaceVector = new Vector(-1.00, -0.59);
			rampShortFaceVector = new Vector(0.0, 0.15);
		}
		
		
		Path2D.Double p = new Path2D.Double();
		
		if (getPartType() == PartType.RAMPRIGHTFACE) {
			Vector upperLeft = getRampFaceVectorBeginPos();
			Vector upperRight = Vector.add(upperLeft, getRampLongFaceVector());
			Vector lowerRight = Vector.add(upperRight, getRampShortFaceVector());
			Vector lowerLeft = Vector.add(lowerRight, new Vector(getRampLongFaceVector().getX() * -1, getRampLongFaceVector().getY() * -1));

			p.moveTo(upperLeft.getX(), upperLeft.getY());
			p.lineTo(upperRight.getX(), upperRight.getY());
			p.lineTo(lowerRight.getX(), lowerRight.getY());
			p.lineTo(lowerLeft.getX(), lowerLeft.getY());
			p.closePath();
		} else {
			Vector upperLeft = getRampFaceVectorBeginPos();
			Vector lowerLeft = Vector.add(upperLeft, getRampShortFaceVector());
			Vector lowerRight = Vector.add(lowerLeft, getRampLongFaceVector());
			Vector upperRight = Vector.add(lowerRight, new Vector(getRampShortFaceVector().getX() * -1, getRampShortFaceVector().getY() * -1));

			p.moveTo(upperLeft.getX(), upperLeft.getY());
			p.lineTo(lowerLeft.getX(), lowerLeft.getY());
			p.lineTo(lowerRight.getX(), lowerRight.getY());
			p.lineTo(upperRight.getX(), upperRight.getY());
			p.closePath();
		}
		
		this.shape = p;
		
		PathIterator pi = p.getPathIterator(null);
		
		System.out.println("PRINTING RAMP PATH SEGMENTS");
		double[] points = new double[6];
		
		while (!pi.isDone()) {
			
			switch (pi.currentSegment(points)) {
				case PathIterator.SEG_MOVETO:
					System.out.print("Move to: ");
					break;
				case PathIterator.SEG_LINETO:
					System.out.print("Line to: ");
					break;
				case PathIterator.SEG_CLOSE:
					System.out.print("Close: ");
					break;
			}
			
			for (double d : points) {
				System.out.print(d + ", ");
			}
			
			System.out.println();
			
			pi.next();
		}
	}

	/**
	 * Returns the vector position of the first vertex of this ramp.
	 * 
	 * @return <code>Vector</code>
	 */
	private Vector getRampFaceVectorBeginPos() {
		Vector result = new Vector(this.position.getX(), this.position.getY());
		result.add(this.rampFaceVectorBeginPos);
		return result;
	}

	/**
	 * Gets the vector that represents the length of the short sides of the
	 * ramp. The vector is oriented from top to bottom.
	 * 
	 * @return <code>Vector</code> whose length and direction matches the short
	 *         sides of the ramp.
	 */
	private Vector getRampLongFaceVector() {
		return this.rampLongFaceVector;
	}

	/**
	 * Gets the vector that represents the length of the long sides of the ramp.
	 * The vector is oriented from top to bottom.
	 * 
	 * @return <code>Vector</code> whose length and direction matches the long
	 *         sides of the ramp.
	 */
	public Vector getRampShortFaceVector() {
		return this.rampShortFaceVector;
	}

	@Override
	public Shape getShape() {
		return this.shape;
	}
	
}
