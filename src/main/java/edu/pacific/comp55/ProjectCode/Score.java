package edu.pacific.comp55.ProjectCode;

public class Score {
	private int myScore;
	private String myName;
	
	Score(int someScore, String someName){
		myScore = someScore;
		myName = someName;
	}
	
	public int getScore() {
		return myScore;
	}
	public String getName() {
		return myName;
	}
	public void setScore(int theScore) {
		myScore = theScore;
	}
	public void setName(String theName) {
		myName = theName;
	}
	public String toString() {
		return String.valueOf(myScore) + " " + myName;
	}
}
