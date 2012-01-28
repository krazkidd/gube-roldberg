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

import view.WorldPanel;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * A specialized JPanel that holds world control buttons.
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
public class ToolbarPanel extends JPanel {
	
	// attributes ///////////////////////////////////////////
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5726030976257428162L;
	/**
	 * JButtons for 'Run' 'Stop' and 'Clear Screen' actions.
	 */
	private JButton runButton, tempSaveButton, resetButton, clearPartsButton;
	/**
	 * A reference to the WorldPanel
	 */
	private WorldPanel worldPanel;
	/**
	 * Icons for JButtons
	 */
	private Icon runIcon, resetIcon, clearIcon, pauseIcon;

	
	// constructors /////////////////////////////////////////

	/**
	 * Creates a new ToolbarPanel with a reference to the world. It needs to
	 * know about the world so that it can start the physics simulation, stop
	 * (pause) the physics simulation and clear the screen of parts.
	 * 
	 * @param worldPnl
	 *            - a WorldPanel
	 */
	public ToolbarPanel(WorldPanel worldPnl) {
		this.worldPanel = worldPnl;
		this.setLayout(new GridLayout(1, 0));
		
		// Create Icons
		this.runIcon = new ImageIcon("resources/images/other/play.png");
		this.pauseIcon = new ImageIcon("resources/images/other/pause.png");
		this.resetIcon = new ImageIcon("resources/images/other/reset.png");
		this.clearIcon = new ImageIcon("resources/images/other/clear.png");		

		// Create Buttons
		//this.runButton = new JButton(runIcon);
		this.runButton = new JButton("Run");
		this.tempSaveButton = new JButton("Temp Save");
		//this.resetButton = new JButton(resetIcon);
		this.resetButton = new JButton("Reset");
		//this.clearPartsButton = new JButton(clearIcon);
		this.clearPartsButton = new JButton("Clear");

		// set preferred size
		this.runButton.setPreferredSize(new Dimension(40, 30));
		this.tempSaveButton.setPreferredSize(new Dimension(40, 30));
		this.resetButton.setPreferredSize(new Dimension(40, 30));
		this.clearPartsButton.setPreferredSize(new Dimension(40, 30));

		// add listeners
		this.runButton.addActionListener(new runButtonListener());
		this.tempSaveButton.addActionListener(new tempSaveButtonListener());
		this.resetButton.addActionListener(new resetButtonListener());
		this.clearPartsButton.addActionListener(new clearPartsButtonListener());

		// Add buttons to this panel
		this.add(runButton);
		this.add(resetButton);
		this.add(tempSaveButton);
		this.add(clearPartsButton);
		
		try {
			worldPanel.saveParts("resources/savedgames/tempSave.rgm");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		}
	}

	
	// behaviors ////////////////////////////////////////////

	private void setButtonsOnPause() {
		//runButton.setIcon(runIcon);
		runButton.setText("Run");
		tempSaveButton.setEnabled(true);
		resetButton.setEnabled(true);
		clearPartsButton.setEnabled(true);
	}

	private void setButtonsOnRun() {
		//runButton.setIcon(pauseIcon);
		runButton.setText("Pause");
		tempSaveButton.setEnabled(false);
		resetButton.setEnabled(false);
		clearPartsButton.setEnabled(false);
	}

	
	// listeners ////////////////////////////////////////////

	/**
	 * This private Listener class will 'start' the physics simulation on parts
	 * that have been added to the worldPanel. Also, this Listener will enable
	 * the stop button and disable both the run and clear buttons.
	 */
	private class runButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0) {
			// if world is running (pause button)
			if (worldPanel.isRunning()) {
				// stop run thread
				worldPanel.deactivateWorld();
				// set run icon
				setButtonsOnPause();

			} else {
				// set pause icon
				setButtonsOnRun();
				// resume run thread
				worldPanel.activateWorld();
			}
		}
		
	}
	
	/**
	 * TODO
	 */
	private class tempSaveButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			try {
				worldPanel.saveParts("resources/savedgames/tempSave.rgm");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

	/**
	 * This private Listener class will 'stop' the physics simulation on parts
	 * that have been added to the worldPanel. Also, this Listener will disable
	 * the stop button and enable both the run and clear buttons.
	 */
	private class resetButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			try {
				worldPanel.loadParts("resources/savedgames/tempSave.rgm");
				worldPanel.repaint();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

	/**
	 * This private Listener class will clear parts from the worldPanel.
	 */
	private class clearPartsButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			// clear parts from worldPanel
			worldPanel.clearParts();
			
			// clear temp save
			try {
				worldPanel.saveParts("resources/savedgames/tempSave.rgm");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

}