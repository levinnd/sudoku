package utilities;

import java.util.Random;

import sudokuBoard.Board;

public class Solver {

	private Board solvedBoard;

	private Random rand = new Random();
	
	protected Boolean DEBUG = false;

	/**
	 * This solver is exhaustive, and should be used to solve sudokus.
	 * In the future, I will modify this solver to identify multiple solutions.
	 * @param board
	 * @return
	 */
	public boolean solve(Board board){
		return solve(board, 0);
	}
	
	
	private boolean solve(Board board, int layer){

		layer++;
		if(DEBUG && layer>=80){
			System.out.println("\n__________________________\n"+board.toString()+"\n");
		}
		
		if(!board.hasSpace()){

			solvedBoard = board;
			return true;
		}
		else{

			//I just need to find the first blank value.
			int[] blankValue = findBlankValue(board);

			int x = blankValue[0];
			int y = blankValue[1];
			
			//Make a defensive copy of the board for testing purposes.
			Board testBoard = board.makeCopy();

			//Try putting a number on the selected index.
			for(int val = 1; val <10; val++){

				//If we succeed, see if we can solve it. Otherwise, keep going.
				if(testBoard.placeNumber(x, y, val)){

					if(solve(testBoard, layer)){
						return true;
					}


				}
			}

			//If I hit this point, that means that no number can go into x,y. Therefore, a mistake was made.
			return false;

		}

	}


	
	/**
	 * This solver works by trying random values instead of working exhaustively.
	 * It isn't very efficient, but I intend to use it for board generation.
	 //TODO: NOT DONE YET
	 * @param board
	 * @return
	 */
	private boolean randomSolve(Board board, int layer){

		layer++;
		System.out.println("layer: "+ layer);
		
		if(layer==71){
			solvedBoard = board;
			return true;
		}
		
		if(!board.hasSpace()){

			solvedBoard = board;
			return true;
		}
		else{

			//I just need to find the first blank value.
			int[] blankValue = findRandomBlankValue(board);

			int x = blankValue[0];
			int y = blankValue[1];

			//Make a defensive copy of the board for testing purposes.
			Board testBoard = board.makeCopy();

			//Try putting a number on the selected index.
			for(int val = 1; val<10; val++){

				//If we succeed, see if we can solve it. Otherwise, keep going.
				if(testBoard.placeNumber(x, y, val)){

					if(randomSolve(testBoard, layer)){
						return true;
					}


				}

			}

			//If I hit this point, that means that no number can go into x,y. Therefore, a mistake was made.
			return false;

		}

	}

	private int[] findRandomBlankValue(Board board) {

		int x; int y;

		do{
			x = rand.nextInt(9);
			y = rand.nextInt(9);
		}
		while(board.getValue(x, y)!=null);

		return new int[]{x,y};

	}

	/**
	 * Returns the first blank value it finds in the board. Checks horizontally.
	 * @param board
	 * @return array of the x y coordinates. The first value is the x coordinate the second value is the y coordinate.
	 */
	protected int[] findBlankValue(Board board) {

		for(int y = 0; y<9; y++){
			for(int  x = 0; x<9; x++){
				if(board.getValue(x,y)==null){
					return new int[]{x,y};
				}
			}
		}
		throw new IllegalArgumentException("Passed in a full board");
	}

	public Board getSolvedBoard(){
		return solvedBoard;
	}
}
