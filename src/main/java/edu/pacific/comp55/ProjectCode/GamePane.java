package edu.pacific.comp55.ProjectCode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GObject;
import edu.pacific.comp55.starter.GraphicsPane;

public class GamePane extends GraphicsPane implements ActionListener {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GImage game;
	
	private Board board;
	public Timer gameTimer;
	private int counter;
	private int speed = 500;
	private int scoreNum = 0;
	private GImage next;
	private GImage heldBlock;
	private GImage hold;

	public boolean gamePaused = false;
	public boolean justPaused = false;
	public boolean newGame = false;

	public GamePane(MainApplication app) {
		this.program = app;
		game = new GImage("game1.png", 0, 0);
		game.setSize(program.WINDOW_WIDTH, program.WINDOW_HEIGHT-20);
		board = new Board();
		gameTimer = new Timer(speed, this);
		counter = 0;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub				
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			//TODO find some way to implement fasterFall here (if down is held down)
			moveDown();
		}
		else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			instantFall();
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			moveLeft();
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			moveRight();
		}
		else if(e.getKeyCode() == KeyEvent.VK_C) {
			holdBlock();
			// createHeldBlock();
		}
		else if(e.getKeyCode() == KeyEvent.VK_UP) {
			rotateRight();
		}
		else if(e.getKeyCode() == KeyEvent.VK_Z) {
			rotateLeft();
		}else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			//System.out.println("esc pressed");
			gamePaused = true;
			program.pauseSound();
			program.stopTime();
			program.switchToPause();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	//KEY EVENT FUNCTIONS
	public void moveDown() {
		board.moveActiveBlockDown();
	}
	public void instantFall() {
		board.instantFall();
	}
	public void moveRight() {
		board.moveActiveBlockRight();
	}
	public void moveLeft() {
		board.moveActiveBlockLeft();
	}
	public void holdBlock() {
		board.holdBlock();
		displayHoldBlock();
//		if (e.getKeyCode() == KeyEvent.VK_C) {
//			// TODO
//		}
	}
	public void rotateRight() {
		board.rotateBlockRight();
	}
	public void rotateLeft() {
		board.rotateBlockLeft();
	}
	
	void playGame() {
		counter++;
		justPaused = false;
		
		if (board.fullBoard()) {
			gameTimer.stop();
			program.stopTime();
			program.stopSound();
			program.switchToGameOver();
			return;
		}
		
		if(gamePaused) {
			justPaused = true;
			return;
		}
		
		if(counter % 2 == 0) {
			board.moveActiveBlockDown();
			if(board.activeBlockSat) {
				createNextBlock();
				board.activeBlockSat = false;
			}
		}

		//graphically print the board
		graphicBoard();
		
		if(counter % 20 == 0 && speed > 100) {
			gameTimer.stop();
			speed -= 50;
			gameTimer = new Timer(speed, this);
			gameTimer.start();
		}
		
		while(board.canClearLine() != -1) {
			board.clearLine();
			scoreNum+=100;
			graphicBoard();
		}
	}
	
	public int getScore() {
		return scoreNum;
	}
	
	public void drawBoard() {
		program.add(game);
	}
	
	public void removeBoard() {
		program.remove(game);
	}
	
	public void graphicBoard() {
		Block[][] addMe = new Block[20][10];
		addMe = board.getBoard();
		GImage px = new GImage("lpx.jpg", program.WINDOW_WIDTH / 2, program.WINDOW_HEIGHT / 2);
		for(int i = 0; i<20; i++) {
			for(int j = 0; j<10; j++) {
				if(addMe[i][j]==null) {
					px = new GImage("emptypx.jpg", program.WINDOW_WIDTH - 400 - (31*j), program.WINDOW_HEIGHT - 60 - (31*i));
				}
				else if(addMe[i][j].getBlockType().toString()=="Left L") {
					px = new GImage("lmpx.jpg", program.WINDOW_WIDTH - 400 - (31*j), program.WINDOW_HEIGHT - 60 - (31*i));
				}
				else if(addMe[i][j].getBlockType().toString()=="T") {
					px = new GImage("tpx.jpg", program.WINDOW_WIDTH - 400 - (31*j), program.WINDOW_HEIGHT - 60 - (31*i));
				}
				else if(addMe[i][j].getBlockType().toString()=="Right S") {
					px = new GImage("spx.jpg", program.WINDOW_WIDTH - 400 - (31*j), program.WINDOW_HEIGHT - 60 - (31*i));
				}
				else if(addMe[i][j].getBlockType().toString()=="Left S") {
					px = new GImage("zpx.jpg", program.WINDOW_WIDTH - 400 - (31*j), program.WINDOW_HEIGHT - 60 - (31*i));
				}
				else if(addMe[i][j].getBlockType().toString()=="Square") {
					px = new GImage("squarepx.jpg", program.WINDOW_WIDTH - 400 - (31*j), program.WINDOW_HEIGHT - 60 - (31*i));
				}
				else if(addMe[i][j].getBlockType().toString()=="Bar"){
					px = new GImage("barpx.jpg", program.WINDOW_WIDTH - 400 - (31*j), program.WINDOW_HEIGHT - 60 - (31*i));
				}
				else if(addMe[i][j].getBlockType().toString()=="Right L") {
					px = new GImage("lpx.jpg", program.WINDOW_WIDTH - 400 - (31*j), program.WINDOW_HEIGHT - 60 - (31*i));
				}				
				program.add(px);
			}
		}
	}
	
