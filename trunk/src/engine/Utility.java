/* 
 * Copyright 2008 Mark Ross and Duncan Krassikoff
 * Copyright 2011 Mark Ross
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
	public static void getEdge(Vector edge, double[][] polyPoints, int i) {
		switch (i) {
			case 0:
				edge.setX(polyPoints[1][0] - polyPoints[0][0]);
				edge.setY(polyPoints[1][1] - polyPoints[0][1]);
				break;
			case 1:
				edge.setX(polyPoints[2][0] - polyPoints[1][0]);
				edge.setY(polyPoints[2][1] - polyPoints[1][1]);
				break;
			case 2:
				edge.setX(polyPoints[3][0] - polyPoints[2][0]);
				edge.setY(polyPoints[3][1] - polyPoints[2][1]);
				break;
			case 3:
				edge.setX(polyPoints[0][0] - polyPoints[3][0]);
				edge.setY(polyPoints[0][1] - polyPoints[3][1]);
				break;
		}
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