package edu.pacific.comp55.ProjectCode;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;
import edu.pacific.comp55.starter.GButton;
import edu.pacific.comp55.starter.GraphicsPane;

public class MenuPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GButton rect;
	private GImage menu;
	private GamePane game;
	private GButton play;
	private GButton quit;
	//NEW ADDITION BY NORA
	private GButton scores;
	

	public MenuPane(MainApplication app) {
		super();
		program = app;
		rect = new GButton("controls", 430, 450, 200, 100);
		rect.setColor(Color.black);
		rect.setVisible(false);
		
		play = new GButton("play", 240, 380, 150, 80);
		play.setFillColor(Color.red);
		play.setVisible(false);
		
		quit = new GButton("quit", 640, 380, 200, 80);
		quit.setFillColor(Color.red);
		quit.setVisible(false);
		
		scores = new GButton("NEW! Leaderboard", 430, 320, 200, 80);
		scores.setFillColor(Color.yellow);
		scores.setVisible(true);
		
		menu = new GImage("mainmenu.png", 0,0);
		menu.setSize(program.WINDOW_WIDTH, program.WINDOW_HEIGHT);
	}

	@Override
	public void showContents() {
		program.add(menu);
		program.add(rect);
		program.add(play);
		program.add(quit);
		program.add(scores);
	}

	@Override
	public void hideContents() {
		program.remove(menu);
		program.remove(rect);
		program.remove(play);
		program.remove(quit);
		program.remove(scores);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == rect) {
			program.switchToSome();
		}else if(obj == play) {
			//program.restartTime();
			program.min = 0;
			program.sec = 0;
			program.newGame = true;
			program.playSound();
			program.startTime();
			program.switchToGame();
		}else if(obj == quit) {
			program.switchToQuit();
		}else if(obj == scores) {
			program.switchToScore();
		}
	}
	
}
