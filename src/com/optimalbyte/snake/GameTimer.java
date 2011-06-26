package com.optimalbyte.snake;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class GameTimer extends TimerTask {

	private static final int RATE = 50;
	private Game game;
	
	public GameTimer(Game game) {
		this.game = game;
		new Timer().scheduleAtFixedRate(this, 0, RATE);
	}

	@Override
	public void run() {
		if (game.hasDied() || game.isPaused()) {
			game.doDrawing();
			return;
		}

		game.doDrawing();		
		for (int i = 0; i < game.getEntities().size(); i++) {
			Entity entity = game.getEntities().get(i);
			for (int n = (i + 1); n < game.getEntities().size(); n++) {
				Entity next = game.getEntities().get(n);
				entity.onCollision(next);
			}
		
			if (entity instanceof SnakeEntity) {
				((SnakeEntity) entity).move();
			}
		}
		
		System.gc();
	}
}
