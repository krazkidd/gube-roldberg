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

/**
 * Used in collision detection tests. Signifies whether or not there was a
 * collision. If there was a collision, a projection vector is stored that may
 * be used to project (push) the offending part back so that it is no longer
 * colliding.
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
public class CollisionResult {
	
	// attributes ////////////////////////////////////////////////////////////

	/**
	 * Whether or not there was a collision.
	 */
	private boolean collisionOccurred;
	/**
	 * Serves two purposes. 1) It tells where to *push* a colliding object so
	 * that it is no longer colliding and 2) it tells in which direction the collision
	 * occurred. For example, it gives the normal of the surface so you
	 * would use this vector for the "bounce" direction and a perpendicular
	 * vector for the friction.
	 */
	private Vector projectionVector;

	
	// constructors //////////////////////////////////////////////////////////

	/**
	 * Creates a new CollisionResult with default settings of NO collision and
	 * (0.0, 0.0) projection vector.
	 */
	public CollisionResult() {
		collisionOccurred = false;
		projectionVector = new Vector();
	}

	
	// behaviors /////////////////////////////////////////////////////////////

	public void setProjectionVector(Vector v) {
		projectionVector = v;
	}

	public Vector getProjectionVector() {
		return projectionVector;
	}

	public void setCollisionOccurred(boolean coll) {
		collisionOccurred = coll;
	}

	public boolean getCollisionOccurred() {
		return collisionOccurred;
	}
	
}
