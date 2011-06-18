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
public class Wall extends Part {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5678251197101575605L;

	/**
	 * Wall takes in a Vector representing position. and passes it to its super
	 * class.
	 * 
	 * @param pos
	 *            - a Vector
	 * @param aType
	 *            - a PartType
	 */
	public Wall(Vector pos, PartType aType) {
		super(pos);
		this.type = aType;
	
		Path2D.Double p = new Path2D.Double();
		
		
		Vector lowerLeft = new Vector(getPosition().getX(), getPosition().getY() + getHeight());
		Vector lowerRight = new Vector(getPosition().getX() + getWidth(), getPosition().getY() + getHeight());
		Vector upperRight = new Vector(getPosition().getX() + getWidth(), getPosition().getY());

		// TODO use Path2D methods to draw polygon
		//p.((int) getPosition().getX(), (int) getPosition().getY());
		//p.addPoint((int) lowerLeft.getX(), (int) lowerLeft.getY());
		//p.addPoint((int) lowerRight.getX(), (int) lowerRight.getY());
		//p.addPoint((int) upperRight.getX(), (int) upperRight.getY());
		p.moveTo(pos.getX(), pos.getY());
		p.lineTo(upperRight.getX(), upperRight.getY());
		p.lineTo(lowerRight.getX(), lowerRight.getY());
		p.lineTo(lowerLeft.getX(), lowerLeft.getY());
		p.closePath();
		
		this.shape = p;
		
		// TODO remove below
		PathIterator pi = p.getPathIterator(null);
		
		System.out.println("PRINTING RAMP WALL SEGMENTS");
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

	@Override
	public Shape getShape() {
		return this.shape;
	}
}
