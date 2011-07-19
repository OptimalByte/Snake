package com.optimalbyte.snake;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A timed task that is exuted every 100ms. This is used mainly for drawing
 * and collision checking, but has the consequence of dealing with game speed
 * (the game will update quicker depending on how fast this task is executed).
 *
 * TODO: I really don't like the way this is done, and should really
 * be redone when I get the chance.
 *
 * @author samuraiblood2
 */
public class GameTimer extends TimerTask {

	/** The delay before the task will be executed in milliseconds.  */
	private static final int RATE = 100;

	/** An instance of the Game. */
	private Game game;
	
	/**
	 * We use the constructor to pass an instance of the Game to this class.
	 *
	 * @param game An instance of Game.
	 */
	public GameTimer(Game game) {
		this.game = game;
		new Timer().scheduleAtFixedRate(this, 0, RATE);
	}

	/**
	 * Here we do basically everything, and is essentially the core of the game.
	 *
	 * @see Game#doDrawing()
	 * @see Entity#onCollision(Entity)
	 */
	@Override
	public void run() {
		
		// XXX: If the game is paused or the player died, we want to do one last
		// draw and stop the game there.
		if (game.hasDied() || game.isPaused()) {
			game.doDrawing();
			return;
		}
		
		// XXX: The actual drawing is done in a seperate method.
		game.doDrawing();		
		
		// XXX: Here we iterate through all the Entities we just drew.
		for (int i = 0; i < game.getEntities().size(); i++) {
			Entity entity = game.getEntities().get(i);
			
			// XXX: Where we check if the current Entity has collided with another.
			// TODO: I don't like this, it seems to lag a little.
			for (int n = (i + 1); n < game.getEntities().size(); n++) {
				Entity next = game.getEntities().get(n);
				entity.onCollision(next);
			}
		
			// XXX: We check if the Entity is the snake and, if so, make it move.
			if (entity instanceof SnakeEntity) {
				((SnakeEntity) entity).move();
			}
		}
		
		// XXX: Garbage collector.
		System.gc();
	}
}
