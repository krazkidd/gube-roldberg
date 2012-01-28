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

package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

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
public class AboutFrame extends JFrame {

	// attributes /////////////////////////////////////////

	/**
	 * 
	 */
	private static final long serialVersionUID = -148033926718571423L;

	
	// constructors /////////////////////////////////////////
	
	/*---------------------------------------------------------------------
	|  Method AboutFrame (constructor)
	|
	|  Purpose:  
	|
	|  Pre-condition:  
	|
	|  Post-condition: 
	|
	|  Parameters:
	|      
	|
	|  Returns:  
	*-------------------------------------------------------------------*/
	public AboutFrame() {
		// icon
		JPanel pnlIcon = new JPanel();
		pnlIcon.setLayout(new BorderLayout());

		// close button
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new btnCloseListener());

		// add stuff to panel
		JTextArea txtAuthor = new JTextArea("Gube Roldberg\nCopyright 2008 Mark Ross and Duncan Krassikoff\nCopyright 2011 Mark Ross\nThis program comes with ABSOLUTELY NO WARRANTY.\nThis is free software, and you are welcome to redistribute it under certain conditions;\nsee COPYING for details.");
		pnlIcon.add(txtAuthor, BorderLayout.CENTER);
		pnlIcon.add(btnClose, BorderLayout.SOUTH);

		// add panel and set window properties
		add(pnlIcon);
		//setTitle("Team Tokyo Sea Lions");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setPreferredSize(new Dimension(650, 450));
		pack();
	}

	
	// listeners ////////////////////////////////////////////
	
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
	private class btnCloseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			dispose();
		}
		
	}
	
}
