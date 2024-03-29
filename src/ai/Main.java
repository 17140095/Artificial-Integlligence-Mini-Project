package ai;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application  {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader();
	    //URL url = new URL("file:///C:/Users/LMS/eclipse-Workspace-A/SceneBuilderDemo/src/pk/edu/gift/studentView.fxml");	
		
		URL url = getClass().getResource("MapView.fxml");
		loader.setLocation(url);
		
		BorderPane pane = loader.load();
		Scene scene = new Scene(pane);
		primaryStage.setFullScreen(true);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Scene Builder Demo");
		primaryStage.show();
	}

}
