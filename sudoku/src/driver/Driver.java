
package driver;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.MainUI;

public class Driver extends Application {

	private MainUI controller;

	public static void main(String[] args) {
		Application.launch(Driver.class, args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		FXMLLoader loader;

		URL url = Driver.class.getResource("Driver.class");
		
		if(url.toString().startsWith("jar:")){
			
			loader = new FXMLLoader();

			String relativePath = "/MainUI.fxml";

			URL uri = Driver.class.getResource(relativePath);

			loader.setLocation(uri);
			
		}

		else{	
			
			loader = new FXMLLoader(getClass().getResource("../resources/MainUI.fxml"));

		}

		Parent root = loader.load();   

		controller = loader.getController();

		stage.setTitle("Commit Sudoku");
		stage.setScene(new Scene(root));
		stage.show();
		stage.setMinWidth(stage.getWidth());
		stage.setMinHeight(stage.getHeight());

		controller.initialize(stage);
	}



}

