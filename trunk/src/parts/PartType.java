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

package parts;

import java.awt.Color;

import engine.CollisionBoundType;

/**
 * An enum representing part types, which correspond to all the possible
 * concrete Part Objects in the model. <br>
 * <br>
 * Each enum stores information that is shared across objects containing each
 * PartType enum. <br>
 * <br>
 * <br>
 * Order in which values are stores:<br>
 * MUk / MUs / height / width / mass / CollisionType / draw (debug) color
 * height and width are in m. mass is in g
 * 
 * @author Mark Ross
 * @author Duncan Krassikoff
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
public enum PartType {
	
	// Baseball
	BASEBALL(0.3, 0.5, 0.10, 0.10, 143, CollisionBoundType.ELLIPSE, Color.WHITE),

	// Basketball
	BASKETBALL(0.8, 0.8, 0.35, 0.35, 620, CollisionBoundType.ELLIPSE, Color.ORANGE),

	// Bowling ball
	BOWLINGBALL(0.2, 0.9, 0.30, 0.30, 3629, CollisionBoundType.ELLIPSE, Color.BLUE),

	// Slimeball
	SLIMEBALL(0.0, 0.1, 0.12, 0.12, 100, CollisionBoundType.ELLIPSE, Color.RED),

	// Balloon
	BALLOON(0.9, 0.9, 0.40, 0.25, 2, CollisionBoundType.ELLIPSE, Color.GREEN),

	// Horizontal Brick wall
	WALLBRICKHORIZONTAL(0.65, 0.5, 0.20, 1.15, 10000, CollisionBoundType.RECTANGLE, Color.MAGENTA),

	// Vertical Brick Wall
	WALLBRICKVERTICAL(0.65, 0.5, 1.15, 0.20, 10000, CollisionBoundType.RECTANGLE, Color.MAGENTA),

	// Horizontal Glass Wall
	WALLGLASSHORIZONTAL(0.99, 0.98, 0.20, 1.15, 10000,	CollisionBoundType.RECTANGLE, Color.CYAN),

	// Vertical Glass Wall
	WALLGLASSVERTICAL(0.99, 0.98, 1.15, 0.20, 10000, CollisionBoundType.RECTANGLE, Color.CYAN),

	// Ramp
	RAMPLEFTFACE(0.99, 0.98, 0.74, 1.00, 4000, CollisionBoundType.POLYGON, Color.YELLOW),

	RAMPRIGHTFACE(0.99, 0.98, 0.74, 1.00, 4000, CollisionBoundType.POLYGON, Color.YELLOW);
	
/*
	// Free Roaming Chicken object
	CHICKEN(0.99, 0.98, 48, 48, 10, CollisionBoundType.CIRCLE),

	FLASHLIGHTOFF(0.99, 0.98, 28, 100, 5, CollisionBoundType.RECTANGLE), FLASHLIGHTON(
			0.99, 0.98, 28, 100, 5, CollisionBoundType.RECTANGLE),

	// Rope
	ROPE(0.0, 0.0, 0, 0, 1, CollisionBoundType.LINE),

	// Pulley
	PULLEY(0.99, 0.98, 40, 40, 2, CollisionBoundType.CIRCLE),

	// Bucket
	BUCKET(0.6, 0.7, 51, 42, 5, CollisionBoundType.CIRCLE),

	// Pull Cord Light
	PULLCORDLIGHTOFF(0.99, 0.98, 67, 50, 5, CollisionBoundType.RECTANGLE), PULLCORDLIGHTON(
			0.99, 0.98, 67, 50, 5, CollisionBoundType.RECTANGLE),

	// Belt
	BELT(0.0, 0.0, 0, 0, 1, CollisionBoundType.LINE),

	// Gear
	GEAR(0.99, 0.98, 48, 48, 2, CollisionBoundType.CIRCLE),

	// Conveyor Belt
	CONVEYORBELT(0.8, 0.8, 20, 100, 40, CollisionBoundType.RECTANGLE), 
	CONVEYORBELTNORM(0.8, 0.8, 20, 100, 40, CollisionBoundType.RECTANGLE), 
	CONVEYORBELTREV(0.8, 0.8, 20, 100, 40, CollisionBoundType.RECTANGLE),

	// Rodent Motor
	RODENTMOTOROFF(0.99, 0.9, 54, 82, 40, CollisionBoundType.RECTANGLE),

	// Electric Motor
	ELECTRICMOTOR(0.99, 0.98, 50, 50, 40, CollisionBoundType.CIRCLE),

	CORD(0.0, 0.0, 0, 0, 1, CollisionBoundType.LINE),

	// Power Generator
	POWERGENERATOR(0.4, 0.4, 75, 75, 40, CollisionBoundType.RECTANGLE),

	// Wall Switch
	SWITCH(0.99, 0.98, 48, 36,2, CollisionBoundType.RECTANGLE),

	// Outlet
	OUTLET(0.99, 0.98, 21, 25, 2, CollisionBoundType.RECTANGLE);
*/
	/**
	 * The coefficient of friction for this PartType
	 */
	private final double MUk;
	/**
	 * This static coefficient of friction for this PartType
	 */
	private final double MUs;
	/**
	 * Height for this PartType
	 */
	private final double height;
	/**
	 * Width for this PartType
	 */
	private final double width;
	/**
	 * Mass for this PartType
	 */
	private final int mass;
	/**
	 * CollisionBoundType for this PartType.
	 */
	private final CollisionBoundType collisionBoundType;
	/**
	 * The default/debug color
	 */
	Color color;
	
	// constructors /////////////////////////////////////////
	
	PartType(double muk, double mus, double h, double w, int m,
			CollisionBoundType colBoundType, Color color) {
		this.MUk = muk;
		this.MUs = muk;
		this.height = h;
		this.width = w;
		this.mass = m;
		this.collisionBoundType = colBoundType;
		this.color = color;
	}

	// behaviors /////////////////////////////////////////
	
	/**
	 * This method returns this PartType's MUk
	 * 
	 * @return - a double
	 */
	public double getMUk() {
		return MUk;
	}

	/**
	 * This method returns this PartType's MUs
	 * 
	 * @return
	 */
	public double getMUs() {
		return MUs;
	}

	/**
	 * This method is called on a PartType, and is passed another PartType. It
	 * returns the larger MUk out of the two.
	 * 
	 * @param type
	 *            - a PartType
	 * @return - a double
	 */
	public double getMUk(PartType type) {
		return Math.max(MUk, type.getMUk());
	}

	/**
	 * This method 'gets' a double representing the width of this Part Object.
	 * 
	 * @return a double
	 */
	public double getWidth() {
		return this.width;
	}

	/**
	 * This method 'gets' a double representing the height of this Part Object.
	 * 
	 * @return a double
	 */
	public double getHeight() {
		return this.height;
	}

	/**
	 * This method 'gets' a double representing the mass of this Part Object.
	 * 
	 * @return a double
	 */
	public double getMass() {
		return this.mass;
	}

	/**
	 * This method 'gets' the CollisionBoundType of this Part Object.
	 * 
	 * @return a CollisionBoundType Object
	 */
	public CollisionBoundType getCollisionBoundType() {
		return this.collisionBoundType;
	}
	
	public Color getColor() {
		return this.color;
	}
	
}
