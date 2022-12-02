package edu.pacific.comp55.ProjectCode;

import java.util.*;

public class Board {
	// private int numRows;  *Rows are always 20
	// private int numCols;  *Cols are always 10
	
	//Block[] list; made a list instead like a vector to input easier
	private ArrayList<Block>blocksOnBoard = new ArrayList<Block>();
	
	private Block[][] board;
	
	private Block activeBlock;
	private Block nextBlock;
	private Block heldBlock;
	private Block tempBlock;
	
	public Board() {
		board = new Block[20][10];
	}

	
	public Block createNextBlock(Orientation orientation, boolean rock, boolean cantRotatePhase, int startRow, int startCol) {
		nextBlock = new Block(orientation, rock, cantRotatePhase, startRow, startCol);
		return nextBlock;
	}
	
	public void spawnBlock(){
		activeBlock = nextBlock;
		nextBlock = new Block(Orientation.UP, false, false, 0,0);
		if (activeBlock.type == BlockType.BAR) {
			activeBlock.setStartSpace(0,4);
		}
		else if(activeBlock.type == BlockType.LEFTL){
			activeBlock.setStartSpace(1, 3);
		}
		else if (activeBlock.type == BlockType.RIGHTL) {
			activeBlock.setStartSpace(1,5);
		}
		else if (activeBlock.type == BlockType.LEFTS) {
			activeBlock.setStartSpace(0, 4);
		}
		else if (activeBlock.type == BlockType.RIGHTS) {
			activeBlock.setStartSpace(0, 4);
		}
		else if (activeBlock.type == BlockType.SQUARE) {
			activeBlock.setStartSpace(1, 4);
		}
		else { // T block
			activeBlock.setStartSpace(1,4);
		}
		
		System.out.println(activeBlock);
		blocksOnBoard.add(activeBlock);
		
		Space[] spaces = new Space[4];
		spaces = activeBlock.spacesOccupied();
		for (int i = 0; i < 4; i++) {
			board[spaces[i].getRow()][spaces[i].getCol()] = activeBlock;
		} 
	}
	
	public Block getBlock(Space s) {
		//nora: this function will be the equivalent of getVehicle in trafficjam, takes a space and returns whether there's a block there or not
		return board[s.getRow()][s.getCol()];
	}
	
	public int canClearLine() {
		//nora: go through each of teh rows and loop through each column. if every column is full for some row, return that row. if no row can be cleared, return -1
		boolean canClear = true;
		int returnInd = -1; //needed to be init, -1 isnt a valid row. but, it should be reassigned if the value is used. 
		for(int i = 0; i<board.length; i++) {
			canClear = true;
			for(int j = 0; j<board[i].length; j++) {
				if(board[i][j]==null) {
					canClear = false;
				}
			}
			if(canClear) {
				returnInd = i;
				return returnInd;
			}
		}
		if(canClear) {
			return returnInd;
		}
		else {
			return -1;
		}
		//need to remember wherever we call this, it needs to be called more than once because it can only return one row index at a time, and more than one line may be cleared at a time
	}
	
