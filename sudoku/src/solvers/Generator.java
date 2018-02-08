package solvers;

import java.util.Random;

import sudokuBoard.Board;

public class Generator {

	private Board board = new Board();
	private Board solution = new Board();
	Random rand = new Random();
	
	public Generator(){
		
		populateSolution();
		
	};
	
	private void populateSolution(){
		
		Solver solver = new Solver();
		solver.solve(board);
		
		solution = solver.getSolvedBoard().makeCopy();
		
	}
	
	public Board getBoard(){
		return board;
	}
	
	protected Board getSolution(){
		return solution;
	}
}
