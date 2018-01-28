package sudokuBoard;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class testSolver {

	Integer[][] puzzle;
	Integer[][] solution;

	@Before
	public void setup(){

		puzzle = new Integer[][]{

			{null,null,null,      2,   6,null,      7,null,   1},
			{6,      8,null,   null,   7,null,   null,   9,null},
			{1,      9,null,   null,null,    4,     5,null,null},

			{8,      2,null,      1,null,null,   null,   4,null},
			{null,null,   4,      6,null,   2,      9,null,null},
			{null,   5,null,   null,null,   3,   null,   2,   8},

			{null,null,   9,      3,null,null,   null,   7,   4},
			{null,   4,null,   null,   5,null,   null,   3,   6},
			{null,null,   3,   null,   1,   8,   null,null,null}

		};

		solution = new Integer[][]{

			{4,3,5,  2,6,9,  7,8,1},
			{6,8,2,  5,7,1,  4,9,3},
			{1,9,7,  8,3,4,  5,6,2},

			{8,2,6,  1,9,5,  3,4,7},
			{3,7,4,  6,8,2,  9,1,5},
			{9,5,1,  7,4,3,  6,2,8},

			{5,1,9,  3,2,6,  8,7,4},
			{2,4,8,  9,5,7,  1,3,6},
			{7,6,3,  4,1,8,  2,5,9},

		};
	}
	
	@Test
	public void testSolver(){
		
		Solver solver = new Solver();
		
		Board board = new Board(puzzle);
		Board solved = new Board(solution);
	
		assertTrue(solved.validate());
		
		System.out.println("Puzzle\n");
		System.out.println(board.toString()+"\n\n");
		
		assertTrue(solver.solve(board,0));
		
		System.out.println("Solution\n");
		System.out.println(solved.toString());
		
		assertEquals(solution, solver.getSolvedBoard());
		
	}
	
	
}
