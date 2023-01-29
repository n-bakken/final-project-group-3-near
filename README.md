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
```
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
```
### ScorePane Class
#### I took some code from the SomePane class (the pane for the controls screen) since without the main functionality, ScorePane is very similar. There were special instructions to follow regarding overriding some default functions in GraphicsPane, which ScorePane and SomePane extend, so I wanted to make sure I got it right. Other than that, I'll make the class from scratch.
```
 Member variables:
  private ArrayList<Score> scoreList
  private ArrayList<Score> topFiveList
  private GLabel score1
  private GLabel score2
  private GLabel score3
  private GLabel score4
  private GLabel score5
  may also need a scanner object
 Methods:
 //OPEN A TEXT FILE, WITH ERROR HANDLING
   private void openFile("leaderboard.txt"){
     try
       open the file as normal
     catch(file exception)
     	throw an exception
   }
		
 //READ FROM THE FILE, AND MAKE THE CONTENTS INTO SCORES IN AN ARRAYLIST
   private void fileToScore(){
     use readLine() built in method and split along newline characters to form a temporary list
     loop through the temporary list, which should be a pattern of score, name (start at index +1 and use step size 2)
     	Score temp = new Score(list[i-1], list[i])
	add temp to the member ArrayList
     close the file! this is the only place ill be reading from the file, and it's not the best place to handle this, but it makes more sense than my other options
   }
 
 //SORT THE ARRAYLIST
   private void insertionSortScores(){
     perform sorting algorithm on the scoreList array, use Score's myScore value to sort
   }
   
 //PUT THE TOP SCORES INTO THE SMALLER ARRAY, THIS IS SO IF THERE'S < 5 SCORES, THE REMAINING GLABELS WILL STILL BE INITIALIZED	
   private void initTopFiveScores(){
     int listsize = size of scoreList
     for loop from 0 to listsize...
     	add scoreList[i] to topFiveScores 
     if listsize < 5 //need to account for the other labels!
     	remainingsize = 5 - listsize
	for loop from 0 to remainingsize...
		create temporary blank score
		add temp to topFiveScores
   }
  
 //SHOW SHOWARRAY (these will need to be called BEFORE the game switches to this pane!)
   public void readyLeaderboard(){
     call all of the above functions, in the order they appear above
   }
   public void showLeaderboard(){
     call readyLeaderboard()
     loop through the topFiveScores arraylist...
     	create a GLabel for the score stats, need to update the coordinates in the constructor as i go
	add GLabel to the screen
   }
  //REMOVE EVERYTHING FROM THE SCREEN (call when you move away from the screen)
    public void hideLeaderboard(){
    	remove all the GLabels
    }
```
### MainApplication Class
#### This class is already made, but I am going to make some minor changes to allow the new button and screen to work such as adding a ScorePane object and using the already made switchToScreen() method to make the new screen appear when the button in MenuPane is pressed. 
```
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
```
### MenuPane Class
#### This class is also already made, but I am going to make a minor addition to add a button leading to the leaderboards (ScorePane) screen. Along with this code I'll need to review the old methods to see if I should edit them to include ScorePane.
```
Code to add:
  create a GButton leading the user to the ScorePane, with correct styling and placing in the constructor
  set its color
  add it to the screen
```

## Steps I took to implement (done after implementation)
1. First, I debugged everything with errors in the forked project to ensure that everything was working how I left it a month ago. This took some time as I has some strange problems with libraries not importing and filenames being incorrect, and had to ask the professor for help before starting work on anything. It took changing names in .project and settings.gradle, and refreshing the gradle. 
2. Once everything was working in the forked project how it did a month ago, I created the two new classes Score.java and ScorePane.java, and filled in their code similar to the pseudocode above; however, in the ScorePane class, I just wrote code to make it match the SomePane class. This was so I could get the basics working and tackle the main functionality later. I also changed the MenuPane and MainApplication classes in the same fashion I outlined above, which resulted in a new button on the main menu which leads the user to the leaderboard. I didn't create the filewriter in MainApplication at this time. 
3. Next, I decided to create a text file and get the filewriter working in MainApplication. I had a problem where it wasn't writing anything but also wasn't throwing an exception, so I decided to make a function where it tries to open a file, and makes a new one if it can't find it. This didn't work either, because it was just acting like it was working fine but didn't edit the file, so I decided to try to get the leaderboard working from a global array and work on writing to a file later. 
4. I started on the functions in ScorePane, and quickly figured out that I designed them wrong. I wanted one function to open the file, and another to read it and att the contents to an array. This was overly complicated (and the second function didn't have access to the scanner to read the file) so I decided to combine the functions into one. I followed my original idea here, but instead of splitting along newline characters, I looped through the file to create a new Score object every other line. I also added support for holding the new scores in an array, since I was not confident in my ability to solve the file problem. 
5. I took a small break and talked with a friend about my problem and they actually figured out what was wrong with it! I needed to append to the file instead of writing, and I was checking scores.txt in the wrong directory. Solved it and its working now!
6. I finished the function taking the array and making it into GButtons, and the functions adding and removing them to the screen. I also handled turning the large array into only the top five scores. I ran into a problem where nothing was appearing on the screen, but I messed around with the coordinates, sizes, and visibility, and eventually figured it out. I also decided to use GButtons instead of GLabels because I liked how the buttons looked better, and I'm handling returning to the main menu by having the user click anywhere, so the buttons are useless beyond displaying the message. 
7. I implemented the player name input last, and had to debug a little when it came to the order in which code was executed. I cleaned up my code a little after that and then I was done!
