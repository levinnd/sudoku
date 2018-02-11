package io;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import sudokuBoard.Board;

public class TestWriter {

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
	public void testWriter() throws IOException{
		
		Board board = new Board(puzzle);
		Board board2 = new Board(solution);
		
		File file = new File("./test/data/board1.txt");
		File file2 = new File("./test/data/board2.txt");
		
		BoardWriter writer = new BoardWriter(file);
		writer.writeBoard(board, false);
		
		writer.setFile(file2);
		writer.writeBoard(board2, false);
	}
	
}
