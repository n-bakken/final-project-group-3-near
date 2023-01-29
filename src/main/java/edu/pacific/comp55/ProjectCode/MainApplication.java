package edu.pacific.comp55.ProjectCode;

import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.Timer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
	private ScorePane scores;
	private Game consoleGame;
	
	private Block block;	
	private Board board;
	
	private GLabel timer;
	private GLabel scoreLabel;
	private GLabel score;
	
	//public FileWriter myWriter;
	//public File scoreFile;
	
	public int min = 0;
	public int sec = 0;
	public int scoreNum = 0;
	public boolean newGame = false;
	
	private Timer time = new Timer(1000, this);
	
	private int count;

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		requestFocus();
	}

	public void run() {
		timer = new GLabel("timer", 0, 100);
		scoreLabel = new GLabel("SCORE: ", 20, 100);
		score = new GLabel(Integer.toString(scoreNum), 10, 100);
		
		add(timer);
		add(scoreLabel);
		add(score);
	
		System.out.println("Hello, world!");
		over = new GameOverPane(this);
		quit = new QuitPane(this);
		pause = new PausePane(this);
		game = new GamePane(this);
		somePane = new SomePane(this);
		menu = new MenuPane(this);
		scores = new ScorePane(this);
		setupInteractions();
		switchToMenu();
		
		//System.out.print("working!");
	}
	
	public void actionPerformed(ActionEvent e) {
		// timer
		min++;
		sec++;
		
		timer.setFont(Font.MONOSPACED);
		timer.setLocation(85, 75);
		timer.sendToFront();
		timer.setLabel(min/60 + ":" + sec);
		
		if(sec <= 9) {
			timer.setLabel(min/60 + ":0" + sec); // makes the timer have a zero before single integers like 0:05
		}
		if(sec == 59) {
			sec = 0;
		}
		
		// scoreLabel
		//
		
		scoreLabel.setFont(Font.MONOSPACED);
		// score.setColor(Color.WHITE);
		scoreLabel.setLocation(40, 110);
		scoreLabel.sendToFront();
		
		// score
		
		score.setFont(Font.MONOSPACED);
		// score.setColor(Color.WHITE);
		score.setLocation(85, 110);
		score.sendToFront();
		
		scoreNum = game.getScore();
		score.setLabel(Integer.toString(scoreNum));
		
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
		//add score to leaderboard first!
		writeScore();
		switchToScreen(over);
	}
	
//	private void tryFile() {
//		try {
//			File scoreFile = new File("scores.txt");
//			Scanner myReader = new Scanner(scoreFile);
//			while (myReader.hasNextLine()) {
//		        String data = myReader.nextLine();
//		        System.out.println(data);
//		    }
//			System.out.println("file behaving as expected");
//			myReader.close();
//		} catch (Exception h) {
//			//the file couldn't be found, so make a new one
//			System.out.println("catch in try catch, file couldn't be found");
//			//make
//		}
//	}
//	
	private void writeScore() {
		try {
			FileWriter myWriter = new FileWriter("scores.txt", true);
		    myWriter.write(String.valueOf(game.getScore()) + "\n");
		    myWriter.write("player name" + "\n");
		    myWriter.close();
		    System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
		    System.out.println("An error occurred in adding score to leaderboard.");
		}
	}
	
	public void switchToScore() {
		scores.showLeaderboard();
		switchToScreen(scores);
	}
	
	/*public void displayBlock(Block b) {//
		
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
