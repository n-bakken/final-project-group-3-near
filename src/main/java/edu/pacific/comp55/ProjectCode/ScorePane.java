package edu.pacific.comp55.ProjectCode;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import edu.pacific.comp55.starter.GButton;
import edu.pacific.comp55.starter.GraphicsPane;

public class ScorePane extends GraphicsPane {
	
	private MainApplication program; // you will use program to get access to
	// all of the GraphicsProgram calls
	private GImage background;
	private GButton returnMessage;
	
	public ScorePane(MainApplication app) {
		this.program = app;
		background = new GImage("game1.png", 0, 0);
		background.setSize(program.WINDOW_WIDTH, program.WINDOW_HEIGHT);
		//para = new GParagraph("welcome\nto my\nsecret room!", 150, 300);
		//para.setFont("Arial-24");
		returnMessage = new GButton("Click anywhere to return to main menu.", 0, 0, 500, 50);
		returnMessage.setFillColor(Color.yellow);
		returnMessage.setVisible(true);
	}
	
	@Override
	public void showContents() {
		program.add(background);
		program.add(returnMessage);
		//program.add(para);
	}

	@Override
	public void hideContents() {
		program.remove(background);
		//program.remove(para);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//para.setText("you need\nto click\non the eyes\nto go back");
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == background) {
			program.switchToMenu();
		}
	}
	
	//METHOD TO OPEN A TEXT FILE, WITH ERROR HANDLING
	
	//METHOD TO CLOSE THE TEXT FILE
	
	//METHOD TO READ FROM THE FILE, AND MAKE THE CONTENTS INTO SCORES IN AN ARRAYLIST
	
	//METHOD TO SORT THE ARRAYLIST
	
	//METHOD TO GRAB FIRST 5 ELEMENTS IN THE ARRAY LIST, IN SHOWARRAY
	
	//METHOD TO SHOW SHOWARRAY
	
}
