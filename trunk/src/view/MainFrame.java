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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/*=============================================================================
|   Prog. Name:  Rube Goldberg
|      Authors:  Mark Ross
|                Duncan Krassikoff
|
|       Grader:  [Your TA or Section Leader's name]
|
|       Course:  [Course Number]
|   Instructor:  L. McCann
|     Due Date:  [Due Date and Time]
|
|  Description:  The entry point for the Rube Goldberg game. This class creates and displays
|                all visual aspects of the program. TODO MOAR
|                
| Deficiencies:  [If you know of any problems with the code, provide
|                details here, otherwise clearly state that you know
|                of no unsatisfied requirements and no logic errors.]
*===========================================================================*/
public class MainFrame extends JFrame {
	
	// attributes ///////////////////////////////////////////

	/**
	 * 
	 */
	private static final long serialVersionUID = -6336449520993659736L;
	/**
	 * The panel that contains the world.
	 */
	private WorldPanel worldPanel;
	/**
	 * The panel that contains the Parts buttons.
	 */
	private PartsPanel partsPanel;
	/**
	 * The panel that contains the Start/Stop buttons, status, etc.
	 */
	private ToolbarPanel toolbarPanel;
	/**
	 * 
	 */
	private StatusPanel statusPanel;
	/**
	 * The menu of this window.
	 */
	private JMenuBar menuBar;

	
	// constructors /////////////////////////////////////////

	/**
	 * Creates a new MainFrame which is a Rube Goldberg game.
	 */
	public MainFrame() {
		// set window properties
		setLayout(new BorderLayout());
		//setDefaultCloseOperation(DISPOSE_ON_CLOSE);// already the default op
		setPreferredSize(new Dimension(1024, 768));
		setTitle("Gube Roldberg");

		// create menu bar and top level menu items
		menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);

		// create second level menu items and add them
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		JMenuItem aboutMenuItem = new JMenuItem("About");
		
		// add listeners
		exitMenuItem.addActionListener(new mnuExitListener());
		aboutMenuItem.addActionListener(new mnuAboutListener());
		
		// add items to menu
		fileMenu.add(exitMenuItem);
		helpMenu.add(aboutMenuItem);

		// create panels
		partsPanel = new PartsPanel();
		statusPanel = new StatusPanel();
		worldPanel = new WorldPanel(partsPanel, statusPanel);
		toolbarPanel = new ToolbarPanel(worldPanel);

		// add everything to the window
		this.setJMenuBar(this.menuBar);
		this.add(worldPanel, BorderLayout.CENTER);
		this.add(partsPanel, BorderLayout.SOUTH);
		this.add(toolbarPanel, BorderLayout.NORTH);
		this.addWindowListener(new myWindowListener());

		this.pack();
	}

	
	// main /////////////////////////////////////////////////

	/**
	 * Main entry point of the program. Creates and displays a new window.
	 */
	public static void main(String[] args) {
		// create the window and display it
		new MainFrame().setVisible(true);
	}

	
	// listeners ////////////////////////////////////////////

	/**
	 * This ActionListener creates a pop-up dialog containing about information.
	 */
	private class mnuAboutListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			AboutFrame abt = new AboutFrame();
			abt.setLocationRelativeTo(null);
			abt.setVisible(true);
		}
	}

	/**
	 * This ActionListener exits the program.
	 */
	private class mnuExitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			worldPanel.deactivateWorld();
			dispose();
		}
	}
	
	private class myWindowListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent we) {
			worldPanel.deactivateWorld(); 
			dispose();
		}
	}
}
