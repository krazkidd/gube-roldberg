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

import java.util.List;

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
public class Core {

	/**
	 * Updates the Updateable parts in the given list of Parts and checks for
	 * collisions. If there is a collision, performs calculations. If one of the
	 * parts performs some special function upon collision (i.e. it implements
	 * Interactable), do the action
	 * 
	 * @param parts
	 *            the entire list of parts in the world. This is needed in order
	 *            to check for collisions.
	 * @param partsToUpdate
	 *            the list of Updateable parts
	 * @param timeElapsed
	 *            used as a scale factor in calculations
	 */
	public static void updatePhysicsObjects(List<? extends PhysicsObject> parts, List<Updateable> partsToUpdate, double timeElapsed, Vector gravity) {
		//TODO change names of parts and partsToUpdate
		
		// update the Updateable parts
		for (Updateable u : partsToUpdate) {
			u.update(timeElapsed, gravity);

			// check for collisions
			for (PhysicsObject po : parts) {
				// don't check a part with itself!
 				if (!u.equals(po)) { 
					CollisionResult collResult = Collisions.checkForCollision((PhysicsObject) u, po);

					if (collResult.getCollisionOccurred() == true) {
						// move the part so that it is no longer colliding
						((PhysicsObject) u).getPosition().add(collResult.getProjectionVector());

						// perform physics calculations
						Collisions.collide((PhysicsObject) u, po, collResult);

						// make the parts interact if needed
						//if (parts.get(j) instanceof Interactable)
						//	((Interactable) parts.get(j)).interact((Part) partsToUpdate.get(i));
					}
				}
			}
		}
	}
	
}
