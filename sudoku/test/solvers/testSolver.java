package solvers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import solvers.MultiSolver;
import solvers.Solver;
import sudokuBoard.Board;

public class testSolver {

	Integer[][] puzzle;
	Integer[][] solution;
	Integer[][] puzzle2;
	Integer[][] puzzle3;

	@Before
	public void setup(){

		//The "easiest" sudoku puzzle out there supposively
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
		
		//The "Hardest" sudoku puzzle out there supposively.
		puzzle2 = new Integer[][]{

			{   8,null,null,  null,null,null,  null,null,null},
			{null,null,   3,     6,null,null,  null,null,null},
			{null,   7,null,  null,   9,null,     2,null,null},

			{null,   5,null,  null,null,   7,  null,null,null},
			{null,null,null,  null,   4,   5,     7,null,null},
			{null,null,null,     1,null,null,  null,   3,null},

			{null,null,   1,  null,null,null,  null,   6,   8},
			{null,null,   8,     5,null,null,  null,   1,null},
			{null,   9,null,  null,null,null,     4,null,null},

		};
		
		//The "Hardest" sudoku puzzle out there supposively.
		puzzle3 = new Integer[][]{

			{   8,null,null,  null,null,null,  null,null,null},
			{null,null,   3,     6,null,null,  null,null,null},
			{null,   7,null,  null,   9,null,     2,null,null},

			{null,   5,null,  null,null,   7,  null,null,null},
			{null,null,null,  null,   4,null,  null,null,null},
			{null,null,null,     1,null,null,  null,   3,null},

			{null,null,   1,  null,null,null,  null,null,   8},
			{null,null,null,     5,null,null,  null,   1,null},
			{null,   9,null,  null,null,null,     4,null,null},

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
		
		assertTrue(solver.solve(board));
		
		System.out.println("Solution\n");
		System.out.println(solved.toString());
		
		assertEquals(solved.toString(), solver.getSolvedBoard().toString());
		
	}
	
	@Test
	public void testGetSpaceLocation(){
		Solver solver = new Solver();
		
		Board board = new Board(puzzle);
		
		int[] xy = solver.findBlankValue(board);
		assertArrayEquals(new int[]{0,0},xy);
		
		board.placeNumber(0, 0, 4);
		board.placeNumber(1, 0, 3);
		board.placeNumber(2, 0, 5);
		
		xy = solver.findBlankValue(board);
		assertArrayEquals(new int[]{5,0},xy);
	}
	
	@Test
	public void testHardestSolve(){
		
		Solver solver = new Solver();
		
		Board board = new Board(puzzle2);
	
	//	assertTrue(solved.validate());
		
		System.out.println("Puzzle\n");
		System.out.println(board.toString()+"\n\n");
		
		assertTrue(solver.solve(board));
		assertTrue(solver.getSolvedBoard().validate());
		
		System.out.println("Solution\n");
		System.out.println(solver.getSolvedBoard().toString());
		
	//	assertEquals(solved.toString(), solver.getSolvedBoard().toString());
		
	}
	
	@Test
	public void testHardestSolve2(){
		
		Solver solver = new Solver();
		
		Board board = new Board(puzzle3);
	
	//	assertTrue(solved.validate());
		
		System.out.println("Puzzle\n");
		System.out.println(board.toString()+"\n\n");
		
		assertTrue(solver.solve(board));
		assertTrue(solver.getSolvedBoard().validate());
		
		System.out.println("Solution\n");
		System.out.println(solver.getSolvedBoard().toString());
		
	//	assertEquals(solved.toString(), solver.getSolvedBoard().toString());
		
	}
	
	
	@Test
	public void testMultiSolverEasy(){
		
		MultiSolver solver = new MultiSolver();
		
		Board board = new Board(puzzle);
		
		System.out.println("Puzzle\n");
		System.out.println(board.toString()+"\n\n");
		
		solver.solve(board);
		assertTrue(!solver.getAllSolvedBoards().isEmpty());
		
		System.out.println("Solutions\n");
		
		for(Board sol : solver.getAllSolvedBoards()){
			System.out.println("______________________\n"+sol.toString());
		}
		
	}
	
	@Test
	public void testHardestMultiSolve(){
		
		MultiSolver solver = new MultiSolver();
		
		Board board = new Board(puzzle2);
		
		System.out.println("Puzzle\n");
		System.out.println(board.toString()+"\n\n");
		
		solver.solve(board);
		assertTrue(!solver.getAllSolvedBoards().isEmpty());
		
		System.out.println("Solutions\n");
		
		for(Board sol : solver.getAllSolvedBoards()){
			System.out.println("______________________\n"+sol.toString());
		}
		
	}
	
	@Test
	public void testHardestMultiSolve2(){
		
		MultiSolver solver = new MultiSolver();
		
		Board board = new Board(puzzle3);
		
		System.out.println("Puzzle\n");
		System.out.println(board.toString()+"\n\n");
		
		solver.solve(board);
		assertTrue(!solver.getAllSolvedBoards().isEmpty());
		
		System.out.println("Solutions\n");
		
		for(Board sol : solver.getAllSolvedBoards()){
			System.out.println("______________________\n"+sol.toString());
		}
		
	}
}
