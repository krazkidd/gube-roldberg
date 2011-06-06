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

import java.awt.Point;

import engine.Vector;
import exceptions.PartCreationException;

/**
 * Produces new parts. The parts fit into two broad categories: Either they can 
 * exist on their own or they only exist between other Parts.
 * 
 * There are two different <code>getPart</code> methods to account for this.
 * 
 * @author Mark Ross
 * @author Duncan Krassikoff
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
public class PartFactory {

	/**
	 * Creates a Part that can be placed in the world on its own (it does not depend
	 * on other Parts for its existence. A Point is used to tell the new Part its 
	 * position in the world.
	 * 
	 * @param type
	 *            the <code>PartType</code> of the Part to be produced.
	 * @param point
	 *            the <code>Point</code> where the new Part's position vector is
	 *            supposed to be (probably provided by a mouse click).
	 * @return a reference to the newly created <code>Part</code>.
	 * @throws PartCreationException 
	 */
	public static Part getPart(PartType type, Point point) throws PartCreationException {
		Part toReturn = null;
		// set the position vector of the new part so that the center of the part is where the
		// cursor was upon the click
		Vector aPoint = new Vector(point.x - (type.getWidth() / 2), point.y - (type.getHeight() / 2));

		switch (type) {

		default:
			throw new PartCreationException("Invalid part type selected!");

		case BASEBALL:
			toReturn = new Ball(aPoint, type);
			break;

		case BASKETBALL:
			toReturn = new Ball(aPoint, type);
			break;

		case BOWLINGBALL:
			toReturn = new Ball(aPoint, type);
			break;
			
		case BALLOON:
			toReturn = new Balloon(aPoint);
			break;
			
		case WALLBRICKHORIZONTAL:
			toReturn = new Wall(aPoint, type);
			break;

		case WALLBRICKVERTICAL:
			toReturn = new Wall(aPoint, type);
			break;
/*					
		 case BUCKET:
			toReturn = new Bucket(aPoint);
			break;

		 case ELECTRICMOTOR:
			toReturn = new ElectricMotor(aPoint);
			break;
			
		 case CHICKEN:
			 toReturn = new Chicken(aPoint);
			 break;
			 
		 case FLASHLIGHTOFF:
			 toReturn = new Flashlight(aPoint);
			 break;
			
		 case POWERGENERATOR:
			 toReturn = new PowerGenerator(aPoint);
			 break;
*/
		 case WALLGLASSHORIZONTAL:
			 toReturn = new Wall(aPoint, type);
			break;
			
		 case WALLGLASSVERTICAL:
			 toReturn = new Wall(aPoint, type);
				break;
/*
		 case PULLCORDLIGHTOFF:
			 toReturn = new PullCordLight(aPoint);
			break;
		
		 case PULLEY:
			toReturn = new Pulley(aPoint);
			break;
*/					
		 case RAMPLEFTFACE:
			toReturn = new Ramp(aPoint, PartType.RAMPLEFTFACE);
			break;
			
		 case RAMPRIGHTFACE:
				toReturn = new Ramp(aPoint, PartType.RAMPRIGHTFACE);
				break;
/*				
		 case RODENTMOTOROFF:
			toReturn = new RodentMotor(aPoint);
			break;
*/				
		 case SLIMEBALL:
			toReturn = new Ball(aPoint, type);
			break;
/*		 
		 case GEAR:
			 toReturn = new Gear(aPoint);
			 break;
					
		 case SWITCH:
			toReturn = new Switch(aPoint);
			break;
			
		 case CONVEYORBELT:
			 toReturn = new ConveyorBelt(aPoint);
			 break;
			 
		 case OUTLET:
			 toReturn = new Outlet(aPoint);
			 break;
*/			
		}

		return toReturn;
	}

	/**
	 * Creates a new part that depends on two other parts for its existence. 
	 * Use this when the new Part is to be *connected* to the two given Part 
	 * parameters.
	 * 
	 * @param type
	 *            - a PartType
	 * @param partOne
	 *            - a Part Object
	 * @param partTwo
	 *            - a Part Object
	 * @return - a reference to the Part that has been created.
	 * @throws PartCreationException 
	 */
/*	public static Part getPart(PartType type, Attachable partOne, Attachable partTwo) throws PartCreationException {
		Part toReturn = null;

		switch (type) {

		default:
			throw new PartCreationException("Invalid part type selected!");

		case BELT:
			toReturn = new Belt(partOne, partTwo);
			break;
		
		 case ROPE:
			 toReturn = new Rope(partOne, partTwo);
			 break;
			 
		 case CORD:
			 toReturn = new Cord(partOne, partTwo);
			 break;
		}

		return toReturn;
	}
*/
}
