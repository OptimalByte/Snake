package com.optimalbyte.snake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

/**
 * A simple wrapper for the KeyListener interface.
 *
 * @author samuraiblood2
 */
public class InputListener implements KeyListener {
	
	/** An instance of the Game. */
	private Game game;
	
	/**
	 * We use the constructor to pass an instance of the Game to this class.
	 *
	 * @param game An instance of Game.
	 */
	public InputListener(Game game) {
		this.game = game;
	}
	
	/**
	 * Called when (obviously) a key is pressed. This is where
	 * we will be making use of the various keys that will need to
	 * be pressed when eventually playing the game.
	 *
	 * @param event The KeyEvent passed to this method.
	 */
	@Override
	public void keyPressed(KeyEvent event) {
		int code = event.getKeyCode();
		if (code == KeyEvent.VK_P) {
			game.doPaused();
		}
		
		if (game.hasDied() || game.isPaused()) {
			
			// XXX: Resets the game if the R key is pressed.
			if (code == KeyEvent.VK_R) {
				game.reset();
			}
			
			if (game.isPaused()) {
			
				// XXX: Allows the user to change the length of the snake when the F1 key is pressed.
				if (code == KeyEvent.VK_F1) {
					int length = Integer.parseInt(JOptionPane.showInputDialog(null, "Length"));
				
					for (int i = 0; i < length; i++) {
						game.addScore(10);
						((SnakeEntity) game.getEntities().get(0)).addTailPiece();
					}
				} else if (code == KeyEvent.VK_F2) {
					game.displayTiles();
				}
			}
			return;
		}

		// XXX: For getting the Direction from each key pressed.
		for (Direction dir : Direction.values()) {
			if (dir.contains(code)) {
				if (dir.getOpposite() == game.getDirection()) {
					continue;
				}
				
				game.setDirection(dir);
			}
		}
	}	
	
	
	/**
	 * Called when (obviously) a key is released. This is otherwise useless
	 * for us since we don't want the snake to stop moving until another
	 * key is pressed.
	 *
	 * @param event The KeyEvent passed to this method.
	 */
	@Override
	public void keyReleased(KeyEvent event) {
		// XXX: Nothing to do here.
	}
	
	/**
	 * Called when (obviously) a key is typed. It wouldn't be impossible
	 * to use this instead of keyPressed(KeyEvent), however, it would seem that 
	 * this method is partially deaf making it a possible hiderence if used.
	 *
	 * @param event The KeyEvent passed to this method.
	 */
	@Override
	public void keyTyped(KeyEvent event) {
		// XXX: Nothing to do here.
	}
}