	public void clearLine() {
		int i = this.canClearLine();
		if (i == -1) {
			return;
		}
		else {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = null;
			}
		}
		this.dropBlocks();
	}
	
	public void dropBlocks() {
		System.out.println(this);
		for (int j = 0; j < 10; j++) {
			int lastInd = 19;
			for (int i = board.length - 1; i > 0; i--) {
				if (board[lastInd][j] != null && lastInd > 0) {
					lastInd -= 1;
				}
				if (board[i][j] != null && i != 19) {
					board[lastInd][j] = board[i][j];
					board[i][j] = null;
					lastInd -= 1;
				}
			}
		}
	}
	
	public boolean canRotate() {
		if(!activeBlock.canRotateType()) {
			return false;
		}
		return true;
	}
	
	public void rotateBlockRight() {
		removeBlock();
		activeBlock.rotateRight();
		addBlock(activeBlock);
	}
	
	public void rotateBlockLeft() {
		removeBlock();
		activeBlock.rotateLeft();
		addBlock(activeBlock);
	}
	
	public void holdBlock() {
		boolean canHold = true;
		boolean blockHeld = false;
		int numHolds = 0;
		
		if (canHold) {
			if (numHolds == 0) {
				if (blockHeld == false) {
					heldBlock = activeBlock;
					removeBlock();
					addBlock(nextBlock);
				}
				else {
					tempBlock = activeBlock;
					activeBlock = heldBlock;
					heldBlock = tempBlock;
				}
			}
			numHolds++;
			
		}
		
		// TODO: need to find out how to set boolean canHold back to true after placing a block, if i need a separate canHold boolean function
	}
	
	public void addBlock(Block addMe) {
		activeBlock = addMe;
		//add addMe to board and arraylist
		Space[] addMeSpaces = addMe.spacesOccupied();
		for(int i = 0; i<addMeSpaces.length; i++) {
			board[addMeSpaces[i].getRow()][addMeSpaces[i].getCol()] = addMe;
		}
		blocksOnBoard.add(addMe);
	}//nora
	
	public void removeBlock() {
		//need to remove it from the board/grid
		Space[] s = activeBlock.spacesOccupied();
		for(int i = 0; i<s.length; i++) {
			board[s[i].getRow()][s[i].getCol()] = null;
		}
		//need to remove it from the array
		for(int i = 0; i<blocksOnBoard.size(); i++) {
			if(blocksOnBoard.get(i)==activeBlock) {
				blocksOnBoard.remove(i);
				break;
			}
		}
	}//nora
	
	//this will check the spaces occupied against the spaces if I move some direction, and see if the space is otherwise occupied by some block on the board
	public boolean moveActiveBlockDown() {
		//check if it can move considering the bounds of the board
		if(activeBlock.canMoveDown()==0) {
			return false;
		}
		else if(activeBlock.canMoveDown()==2) {
			System.out.println("block placed!");
			spawnBlock();
			return false;
		}
		Space[] spacesOccupied = activeBlock.spacesOccupied();
		for(int i = 0; i<spacesOccupied.length; i++) {
			Space s = new Space(spacesOccupied[i].getRow()+1, spacesOccupied[i].getCol());
			if(getBlock(s) != null && getBlock(s) != activeBlock) {
				System.out.println("block placed!");
				spawnBlock();
				return false;
			}
		}
		removeBlock();
		//now move it!!
		activeBlock = new Block(activeBlock.type, activeBlock.getOrientation(), activeBlock.isRock(), activeBlock.getCantRotatePhase(), activeBlock.getStartSpace().getRow()+1, activeBlock.getStartSpace().getCol());
		addBlock(activeBlock);
		return true; // nora
	}
	
	public boolean fasterFall() {
		if(moveActiveBlockDown()) {
			if(moveActiveBlockDown()) {
				return true;
			}
		}
		return false;
	}
	
	public void instantFall() {
		for(int i = 0; i<20; i++) {
			if(!moveActiveBlockDown()) {
				return;
			}
		}
	}
	
	public boolean moveActiveBlockLeft() {
		//check if it can move considering the bounds of the board
		if(!activeBlock.canMoveLeft()) {
			return false;
		}
		Space[] spacesOccupied = activeBlock.spacesOccupied();
		for(int i = 0; i<spacesOccupied.length; i++) {
			Space s = new Space(spacesOccupied[i].getRow(), spacesOccupied[i].getCol()-1);
			if(getBlock(s) != null && getBlock(s) != activeBlock) {
				System.out.println("there's already a block there!");
				return false;
			}
		}
		removeBlock();
		//now move it!
		activeBlock = new Block(activeBlock.type, activeBlock.getOrientation(), activeBlock.isRock(), activeBlock.getCantRotatePhase(), activeBlock.getStartSpace().getRow(), activeBlock.getStartSpace().getCol()-1);
		addBlock(activeBlock);
		return true; // nora
	}
	
	public boolean moveActiveBlockRight() {
		//check if it can move considering the bounds of the board
		if(!activeBlock.canMoveRight()) {
			return false;
		}
		Space[] spacesOccupied = activeBlock.spacesOccupied();
		for(int i = 0; i<spacesOccupied.length; i++) {
			Space s = new Space(spacesOccupied[i].getRow(), spacesOccupied[i].getCol()+1);
			if(getBlock(s) != null && getBlock(s) != activeBlock) {
				System.out.println("there's already a block there!");
				return false;
			}
		}
		removeBlock();
		//now move it!!
		activeBlock = new Block(activeBlock.type, activeBlock.getOrientation(), activeBlock.isRock(), activeBlock.getCantRotatePhase(), activeBlock.getStartSpace().getRow(), activeBlock.getStartSpace().getCol()+1);
		addBlock(activeBlock);
		return true; // nora
	}
	
	public boolean fullBoard() {
		if (board[0][4] != null && board[0][4] != activeBlock) {
			return true;
		}
		return false;
	}
	
	public Block getActiveBlock() {
		return activeBlock;
	}


	public void setActiveBlock(Block activeBlock) {
		this.activeBlock = activeBlock;
	}


	public Block getNextBlock() {
		return nextBlock;
	}


	public void setNextBlock(Block nextBlock) {
		this.nextBlock = nextBlock;
	}


	public String toString() {
		return BoardConverter.createString(this);
	}
	
	//Board Testing
	/*
	public static void main(String[] args) {
//		Board b = new Board();
//		b.createNextBlock(Orientation.UP, false, false, 0,0);
//		b.spawnBlock();
//		b.fasterFall();
//		b.rotateBlockLeft();
//		System.out.println(b);
		
		
		Board b = new Board();
		Block a = new Block(BlockType.BAR,Orientation.UP, false, false, 19,1);
		Block c = new Block(BlockType.BAR,Orientation.UP, false, false, 19,5);
		Block d = new Block(BlockType.SQUARE,Orientation.UP, false, false, 19,8);
		Block e = new Block(BlockType.SQUARE,Orientation.UP, false, false, 17,8);
		Block g = new Block(BlockType.BAR,Orientation.UP, false, false, 18,1);
		Block h = new Block(BlockType.BAR,Orientation.UP, false, false, 18,5);
		Block i = new Block(BlockType.SQUARE,Orientation.UP, false, false, 17,8);
		Block j = new Block(BlockType.SQUARE,Orientation.UP, false, false, 17,8);
		
		
		b.addBlock(a);
		b.addBlock(c);
		b.addBlock(d);
		b.addBlock(e);
		b.addBlock(g);
		b.addBlock(h);
		
		System.out.println(b);
		b.clearLine();
		System.out.println(b);
	}
	*/
}
