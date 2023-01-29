package edu.pacific.comp55.ProjectCode;
import java.awt.Color;
import acm.program.*;
import java.awt.event.MouseEvent;
import acm.graphics.GImage;
import acm.graphics.GObject;
import edu.pacific.comp55.starter.GButton;
import edu.pacific.comp55.starter.GraphicsPane;

public class GameOverPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GImage gameOver;
	private GButton menu;
	private GButton nameMessage;

	public GameOverPane(MainApplication app) {
		this.program = app;
		gameOver = new GImage("gameover1.png", 0, 0);
		gameOver.setSize(program.WINDOW_WIDTH, program.WINDOW_HEIGHT);
		
		menu = new GButton("menu", 400, 415, 240, 100); // TODO edit button location
		menu.setColor(Color.red);
		menu.setVisible(false);
		
		nameMessage = new GButton("Please enter your name in the console!", 280, 325, 500, 50);
		nameMessage.setFillColor(Color.yellow);
		nameMessage.setVisible(true);
		nameMessage.sendToFront();
	}

	@Override
	public void showContents() {
		program.add(gameOver);
		program.add(menu);
		program.add(nameMessage);
	}

	@Override
	public void hideContents() {
		program.remove(gameOver);
		program.remove(menu);
		program.remove(nameMessage);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == menu) {
			program.switchToMenu();
		}
	}
}
