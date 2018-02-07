package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import sudokuBoard.Board;

public class BoardWriter {
	
	private File file;
	
	public BoardWriter(File file){
		this.file = file;
	}
	
	public void writeBoard(Board board){
		
		try(PrintWriter writer = new PrintWriter(file);) {
		
			writer.write(board.toString());
			writer.write("\n&&\n");
			
			writer.write(board.getEditableBoxAsString());
			
			System.out.println("Board written to "+ file.getAbsolutePath().toString());
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new IllegalStateException("This should never happen with PrintWriter");
		}
		
	}
	
}
