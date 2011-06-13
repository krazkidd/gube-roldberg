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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

import parts.Part;
import parts.PartFactory;
import parts.PartType;

import engine.Collisions;
import engine.Core;
import engine.Updateable;
import engine.Vector;
import exceptions.PartCreationException;

/*+----------------------------------------------------------------------
||
||        Authors:  Mark Ross
||                  Duncan Krassikoff
||
||        Purpose:  The WorldPanel is where where the parts show and interact
||                  with each other. The user, after selecting a Part from the
||                  PartsPanel, places the Part in the WorldPanel. At any time,
||					the user can click the 'Play' button in the ToolbarPanel to
||					make the WorldPanel run. The run() method is where Parts
||					are updated, collisions are checked, and physics calculations
||					are made. After each iteration of the run() method, repaint()
||					is called in order to update the screen. More methods will be
||                  added in the future to give the user more power in manipulating
||                  the Parts.
||
||  Inherits From:  JPanel
||
||     Interfaces:  Runnable, Serializable
||
|+-----------------------------------------------------------------------
||
||      Constants:  serialVersionUID - for use with the Serializable interface
||
|+-----------------------------------------------------------------------
||
||   Constructors:  + WorldPanel(PartsPanel, StatusPanel)
||
||  Class Methods:  N/A
||
||  Inst. Methods:  + paint(Graphics) : void
||                  + run() : void
||                  + activateWorld() : void
||                  + deactivateWorld() : void
||                  + isRunning() : boolean
||                  + clearParts() : void
||                  + saveParts(String) : void
||                  + loadParts(String) : void
||
++-----------------------------------------------------------------------*/
public class WorldPanel extends JPanel implements Runnable {
	
	// attributes ///////////////////////////////////////////

	/**
	 * 
	 */
	private static final long serialVersionUID = 2491605962802283992L;
	/**
	 * Tells whether or not the update thread is running.
	 */
	private boolean isRunning;
	/**
	 * The list of Parts that have been added to the world.
	 */
	private List<Part> parts;
	/**
	 * A reference to the parts panel so that the currently selected part
	 * can be retrieved.
	 */
	private PartsPanel partsPanel;
	/**
	 * A reference to the status panel so that messages can be displayed.
	 */
	private StatusPanel statusPanel;
	
	private Point2D.Double worldCenter;
	
	private double zoomLevel;

	
	// constructors /////////////////////////////////////////

	/*---------------------------------------------------------------------
    |  Method WorldPanel (constructor)
    |
    |  Purpose:  Initializes and displays the world.
    |
    |  Pre-condition: N/A
    |
    |  Post-condition: N/A
    |
    |  Parameters:
    |    partsPnl -- the parts selection panel
    |    statusPnl -- the status panel
    |
    |  Returns:  N/A
    *-------------------------------------------------------------------*/
	public WorldPanel(PartsPanel partsPnl, StatusPanel statusPnl) {
		this.partsPanel = partsPnl;
		this.statusPanel = statusPnl;
		this.isRunning = false;
		this.parts = new ArrayList<Part>();
		MouseListener ml = new MouseEventListener();
		addMouseListener(ml);
		addMouseMotionListener((MouseMotionListener) ml);
		addMouseWheelListener((MouseWheelListener) ml);
		worldCenter = new Point2D.Double(0.0, 0.0);
		zoomLevel = 100.0;
	}

	
	// behaviors ////////////////////////////////////////////

    /*---------------------------------------------------------------------
    |  Method paint
    |
    |  Purpose:  This method is called by Swing whenever the panel needs to
    |            be repainted. It can be and is manually triggered, though,
    |            whenever a part is added/removed and every time the Parts
    |            are updated in the run() method.
    |
    |  Pre-condition:  N/A
    |
    |  Post-condition: N/A
    |
    |  Parameters:
    |      g -- a Graphics instance given by Swing which keeps track of graphics
    |           devices and device space. Methods are called on this object in
    |           order to paint anything.
    |
    |  Returns:  None
    *-------------------------------------------------------------------*/
	public void paint(Graphics g) {
		// this is necessary to clear the panel
		super.paintComponent(g);

		// set background color
		setBackground(Color.LIGHT_GRAY);

		// cast the Graphics object to Graphics2D so we can draw 2D objects more
		// easily
		Graphics2D g2 = (Graphics2D) g;
		
		// put origin in center of window
		g2.translate(this.getWidth() / 2, this.getHeight() / 2);
		
		// apply transformations to flip y-axis (normally, positive y goes down)
		g2.transform(new AffineTransform(1.0, 0.0, 0.0, -1.0, 0.0, 0.0));
				
		// zoom in
		//g2.transform(new AffineTransform(10.0, 0.0, 0.0, 10.0, 0.0, 0.0));
		g2.scale(zoomLevel, zoomLevel);

		// draw all the parts
		for (Part p : parts) {
			g2.setColor(p.getPartType().getColor());
			g2.fill(p.getShape());
		}
		
		// draw debug lines
		//g2.drawLine(-1000, 0, 1000, 0);
		//g2.drawLine(0, -1000, 0, 1000);
		//g2.drawRect(10, 10, 20, 20);
	}	

