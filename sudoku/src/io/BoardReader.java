package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sudokuBoard.Board;

public class BoardReader {

	private File file;

	public BoardReader(File file) {
		this.file = file;
	}

	public Board read() {

		Integer[][] data = new Integer[9][9];
		boolean[][] edit = new boolean[9][9];

		Pattern p = Pattern.compile(".*?..*?..*?..*?.(.).*?.."
				+ "*?..*?..*?(.).*?..*?..*?..*?(.).*?..*?.."
				+ "*?..*?..*?..*?(.).*?..*?..*?..*?(.).*?.."
				+ "*?..*?..*?(.).*?..*?..*?..*?..*?..*?(.)."
				+ "*?..*?..*?..*?(.).*?..*?..*?..*?(.)\\}");
		
		Pattern p2 = Pattern.compile(".*?(true|false).*?(true|false).*?(true|false).*"
				+ "?(true|false).*?(true|false).*?(true|false).*?(true|false).*"
				+ "?(true|false).*?(true|false).*",Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

		try(Scanner scanner = new Scanner(file);){
			int j = 0;

			while(scanner.hasNextLine() && j<9){
				String line = scanner.nextLine();

				Matcher m = p.matcher(line);
				if(m.matches()){

					for(int i = 1; i < 10; i++){
						String str = m.group(i);
						if(str.trim().isEmpty()){
							data[j][i-1] = null;
						}
						else{
							data[j][i-1] = Integer.valueOf(m.group(i));
						}
					}
					j++;
				}
			}

			scanner.nextLine();
			scanner.nextLine();
			
			j=0;
			
			while(scanner.hasNextLine() && j<9){
				String line = scanner.nextLine();

				Matcher m2 = p2.matcher(line);
				if(m2.matches()){
					for(int i = 1; i < 10; i++){
						edit[j][i-1] = Boolean.valueOf(m2.group(i));
					}
					j++;
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("The passed in file does not exist!");
		}


		return new Board(data, edit);
	}

	public void setFile(File file) {
		this.file = file;

	}

}
