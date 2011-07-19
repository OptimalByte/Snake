package com.optimalbyte.snake;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Anything in-game that should be drawn specially or have some signifigant meaning, 
 * however small that meaning is, should be the child of this object.
 *
 * TODO: The above is a really shitty definition of this class, but its late and I'm lazy.
 *
 * @author samuraiblood2
 */
public abstract class Entity {

	/** An instance of the Game. */
	protected Game game;
	
	/** The location this Entity is currently at. */
	protected Point location = new Point();

	/**
	 * We use the constructor to pass an instance of the Game to this class.
	 *
	 * XXX: Does this look familiar?
	 *
	 * @param game An instance of Game.
	 */
	public Entity(Game game) {
		this.game = game;
	}

	/**
	 * Called when this Entity is ready to be drawn.
	 *
	 * @param graphics An instance of the Graphic2D object.
	 * @see Game#doDrawing()
	 * @see GameTimer
	 */
	public abstract void onDraw(Graphics2D graphics);

	/**
	 * Called when this Entity collides with another.
	 *
	 * @param other The Entity it collided with.
	 * @see GameTimer
	 */
	public abstract void onCollision(Entity other);

	/**
	 * Getter method for the game variabe.
	 *
	 * @return An instance of the Game object.
	 */
	public Game getGame() {
		return game;
	}
	
	/**
	 * Whether or not this Entity is at the specified Point.
	 *
	 * @return True if yes, false other-wise.
	 */
	public boolean atPosition(Point point) {
		return ((point.getX() == location.getX()) && (point.getY() == location.getY()));
	}
	
	/**
	 * Getter method for the location of this Entity.
	 *
	 * @return The location of this Entity.
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * Convience method for setting the location of this Entity.
	 *
	 * @param x The X position.
	 * @param y The Y position.
	 */
	public void setLocation(int x, int y) {
		location.setLocation(x, y);
	}
	
	/**
	 * Convience method for setting the location of this Entity.
	 *
	 * @param point The position of this Entity.
	 */
	public void setLocation(Point point) {
		location.setLocation(point);
	}
}