    /*---------------------------------------------------------------------
    |  Method run
    |
    |  Purpose:  This is an override of the method in the Runnable interface. We use this
	|            to update the objects on the screen. This method should start when the
	|            user clicks 'Play' and keep going until the user clicks 'Stop.'
    |
    |  Pre-condition:  [Any non-obvious conditions that must exist
    |      or be true before we can expect this method to function
    |      correctly.]
    |
    |  Post-condition: [What we can expect to exist or be true after
    |      this method has executed under the pre-condition(s).]
    |
    |  Parameters:
    |      None
    |
    |  Returns:  None
    *-------------------------------------------------------------------*/
	public void run() {
		// save the parts list (so it can be reset) and make a list of only the Updateable parts
		List<Part> savedPartsList = new ArrayList<Part>();
		List<Updateable> partsToUpdate = new ArrayList<Updateable>();
		for (Part p : parts) {
			savedPartsList.add(p);
			
			if (p instanceof Updateable) 
				partsToUpdate.add((Updateable) p);
		}
	
		Vector gravity = new Vector(0.0, -9.80);
		
		long currTime = System.nanoTime();
		long prevTime = currTime;

		// start update loop
		while (isRunning) {
			// update position of movable objects
			//currTime = System.nanoTime();
			//Core.updatePhysicsObjects(parts, partsToUpdate, 100.0, gravity);
			Core.updatePhysicsObjects(parts, partsToUpdate, 0.01, gravity);
 			
			//System.out.println((currTime - prevTime) / 1000000000.0);
		
			repaint();
			prevTime = currTime;
			try{   
				Thread.sleep(10);//sleep for 1 ms    
			} 
				catch(InterruptedException ie){ 
			}
		}

		// restore the saved parts list
		parts = savedPartsList;
		repaint(); 
	}

    /*---------------------------------------------------------------------
    |  Method activateWorld
    |
    |  Purpose:  Sets this WorldPanel in motion by creating a new thread,
    |            which then calls run().
    |
    |  Pre-condition:  [Any non-obvious conditions that must exist
    |      or be true before we can expect this method to function
    |      correctly.]
    |
    |  Post-condition: [What we can expect to exist or be true after
    |      this method has executed under the pre-condition(s).]
    |
    |  Parameters:
    |      None
    |
    |  Returns:  None
    *-------------------------------------------------------------------*/
	public void activateWorld() {
		this.isRunning = true;
		Thread updateThread = new Thread(this);
		updateThread.start();
	}

    /*---------------------------------------------------------------------
    |  Method [Method Name]
    |
    |  Purpose:  Stops the update loop, pausing the world.
    |
    |  Pre-condition:  [Any non-obvious conditions that must exist
    |      or be true before we can expect this method to function
    |      correctly.]
    |
    |  Post-condition: [What we can expect to exist or be true after
    |      this method has executed under the pre-condition(s).]
    |
    |  Parameters:
    |      None
    |
    |  Returns:  None
    *-------------------------------------------------------------------*/
	public void deactivateWorld() {
		this.isRunning = false;
	}

	/*---------------------------------------------------------------------
    |  Method isRunning
    |
    |  Purpose:  Tests if the WorldPanel is in a running state, in which the Parts are being
	|            updated and repainted.
    |
    |  Pre-condition:  N/A
    |
    |  Post-condition: N/A
    |
    |  Parameters:
    |      N/A
    |
    |  Returns:  true if the WorldPanel is currently running, false otherwise.
    *-------------------------------------------------------------------*/
	public boolean isRunning() {
		return this.isRunning;
	}
	
    /*---------------------------------------------------------------------
    |  Method clearParts
    |
    |  Purpose:  Clears all the parts from the world and forces a repaint.
    |
    |  Pre-condition:  [Any non-obvious conditions that must exist
    |      or be true before we can expect this method to function
    |      correctly.]
    |
    |  Post-condition: [What we can expect to exist or be true after
    |      this method has executed under the pre-condition(s).]
    |
    |  Parameters:
    |      None
    |
    |  Returns:  None
    *-------------------------------------------------------------------*/
	public void clearParts() {
		parts.clear();

		repaint();
	}

