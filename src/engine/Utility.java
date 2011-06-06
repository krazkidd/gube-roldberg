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

import java.awt.Point;
import java.awt.Polygon;
import java.util.List;

/**
 * Has static methods that can be used for various purposes, including collision
 * detection and updating parts. See method descriptions for their capabilities.
 * 
 * SEPARATING AXIS THEOREM
 *	 See: http://www.codeproject.com/KB/GDI-plus/PolygonCollision.aspx
 *	 See: http://www.metanetsoftware.com/technique.html
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
public class Utility {
	
	// behaviors ////////////////////////////////////////////

	/**
	 * Gets an edge of a polygon.
	 * 
	 * @param polyA
	 *            a Polygon
	 * @param i
	 *            the index of the edge you want.<br>
	 *            1 <= i <= # of vertices
	 * @return a <code>Vector</code> representing the edge
	 */
	public static Vector getEdge(Polygon polyA, int i) {
		if (i < polyA.npoints)
			return new Vector(polyA.xpoints[i] - polyA.xpoints[i - 1], polyA.ypoints[i] - polyA.ypoints[i - 1]);
		else
			return new Vector(polyA.xpoints[0] - polyA.xpoints[i - 1], polyA.ypoints[0] - polyA.ypoints[i - 1]);
	}

	/**
	 * Finds what Part is at a certain point.
	 * 
	 * @param physicsObjects
	 *            the list of Parts to be checked.
	 * @param pt
	 *            the point where the user clicked
	 * @return a reference to the part that was clicked (or <code>null</code> if
	 *         no part was at the point
	 */
	public static PhysicsObject getClickedPart(List<PhysicsObject> physicsObjects, Point pt) {
		PhysicsObject result = null;
		
		for (PhysicsObject po : physicsObjects) {
			if (po.getShape().contains(pt))
					result = po;
		}

		return result;
	}
	
}