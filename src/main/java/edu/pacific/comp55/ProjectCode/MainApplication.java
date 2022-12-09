package edu.pacific.comp55.ProjectCode;

import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.Timer;

import acm.graphics.GLabel;
import edu.pacific.comp55.starter.AudioPlayer;
import edu.pacific.comp55.starter.GraphicsApplication;
import javafx.scene.paint.Color;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 1050;
	public static final int WINDOW_HEIGHT = 650;
	public static final String MUSIC_FOLDER = "sounds";
	private static final String SOUND_FILE = "Tetris.mp3";

	private SomePane somePane;
	private MenuPane menu;
	private GamePane game;
	private PausePane pause;
	private QuitPane quit;
	private GameOverPane over; 
	
	private Game consoleGame;
	
	private Block block;	
	private Board board;
	
	private GLabel timer;
	private GLabel score;
	
	public int min = 0;
	public int sec = 0;
	private int scoreNum = 0;
	public boolean newGame = false;
	
	private Timer time = new Timer(1000, this);
	
	private int count;

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		requestFocus();
	}

	public void run() {
		timer = new GLabel("timer", 0, 100);
		score = new GLabel("score", 90, 90);
		add(timer);
		add(score);
		
		score = new GLabel("Score", 10, 100);
		//add(score);
		
		System.out.println("Hello, world!");
		over = new GameOverPane(this);
		quit = new QuitPane(this);
		pause = new PausePane(this);
		game = new GamePane(this);
		somePane = new SomePane(this);
		menu = new MenuPane(this);
		setupInteractions();
		switchToMenu();
	}
	
	public void actionPerformed(ActionEvent e) {
		min++;
		sec++;
		
		timer.setFont(Font.MONOSPACED);
		timer.setLocation(90, 75);
		timer.sendToFront();
		timer.setLabel(min/60 + ":" + sec);
		if(sec <= 9) {
			timer.setLabel(min/60 + ":0" + sec); // makes the timer have a zero before single integers like 0:05
		}
		if(sec == 59) {
			sec = 0;
		}
		
//<<<<<<< HEAD
		score.setFont(Font.MONOSPACED);
		score.setLocation(90, 90); // not sure just a guesstimate
//=======
		/*score.setFont(Font.MONOSPACED);
		score.setLocation(90, 90);
>>>>>>> branch 'main' of https://github.com/COMP55Fall2022/final-project-group-3-near.git
		
<<<<<<< HEAD
		if (board.clearLine()) {
=======
		if(board.clearLine() == true) {
>>>>>>> branch 'main' of https://github.com/COMP55Fall2022/final-project-group-3-near.git
			scoreNum+=100;
		}
		
<<<<<<< HEAD
		score.setLabel("SCORE " + scoreNum);
=======
		score.sendToFront();
		score.setLabel("SCORE " + scoreNum);*/
//>>>>>>> branch 'main' of https://github.com/COMP55Fall2022/final-project-group-3-near.git
		
	}
	
	public void addScore() { // idk man supposed to add score on game pane
		add(score);
	}
	
	public int getScore() {
		return scoreNum;
	}
	
	public void startTime() {
		time.start();
	}
	
	public void restartTime() {
		time.restart();
	}
	
	public void stopTime() {
		time.stop();
	}

	public void switchToMenu() {
		count++;
		switchToScreen(menu);
	}

	public void switchToSome() {
		switchToScreen(somePane);
	}
	
	public void switchToGame() {
		game.gamePaused = false;
		if(newGame) {
			game.newGame = true;
		}
		switchToScreen(game);
	}
	
	public void switchToPause() {
		switchToScreen(pause);
	}
	
	public void switchToQuit() {
		switchToScreen(quit);
	}
	
	public void switchToGameOver() {
		switchToScreen(over);
	}
	
	/*public void displayBlock(Block b) {
		
		Space[] space = new Space[4];
		if (b.getBlockType() == BlockType.BAR) {
 			space.
 		}
 		else if (b.getBlockType() == BlockType.RIGHTL) {
 			space
 		}
 		else if (b.getBlockType() == BlockType.LEFTL) {
 			space
 		}
 		else if (b.getBlockType() == BlockType.RIGHTS) {
 			space 
 		}
 		else if (b.getBlockType() == BlockType.SQUARE) {
 			space 
 		}
 		else {
 			space 
 		}
	}*/

	

	public void playSound() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, SOUND_FILE, true);
	}
	
	public void pauseSound() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.pauseSound(MUSIC_FOLDER, SOUND_FILE);
	}
	
	public void stopSound() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.stopSound(MUSIC_FOLDER, SOUND_FILE);
	}
	
	public static void main(String[] args) {
		new MainApplication().start();
	}
	
}
