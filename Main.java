package petris;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
	private static Stage primaryStage;
        
        @Override
	public void start(Stage primaryStage) throws Exception{
                this.primaryStage = primaryStage;
                //create an instance of the method that creates the starting scene
                Menu menu = new Menu();
                
		primaryStage.setTitle("Petris");
		primaryStage.setScene(menu.drawStartMenu());
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
