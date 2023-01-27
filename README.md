# 55starterfiles
Starter Project for Comp 55

You will use this project as your base.
Make sure that you understand the two files provided here

## UML Class Diagram for files provided
![](media/55GroupProjectUML.jpg)

## UML Sequence Diagram for files provided
![](media/55GroupProjectSequenceDiagram.png)

# COMP129: COMP055 Resurrection Project
## Overview of added feature: Leaderboard
The feature I am planning to add is a leaderboard of scores and initials that will remain even after the game is closed, and be sorted to show only the top five scores. My original group's project was upside-down tetris, and for every line cleared the player received 100 points; therefore, a leaderboard is a very logical step for our game. I plan to add a button to the leaderboard on the main menu screen that will lead the player to the screen, and have the scores appear as GLabels so they can change as the top scores change. 
## Pseudocode
Note: I started coding a little before doing this section of the project; I wanted to cover parts of the code that might result in problems so that I would have time to speak to the professor about them. I needed to manage my time to ensure I would have time to ask the professor. I did not complete the main functionality, I just added buttons and such to prepare for the main functionality.
### Score Class
#### This class will facilitate the creation of score objects, so I can handle a score and a name together even after I sort and display them. I thought this was better than a hashmap or dictionary because it really keeps the two together even if I hold them in an ArrayList and sort them. Most of the methods were self explanatory, so I did not add a description.
Constructor:
  Score(int someScore, String someName);
Member variables:
  private int myScore
  private String myName
Methods:
  public void setScore();
  public void setName();
  public int getScore();
  public String getName();
### ScorePane Class
#### I took some code from the SomePane class (the pane for the controls screen) since without the main functionality, ScorePane is very similar. There were special instructions to follow regarding overriding some default functions in GraphicsPane, which ScorePane and SomePane extend, so I wanted to make sure I got it right. Other than that, I'll the class from scratch.
 Member variables:
  private ArrayList<Score> scoreList
Methods:
	//METHOD TO OPEN A TEXT FILE, WITH ERROR HANDLING
	
	//METHOD TO CLOSE THE TEXT FILE
	
	//METHOD TO READ FROM THE FILE, AND MAKE THE CONTENTS INTO SCORES IN AN ARRAYLIST
	
	//METHOD TO SORT THE ARRAYLIST
	private void insertionSortScores(){
    perform sorting algorithm on the scoreList array, use Score's myScore value to sort
  }
  
	//SHOW SHOWARRAY (this will need to be called BEFORE the game switches to this pane!)
  public void readyLeaderboard(){
    call all of the above functions, in the order they appear above
  }
  
### MainApplication Class
#### This class is already made, but I am going to make some minor changes to allow the new button and screen to work such as adding a ScorePane object and using the already made switchToScreen() method to make the new screen appear when the button in MenuPane is pressed. 
Code to add:
Member variables:
  private ScorePane scores
  private FileWriter write
Methods:
  public void switchToScore(){
    call the switchToScreen method with the ScorePane object
  }
  public void switchToGameOver(){
    open the scores file
    add the score for the game to the file, then newline
    ask the user for their initials
    add their input, then newline
    close the file
    call the switchToScreen method with the ScorePane object
  }
I also need to edit the run() method to add the ScorePane object to the program. 
### MenuPane Class
#### This class is also already made, but I am going to make a minor addition to add a button leading to the leaderboards (ScorePane) screen. Along with this code I'll need to review the old methods to see if I should edit them to include ScorePane.
Code to add:
  create a GButton leading the user to the ScorePane, with correct styling and placing in the constructor
  set its color
  add it to the screen

## Steps I took to implement (done after implementation)
