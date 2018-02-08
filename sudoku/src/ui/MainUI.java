package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import sudokuBoard.Board;

public class MainUI {

	@FXML
	private AnchorPane root;
	
    @FXML
    private TextArea box00;

    @FXML
    private TextArea box01;

    @FXML
    private TextArea box02;

    @FXML
    private TextArea box10;

    @FXML
    private TextArea box11;

    @FXML
    private TextArea box12;

    @FXML
    private TextArea box20;

    @FXML
    private TextArea box21;

    @FXML
    private TextArea box22;

    @FXML
    private TextArea box03;

    @FXML
    private TextArea box13;

    @FXML
    private TextArea box23;

    @FXML
    private TextArea box14;

    @FXML
    private TextArea box04;

    @FXML
    private TextArea box24;

    @FXML
    private TextArea box05;

    @FXML
    private TextArea box15;

    @FXML
    private TextArea box25;

    @FXML
    private TextArea box16;

    @FXML
    private TextArea box26;

    @FXML
    private TextArea box07;

    @FXML
    private TextArea box17;

    @FXML
    private TextArea box27;

    @FXML
    private TextArea box08;

    @FXML
    private TextArea box18;

    @FXML
    private TextArea box28;

    @FXML
    private TextArea box06;

    @FXML
    private TextArea box30;

    @FXML
    private TextArea box31;

    @FXML
    private TextArea box32;

    @FXML
    private TextArea box40;

    @FXML
    private TextArea box41;

    @FXML
    private TextArea box42;

    @FXML
    private TextArea box50;

    @FXML
    private TextArea box51;

    @FXML
    private TextArea box52;

    @FXML
    private TextArea box33;

    @FXML
    private TextArea box34;

    @FXML
    private TextArea box43;

    @FXML
    private TextArea box44;

    @FXML
    private TextArea box35;

    @FXML
    private TextArea box45;

    @FXML
    private TextArea box53;

    @FXML
    private TextArea box54;

    @FXML
    private TextArea box55;

    @FXML
    private TextArea box36;

    @FXML
    private TextArea box37;

    @FXML
    private TextArea box38;

    @FXML
    private TextArea box46;

    @FXML
    private TextArea box48;

    @FXML
    private TextArea box47;

    @FXML
    private TextArea box56;

    @FXML
    private TextArea box57;

    @FXML
    private TextArea box58;

    @FXML
    private TextArea box60;

    @FXML
    private TextArea box61;

    @FXML
    private TextArea box62;

    @FXML
    private TextArea box70;

    @FXML
    private TextArea box71;

    @FXML
    private TextArea box72;

    @FXML
    private TextArea box81;

    @FXML
    private TextArea box82;

    @FXML
    private TextArea box80;

    @FXML
    private TextArea box63;

    @FXML
    private TextArea box64;

    @FXML
    private TextArea box73;

    @FXML
    private TextArea box74;

    @FXML
    private TextArea box83;

    @FXML
    private TextArea box84;

    @FXML
    private TextArea box65;

    @FXML
    private TextArea box75;

    @FXML
    private TextArea box85;

    @FXML
    private TextArea box66;

    @FXML
    private TextArea box76;

    @FXML
    private TextArea box86;

    @FXML
    private TextArea box67;

    @FXML
    private TextArea box77;

    @FXML
    private TextArea box87;

    @FXML
    private TextArea box68;

    @FXML
    private TextArea box78;

    @FXML
    private TextArea box88;

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

    public void loadData(Board board){
    	
    	for(int y = 0; y<9; y++){
    		for(int x = 0; x<9; x++){
    			TextArea area = (TextArea) root.lookup("box"+y+x);
    			area.setText(board.getValue(x, y).toString());
    		}
    	}
    	
    }
    
}
