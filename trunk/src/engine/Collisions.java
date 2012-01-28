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

package engine;

import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

/*+----------------------------------------------------------------------
||
||  Class Collisions
||
||         Author:  Mark Ross
||
||        Purpose:  Provides for the detecting of a collision between any two
||                  CollisionBoundType's as well as updating the two upon
||                  collision.
||
||                  The algorithm used for detecting collisions uses the
||                  Separating Axis Theorem. The theorem basically says that
||                  if you can find an axis (line) that runs between two convex
||                  polygons without touching them, then they are not colliding.
||                  You can find more information here:
||                  * http://www.codeproject.com/KB/GDI-plus/PolygonCollision.aspx
||                  * http://www.metanetsoftware.com/technique.html
||                  
||                  When a collision is detected, the collide() method can be called
||                  to perform the proper kinematics calculations. Right now, this causes
||                  PhysicsObjects to bounce and slow down due to friction.
||
||  Inherits From:  None
||
||     Interfaces:  None
||
|+-----------------------------------------------------------------------
||
||      Constants:  None
||
|+-----------------------------------------------------------------------
||
||   Constructors:  N/A
||
||  Class Methods:  + collide(PhysicsObject, PhysicsObject, CollisionResult) : void
||                  + checkForCollision(PhysicsObject, PhysicsObject) : CollisionResult
||
||  Inst. Methods:  N/A
||
++-----------------------------------------------------------------------*/
public class Collisions {
	
	/**
	 * The default CollisionResult to return when there is *no* collision. Allows
	 * for object re-use!! (Which design pattern is this??)
	 */
	private static CollisionResult noCollisionDetected = new CollisionResult();
	
	private static CollisionResult collisionDetected = new CollisionResult();
	
	// behaviors ////////////////////////////////////////////

	/**
	 * Makes two given parts collide by calculating the reaction between the two
	 * parts. Makes things bounce, etc.
	 * 
	 * @param a
	 *            a <code>Part</code>
	 * @param b
	 *            a <code>Part</code>
	 * @param collAxis
	 *            a Vector representing the direction in which the collision
	 *            occurred.
	 */
	public static void collide(PhysicsObject a, PhysicsObject b, CollisionResult collResult) {
		// if both parts are Updateable, use conservation of momentum; if not,
		// use inelastic collision
		// TODO how can we merge these two?
		if (a instanceof Updateable && b instanceof Updateable)
			elasticCollision(a, b, collResult.getProjectionVector().getNormalized());
		else
			inelasticCollision(a, b, collResult.getProjectionVector().getNormalized());
	}

