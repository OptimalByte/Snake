package com.optimalbyte.snake;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents the snake that the player will move.
 *
 * @author samuraiblood2
 */
public class SnakeEntity extends Entity {

	/** A list of the various sections of the sankes tail (not including the head). */
	private LinkedList<TailEntity> tailPieces = new LinkedList<TailEntity>();
	
	/**
	 * We use the constructor to pass an instance of the Game to this class as
	 * well as setting the location of the snake and adding some sections to the tail
	 * to begin with.
	 *
	 * @param game An instance of Game.
	 */
	public SnakeEntity(Game game) {
		super(game);

		// XXX: Determines what tile the position we want the snake to spawn in is.
		Rectangle spot = new Rectangle();
		Point point = new Point(50, 50);
		for (Rectangle tile : game.getTiles()) {
			if (tile.contains(point)) {
				setLocation(tile.getLocation());
				spot.setBounds(tile);
			}
		}
		
		// XXX: Append four tail sections to the list and set their position.
		for (int i = 0; i < 5; i ++) {
			if (i == 0) {
				continue;
			}
			
			TailEntity entity = new TailEntity(game);
			entity.setLocation((int) (spot.getX() - (i * Game.SIZE)), (int) spot.getY());
			tailPieces.add(entity);
		}
		
		// XXX: Add all of the tail sections to the Entities list.
		game.getEntities().addAll(tailPieces);
	}
	
	/**
	 * @see Entity#onDraw(Graphics2D)
	 */
	@Override
	public void onDraw(Graphics2D graphics) {
		graphics.setColor(Color.GREEN.darker().darker());
		graphics.fill(new Ellipse2D.Double(location.getX(), location.getY(), Game.SIZE, Game.SIZE));
	
		graphics.setColor(Color.WHITE);
		graphics.drawString("Length: " + tailPieces.size(), (int) ((game.getGameBoundries().getWidth() / 2) + 50), 20);
	}
	
	/**
	 * @see Entity#onCollision(Entity)
	 */
	@Override
	public void onCollision(Entity other) {
		if (other instanceof FruitEntity) {
			FruitEntity fruit = (FruitEntity) other;
			
			// XXX: If the snake hits a fruit, then we increase the players score
			// and increase the length of the snakes tail.
			if (atPosition(fruit.getLocation())) {
				game.addScore(10);
				addTailPiece();

				fruit.spawnFruit();
			}
		} else if (other instanceof TailEntity) {
			TailEntity tail = (TailEntity) other;
			
			// XXX: If the snake hits its own tail the game is over.
			if (atPosition(tail.getLocation())) {
				game.doGameOver();
			}
		}
		
		// XXX: If the snake hits the edge of the window the game is over.
		if (!game.getGameBoundries().contains(location)) {
			game.doGameOver();
		}
	}
	
	/**
	 * Handles the movement of the snake and its tail.
	 *
	 * @see Direction
	 * @see GameTimer
	 */
	public void move() {
		for (int i = (tailPieces.size() - 1); i >= 0; i--) {
			TailEntity piece = tailPieces.get(i);
			Entity prev = (i == 0 ? this : tailPieces.get(i - 1));
			piece.setLocation(prev.getLocation());
		}

		int x = (int) location.getX();
		int y = (int) location.getY();
		switch (game.getDirection()) {
			case LEFT:
				x -= Game.SIZE;
				break;
				
			case RIGHT:
				x += Game.SIZE;
				break;
				
			case UP:
				y -= Game.SIZE;
				break;
				
			case DOWN:
				y += Game.SIZE;
				break;
		}
		setLocation(x, y);
	}

	/**
	 * Adds another TailEntity.
	 */
	public void addTailPiece() {
		tailPieces.addLast(new TailEntity(game));
		game.getEntities().addAll(tailPieces);
	}
	
	/**
	 * Represents the a single section of the snakes tail.
	 *
	 * @author samuraiblood2
	 */
	class TailEntity extends Entity {
		
		/**
		 * We use the constructor to pass an instance of the Game to this class.
		 *
		 * @param game An instance of Game.
		 */
		public TailEntity(Game game) {
			super(game);
		}
		
		/**
		 * @see Entity#onDraw(Graphics2D)
		 */
		@Override
		public void onDraw(Graphics2D graphics) {
			graphics.setColor(Color.GREEN);
			graphics.fill(new Ellipse2D.Double(location.getX(), location.getY(), Game.SIZE, Game.SIZE));
		}
		
		/**
	 	 * @see Entity#onCollision(Entity)
		 */
		@Override
		public void onCollision(Entity other) {
			// XXX: This will be handled somewhere else.
		}
	}
}