    /*---------------------------------------------------------------------
    |  Method saveParts
    |
    |  Purpose:  Saves the list of parts to a file.
    |
    |  Pre-condition:  [Any non-obvious conditions that must exist
    |      or be true before we can expect this method to function
    |      correctly.]
    |
    |  Post-condition: [What we can expect to exist or be true after
    |      this method has executed under the pre-condition(s).]
    |
    |  Parameters:
    |      filename -- the absolute or relative location to save the file to
    |
    |  Returns:  None
    *-------------------------------------------------------------------*/
	public void saveParts(String filename) throws IOException {
		Serializable object;
		ObjectOutputStream objstream = new ObjectOutputStream(new FileOutputStream(filename));
		
		object = (Serializable) parts;
		objstream.writeObject(object);
		
		objstream.close();
	}

    /*---------------------------------------------------------------------
    |  Method loadParts
    |
    |  Purpose:  Loads a list of parts from a file. Works with the game save
    |            mechanism as well as the reset button.
    |
    |  Pre-condition:  The file must exist and contain a serialized List<Part> object.
    |
    |  Post-condition: The parts list will have been replaced by the new one.
    |
    |  Parameters:
    |      filename -- the relative or absolute location to the file 
    |
    |  Returns:  None
    *-------------------------------------------------------------------*/
	public void loadParts(String filename) throws FileNotFoundException, IOException, ClassNotFoundException  {
		ObjectInputStream objstream = new ObjectInputStream(new FileInputStream(filename));

		Object object = objstream.readObject();
		try {
			parts = (List<Part>) object;
		} catch (ClassCastException e) {
			// TODO is this the right exception class??
			System.err.println("CLASS CAST EXCEPTION\n");
			e.printStackTrace();
		}

		objstream.close();
	}

	
	// listeners /////////////////////////////////////////////////////////

	/*+----------------------------------------------------------------------
	 ||
	 ||  Class MouseEventListener
	 ||
	 ||        Purpose:  Handles all mouse events for the WorldPanel. The resulting
	 ||                  actions include adding/deleting parts, connecting parts,
	 ||                  moving parts, zooming/panning the world, etc.
	 ||
	 ||  Inherits From:  None
	 ||
	 ||     Interfaces:  MouseListener, MouseMotionListener
	 ||
	 |+-----------------------------------------------------------------------
	 ||
	 ||      Constants:  None
	 ||
	 |+-----------------------------------------------------------------------
	 ||
	 ||   Constructors:  MouseEventListener()
	 ||
	 ||  Class Methods:  None
	 ||
	 ||  Inst. Methods:  + mousePressed(MouseEvent)
	 ||                  + mouseClicked(MouseEvent)
	 ||                  + mouseEntered(MouseEvent)
	 ||                  + mouseExited(MouseEvent)
	 ||                  + mouseReleased(MouseEvent)
	 ||                  + mouseDragged(MouseEvent)
	 ||                  + mouseMoved(MouseEvent)
	 ||
	 ++-----------------------------------------------------------------------*/
	private class MouseEventListener implements MouseListener, MouseMotionListener, MouseWheelListener {

		/**
		 * current selected part type reference
		 */
		private PartType partTypeSelected;
		/**
		 * reference to the point that was clicked
		 */
		private Point2D.Double mouseLocation;

		/*---------------------------------------------------------------------
	    |  Method MouseEventListener (constructor)
	    |
	    |  Purpose:  Initializes instance variables.
	    |
	    |  Pre-condition: N/A
	    |
	    |  Post-condition: N/A
	    |
	    |  Parameters:
	    |    None
	    |
	    |  Returns:  N/A
	    *-------------------------------------------------------------------*/
		public MouseEventListener() {
			this.partTypeSelected = null;
			this.mouseLocation = new Point2D.Double();
		}

		/*---------------------------------------------------------------------
	    |  Method mousePressed
	    |
	    |  Purpose:  Handles any mouse clicks. TODO
	    |
	    |  Pre-condition:  [Any non-obvious conditions that must exist
	    |      or be true before we can expect this method to function
	    |      correctly.]
	    |
	    |  Post-condition: [What we can expect to exist or be true after
	    |      this method has executed under the pre-condition(s).]
	    |
	    |  Parameters:
	    |      mse -- the MouseEvent given by Swing which contains information
	    |             about the user's actions.
	    |
	    |  Returns:  None
	    *-------------------------------------------------------------------*/
		@Override
		public void mousePressed(MouseEvent mse) {
			// if game is not running
			if (!isRunning) {
				mouseLocation.setLocation((mse.getPoint().getX() - getWidth() / 2.0) / zoomLevel, ((mse.getPoint().getY() * -1 + getHeight() / 2.0)) / zoomLevel); 
				partTypeSelected = partsPanel.getCurrentlySelectedPart();
				
				// on a left click, do this
				if (mse.getButton() == MouseEvent.BUTTON1) // TODO change to use &
					leftPress();
				if (mse.getButton() == MouseEvent.MOUSE_WHEEL)
					System.out.println("mousewheel scrolled");
			}
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}

