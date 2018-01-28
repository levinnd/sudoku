package sudokuBoard;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sudokuBoard.Board;

public class testBoard {

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
	public void testPopulateBox(){

		Board board = new Board(puzzle);

		Integer[][] box = new Integer[3][3];

		board.populateBox(box, 0, 0);

		assertArrayEquals(new Integer[][]{
			{null,null,null},
			{6,      8,null},
			{1,      9,null},
		}
		,box);

		board.populateBox(box, 3, 0);

		assertArrayEquals(new Integer[][]{
			{2,      6,null},
			{null,   7,null},
			{null,null,   4},
		}
		,box);

		board.populateBox(box, 3, 6);

		assertArrayEquals(new Integer[][]{
			{3,   null,null},
			{null,   5,null},
			{null,   1,   8},
		}
		,box);

		board.populateBox(box, 6, 6);

		assertArrayEquals(new Integer[][]{
			{null,   7,    4},
			{null,   3,    6},
			{null,null, null},
		}
		,box);

	}

	@Test
	public void testCopy(){

		Integer[][] copy = Board.deepCopy(puzzle);
		assertArrayEquals(puzzle, copy);
		assertTrue(copy!=puzzle);
	}

	@Test
	public void testHasSpace(){

		Board board = new Board(puzzle);
		assertTrue(board.hasSpace());

		board = new Board(solution);
		assertTrue(!board.hasSpace());
	}

	@Test
	public void testGetRow(){
		
		Board board = new Board(puzzle);
		assertArrayEquals(board.getRow(0),new Integer[]{null,null,null,      2,   6,null,      7,null,   1});
		assertArrayEquals(board.getRow(3),new Integer[]{8,      2,null,      1,null,null,   null,   4,null});
		assertArrayEquals(board.getRow(8),new Integer[]{null,null,   3,   null,   1,   8,   null,null,null});
	}
	
	@Test
	public void testGetColumn(){
		
		Board board = new Board(puzzle);
		assertArrayEquals(board.getColumn(0),new Integer[]{null,   6,   1,   8,null,null,null,null,null});
		assertArrayEquals(board.getColumn(3),new Integer[]{   2,null,null,   1,   6,null,   3,null,null});
		assertArrayEquals(board.getColumn(8),new Integer[]{   1,null,null,null,null,   8,   4,   6,null});
	}
	
	@Test
	public void testGetBox(){
		Board board = new Board(puzzle);
		assertArrayEquals(new Integer[][]{
			{null,null,null},
			{6,      8,null},
			{1,      9,null},
		},board.getBox(0, 0));

		assertArrayEquals(new Integer[][]{
			{2,      6,null},
			{null,   7,null},
			{null,null,   4},
		}
		,board.getBox(3, 0));

		assertArrayEquals(new Integer[][]{
			{3,   null,null},
			{null,   5,null},
			{null,   1,   8},
		}
		,board.getBox(3, 6));

		assertArrayEquals(new Integer[][]{
			{null,   7,    4},
			{null,   3,    6},
			{null,null, null},
		}
		,board.getBox(6,6));
	}
	
	@Test
	public void testPlaceNumber(){
		Board board = new Board(puzzle);
		
		//Place a number that is in a row
		assertTrue(!board.placeNumber(0, 0, 1));
		assertEquals(null, board.getValue(0, 0));
		
		//Place a number that is in a column
		assertTrue(!board.placeNumber(0, 0, 8));
		assertEquals(null, board.getValue(0, 0));
		
		//Place a good number
		assertTrue(board.placeNumber(0, 0, 4));
		assertTrue(4==board.getValue(0, 0));
		
		//Place another good number
		assertTrue(board.placeNumber(1, 0, 3));
		assertTrue(3==board.getValue(1, 0));
		
		//Place a number that's bad for the box.
		assertTrue(!board.placeNumber(8, 8, 7));
		assertTrue(null==board.getValue(8, 8));
		
		//Erase number
		assertTrue(board.placeNumber(0, 0, null));
		assertEquals(null,board.getValue(0, 0));
		
		//If this fails, you can rewrite the board right now. 
		assertTrue(!board.placeNumber(0, 1, 2));
		assertTrue(6==board.getValue(0, 1));
	}
}
