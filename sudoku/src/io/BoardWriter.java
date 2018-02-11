package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import sudokuBoard.Board;

public class BoardWriter {
	
	private File file;
	
	public BoardWriter(File file){
		this.file = file;
	}
	
	public void setFile(File file){
		this.file = file;
	}
	
	public void writeBoard(Board board, boolean protect) throws IOException{
		
		//Check to see if the file is write protected. If it is, throw an exception.
		if(file.exists()){
			try(Scanner scanner = new Scanner(file);){
				
				String line = scanner.nextLine();
				if(line.contains("protected")){
					throw new IOException("The file is write protected");
				}
				else{
					System.out.println("Overwriting file at "+ file);
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new IllegalArgumentException("The passed in file does not exist!");
			}
			
		}
		
		//I delete it outside of the try statement so I don't have to deal with
		//scanner's file lock.
		if(file.exists()){
			file.delete();
		}
		
		try(PrintWriter writer = new PrintWriter(file);) {
		
			if(protect){
				writer.write("protect");
			}
			
			writer.write(board.toString());
			writer.write("\n&&\n");
			
			writer.write(board.getEditableBoxAsString());
			
			System.out.println("Board written to "+ file.getAbsolutePath().toString());
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new IllegalStateException("The path to the file was missing");
		}
		
	}
	
}
