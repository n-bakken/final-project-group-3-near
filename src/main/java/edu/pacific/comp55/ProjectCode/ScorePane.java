package edu.pacific.comp55.ProjectCode;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import edu.pacific.comp55.starter.GButton;
import edu.pacific.comp55.starter.GraphicsPane;
import java.util.ArrayList;

public class ScorePane extends GraphicsPane {
	
	private MainApplication program;
	private GImage background;
	private GButton returnMessage;
	
	private ArrayList<Score> scoreList;
	private ArrayList<Score> topFiveList;
	private GLabel score1;
	private GLabel score2;
	private GLabel score3;
	private GLabel score4;
	private GLabel score5;
	
	public ScorePane(MainApplication app) {
		this.program = app;
		background = new GImage("game1.png", 0, 0);
		background.setSize(program.WINDOW_WIDTH, program.WINDOW_HEIGHT);
		returnMessage = new GButton("Click anywhere to return to main menu.", 0, 0, 500, 50);
		returnMessage.setFillColor(Color.yellow);
		returnMessage.setVisible(true);
		scoreList = new ArrayList<Score>();
		topFiveList = new ArrayList<Score>();
	}
	
	@Override
	public void showContents() {
		program.add(background);
		program.add(returnMessage);
	}

	@Override
	public void hideContents() {
		program.remove(background);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == background) {
			hideLeaderboard();
			program.switchToMenu();
		}
	}
	
	//METHOD TO OPEN A TEXT FILE, WITH ERROR HANDLING
	//METHOD TO READ FROM THE FILE, AND MAKE THE CONTENTS INTO SCORES IN AN ARRAYLIST
	private void openAndReadFile() {
		try {
			//open the file as normal
			File scoreFile = new File("scores.txt");
			Scanner myReader = new Scanner(scoreFile);
			int count = 0;
			int tempScore = 0;
			String tempName = "";
			while(myReader.hasNextLine()) {
				if(count%2 == 0) { //even number, scores are first
					//gotta cast it to integer...
					tempScore = Integer.parseInt(myReader.nextLine());
				}
				else { //odd number, must be a name
					//its the second thing, so make it a score and add to the array!
					tempName = myReader.nextLine();
					Score addMe = new Score(tempScore, tempName);
					scoreList.add(addMe);
				}
				count++;
			}
			myReader.close();
		} catch (FileNotFoundException f) {
			System.out.println("the scores file couldn't be found!");
			return;
		}
	}
	
	//print array...for testing!
	public void printScoreArray() {
		for(int i = 0; i<scoreList.size(); i++) {
			System.out.println(scoreList.get(i).toString());
		}
	}
	
	//while file writing isnt working...do it with just the array!
	public void addScoreToArray(int newScore, String newName) {
		Score addMe = new Score(newScore, newName);
		scoreList.add(addMe);
	}

	 //SORT THE ARRAYLIST
	   private void insertionSortScores(){
	     //perform sorting algorithm on the scoreList array, use Score's myScore value to sort
	   }
	   
	 //PUT THE TOP SCORES INTO THE SMALLER ARRAY, THIS IS SO IF THERE'S < 5 SCORES, THE REMAINING GLABELS WILL STILL BE INITIALIZED	
	   private void initTopFiveScores(){
	     int listsize = scoreList.size();
	     if(listsize < 5) {
	    	 //going to need some blank scores to fill in the other GLabels
	    	 //go as far as i can with the list I have...
	    	 for(int i = 0; i<listsize; i++) {
	    		 topFiveList.add(scoreList.get(i));
	    	 }
	    	 int remainingSize = 5-listsize;
	    	 //then fill in the rest with blanks
	    	 for(int i = 0; i<remainingSize; i++) {
	    		//create temporary blank score
	 			//add temp to topFiveScores
	    		 Score temp = new Score(0, "N/A");
	    		 topFiveList.add(temp);
	    	 }
	     }
	     else {
		     //add scoreList[i] to topFiveScores 
	    	 for(int i = 0; i<5; i++) {
	    		 topFiveList.add(scoreList.get(i));
	    	 }
	     }
	   }
	  
	 //SHOW SHOWARRAY (these will need to be called BEFORE the game switches to this pane!)
	   public void readyLeaderboard(){
	     //call all of the above functions, in the order they appear above
		 openAndReadFile();
		 insertionSortScores();
		 initTopFiveScores();
	   }
	   public void showLeaderboard(){
		   readyLeaderboard();
		   printScoreArray();
		   score1 = new GLabel(topFiveList.get(0).toString(), 50, 50);
		   score2 = new GLabel(topFiveList.get(0).toString(), 50, 100);
		   score3 = new GLabel(topFiveList.get(0).toString(), 50, 150);
		   score4 = new GLabel(topFiveList.get(0).toString(), 50, 200);
		   score5 = new GLabel(topFiveList.get(0).toString(), 50, 250);
		   program.add(score1);
		   program.add(score2);
		   program.add(score3);
		   program.add(score4);
		   program.add(score5);
	   }
	  //REMOVE EVERYTHING FROM THE SCREEN (call when you move away from the screen)
	   //currently being called in mouse event in this class
	    public void hideLeaderboard(){
	    	//remove all the GLabels
	    	program.remove(score1);
	    	program.remove(score2);
			program.remove(score3);
			program.remove(score4);
			program.remove(score5);
	    }
	
}
