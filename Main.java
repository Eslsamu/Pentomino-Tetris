import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception{
                //create an instance of GameCycle
        PetrisGame game = new PetrisGame();
        
        
        
		primaryStage.setTitle("Petris");
                //get the scene from GameCycle
		primaryStage.setScene(game.getView());//TODO
		game.startGame();
		primaryStage.setMaxWidth(800);
                primaryStage.setMinWidth(600);
                primaryStage.show();
	}
	
	public static void main(String[]args) {
            Application.launch(args);
          
	}
}