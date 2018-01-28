package sudokuBoard;

public class Solver {

	private Board solvedBoard;

	public boolean solve(Board board){

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

					if(solve(testBoard)){
						return true;
					}


				}
			}

			//If I hit this point, that means that no number can go into x,y. Therefore, a mistake was made.
			return false;

		}

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
