package edu.pacific.comp55.ProjectCode;
import acm.program.*;

import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class Game {
	public static Board board;
	
	public  void main(String[] args) {
		Game g = new Game();
		JFrame frame = new JFrame("Key Listener");
        Container contentPane = frame.getContentPane();
		KeyListener listener = new KeyListener() {
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
					moveRight();
				}
				else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
					moveLeft();
				}
				else if(e.getKeyCode() == KeyEvent.VK_C) {
					holdBlock();
				}
				else if(e.getKeyCode() == KeyEvent.VK_UP) {
					rotateRight();
				}
				else if(e.getKeyCode() == KeyEvent.VK_Z) {
					rotateLeft();
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
			}
			public void rotateRight() {
				board.rotateBlockRight();
			}
			public void rotateLeft() {
				board.rotateBlockLeft();
			}
		};
		JTextField textField = new JTextField();
        textField.addKeyListener(listener);
        contentPane.add(textField, BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true);
		g.setupBoard();
		g.playGame();
	}

	public void setupBoard() {
		board = new Board();
	}
	
	public void playGame() {
		board.createNextBlock(Orientation.UP, false, false, 0,0);
		board.spawnBlock();
		int counter = 0;
		int waitTime = 500;
		while (board.fullBoard() == false) {
			board.clearLine();    
			if(counter % 2 == 0) {
				if (board.getActiveBlock() == null) {
					break;
				}
				board.moveActiveBlockDown();
			}
			System.out.println(board);
			if(counter % 10 == 0) {
				waitTime--;
			}
			wait(waitTime);
			counter++;
			//check for clearing line
			while(board.canClearLine() != -1) {
				board.clearLine();
			}
		}
		//System.out.println("game over!");
		
	}

	public static void wait(int ms) {
	    try {
	        Thread.sleep(ms);
	    }
	    catch(InterruptedException ex) {
	        Thread.currentThread().interrupt();
	    }
	}
}