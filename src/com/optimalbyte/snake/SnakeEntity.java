package com.optimalbyte.snake;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public class SnakeEntity extends Entity {

	private List<TailEntity> tailPieces = new ArrayList<TailEntity>();
	
	public SnakeEntity(Game game) {
		super(game);

		setLocation(50, 50);
		for (int i = 0; i < 5; i ++) {
			if (i == 0) {
				continue;
			}
			TailEntity entity = new TailEntity(game);
			entity.setLocation((50 - (i * Game.SIZE)), 50);
			tailPieces.add(entity);
		}
		game.getEntities().addAll(tailPieces);
	}
	
	@Override
	public void onDraw(Graphics2D graphics) {
		graphics.setColor(Color.WHITE);
		graphics.drawString("Length: " + tailPieces.size(), 5, 30);

		graphics.setColor(Color.GREEN.darker().darker());
		graphics.fill(new Ellipse2D.Double(location.getX(), location.getY(), Game.SIZE, Game.SIZE));
	}
	
	@Override
	public void onCollision(Entity other) {
		if (other instanceof FruitEntity) {
			FruitEntity fruit = (FruitEntity) other;
			if (atPosition(fruit.getLocation())) {
				game.addScore(10);

				tailPieces.add(new TailEntity(game));
				game.getEntities().addAll(tailPieces);

				fruit.spawnFruit();
			}
		} else if (other instanceof TailEntity) {
			TailEntity tail = (TailEntity) other;
			if (atPosition(tail.getLocation())) {
				game.doGameOver();
			}
		}
		
		if (!game.getGameBoundries().contains(location)) {
			game.doGameOver();
		}
	}
	
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

	public void addTailPiece() {
		tailPieces.add(new TailEntity(game));
		game.getEntities().addAll(tailPieces);
	}
	
	class TailEntity extends Entity {
		public TailEntity(Game game) {
			super(game);
		}
		
		@Override
		public void onDraw(Graphics2D graphics) {
			graphics.setColor(Color.GREEN);
			graphics.fill(new Ellipse2D.Double(location.getX(), location.getY(), Game.SIZE, Game.SIZE));
		}
		
		@Override
		public void onCollision(Entity other) {
			// XXX: This will be handled somewhere else;
		}
	}
}