	/**
	 * Makes two objects collide while conserving their momentum.
	 * 
	 * @param a
	 *            a <code>Part</code> involved in the collision
	 * @param b
	 *            another <code>Part</code> involved in the collision
	 * @param collAxis
	 *            a Vector representing the direction in which the collision
	 *            occurred.
	 */
	private static void elasticCollision(PhysicsObject a, PhysicsObject b, Vector collAxis) {
		// get their velocities *projected* onto the collision axis. save their
		// velocities *projected* onto a vector perpendicular to the axis of
		// collision.
		Vector v_a_coll = a.getVelocity().projectionOnto(collAxis);
		Vector v_b_coll = b.getVelocity().projectionOnto(collAxis);

		// get components perpendicular to collision
		Vector collAxis_n = collAxis.getRightHandNormal();
		Vector v_a_save = a.getVelocity().projectionOnto(collAxis_n);
		Vector v_b_save = b.getVelocity().projectionOnto(collAxis_n);
		
		// TODO these equations can only be used in the proper inertial frame of reference (where one of the
		// velocities is zero)...see WP

		// perform conservation of momentum calculation
		// v_1_f = (m_1 - m_2)/(m_1 + m_2) * v_1_i + (2 * m_2)/(m_1 + m_2) *
		// v_2_i
		// v_2_f = (2 * m_1)/(m_1 + m_2) * v_1_i + (m_2 - m_1)/(m_1 + m_2) *
		// v_2_i
		//Vector v_a_coll_f = Vector.add(v_a_coll.scalarProduct(((a.getMass() - b.getMass()) / (a.getMass() + b.getMass()))), (v_b_coll.scalarProduct(2 * b.getMass() / (a.getMass() + b.getMass()))));
		//Vector v_b_coll_f = Vector.add(v_a_coll.scalarProduct(2 * a.getMass() / (a.getMass() + b.getMass())), (v_b_coll.scalarProduct((a.getMass() - b.getMass()) / (a.getMass() + b.getMass()))));
		
		// v_a_f = (C_R * m_b (v_b_i - v_a_i) + m_a * v_a_i + m_b * v_b_i) / (m_a + m_b)
		// v_a_f = (C_R * m_a (v_a_i - v_b_i) + m_a * v_a_i + m_b * v_b_i) / (m_a + m_b)
		Vector v_a_collTimesMass = v_a_coll.scalarProduct(a.getMass());
		Vector v_b_collTimesMass = v_b_coll.scalarProduct(b.getMass());
		double totalMass = a.getMass() + b.getMass();
		double C_R = Math.max(a.getMUk(), b.getMUk());
		//TODO need another way to get C_R instead of faking with mu_k
		
		Vector v_a_coll_f = Vector.add(Vector.add(Vector.subtract(v_b_coll, v_a_coll).scalarProduct(b.getMass() * C_R), v_a_collTimesMass), v_b_collTimesMass).scalarProduct(1.0 / totalMass);
		Vector v_b_coll_f = Vector.add(Vector.add(Vector.subtract(v_a_coll, v_b_coll).scalarProduct(b.getMass() * C_R), v_a_collTimesMass), v_b_collTimesMass).scalarProduct(1.0 / totalMass);
		
		//Vector v_a_saveTimesMass = v_a_save.scalarProduct(a.getMass());
		//Vector v_b_saveTimesMass = v_b_save.scalarProduct(b.getMass());
		
		//Vector v_a_save_f = Vector.add(Vector.add(Vector.subtract(v_b_save, v_a_save).scalarProduct(b.getMass() * C_R), v_a_saveTimesMass), v_b_saveTimesMass).scalarProduct(1.0 / totalMass);
		//Vector v_b_save_f = Vector.add(Vector.add(Vector.subtract(v_a_save, v_b_save).scalarProduct(b.getMass() * C_R), v_a_saveTimesMass), v_b_saveTimesMass).scalarProduct(1.0 / totalMass);

		// add the saved velocity with the calculated velocity for each part
		a.setVelocity(Vector.add(v_a_save, v_a_coll_f));
		b.setVelocity(Vector.add(v_b_save, v_b_coll_f));
	}

	/**
	 * Makes two objects collide, accounting for energy lost (due to
	 * deformation, etc.).
	 * 
	 * @param a
	 *            a <code>Part</code> involved in the collision
	 * @param b
	 *            another <code>Part</code> involved in the collision
	 * @param collAxis
	 *            a Vector representing the direction in which the collision
	 *            occurred.
	 */
	private static void inelasticCollision(PhysicsObject a, PhysicsObject b, Vector collAxis) {
		// project a's velocity onto collision axis (this is the *bounce*)
		Vector v_coll = a.getVelocity().projectionOnto(collAxis);
		v_coll.setX(v_coll.getX() * -1 * a.getMUk()); // kinetic friction is used because 
		v_coll.setY(v_coll.getY() * -1 * a.getMUk()); // we don't have a restitution field
		
		// TODO ERROR this is all wrong. this and the above method need to be merged somehow

		// project a's velocity onto vector perpendicular to collision axis
		// (used for the *friction*)
		Vector v_perp = a.getVelocity().projectionOnto(collAxis.getRightHandNormal());
		v_perp.setX(v_perp.getX() * b.getMUs()); // this is wrong too
		v_perp.setY(v_perp.getY() * b.getMUs());

		a.setVelocity(Vector.add(v_coll, v_perp));
	}

	/**
	 * Checks the <code>CollisionBoundType</code> of each <code>Part</code>
	 * and performs the appropriate collision detection routine.
	 * 
	 * @param a
	 * 			a <code>Part</code> that is being checked for a collision with
	 *            <code>b</code>; order does not matter
	 * @param b
	 *          a <code>Part</code> that is being checked for a collision with
	 *            <code>a</code>; order does not matter
	 * @return a 
	 * 			@see <code>CollisionResult</code>
	 */
	public static CollisionResult checkForCollision(PhysicsObject a, PhysicsObject b) {
		// check bounding box first
		if (!a.getShape().intersects(b.getShape().getBounds()))
			return noCollisionDetected;
		
		if (a.getCollisionBoundType() == CollisionBoundType.ELLIPSE && b.getCollisionBoundType() == CollisionBoundType.ELLIPSE) {
			return checkCircleWithCircle(a, b);
		}
		else if ((a.getCollisionBoundType() == CollisionBoundType.ELLIPSE && b.getCollisionBoundType() == CollisionBoundType.RECTANGLE)) {
			//return checkCircleWithRectangle(a, b);
			return checkCircleWithPolygon(a, b);
		}
		else if ((a.getCollisionBoundType() == CollisionBoundType.ELLIPSE && b.getCollisionBoundType() == CollisionBoundType.POLYGON)) {
			return checkCircleWithPolygon(a, b);
		}
		else if ((a.getCollisionBoundType() == CollisionBoundType.RECTANGLE && b.getCollisionBoundType() == CollisionBoundType.ELLIPSE)) {
			//return checkCircleWithRectangle(b, a);
			return checkCircleWithPolygon(b, a);
		}
		else if ((a.getCollisionBoundType() == CollisionBoundType.RECTANGLE || a.getCollisionBoundType() == CollisionBoundType.POLYGON) && (b.getCollisionBoundType() == CollisionBoundType.RECTANGLE || b.getCollisionBoundType() == CollisionBoundType.POLYGON)) {
			return checkPolygonWithPolygon(a, b);
		}

		// default - no collision
		return noCollisionDetected;
	}

