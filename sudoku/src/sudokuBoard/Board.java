package sudokuBoard;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {

	private Integer[][] board = new Integer[9][9];

	private Integer[][] backupBoard = new Integer[9][9];

	/**
	 * Creates an empty board
	 */
	public Board(){}

	/**
	 * Creates a board that has been prepopulated.
	 * @param board
	 */
	public Board(Integer[][] input ){
		backupBoard = input;
		board = deepCopy(backupBoard);
	}

	public boolean placeNumber(int indexX, int indexY, int val){
		
		if(indexX<0 || indexY<0 || val<1 || indexX>8 || indexY>8 || val>9){
			throw new IllegalArgumentException(" Bad input: indexX: "+ indexX + " indexY: "+ indexY +" val: "+ val);
		}
		
		board[indexX][indexY] = val;
		
		//TODO: How the board should respond based on validation still needs to be defined. This is a placeholder.
		
		//Check if the board is legal. 
		if(validate()){ // If the board is legal, back it up and return true
			backupBoard = deepCopy(board);
			return true;
		}
		else{ //If the board is illegal, return it to its old state and return false;
			board = deepCopy(backupBoard);
			return false;
		}
	}
	
	private boolean validate(){

		return verifyColumns() && verifyRows() && verifyBoxes();
	}

	private boolean verifyBoxes() {
		
		//Loop through the boxes.
		for(int x = 0; x<9; x=x+3){
			for(int y = 0; y<9; y=y+3){
				
				Integer[][] box = getBox(x, y);
				Set<Integer> set = new HashSet<>();
				
				//This count refers to the number of non-null values in the box.
				int count = 0;
				
				//go through each number in the box.
				for(int i = 0; i<3; i++){
					for(int j = 0; j<3; j++){
						
						Integer num = box[i][j];
						
						//If a num is null skip it. If its not, add it to the set.
						//sets ignore duplicate numbers.
						if(num!=null){
							count++;
							set.add(box[i][j]);
						}
					}
				}
				
				//If we have the same number of numbers as our set size, that means
				//that each number only appeared once, so its valid. Otherwise there's
				//a duplicate.
				if(count == set.size()){
					return true;
				}
				else{
					return false;
				}
			}
		}
		
		return true;
	}

	private boolean verifyRows() {

		for(int i = 0; i<8; i++){
			Integer[] row = getRow(i);
			List<Integer> list = Arrays.asList(row);

			//If the row has the number, and it has more than one copy of it,
			//the row is invalid. We do this by making sure the index of a number
			//going one way is the same as the index coming from the other way.
			for(int j = 1; j<10; j++){
				
				if(list.contains(j) && (list.lastIndexOf(j) != list.indexOf(j))){
					return false;
				}
			}

		}
		return true;
	}

	private boolean verifyColumns() {
		
		for(int i = 0; i<8; i++){
			Integer[] col = getColumn(i);
			List<Integer> list = Arrays.asList(col);

			//If the col has the number, and it has more than one copy of it,
			//the col is invalid. We do this by making sure the index of a number
			//going one way is the same as the index coming from the other way.
			for(int j = 1; j<10; j++){
				
				if(list.contains(j) && (list.lastIndexOf(j) != list.indexOf(j))){
					return false;
				}
			}

		}
		return true;
	}

	/**
	 * Returns the column at the specified index, 0 to 8.
	 * @param index
	 * @return the column
	 */
	private Integer[] getColumn(int index){

		Integer[] col = new Integer[9];

		for(int i = 0; i< 9; i++){
			col[i] = board[index][i];
		}

		return col;
	}

	/**
	 * Returns the row at the specified index, 0 to 8.
	 * @param index
	 * @return the row
	 */
	private Integer[] getRow(int index){

		Integer[] row = new Integer[9];

		for(int i = 0; i< 9; i++){
			row[i] = board[i][index];
		}

		return row;
	}

	/**
	 * Returns the two dimensional array containing the box of values
	 * containing the value at index x,y, where the indexes can vary
	 * from 0 to 8.
	 * @param x
	 * @param y
	 * @return the box
	 */
	private Integer[][] getBox(int x, int y){

		Integer[][] box = new Integer[3][3];

		/*
		 * x|o|o
		 * o|o|o
		 * o|o|o
		 */
		int offsetX;
		int offsetY;

		if(x<4 && y<4){
			offsetX = 0;
			offsetY = 0;
		}

		/*
		 * o|x|o
		 * o|o|o
		 * o|o|o
		 */
		else if(x>3 && x<7 && y<4){
			offsetX = 3;
			offsetY = 0;
		}

		/*
		 * o|o|x
		 * o|o|o
		 * o|o|o
		 */
		else if(x>6 && y<4){
			offsetX = 6;
			offsetY = 0;
		}
		/*
		 * o|o|o
		 * x|o|o
		 * o|o|o
		 */
		else if(x>3 && x<7 && y<4){
			offsetX = 0;
			offsetY = 3;
		}
		/*
		 * o|o|o
		 * o|x|o
		 * o|o|o
		 */
		else if(x>3 && x<7 && y<4){
			offsetX = 3;
			offsetY = 3;
		}
		/*
		 * o|o|o
		 * o|o|x
		 * o|o|o
		 */
		else if(x>3 && x<7 && y<4){
			offsetX = 6;
			offsetY = 3;
		}
		/*
		 * o|o|o
		 * o|o|o
		 * x|o|o
		 */
		else if(x>3 && x<7 && y<4){
			offsetX = 0;
			offsetY = 6;
		}
		/*
		 * o|o|o
		 * o|o|o
		 * o|x|o
		 */
		else if(x>3 && x<7 && y<4){
			offsetX = 3;
			offsetY = 6;
		}
		/*
		 * o|o|o
		 * o|o|o
		 * o|o|x
		 */
		else if(x>3 && x<7 && y<4){
			offsetX = 6;
			offsetY = 6;
		}
		else{
			throw new IllegalArgumentException("Illegal indexes x: "+ x + " y: "+ y);
		}

		populateBox(box, offsetX, offsetY);

		return box;
	}

	/**
	 * Populates the passed in box with values from the board.
	 * The values that are copied are controlled by the offsets.
	 * So Box 1 would be offset (0,0), box 2 would be offset(3,0), etc.
	 * @param box
	 * @param offsetX
	 * @param offsetY
	 */
	private void populateBox(Integer[][] box, int offsetX, int offsetY) {

		if(offsetX%3!=0 || offsetY%3!=0 || offsetX>8 || offsetX<0 || offsetY>8 || offsetY<0){
			throw new IllegalArgumentException("Illegal offsets: X:"+ offsetX + " Y: "+ offsetY);
		}

		for(int i = 0; i<3; i++){
			for(int j = 0; j<3; j++){
				box[i][j] = board[i+offsetX][j+offsetY];
			}
		}

	}

	@Override
	public String toString(){
		
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i<9; i++){
			if(i!=0){
				builder.append("\n");
			}

			for(int j = 0; j<9; j++){
				
				if(j%3==0){
					builder.append("   ");
				}
				
				if(board[i][j]!=null){
					builder.append("{"+board[i][j]+"}");
				}
				else{
					builder.append("{ }");
				}
				
				if(j!=8 && j!=2 && j!=5){
					builder.append(",");
				}
			}

		}
		
		return builder.toString();
	}

	/**
	 * Returns true if there are any nulls on the board.
	 * Returns false if there is no empty space.
	 * @return
	 */
	public boolean hasSpace() {

		for(int i = 0; i< 9; i++){
			for(int j = 0; j<9; j++){
				if(board[i][j]==null){
					return true;
				}
			}
		}
		return false;
	}
	
	private Integer[][] deepCopy(Integer[][] board){
		Integer[][] output = new Integer[9][9];
		
		for(int i = 0; i<9; i++){
			for(int j = 0; j<9; j++){
				output[i][j] = board[i][j];
			}
		}
		
		return output;
	}
}
