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

/*+----------------------------------------------------------------------
||
||  Interface Updateable 
||
||         Author:  Mark Ross
||
||        Purpose:  Marks a PhysicsObject that should have its state updated with
||                  each run through the game loop. For example, a ball should
||                  fall and bounce around (update), but not the ground. A synonymous 
||                  interface name could be 'Mobile' or 'Moveable'. 
||
|+-----------------------------------------------------------------------
||
||  Interface Methods:  + update(double, double) : void
||
++-----------------------------------------------------------------------*/
public interface Updateable {

	public void update(double timeElapsed, Vector gravity);	
	
}
