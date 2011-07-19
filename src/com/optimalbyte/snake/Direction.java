package com.optimalbyte.snake;

import java.awt.event.KeyEvent;

/**
 * For determining the Direction the snake will move.
 *
 * @author samuraiblood2
 */
public enum Direction {
	
	/**
	 * Moves the snake left if the left or A keys are pressed.
	 */
	LEFT(KeyEvent.VK_LEFT, KeyEvent.VK_A), 
	
	/**
	 * Moves the snake right if the right or D keys are pressed.
	 */
	RIGHT(KeyEvent.VK_RIGHT, KeyEvent.VK_D), 
	
	/**
	 * Moves the snake up if the up or W keys are pressed.
	 */
	UP(KeyEvent.VK_UP, KeyEvent.VK_W),
	
	/**
	 * Moves the snake down if the down or S keys are pressed.
	 */
	DOWN(KeyEvent.VK_DOWN, KeyEvent.VK_S);
	
	/**
	 * Contains all the possible code representations 
	 * of the keys used to move in the direction specified.
	 */
	private int[] codes;
		
	/**
	 * TODO: Document this; currently too lazy. 
	 *
	 * @param codes I don't get very many opportunities to use an elipses.
	 */
	Direction(int... codes) {
		this.codes = codes;
	}
		
	/**
	 * Gets the opposite Direction of this Direction.  An example would 
	 * NORTH and SOUTH, as they are most obviously the opposites of one another.
	 *
	 * @return The opposite of this Direction, unless for some unexplanable 
	 * 	reason one isn't found, then it returns null.
	 */
	public Direction getOpposite() {
		switch (this) {
			case LEFT:
				return RIGHT;
			
			case RIGHT:
				return LEFT;
			
			case DOWN:
				return UP;
				
			case UP:
				return DOWN;
		}
		return null;
	}
	
	/**
	 * Getter method for the codes array.
	 *
	 * @return The codes array.
	 * @see #codes
	 */
	public int[] getCodes() {
		return codes;
	}
	
	/**
	 * Whether or not this instance contains the value being passed.
	 *
	 * @return True if so, false otherwise.
	 */
	public boolean contains(int code) {
		for (int i = 0; i < codes.length; i++) {
			if (codes[i] == code) {
				return true;
			}
		}
		return false;
	}
}