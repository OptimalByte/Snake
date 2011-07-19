package com.optimalbyte.snake;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;

/**
 * Deals with all the unspecific graphical shit related to the game.
 *
 * @author samuraiblood2
 */
public class Game extends Canvas {
	
	/** The size of each 'tile'. */
	public static final int SIZE = 10;
	
	/** A list of all the Entities. */
	private LinkedList<Entity> entities = new LinkedList<Entity>();

	private LinkedList<Rectangle> tiles = new LinkedList<Rectangle>();
	
	/** The Direction the snake is moving. */
	private Direction direction = Direction.RIGHT;
	
	/** The boundries of the game, and in affect, the Window itself. */
	private Rectangle gameBounds = new Rectangle(280, 320);
	
	/** The BufferStrategy. */
	private BufferStrategy buffer;
	
	/** A flag for player death. */
	private boolean hasDied;

	/** A flag for pausing the game. */
	private boolean isPaused;

	private boolean showTiles;
	
	/** The players score. */
	private int score;

	/**
	 * Where we set the size of the Canvas, and a bunch of other shit.
	 */
	public Game() {
		setSize((int) gameBounds.getWidth(), (int) gameBounds.getHeight());
	
		setIgnoreRepaint(true);
		setFocusable(true);
	
		addKeyListener(new InputListener(this));
	
		for (int x = 0; x < gameBounds.getWidth(); x++) {
			for (int y = 0; y < gameBounds.getHeight(); y++) {
				if (((x % SIZE) == 0) && ((y % SIZE) == 0)) {
					tiles.add(new Rectangle(x, y, SIZE, SIZE));
				}
			}
		}
	
		reset();
		
		new GameTimer(this);
	}

	/**
	 * To make sure that the BufferStrategy is set before we actually start drawing anything.
	 */
	@Override
	public void addNotify() {
		super.addNotify();

		createBufferStrategy(2);
		buffer = getBufferStrategy();
	}

	/**
	 * Where the magic happens.
	 */
	public void doDrawing() {
		
		// XXX: Happened before, I wont don't the possibility of it happening again.
		if (buffer == null) {
			return;
		}
		
		Graphics2D graphics = (Graphics2D) buffer.getDrawGraphics();

		// XXX: Sets the background color of the game. I intend to replace this
		// with an image of some kind.
		graphics.setColor(Color.BLACK);
		graphics.fill(gameBounds);

		// XXX: Displays all the tiles in blue; I just use this for making sure
		// that everything is aligned properly.
		if (showTiles) {
			graphics.setColor(Color.BLUE);
			for (Rectangle tile : tiles) {
				graphics.draw(tile);
			}
		}

		// XXX: We iterate through all the Entities and call the onDraw(Graphics2D)
		// method that they inherit.
		synchronized (entities) {
			for (Entity entity : entities) {
				entity.onDraw(graphics);
			}
		}
		
		// XXX: We want all of different text messages to be on top.
		if (hasDied) {
			displayText("Game Over", graphics);
		} else if (isPaused) {
			displayText("Paused", graphics);
		}

		graphics.setColor(Color.WHITE);
		graphics.drawString("Score: " + score, (int) ((gameBounds.getWidth() / 2) - 100), 20);
		
		// XXX: Dispose of this Graphics object and show the BufferStrategy.
		graphics.dispose();
		buffer.show();
	}

	/**
	 * Resets everything to its original value (if it had one).
	 */
	public void reset() {
		hasDied = false;
		isPaused = false;
		showTiles = false;
		score = 0;
		direction = Direction.RIGHT;
		
		entities.clear();
		entities.add(0, new SnakeEntity(this));
		entities.add(new FruitEntity(this));
	}

	/**
	 * Convience method so I didn't have to keep writing out the contents of the method.
	 *
	 * @param msg The message that will be displayed.
	 * @param graphics The Graphics object that will be used to display the message.
	 */
	// TODO: Half-assed this as well.
	public void displayText(String msg, Graphics2D graphics) {
		Font small = new Font("Helvetica", Font.PLAIN, 12);
		FontMetrics metrics = getFontMetrics(small);

		graphics.setColor(Color.WHITE);
		graphics.setFont(small);
		graphics.drawString(msg, (getWidth() - metrics.stringWidth(msg)) / 2, getHeight() / 2);
	}

	/**
	 * Setter method for the Direction.
	 *
	 * @param direction The new Direction.
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	/**
	 * Getter method for the current Direction.
	 *
	 * @return The current Direction.
	 */
	public Direction getDirection() {
		return direction;
	}
	
	/**
	 * Adds the given amount to the players current score.
	 *
	 * @param amount The amount to be added.
	 */
	public void addScore(int amount) {
		this.score += amount;
	}

	/**
	 * Subtracts the given amount from the players score.
	 *
	 * @param amount The amount to be subtracted.
	 */
	public void subtractScore(int amount) {
		this.score -= amount;
	}
	
	/**
	 * Getter method for the entities list.
	 *
	 * @return A list of all the current Entities.
	 */
	public List<Entity> getEntities() {
		return entities;
	}

	/** 
	 * Getter method for the games boundries.
	 *
	 * @return The games boundries.
	 */
	public Rectangle getGameBoundries() {
		return gameBounds;
	}
	
	public List<Rectangle> getTiles() {
		return tiles;
	}
	
	/**
	 * Basically just sets the hasDied flag to true. The actual
	 * logic is handled elsewhere.
	 */
	public void doGameOver() {
		hasDied = true;
	}
	
	/**
	 * Getter method for the hasDied flag.
	 *
	 * @return Whether or not the player has died.
	 */
	public boolean hasDied() {
		return hasDied;
	}

	/**
	 * Basically just sets the isPaused flag to its opposite. The actual
	 * logic is handled elsewhere.
	 */
	public void doPaused() {
		isPaused = !isPaused;
	}
	
	public boolean showTiles() {
		return showTiles;
	}
	
	public void displayTiles() {
		showTiles = !showTiles;
	}
	
	/**
	 * Getter method for the isPaused flag.
	 *
	 * @return Whether or not the game is paused.
	 */
	public boolean isPaused() {
		return isPaused;
	}
}
