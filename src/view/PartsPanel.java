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

package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import parts.PartType;


/**
 * A JPanel specialization which contains 'buttons' that the user can click to
 * add a new Part to the world.
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
public class PartsPanel extends JPanel {

	// attributes ///////////////////////////////////////////

	/**
	 * 
	 */
	private static final long serialVersionUID = 4809402385159227764L;
	/**
	 * The currently selected Part.
	 */
	private PartType currentlySelectedPart;

	
	// constructors /////////////////////////////////////////

	/**
	 * Creates a new PartsPanel with a bunch of buttons on it.
	 */
	public PartsPanel() {
		currentlySelectedPart = null;
		// set layout
		setLayout(new GridLayout(1, 10));
		// add parts button
		addButtons();
	}

	
	// behaviors ////////////////////////////////////////////

	/**
	 * Creates a button for each PartType, without needing to know the number of
	 * PartTypes that exist.
	 */
	private void addButtons() {
		// uses the *same* listener for all buttons!!
		PartButtonListener listener = new PartButtonListener();
		for (PartType p : PartType.values()) {
/*			if (p != PartType.PULLCORDLIGHTON)
				if (p != PartType.CONVEYORBELTNORM)
					if (p != PartType.CONVEYORBELTREV)
						if (p != PartType.FLASHLIGHTON) {*/
							PartButton newButton = new PartButton(p);
							newButton.addActionListener(listener);
							add(newButton);
						//}	
		}
	}

	/**
	 * Clears the currently selected part.
	 */
	public void clearCurrentlySelectedPart() {
		this.currentlySelectedPart = null;
	}

	/**
	 * Gets the Part that is currently selected.
	 */
	public PartType getCurrentlySelectedPart() {
		return this.currentlySelectedPart;
	}

	
	// listeners ////////////////////////////////////////////

	/**
	 * A listener that can be used for all PartButtons. There is no need to add
	 * a listener for each kind of PartButton.
	 */
	private class PartButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			currentlySelectedPart = ((PartButton) e.getSource()).getPartType();
		}
	}

	/**
	 * A JButton that holds a PartType reference.
	 */
	private class PartButton extends JButton {

		private static final long serialVersionUID = 1L;
		/**
		 * A PartType reference being stored.
		 */
		private PartType type;

		/**
		 * Constructor that sets the PartType of this PartButton object to that
		 * of the parameter. This constructor also creates an icon, then adds
		 * the icon to this PartButton.
		 * 
		 * @param aPartType
		 *            - a PartType
		 */
		public PartButton(PartType aPartType) {
			this.type = aPartType;
			//Icon anIcon = new ImageIcon("resources/images/button_thumbs/"
			//		+ aPartType.toString().toLowerCase() + "_thumb.png");
			//this.setIcon(anIcon);
			this.setText(type.toString());
		}

		/**
		 * This method returns the PartType reference 'type' being stored by
		 * this PartButton class.
		 * 
		 * @return A PartType
		 */
		public PartType getPartType() {
			return type;
		}
		
	}
	
}
