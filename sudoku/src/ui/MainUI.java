package ui;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.BoardReader;
import io.BoardWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import solvers.Solver;
import sudokuBoard.Board;
import sudokuBoard.Coord;

public class MainUI {

	@FXML
	private AnchorPane root;

	@FXML
	private Button checkButton;

	@FXML
	private Button hintButton;

	@FXML
	private Button NewButton;

	@FXML
	private Button saveButton;

	@FXML
	private Button loadButton;

	@FXML
	private Button startButton;

	@FXML
	private Button pauseButton;

	private Stage stage;

	private double time = 0.0;

	public void initialize(){

		disableTheBoard();
		startButton.setDisable(true);
		pauseButton.setDisable(true);
		hintButton.setDisable(true);
		checkButton.setDisable(true);
	}

	private void disableTheBoard() {

		for(int yy = 0; yy<9; yy++){
			for(int xx = 0; xx<9; xx++){

				TextArea area2 = (TextArea) root.lookup("#box"+yy+xx);
				area2.setDisable(true);
			}
		}

	}

	private void enableTheBoard() {

		for(int yy = 0; yy<9; yy++){
			for(int xx = 0; xx<9; xx++){

				TextArea area2 = (TextArea) root.lookup("#box"+yy+xx);
				area2.setDisable(false);
			}
		}

	}

	@FXML
	void checkMyValuesButtonPressed(ActionEvent event) {

		Board board = getBoardFromUI();
		Set<Coord> vals = board.findInvalids();

		if(vals.isEmpty() && board.getSpacesLeft()==0){
			startButton.setDisable(true);
			hintButton.setDisable(true);
			pauseButton.setDisable(true);
			checkButton.setDisable(true);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Sudoku Completed!");
			alert.setHeaderText("The sudoku has been completed.");
			alert.setContentText("You have successfully completed the sudoku. Your time was "+ time +" seconds");
			alert.showAndWait();
		}
		else{
			for(Coord val : vals){
				TextArea area = (TextArea) root.lookup("#box"+val.getY()+val.getX());
				area.setStyle( "-fx-background-color: red" );
			}
		}
	}

	@FXML
	void startButtonPressed(ActionEvent event) {
		enableTheBoard();
		hintButton.setDisable(false);
		checkButton.setDisable(false);
		startButton.setDisable(true);
		pauseButton.setDisable(false);
	}

	@FXML
	void pauseButtonPressed(ActionEvent event) {
		disableTheBoard();
		hintButton.setDisable(true);
		checkButton.setDisable(true);
		startButton.setDisable(false);
		pauseButton.setDisable(true);
	}

	void solve(ActionEvent event) {
		Board board = getBoardFromUI();

		Solver solver = new Solver();

		boolean result = solver.solve(board);

		//If we solve it, load it.
		if(result){
			loadData(board);
		}
		else{ //Otherwise, find out what's wrong and tell the user.
			checkMyValuesButtonPressed(null);
		}

	}

	@FXML
	void chooseANewPuzzlePressed(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Puzzle file");
		fileChooser.setInitialDirectory(new File("./puzzles/"));
		File file = fileChooser.showOpenDialog(stage);

		if(file!=null && file.exists()){
			hintButton.setDisable(true);
			checkButton.setDisable(true);
			pauseButton.setDisable(true);

		}
		else{
			BoardReader reader = new BoardReader(file);
			Board board = reader.read();
			loadData(board);

			startButton.setDisable(false);
		}
	}

	@FXML
	void hintButtonPressed(ActionEvent event) {

	}

	@FXML
	void loadButtonPressed(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Session file");
		fileChooser.setInitialDirectory(new File("./session/"));
		File file = fileChooser.showOpenDialog(stage);

		if(file!=null && file.exists()){
			hintButton.setDisable(true);
			checkButton.setDisable(true);
			pauseButton.setDisable(true);

		}
		else{

			BoardReader reader = new BoardReader(file);
			Board board = reader.read();
			loadData(board);

			startButton.setDisable(false);
		}
	}

	@FXML
	void saveButtonPressed(ActionEvent event) {

		disableTheBoard();

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Session file");
		fileChooser.setInitialDirectory(new File("./session/"));
		File file = fileChooser.showSaveDialog(stage);

		if(file==null){
			System.out.println("File was null");
			return;
		}

		BoardWriter writer = new BoardWriter(file);
		Board board = getBoardFromUI();

		try {
			writer.writeBoard(board, false);
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error: Could Not Write File");
			alert.setHeaderText("The session file could not be saved at "+ file.toString()+".");
			alert.setContentText("The session file could not be saved at "+ file.toString()+". It already exists and is write-protected.");
			alert.showAndWait();
		}

	}

	/**
	 * Constructs the board from the UI.
	 * @return
	 */
	private Board getBoardFromUI() {

		Integer[][] data = new Integer[9][9];
		boolean[][] edit = new boolean[9][9];

		for(int y = 0; y<9; y++){
			for(int x = 0; x<9; x++){

				TextArea area = (TextArea) root.lookup("#box"+y+x);

				String val = area.getText();

				if(val==null || val.isEmpty()){
					data[y][x] = null;
				}
				else{
					data[y][x] = Integer.valueOf(val);
				}

				edit[y][x] = area.isEditable();
			}
		}

		return new Board(data, edit);
	}

	public void initialize(Stage stage){
		this.stage = stage;
		setupFormatters();
	}

	public void setupFormatters(){

		Pattern p = Pattern.compile("^(?:[1-9])$|");

		for(int y = 0; y<9; y++){
			for(int x = 0; x<9; x++){

				TextArea area = (TextArea) root.lookup("#box"+y+x);
				area.setTextFormatter(new TextFormatter<String>((Change change) -> {

					String newText = change.getControlNewText();
					Matcher m = p.matcher(newText);

					if (!m.matches()) {
						return null ;
					} else {
						return change ;
					}
				}));

				//Set up listener so if one area is clicked, grab all of them and set their border to grey.
				area.setOnMouseClicked(e->{

					for(int yy = 0; yy<9; yy++){
						for(int xx = 0; xx<9; xx++){

							TextArea area2 = (TextArea) root.lookup("#box"+yy+xx);
							area2.setStyle( "-fx-background-color: grey" );
						}
					}

				});
			}
		}
	}

	public void loadData(Board board){

		for(int y = 0; y<9; y++){
			for(int x = 0; x<9; x++){

				//I'm using the fx lookup to find the box I want.
				//Otherwise this would be a nightmare.
				TextArea area = (TextArea) root.lookup("#box"+y+x);
				Integer val = board.getValue(x, y);
				if(val==null){
					area.clear();
				}
				else{
					area.setText(board.getValue(x, y)+"");
				}

				//Make sure non editable stuff is not editable
				area.setEditable(board.isEditable(x,y));
				if(board.isEditable(x, y)){
					area.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
				}
				else{
					area.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
				}
			}
		}

	}



}
