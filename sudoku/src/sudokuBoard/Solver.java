package sudokuBoard;

public class Solver {
	
	private Board solvedBoard;
	
	public boolean solve(Board board, int layer){
		
		layer++;
		
		if(layer==38){
			
			System.out.println("Attempt: \n");
			System.out.println(board.toString());
		}
		else if(!board.hasSpace()){
			
			solvedBoard = board;
			return true;
		}
		else{
			
			System.out.println("Number of spaces left: "+ board.getSpacesLeft()+ " layer: "+ layer);
			
			//Go through all the x indices.
			for(int x = 0; x<9; x++)
			{
				//Go through all the y indices
				for(int y = 0; y<9; y++){
					
					if(board.getValue(x, y)!=null){
						continue;
					}
					
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
			
		}
		return false;
	}
	
	public Board getSolvedBoard(){
		return solvedBoard;
	}
}
