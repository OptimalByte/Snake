package com.optimalbyte.snake;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Game extends Canvas {
	
	public static final int SIZE = 10;
	public static final int MAX_TILES = 900;
	
	private List<Entity> entities = new ArrayList<Entity>();
	private Direction direction = Direction.RIGHT;
	private Rectangle gameBounds = new Rectangle(320, 340);
	private BufferStrategy buffer;
	private boolean hasDied = false;
	private boolean isPaused = false;
	private int score = 0;
	
	enum Direction {
		LEFT, RIGHT, UP, DOWN;
	}

	public Game() {
		setIgnoreRepaint(true);
		setFocusable(true);
	
		addKeyListener(new InputListener());
	
		entities.add(0, new SnakeEntity(this));
		entities.add(new FruitEntity(this));

		new GameTimer(this);
	}

	@Override
	public void addNotify() {
		super.addNotify();

		createBufferStrategy(2);
		buffer = getBufferStrategy();
	}

	public void doDrawing() {
		if (buffer == null) {
			return;
		}
		
		Graphics2D graphics = (Graphics2D) buffer.getDrawGraphics();

		graphics.setColor(Color.BLACK);
		graphics.fill(gameBounds);
		
		if (hasDied) {
			displayText("Game Over", graphics);
		} else if (isPaused) {
			displayText("Paused", graphics);
		}

		graphics.setColor(Color.WHITE);
		graphics.drawString("Score: " + score, 5, 15);

		synchronized (entities) {
			for (Entity entity : entities) {
				entity.onDraw(graphics);
			}
		}
		
		graphics.dispose();
		buffer.show();
	}

	public void reset() {
		hasDied = false;
		isPaused = false;
		score = 0;
		direction = Direction.RIGHT;

		entities.clear();
		entities.add(0, new SnakeEntity(this));
		entities.add(new FruitEntity(this));
	}

	public void displayText(String msg, Graphics2D graphics) {
		Font small = new Font("Helvetica", Font.PLAIN, 12);
		FontMetrics metrics = getFontMetrics(small);

		graphics.setColor(Color.WHITE);
		graphics.setFont(small);
		graphics.drawString(msg, (getWidth() - metrics.stringWidth(msg)) / 2, getHeight() / 2);
	}

	public Direction getDirection() {
		return direction;
	}
	
	public void addScore(int amount) {
		this.score += amount;
	}

	public void subtractScore(int amount) {
		this.score -= amount;
	}
	
	public List<Entity> getEntities() {
		return entities;
	}

	public Rectangle getGameBoundries() {
		return gameBounds;
	}
	
	public void doGameOver() {
		hasDied = true;
	}
	
	public boolean hasDied() {
		return hasDied;
	}

	public boolean isPaused() {
		return isPaused;
	}
	
	class InputListener implements KeyListener {
	
		@Override
		public void keyPressed(KeyEvent event) {
			int code = event.getKeyCode();
			if (code == KeyEvent.VK_P) {
				isPaused = !isPaused;
			}

			if (isPaused) {
				if (code == KeyEvent.VK_F1) {
					int length = Integer.parseInt(JOptionPane.showInputDialog(null, "Length"));
					
					for (int i = 0; i < length; i++) {
						addScore(10);
						((SnakeEntity) getEntities().get(0)).addTailPiece();
					}
				}
				return;
			}

			if (hasDied) {
				if (code == KeyEvent.VK_R) {
					reset();
				}
			}
			
			if (((code == KeyEvent.VK_LEFT) || (code == KeyEvent.VK_A)) && (direction != Direction.RIGHT)) {
				direction = Direction.LEFT;
			} else if (((code == KeyEvent.VK_RIGHT) || (code == KeyEvent.VK_D)) && (direction != Direction.LEFT)) {
				direction = Direction.RIGHT;
			} else if (((code == KeyEvent.VK_UP) || (code == KeyEvent.VK_W)) && (direction != Direction.DOWN)) {
				direction = Direction.UP;
			} else if (((code == KeyEvent.VK_DOWN) || (code == KeyEvent.VK_S)) && (direction != Direction.UP)) {
				direction = Direction.DOWN;
			}
		}
		
		@Override
		public void keyReleased(KeyEvent event) {
			// XXX: Nothing to do here.
		}
		
		@Override
		public void keyTyped(KeyEvent event) {
			// XXX: Nothing to do here.
		}
	}
}
