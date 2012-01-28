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

package exceptions;

/**
 * Represents the occurrence of an error when creating a new <code>Part</code>.
 * Used because certain <code>Part</code>s (particularly <code>Rope</code> and <code>Belt</code>) need
 * certain conditions met upon creation; this exception should
 * be thrown if those conditions aren't met.
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
public class PartCreationException extends Exception {

	// attributes ///////////////////////////////////////////
	
	private static final long serialVersionUID = 1605071980909072400L;
	

	// constructors /////////////////////////////////////////

	public PartCreationException(String msg) {
		super(msg);
	}
	
}
