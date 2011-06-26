package com.optimalbyte.snake;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class FruitEntity extends Entity {

	public FruitEntity(Game game) {
		super(game);
		
		spawnFruit();
	}

	@Override
	public void onDraw(Graphics2D graphics) {
		graphics.setColor(Color.RED);
		graphics.fill(new Ellipse2D.Double(location.getX(), location.getY(), Game.SIZE, Game.SIZE));
	}

	@Override
	public void onCollision(Entity other) {
		if (other instanceof SnakeEntity.TailEntity) {

			// XXX: On the off chance that the fruit were to spawn 
			// underneath the snakes tail, we respawn the fruit.
			if (atPosition(((SnakeEntity.TailEntity) other).getLocation())) {
				spawnFruit();
			}
		}
	}
	
	public void spawnFruit() {
		Random rand = new Random();
		int randX = ((rand.nextInt(Game.MAX_TILES / 3) / 10) * 10);
		int randY = ((rand.nextInt(Game.MAX_TILES / 3) / 10) * 10);
		setLocation(randX, randY);
	}
}