	/**
	 * 
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private static CollisionResult checkCircleWithCircle(PhysicsObject a, PhysicsObject b) {
		//CollisionResult result = new CollisionResult();

		// use arrays so we can pass them by *reference*
		double[] maxMinA = { 0.0, 0.0 };
		double[] maxMinB = { 0.0, 0.0 };

		Vector axis = Vector.subtract(b.getCenter(), a.getCenter()).getNormalized();

		// project the circles onto the axis
		projectCircle(axis, a.getCenter(), a.getWidth() / 2, maxMinA);
		projectCircle(axis, b.getCenter(), b.getWidth() / 2, maxMinB);

		// find the distance between the projections
		double intervalDistance = getIntervalDistance(maxMinA, maxMinB);

		if (intervalDistance >= 0)
			// this axis is a separating axis, exit early
			return noCollisionDetected;

		intervalDistance = Math.abs(intervalDistance);

		Vector translationAxis = axis;

		Vector d = Vector.subtract(a.getCenter(), b.getCenter());
		if (Vector.dotProduct(d, translationAxis) < 0)
			translationAxis = new Vector(translationAxis.getX() * -1, translationAxis.getY() * -1);

		collisionDetected.setProjectionVector(new Vector(translationAxis.getX() * intervalDistance, translationAxis.getY() * intervalDistance));

		collisionDetected.setCollisionOccurred(true);
		return collisionDetected;
	}

	/**
	 * @param a
	 * @param b
	 * @return
	 */
/*	private static CollisionResult checkCircleWithRectangle(PhysicsObject a, PhysicsObject b) {
		CollisionResult result = new CollisionResult();

		boolean isInCornerRegion = false;

		Vector axis = new Vector();

		// check which voronoi region the center of the circle is in
		if (a.getCenter().getX() < b.getPosition().getX()) {
			if (a.getCenter().getY() < b.getPosition().getY()) {
				// circle is in upper left corner
				axis = Vector.subtract(a.getCenter(), b.getPosition());
				isInCornerRegion = true;
			}
			else if (a.getCenter().getY() > b.getPosition().getY() + b.getHeight()) {
				// circle is in lower left corner
				axis = Vector.subtract(a.getCenter(), new Vector(b.getPosition().getX(), b.getPosition().getY() + b.getHeight()));
				isInCornerRegion = true;
			}
		}
		else if (a.getCenter().getX() > b.getPosition().getX() + b.getWidth()) {
			if (a.getCenter().getY() < b.getPosition().getY()) {
				// circle is in upper right corner
				axis = Vector.subtract(a.getCenter(), new Vector(b.getPosition().getX() + b.getWidth(), b.getPosition().getY()));
				isInCornerRegion = true;
			}
			else if (a.getCenter().getY() > b.getPosition().getY() + b.getHeight()) {
				// circle is in lower right corner
				axis = Vector.subtract(a.getCenter(), new Vector(b.getPosition().getX() + b.getWidth(), b.getPosition().getY() + b.getHeight()));
				isInCornerRegion = true;
			}
		}

		if (!isInCornerRegion)
			// circle is on an axis-aligned side, just treat it like a
			// rectangle!
			return checkPolygonWithPolygon(a, b);

		// circle can only collide with corner if it is one of the corner
		// (diagonal) regions
		axis = axis.getNormalized();

		double[] maxMinA = { 0.0, 0.0 };
		double[] maxMinB = { 0.0, 0.0 };

		// project the parts onto the axis
		projectCircle(axis, a.getCenter(), a.getWidth() / 2, maxMinA);
		Polygon p = (Polygon) b.getShape();
		projectPolygon(axis, p, maxMinB);

		double intervalDistance = getIntervalDistance(maxMinA, maxMinB);

		if (intervalDistance >= 0)
			// this axis is a separating axis, exit early
			return noCollisionDetected;

		intervalDistance = Math.abs(intervalDistance);

		Vector translationAxis = axis;

		Vector d = Vector.subtract(a.getCenter(), b.getCenter());
		if (Vector.dotProduct(d, translationAxis) < 0)
			translationAxis = new Vector(translationAxis.getX() * -1, translationAxis.getY() * -1);

		result.setProjectionVector(new Vector(translationAxis.getX() * intervalDistance, translationAxis.getY() * intervalDistance));

		result.setCollisionOccurred(true);
		return result;
	}
	*/

