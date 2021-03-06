package ui;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Stopwatch;

import io.BoardReader;
import io.BoardWriter;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

	@FXML
	private Button solveButton;
	
	@FXML
	private Label timeLabel;

	private Stage stage;

	private Stopwatch watch;

	private boolean updateTimer = true;

	private int initialTime = 0;

	public void initialize(Stage stage){
		this.stage = stage;
		setupFormatters();

		disableTheBoard();
		startButton.setDisable(true);
		pauseButton.setDisable(true);
		hintButton.setDisable(true);
		checkButton.setDisable(true);
		solveButton.setDisable(true);

		watch = Stopwatch.createUnstarted();
		updateTimer = true;
		
		timeLabel.setText("0");


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
			stopTimer();

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Sudoku Completed!");
			alert.setHeaderText("The sudoku has been completed.");
			alert.setContentText("You have successfully completed the sudoku. Your time was "+ watch.elapsed(TimeUnit.SECONDS) +" seconds");
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

		startTimer();

		enableTheBoard();
		hintButton.setDisable(false);
		checkButton.setDisable(false);
		startButton.setDisable(true);
		pauseButton.setDisable(false);
		solveButton.setDisable(false);
	}

	private void startTimer() {
		if(!watch.isRunning()){
			updateTimer = true;
			watch.start();

			Runner runner = new Runner(initialTime );
			runner.start();
		}
	}

	class Runner extends Thread{
		
		int currentTime = 0;
		protected int initialTime;
		
		public Runner(int initialTime){
			this.initialTime = initialTime;
		}
		
		@Override
		public void run() {
			while(watch.isRunning()){
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						if(updateTimer){
							String time;
							
							if(!timeLabel.getText().equals("N/A") && (int)watch.elapsed(TimeUnit.SECONDS)!=currentTime){
								currentTime = (int)watch.elapsed(TimeUnit.SECONDS);
								time = (initialTime  + (int)watch.elapsed(TimeUnit.SECONDS))+"";
								timeLabel.setText(time);
							}
							
						}
					}

				});
			}
		}
	}
	
	@FXML
	void pauseButtonPressed(ActionEvent event) {

		stopTimer();
		disableTheBoard();
		hintButton.setDisable(true);
		checkButton.setDisable(true);
		startButton.setDisable(false);
		pauseButton.setDisable(true);
		solveButton.setDisable(true);
	}

	private void stopTimer() {
		if(watch.isRunning()){
			watch.stop();
			updateTimer = false;
		}
	}

	@FXML
	void solveButtonPressed(ActionEvent event) {
		
		pauseButtonPressed(null);
		
		Board board = getBoardFromUI();

		Solver solver = new Solver();

		boolean result = solver.solve(board);

		//If we solve it, load it.
		if(result){
			loadData(solver.getSolvedBoard());
			stopTimer();
			timeLabel.setText("N/A");
			this.checkMyValuesButtonPressed(null);
			enableTheBoard();
			saveButton.setDisable(true);
			startButton.setDisable(true);
		}
		else{ //Otherwise, tell the user we cannot solve it.
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Unsolveable Puzzle");
			alert.setContentText("The puzzle is unsolveable");
			alert.showAndWait();
		}

	}

	@FXML
	void chooseANewPuzzlePressed(ActionEvent event) {

		pauseButtonPressed(null);
		
		boolean startButtonState = startButton.isDisabled();
		boolean pauseButtonState = pauseButton.isDisabled();
		boolean solveButtonState = solveButton.isDisabled();

		startButton.setDisable(true);
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Puzzle file");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")+"/puzzles/"));
		File file = fileChooser.showOpenDialog(stage);

		if(file!=null && file.exists()){
			hintButton.setDisable(true);
			checkButton.setDisable(true);
			pauseButton.setDisable(true);
			saveButton.setDisable(false);

			BoardReader reader = new BoardReader(file);
			Board board = reader.read();
			loadData(board);

			startButton.setDisable(false);
			watch = Stopwatch.createUnstarted();
			watch.reset();
			timeLabel.setText("0");
			initialTime = 0;

		}
		else{
			startButton.setDisable(startButtonState);
			pauseButton.setDisable(pauseButtonState);
			solveButton.setDisable(solveButtonState);
		}

	}

	@FXML
	void hintButtonPressed(ActionEvent event) {

	}

	@FXML
	void loadButtonPressed(ActionEvent event) {

		pauseButtonPressed(null);
		
		boolean startButtonState = startButton.isDisabled();
		boolean pauseButtonState = pauseButton.isDisabled();
		boolean solveButtonState = solveButton.isDisabled();

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Session file");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")+"/session/"));
		File file = fileChooser.showOpenDialog(stage);

		if(file!=null && file.exists()){

			hintButton.setDisable(true);
			checkButton.setDisable(true);
			pauseButton.setDisable(true);
			saveButton.setDisable(false);

			BoardReader reader = new BoardReader(file);
			Board board = reader.read();
			loadData(board);

			startButton.setDisable(false);

			watch = Stopwatch.createUnstarted();
			watch.reset();

			timeLabel.setText(board.getTime()+"");
			initialTime = board.getTime();
			

		}
		else{
			startButton.setDisable(startButtonState);
			pauseButton.setDisable(pauseButtonState);
			solveButton.setDisable(solveButtonState);
		}
	}

	@FXML
	void saveButtonPressed(ActionEvent event) {

		pauseButtonPressed(null);

		disableTheBoard();

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Session file");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")+"/session/"));
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
 
		Board board = new Board(data, edit);
		if(!timeLabel.getText().equals("N/A")){
			board.setTime(Integer.parseInt(timeLabel.getText()));
		}
		return board;
	}

	public void setupFormatters(){

		Pattern p = Pattern.compile("^(?:[1-9])$|");

		for(int y = 0; y<9; y++){
			for(int x = 0; x<9; x++){

				TextArea area = (TextArea) root.lookup("#box"+y+x);
				area.setStyle("-fx-text-alignment: center");
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
