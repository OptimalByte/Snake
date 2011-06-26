package com.optimalbyte.snake;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Snake extends JFrame {

	public Snake() {
		super("Snake");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(320, 340);
		setResizable(false);

		setLocation(getCenterPoint());
		
		add(new Game());
		
		setVisible(true);
	}

	private Point getCenterPoint() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		double x = ((dim.getWidth() - getWidth()) / 2);
		double y = ((dim.getHeight() - getHeight()) / 2);
		return new Point((int) x, (int) y);
		//setLocation((int) x, (int) y);
	}
		
	public static void main(String[] args) {
		new Snake();
	}
}