	/**
	 * @param a
	 * @param b
	 * @return
	 */
	private static CollisionResult checkCircleWithPolygon(PhysicsObject a, PhysicsObject b) {
		//CollisionResult result = new CollisionResult(); // TODO is it possible to modify these methods so this can be placed after the early exit in the for loop?

		// make sure circle is within rectangular bounds of polygon first. Exit
		// early if not.
		//if (!checkCircleWithRectangle(a, b).getCollisionOccurred())
		//	return result;

		double[] maxMinA = { 0.0, 0.0 };
		double[] maxMinB = { 0.0, 0.0 };

		//Shape shapeB = (Shape) b.getShape();
		PathIterator pi = b.getShape().getPathIterator(null);
		double[][] polyPoints = new double[4][6]; // four vertices, and PathIterator.currentSegment() needs an array of size 6
		
		for (int i = 0; i <= 3; i++) {
			pi.currentSegment(polyPoints[i]);
			pi.next();
		}
		
		double minIntervalDistance = Double.MAX_VALUE;
		Vector translationAxis = new Vector();
		Vector edge = new Vector();

		for (int i = 0; i <= 3; i++) {
			Utility.getEdge(edge, polyPoints, i);
			// get the axis perpendicular to the edge of the polygon
			Vector axis = edge.getRightHandNormal().getNormalized();

			// project the shapes onto the axis
			projectCircle(axis, a.getCenter(), a.getWidth() / 2, maxMinA);
			projectPolygon(axis, polyPoints, maxMinB);

			double intervalDistance = getIntervalDistance(maxMinA, maxMinB);

			if (intervalDistance >= 0)
				// there is a separating axis, exit early
				return noCollisionDetected;

			// check if the interval distance is the *minimum* one. if so, store
			// the interval distance and the current distance. this will be used
			// to calculate the minimum translation (projection) vector
			intervalDistance = Math.abs(intervalDistance);
			if (intervalDistance < minIntervalDistance) {
				minIntervalDistance = intervalDistance;
				translationAxis = axis;

				Vector d = Vector.subtract(a.getCenter(), b.getCenter());
				if (Vector.dotProduct(d, translationAxis) < 0)
					translationAxis = new Vector(translationAxis.getX() * -1, translationAxis.getY() * -1);

				collisionDetected.setProjectionVector(new Vector(translationAxis.getX() * minIntervalDistance, translationAxis.getY() * minIntervalDistance));
			}
		}

		// edges checked in last loop, now check vertices?!?!?!

		collisionDetected.setCollisionOccurred(true);
		return collisionDetected;
	}

