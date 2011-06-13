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
	|  Purpose:  [Explain what this method does to support the correct
	|      operation of its class, and how it does it.]
	|
	|  Pre-condition:  [Any non-obvious conditions that must exist
	|      or be true before we can expect this method to function
	|      correctly.]
	|
	|  Post-condition: [What we can expect to exist or be true after
	|      this method has executed under the pre-condition(s).]
	|
	|  Parameters:
	|      parameter_name -- [Explanation of the purpose of this
	|          parameter to the method.  Write one explanation for each
	|          formal parameter of this method.]
	|
	|  Returns:  [If this method sends back a value via the return
	|      mechanism, describe the purpose of that value here, otherwise
	|      state 'None.']
	*-------------------------------------------------------------------*/
	public AboutFrame() {
		// icon
		JPanel pnlIcon = new JPanel();
		pnlIcon.setLayout(new BorderLayout());

		// close button
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new btnCloseListener());

		// add stuff to panel
		JTextArea txtAuthor = new JTextArea("Gube Roldberg\nCopyright 2008 Mark Ross and Duncan Krassikoff\nThis program comes with ABSOLUTELY NO WARRANTY.\nThis is free software, and you are welcome to redistribute it under certain conditions;\nsee COPYING for details.");
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
	private class btnCloseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			dispose();
		}
		
	}
	
}
