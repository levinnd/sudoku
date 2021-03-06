package sudokuBoard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {

	private Integer[][] board = new Integer[9][9];

	private Integer[][] backupBoard = new Integer[9][9];

	private boolean [][] editable = new boolean[9][9];
	
	private int time = 0;

	/**
	 * Creates an empty board
	 */
	public Board(){
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				editable[i][j]=true;
			}
		}
	}

	/**
	 * Creates a board that has been prepopulated.
	 * @param board
	 */
	public Board(Integer[][] input ){
		backupBoard = deepCopy(input);
		board = deepCopy(backupBoard);

		for(int i = 0; i<9; i++){

			for(int j = 0; j<9; j++ ){
				if(board[i][j]!=null){
					editable[i][j] = false;
				}
				else{
					editable[i][j] = true;
				}
			}
		}
	}

	public Board(Integer[][] input, boolean[][] editInput){
		board = deepCopy(input);
		backupBoard = deepCopy(input);
		this.editable = deepCopy(editInput);
	}

	public boolean placeNumber(Integer x, Integer y, Integer val){

		if(x<0 || y<0 || x>8 || y>8 || (val!=null && (val>9 || val<1))){
			throw new IllegalArgumentException(" Bad input: indexX: "+ x + " indexY: "+ y +" val: "+ val);
		}

		//Check to make sure its not one of the original numbers. If it is, don't let the user edit it.
		if(!editable[y][x]){
			return false;
		}


		board[y][x] = val;

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

	public Integer getValue(int x, int  y){
		return board[y][x];
	}

	public boolean validate(){

		return verifyColumns().size()==0 && verifyRows().size()==0 && verifyBoxes().size()==0;
	}

	private Set<Coord> verifyBoxes() {

		Set<Coord> set = new HashSet<>();

		//Loop through the boxes.
		for(int x = 0; x<9; x=x+3){
			for(int y = 0; y<9; y=y+3){

				Integer[][] box = getBox(x, y);

				List<Integer> list = new ArrayList<>();
				//go through each number in the box, convert it into an list.			
				//If I didn't care about what the duplicates were, I could use a faster algorithm.... :(
				for(int i = 0; i<3; i++){
					for(int j = 0; j<3; j++){

						list.add(box[i][j]);
					}
				}

				//Now that I have a row, check it the same way I check other rows.
				for(int num = 1; num<10; num++){

					//Locate the duplicate if there is one
					if(list.contains(num) && (list.lastIndexOf(num) != list.indexOf(num))){

						//Since there's a duplicate, we have to grab every number and get the coordinates
						for(int index = 0; index<9; index++){
							if(list.get(index)!=null && list.get(index)==num){

								//calculate the indexes
								int indexy1 = index/3+y;
								int indexx1 = index%3+x;

								//store
								set.add(new Coord(indexx1,indexy1));
							}
						}

					}
				}

			}
		}
		return set;
	}


	private Set<Coord> verifyRows() {

		Set<Coord> set = new HashSet<>();

		for(int i = 0; i<8; i++){
			Integer[] row = getRow(i);
			List<Integer> list = Arrays.asList(row);

			//If the row has the number, and it has more than one copy of it,
			//the row is invalid. We do this by making sure the index of a number
			//going one way is the same as the index coming from the other way.
			for(int val = 1; val<10; val++){

				//Do we have a duplicate?
				if(list.contains(val) && (list.lastIndexOf(val) != list.indexOf(val))){

					//We do. So go through and find all traces of that number and store it.
					for(int index = 0; index<9; index++){

						//If we have the value, store it.
						if(list.get(index)!=null && list.get(index)==val){
							set.add(new Coord(index,i));
						}
					}

				}
			}

		}
		return set;
	}

	private Set<Coord> verifyColumns() {

		Set<Coord> set = new HashSet<>();

		for(int i = 0; i<8; i++){
			Integer[] col = getColumn(i);
			List<Integer> list = Arrays.asList(col);

			//If the col has the number, and it has more than one copy of it,
			//the col is invalid. We do this by making sure the index of a number
			//going one way is the same as the index coming from the other way.
			for(int val = 1; val<10; val++){

				if(list.contains(val) && (list.lastIndexOf(val) != list.indexOf(val))){

					//We do. So go through and find all traces of that number and store it.
					for(int index = 0; index<9; index++){

						//If we have the value, store it.
						if(list.get(index)!=null && list.get(index)==val){
							set.add(new Coord(i,index));
						}
					}

				}
			}

		}
		return set;
	}

	/**
	 * Returns the row at the specified index, 0 to 8.
	 * @param index
	 * @return the row
	 */
	protected Integer[] getRow(int index){

		Integer[] row = new Integer[9];

		for(int i = 0; i< 9; i++){
			row[i] = board[index][i];
		}

		return row;
	}

	/**
	 * Returns the column at the specified index, 0 to 8.
	 * @param index
	 * @return the column
	 */
	protected Integer[] getColumn(int index){

		Integer[] column = new Integer[9];

		for(int i = 0; i< 9; i++){
			column[i] = board[i][index];
		}

		return column;
	}

	/**
	 * Returns the two dimensional array containing the box of values
	 * containing the value at index x,y, where the indexes can vary
	 * from 0 to 8.
	 * @param x
	 * @param y
	 * @return the box
	 */
	protected Integer[][] getBox(int x, int y){

		Integer[][] box = new Integer[3][3];

		/*
		 * x|o|o
		 * o|o|o
		 * o|o|o
		 */
		int offsetX;
		int offsetY;

		if(x<3 && y<3){
			offsetX = 0;
			offsetY = 0;
		}

		/*
		 * o|x|o
		 * o|o|o
		 * o|o|o
		 */
		else if(x>2 && x<6 && y<3){
			offsetX = 3;
			offsetY = 0;
		}

		/*
		 * o|o|x
		 * o|o|o
		 * o|o|o
		 */
		else if(x>5 && y<3){
			offsetX = 6;
			offsetY = 0;
		}
		/*
		 * o|o|o
		 * x|o|o
		 * o|o|o
		 */
		else if(x<3 && y>2 && y<6){
			offsetX = 0;
			offsetY = 3;
		}
		/*
		 * o|o|o
		 * o|x|o
		 * o|o|o
		 */
		else if(x>2 && x<6 &&  y>2 && y<6){
			offsetX = 3;
			offsetY = 3;
		}
		/*
		 * o|o|o
		 * o|o|x
		 * o|o|o
		 */
		else if(x>5 &&  y>2 && y<6){
			offsetX = 6;
			offsetY = 3;
		}
		/*
		 * o|o|o
		 * o|o|o
		 * x|o|o
		 */
		else if(x<3 && y>5){
			offsetX = 0;
			offsetY = 6;
		}
		/*
		 * o|o|o
		 * o|o|o
		 * o|x|o
		 */
		else if(x>2 && x<6 && y>5){
			offsetX = 3;
			offsetY = 6;
		}
		/*
		 * o|o|o
		 * o|o|o
		 * o|o|x
		 */
		else if(x>5 && y>5){
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
	protected void populateBox(Integer[][] box, int offsetX, int offsetY) {

		if(offsetX%3!=0 || offsetY%3!=0 || offsetX>8 || offsetX<0 || offsetY>8 || offsetY<0){
			throw new IllegalArgumentException("Illegal offsets: X:"+ offsetX + " Y: "+ offsetY);
		}

		for(int i = 0; i<3; i++){
			for(int j = 0; j<3; j++){
				box[i][j] = board[i+offsetY][j+offsetX];
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
			if((i)%3==0){
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

	protected static Integer[][] deepCopy(Integer[][] board){
		Integer[][] output = new Integer[9][9];

		for(int i = 0; i<9; i++){
			for(int j = 0; j<9; j++){
				output[i][j] = board[i][j];
			}
		}

		return output;
	}

	protected static boolean[][] deepCopy(boolean[][] board){
		boolean[][] output = new boolean[9][9];

		for(int i = 0; i<9; i++){
			for(int j = 0; j<9; j++){
				output[i][j] = board[i][j];
			}
		}

		return output;
	}

	public Board makeCopy(){

		Integer[][] newBoard = deepCopy(board);
		boolean[][] editBoard = deepCopy(editable);

		return new Board(newBoard, editBoard);

	}

	public int getSpacesLeft() {
		int count = 0;

		for(int i = 0; i<9; i++){
			for(int j = 0; j<9; j++){

				if(getValue(i,j)==null){
					count++;
				}
			}
		}
		return count;

	}

	public String getEditableBoxAsString() {

		StringBuilder builder = new StringBuilder();

		for(int i = 0; i<9; i++){
			if(i!=0){
				builder.append("\n");
			}
			if((i)%3==0){
				builder.append("\n");
			}

			for(int j = 0; j<9; j++){

				if(j%3==0){
					builder.append("   ");
				}

				builder.append("{"+editable[i][j]+"}");

				if(j!=8 && j!=2 && j!=5){
					builder.append(",");
				}
			}

		}

		return builder.toString();
	}

	@Override
	public int hashCode(){

		return Arrays.deepHashCode(board)*2 + Arrays.deepHashCode(backupBoard)*3 + Arrays.deepHashCode(editable)*5;

	}

	@Override
	public boolean equals(Object obj){

		if (this.getClass()!= obj.getClass()){
			return false;
		}

		if(this.hashCode()!=obj.hashCode()){
			//			return false;
		}

		Board otherBoard = (Board) obj;

		//Check contents
		if(this.toString().equals(otherBoard.toString()) &&
				this.getEditableBoxAsString().equals(otherBoard.getEditableBoxAsString())
				&& this.time == otherBoard.getTime()) {
			return true;
		}

		return false;

	}

	public boolean isEditable(int x, int y) {
		return editable[y][x];

	}

	public Set<Coord> findInvalids() {

		Set<Coord> set = new HashSet<>();

		set.addAll(verifyColumns());
		set.addAll(verifyRows());
		set.addAll(verifyBoxes());
		return set;
	}
	
	public void setTime(int i){
		this.time = i;
	}
	
	public int getTime(){
		return time;
	}
	
}