		@Override
		public void mouseDragged(MouseEvent mse) {
			// if game is not running
			if (!isRunning) {
				mouseLocation.setLocation((mse.getPoint().getX() - getWidth() / 2.0) / zoomLevel, ((mse.getPoint().getY() * -1 + getHeight() / 2.0)) / zoomLevel);
				
				System.out.println("Mouse location: x=" + mse.getX() + ", y=" + mse.getY());
				System.out.println("Transformed location: x=" + mouseLocation.getX() + ", y= " + mouseLocation.getY());
				
				partTypeSelected = partsPanel.getCurrentlySelectedPart();
				
				// on a left click, do this
				//if (mse.getModifiers() == MouseEvent.BUTTON1_DOWN_MASK) { // TODO the bitmask doesn't work
				//	System.out.println("Left mouse button down");
					leftPress();
				//}
			}
			System.out.println("Mouse dragged");
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
		}
		

		@Override
		public void mouseWheelMoved(MouseWheelEvent mse) {
			System.out.println("mousewheel rotated");
			zoomLevel += mse.getWheelRotation() * -10;
			if (zoomLevel < 10)
				zoomLevel = 10;
			repaint();
		}

		/*---------------------------------------------------------------------
	    |  Method tryPartmake
	    |
	    |  Purpose: Called when a regular Part is selected, and attempts to create that
		|           part at the location clicked 
	    |
	    |  Pre-condition:  [Any non-obvious conditions that must exist
	    |      or be true before we can expect this method to function
	    |      correctly.]
	    |
	    |  Post-condition: [What we can expect to exist or be true after
	    |      this method has executed under the pre-condition(s).]
	    |
	    |  Parameters:
	    |      None
	    |
	    |  Returns:  None
	    *-------------------------------------------------------------------*/
		private void tryPartMake() {
			Part toCreate = null;
			
			if (partTypeSelected != null) {
				try {
					System.out.println("Creating part at location: x=" + mouseLocation.getX() + ", y=" + mouseLocation.getY());
					toCreate = PartFactory.getPart(partTypeSelected, mouseLocation);
					
					if (!collisionOnPlace(toCreate)) {
						if (parts.add(toCreate)) { // TODO check for NullPointerException? 
							repaint();
							statusPanel.setStatus("New " + toCreate.getPartType().toString() + " added!");
						}
					}
					else 
						statusPanel.setStatus("Cannot place part there!");
				} catch (PartCreationException e) {
					e.printStackTrace(); 
					statusPanel.setStatus("ERROR: Could not create part!");
				}
			}
		}

		/*---------------------------------------------------------------------
	    |  Method leftPress
	    |
	    |  Purpose:  Tries to create a regular part when the user presses the 
	    |            left mouse button.
	    |
	    |  Pre-condition:  [Any non-obvious conditions that must exist
	    |      or be true before we can expect this method to function
	    |      correctly.]
	    |
	    |  Post-condition: [What we can expect to exist or be true after
	    |      this method has executed under the pre-condition(s).]
	    |
	    |  Parameters:
	    |      None
	    |
	    |  Returns:  None
	    *-------------------------------------------------------------------*/
		private void leftPress() {
			tryPartMake();
		}

		/*---------------------------------------------------------------------
	    |  Method collisionOnPlace
	    |
	    |  Purpose:  Checks if the part being placed collides with any part already
	    |            in the world. This prevents the user from placing any parts on
	    |            top of another.
	    |
	    |  Pre-condition:  [Any non-obvious conditions that must exist
	    |      or be true before we can expect this method to function
	    |      correctly.]
	    |
	    |  Post-condition: [What we can expect to exist or be true after
	    |      this method has executed under the pre-condition(s).]
	    |
	    |  Parameters:
	    |      toCreate -- the part which intends to be placed in the world
	    |
	    |  Returns:  true if there was a collision with another part; false otherwise.
	    *-------------------------------------------------------------------*/
		private boolean collisionOnPlace(Part toCreate) {
			for (Part p : parts) {
				if (Collisions.checkForCollision(toCreate, p).getCollisionOccurred())
					return true;
			}
			
			return false;
		}
	
	}

}
