package com.optimalbyte.snake;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public abstract class Entity {

	protected Game game;
	protected Point location = new Point();

	public Entity(Game game) {
		this.game = game;
	}

	public boolean atPosition(Point point) {
		return ((point.getX() == location.getX()) && (point.getY() == location.getY()));
	}
	
	public abstract void onDraw(Graphics2D graphics);

	public abstract void onCollision(Entity other);

	public Game getGame() {
		return game;
	}
	
	public Point getLocation() {
		return location;
	}
	
	public void setLocation(int x, int y) {
		location.setLocation(x, y);
	}
	
	public void setLocation(Point point) {
		location.setLocation(point);
	}
}
