
package driver;

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
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/MainUI.fxml"));
		Parent root = loader.load();
		controller = loader.getController();
		
		stage.setTitle("FXML Welcome");
		stage.setScene(new Scene(root));
		stage.show();
		stage.sizeToScene();
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
		
		controller.initialize(stage);
	}

	

}

