package sampleGame;

import java.util.Scanner;

import sudokuBoard.Board;

public class sample {

	public static void main(String args[]){

		Integer[][] puzzle = new Integer[][]{

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

		/*Integer[][] solution = new Integer[][]{

			{4,3,5,  2,6,9,  7,8,1},
			{6,8,2,  5,7,1,  4,9,3},
			{1,9,7,  8,3,4,  5,6,2},

			{8,2,6,  1,9,5,  3,4,7},
			{3,7,4,  6,8,2,  9,1,5},
			{9,5,1,  7,4,3,  6,2,8},

			{5,1,9,  3,2,6,  8,7,4},
			{2,4,8,  9,5,7,  1,3,6},
			{7,6,3,  4,1,8,  2,5,9},

		};*/

		
		
		try(Scanner scanner = new Scanner(System.in);){

			Board board = new Board(puzzle);
			
			while(board.hasSpace()){
				System.out.println(board.toString());
				System.out.println("Enter the X index (0-8), Y index (0-8), and new value (1-9)");
				System.out.println("Example: 0 3 8\n");

				String[] input = scanner.nextLine().split(" ");
				boolean result = board.placeNumber(
						Integer.valueOf(input[0]),
						Integer.valueOf(input[1]),
						Integer.valueOf(input[2])
						);

				if(result){
					System.out.println("Your move worked. The new board is: ");
				}
				else{
					System.out.println("Your move was illegal. The new board is: ");
				}
			}
			
			System.out.println(board.toString());
			System.out.println("\nYou WIN!");
		}



	}

}