	/**
	 * @param a
	 * @param b
	 * @return
	 */
	private static CollisionResult checkPolygonWithPolygon(PhysicsObject a, PhysicsObject b) {
		//CollisionResult result = new CollisionResult();
		
		double[] maxMinA = { 0.0, 0.0 };
		double[] maxMinB = { 0.0, 0.0 };

		PathIterator piA = a.getShape().getPathIterator(null);
		double[][] polyPointsA = new double[4][6]; // four vertices, and PathIterator.currentSegment() needs an array of size 6
		
		for (int i = 0; i <= 3; i++) {
			piA.currentSegment(polyPointsA[i]);
			piA.next();
		}
		
		PathIterator piB = b.getShape().getPathIterator(null);
		double[][] polyPointsB = new double[4][6]; // four vertices, and PathIterator.currentSegment() needs an array of size 6
		
		for (int i = 0; i <= 3; i++) {
			piB.currentSegment(polyPointsB[i]);
			piB.next();
		}
		
		double minIntervalDistance = Double.MAX_VALUE;
		Vector translationAxis = new Vector();
		Vector edge = new Vector();

		// check all edges of both polygons, find the shortest projection
		// (translation)		
		for (int i = 0; i <= 3; i++) {
			Utility.getEdge(edge, polyPointsA, i);
			// get the axis perpendicular to the edge of the polygon
			Vector axis = edge.getRightHandNormal().getNormalized();

			// project the shapes onto the axis
			projectPolygon(axis, polyPointsA, maxMinA);
			projectPolygon(axis, polyPointsB, maxMinB);

			double intervalDistance = getIntervalDistance(maxMinA, maxMinB);

			if (intervalDistance >= 0)
				// this axis is a separating axis, exit early
				return noCollisionDetected;

			// check if the interval distance is the *minimum* one. if so, store
			// the interval distance and the current distance. this will be used
			// to calculate the minimum translation vector
			intervalDistance = Math.abs(intervalDistance);
			if (intervalDistance < minIntervalDistance) {
				minIntervalDistance = intervalDistance;
				translationAxis = axis;

				Vector d = Vector.subtract(a.getCenter(), b.getCenter());
				if (Vector.dotProduct(d, translationAxis) < 0)
					translationAxis = new Vector(translationAxis.getX() * -1, translationAxis.getY() * -1);

				collisionDetected.setProjectionVector(new Vector(translationAxis.getX() * minIntervalDistance, translationAxis.getY() * minIntervalDistance));
			}
		}

		for (int i = 0; i <= 3; i++) {
			Utility.getEdge(edge, polyPointsB, i);
			// get the axis perpendicular to the edge of the polygon
			Vector axis = edge.getRightHandNormal().getNormalized();

			// project the shapes onto the axis
			projectPolygon(axis, polyPointsA, maxMinA);
			projectPolygon(axis, polyPointsB, maxMinB);

			double intervalDistance = getIntervalDistance(maxMinA, maxMinB);

			if (intervalDistance >= 0)
				// this axis is a separating axis, exit early
				return noCollisionDetected;

			// check if the interval distance is the *minimum* one. if so, store
			// the interval distance and the current distance. this will be used
			// to calculate the minimum translation vector
			intervalDistance = Math.abs(intervalDistance);
			if (intervalDistance < minIntervalDistance) {
				minIntervalDistance = intervalDistance;
				translationAxis = axis;

				Vector d = Vector.subtract(a.getCenter(), b.getCenter());

				if (Vector.dotProduct(d, translationAxis) < 0)
					translationAxis = new Vector(translationAxis.getX() * -1, translationAxis.getY() * -1);

				collisionDetected.setProjectionVector(new Vector(translationAxis.getX() * minIntervalDistance, translationAxis.getY() * minIntervalDistance));
			}
		}

		collisionDetected.setCollisionOccurred(true);
		return collisionDetected;
	}

	/**
	 * Projects the circle onto a given axis.
	 * 
	 * @param axis
	 *            the axis upon which you want to project the circle
	 * @param center
	 *            the <code>Vector</code> position of the circle
	 * @param radius
	 *            the radius (or half-width)
	 * @param maxMin
	 *            used to keep track of the max/min values of the shape on the
	 *            axis
	 */
	private static void projectCircle(Vector axis, Vector center, double radius, double[] maxMin) {
		// use dot product to project a point onto the axis
		double d = Vector.dotProduct(axis, center);
		maxMin[0] = d;
		maxMin[1] = d;

		d = Vector.dotProduct(axis, new Vector(center.getX() + axis.getX() * radius, center.getY() + axis.getY() * radius));

		// save the point if it is a max or min
		if (d < maxMin[1])
			maxMin[1] = d;
		else if (d > maxMin[0])
			maxMin[0] = d;

		d = Vector.dotProduct(axis, new Vector(center.getX() + axis.getX() * radius * -1, center.getY() + axis.getY() * radius * -1));
		if (d < maxMin[1])
			maxMin[1] = d;
		else if (d > maxMin[0])
			maxMin[0] = d;
	}

	private static void projectPolygon(Vector axis, double[][] polyPoints, double[] maxMin) {
		// use dot product to project a point onto the axis
		double d = Vector.dotProduct(axis, new Vector(polyPoints[0][0], polyPoints[0][1]));
		maxMin[0] = d;
		maxMin[1] = d;

		// save the point if it is a max or min
		for (int i = 1; i <= 3; i++) {
			d = Vector.dotProduct(axis, new Vector(polyPoints[i][0], polyPoints[i][1]));
			if (d < maxMin[1])
				maxMin[1] = d;
			else if (d > maxMin[0])
				maxMin[0] = d;
		}
	}
	
	/**
	 * @param maxMinA
	 * @param maxMinB
	 * @return
	 */
	private static double getIntervalDistance(double[] maxMinA, double[] maxMinB) {
		if (maxMinA[1] < maxMinB[1])
			return maxMinB[1] - maxMinA[0];
		else
			return maxMinA[1] - maxMinB[0];
	}
	
}