//	public void removeGraphicBoard() {
//		GImage bg = new GImage("bg.jpg", program.WINDOW_WIDTH / 2 - (628/2), program.WINDOW_HEIGHT / 2 - (650.5/2));
//		program.add(bg);
//	}
	
	private void createNextBlock() {
		//GImage cover = new GImage("cover.jpg", program.WINDOW_WIDTH - 200, program.WINDOW_HEIGHT / 2);
		//program.add(cover);
		if (next != null) {
			program.remove(next);
		}
		
		next = new GImage("s.png", program.WINDOW_WIDTH - 200, program.WINDOW_HEIGHT / 2);
		switch(board.getNextBlock().getBlockType()) {
		case T:
			next = new GImage("t.png", program.WINDOW_WIDTH - 200, program.WINDOW_HEIGHT / 2);
			break;
		case SQUARE:
			next = new GImage("square.jpg", program.WINDOW_WIDTH - 200, program.WINDOW_HEIGHT / 2);
			break;
		case BAR:
			next = new GImage("bar.jpg", program.WINDOW_WIDTH - 200, program.WINDOW_HEIGHT / 2);
			break;
		case RIGHTS:
			next = new GImage("s.png", program.WINDOW_WIDTH - 200, program.WINDOW_HEIGHT / 2);
			break;
		case LEFTS:
			next = new GImage("z.png", program.WINDOW_WIDTH - 200, program.WINDOW_HEIGHT / 2);
			break;
		case RIGHTL:
			next = new GImage("l.png", program.WINDOW_WIDTH - 200, program.WINDOW_HEIGHT / 2);
			break;
		case LEFTL:
			next = new GImage("lm.png", program.WINDOW_WIDTH - 200, program.WINDOW_HEIGHT / 2);
			break;
		}
		program.add(next);
	}
	
	private void displayHoldBlock() {
		if (heldBlock != null) {
			program.remove(heldBlock);
		}
		
		heldBlock = new GImage("s.png", program.WINDOW_WIDTH - 1000, program.WINDOW_HEIGHT / 2);
		switch(board.getHoldBlock().getBlockType()) {
		case T:
			heldBlock = new GImage("t.png", program.WINDOW_WIDTH - 1000, program.WINDOW_HEIGHT / 2);
			break;
		case SQUARE:
			heldBlock = new GImage("square.jpg", program.WINDOW_WIDTH - 1000, program.WINDOW_HEIGHT / 2);
			break;
		case BAR:
			heldBlock = new GImage("bar.jpg", program.WINDOW_WIDTH - 1000, program.WINDOW_HEIGHT / 2);
			break;
		case RIGHTS:
			heldBlock = new GImage("s.png", program.WINDOW_WIDTH - 1000, program.WINDOW_HEIGHT / 2);
			break;
		case LEFTS:
			heldBlock = new GImage("z.png", program.WINDOW_WIDTH - 1000, program.WINDOW_HEIGHT / 2);
			break;
		case RIGHTL:
			heldBlock = new GImage("l.png", program.WINDOW_WIDTH - 1000, program.WINDOW_HEIGHT / 2);
			break;
		case LEFTL:
			heldBlock = new GImage("lm.png", program.WINDOW_WIDTH - 1000, program.WINDOW_HEIGHT / 2);
			break;
		}
		program.add(heldBlock);
	}

	

	public void actionPerformed( ActionEvent e) {
		playGame();
	}

	@Override
	public void showContents() {
		if(board.fullBoard()) {
			this.board = new Board();
			newGame = false;
			justPaused = false;
		}
		drawBoard();
		if(!justPaused) {
			board.createNextBlock(Orientation.UP, false, false, 0,0);
			board.spawnBlock();
			createNextBlock();
		}
		gamePaused = false;
		gameTimer.start();	
	}

	@Override
	public void hideContents() {
		
	} 
	
	
	
}
