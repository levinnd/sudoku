package solvers;

import java.util.ArrayList;
import java.util.List;

import sudokuBoard.Board;

public class MultiSolver extends Solver {

	
	private List<Board> multiList = new ArrayList<>();
	
	/**
	 * This solver is exhaustive, and should be used to solve sudokus.
	 * It will track multiple solutions and print them out.
	 * @param board
	 * @return
	 */
	@Override
	public boolean solve(Board board){
		 solve(board, 0);
		 if(multiList.isEmpty()){
			 return true;
		 }
		 else{
			 return false;
		 }
	}
	
	private boolean solve(Board board, int layer){

		layer++;
		if(DEBUG && layer>=80){
			System.out.println("\n__________________________\n"+board.toString()+"\n");
		}
		
		if(!board.hasSpace()){
			
			System.out.println("______________________\n\nSolution: \n\n");
			System.out.println(board.toString());
			multiList.add(board.makeCopy());
			return false;
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
	
	public Board getSolvedBoard(){
		throw new UnsupportedOperationException("We may have lots of boards");
	}
	
	public List<Board> getAllSolvedBoards(){
		return multiList;
	}
	
}
