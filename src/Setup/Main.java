package Setup;

import Menu.MenuView;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.Screen;

public class Main extends Application {
	private static Stage primaryStage;
        
        @Override
	public void start(Stage ps){
                primaryStage = ps;
                            
                MenuView menu = new MenuView();
                Scene scene = new Scene(menu);
                
                primaryStage.setTitle("Petris");
                primaryStage.setScene(scene);
                primaryStage.setWidth(400);
                primaryStage.setHeight(400);
                primaryStage.setResizable(false);
                
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
                
                primaryStage.show();
	}
	
	public static void main(String[]args) {
         Application.launch(args);
	}
    
	public static Stage getStage(){
         return primaryStage;
    }
}