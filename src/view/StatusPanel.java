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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

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
public class StatusPanel extends JPanel {
	
	// attributes ///////////////////////////////////////////
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7655338936758713965L;
	/**
	 * JLabel which displays context sensitive information.
	 */
	private JLabel statusLabel;
	/**
	 * Timer that governs the update speed on statusLabel.
	 */
	private Timer statusLabelTimer;
	
	
	// constructors /////////////////////////////////////////
	
	public StatusPanel() {
		// Create Label
		this.statusLabel = new JLabel();
		this.statusLabel.setForeground(Color.BLACK);
		this.statusLabel.setPreferredSize(new Dimension(100, 30));
		this.statusLabelTimer = new Timer(2000, new statusLabelTimerListener());
		this.setStatus("Welcome!");
		
		this.add(statusLabel);
	}
	
	
	// behaviors ////////////////////////////////////////////
	
	/**
	 * This method displays a status message to the user. It also resets the
	 * statusLabelTimer so that messages display for the appropriate amount of
	 * time.
	 * 
	 * @param status
	 *            - a String
	 */
	public void setStatus(String status) {
		// set new status message
		this.statusLabel.setText(status);
		// reset timer
		this.statusLabelTimer.restart();
	}
	
	
	// listeners ////////////////////////////////////////////
	
	/**
	 * This private Listener class will clear the current status message being
	 * displayed in the statusLabel JLabel.
	 */
	private class statusLabelTimerListener implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0) {
			// clear the status message
			setStatus("");
			statusLabelTimer.stop();
		}
		
	}
	
}
