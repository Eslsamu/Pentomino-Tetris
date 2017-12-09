

import GameLogic.PetrisGame;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception{
                //create an instance of GameCycle
        PetrisGame game = new PetrisGame(true);
        game.runGame();
        
		primaryStage.setTitle("Petris");
                //get the scene from GameCycle
		primaryStage.setScene(game.getScene());//TODO
		
		primaryStage.setMaxWidth(800);
		primaryStage.setMinWidth(600);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                closeGame();
            }
        });        
	}
	
	public static void main(String[]args) {
            Application.launch(args);
          
	}
	
	private void closeGame() {
		Platform.exit();
	}
}