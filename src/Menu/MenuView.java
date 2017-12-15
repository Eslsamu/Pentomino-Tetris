package Menu;

import Setup.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * This class creates the starting menu with options to start the game, go to presentation menu, see high scores and exit.
 * @author Jordan, Basia, Stijn, Yvar, Sammuel, Blazej
 */
public class MenuView extends GridPane{
	
        /**
         * Constructor creates the menu with buttons: play, presentation menu, high score, exit.
         */
	public MenuView() {
		
            Button start = new Button("Play");
	    start.setMinSize(150, 50);
	    start.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
	    
	    Button presentation = new Button("Presentation menu");
	    presentation.setMinSize(150, 50);
	    presentation.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
	    
	    Button highscore = new Button("High scores");
	    highscore.setMinSize(150, 50);
	    highscore.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
	    
	    Button exit = new Button("Exit");
	    exit.setMinSize(150, 50);
	    exit.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
	    
	    setAlignment(Pos.CENTER);
	    setHalignment(start, HPos.CENTER);
	    setHalignment(highscore, HPos.CENTER);
	    setHalignment(exit, HPos.CENTER);
	    setVgap(10);
	   
	    start.setOnAction(new EventHandler<ActionEvent>(){
	        @Override
                /**
                * If button is clicked it open a window PlayerNameView that asks for player's name before starting the game. 
                * @param e ActionEvent, if the button is clicked it will trigger the code
                */
                public void handle(ActionEvent e) {
	        	 
                    PlayerNameView pvn = new PlayerNameView(); 
                    Scene scene = new Scene(pvn);
	        	
	            Stage primaryStage = Main.getStage();
	             
	            primaryStage.setScene(scene);
	            primaryStage.setWidth(300);
	            primaryStage.setHeight(200);
	             
	            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
	            primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
	            primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
	         }
	    });
	    presentation.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                /**
                * If button is clicked it will open DemoMenuView
                * @param e ActionEvent, if the button is clicked it will trigger the code
                */
                public void handle(ActionEvent e) {
                    DemoMenuView demo = new DemoMenuView();
                    Scene scene = new Scene(demo);
	        	 
	            Stage primaryStage = Main.getStage();
	            primaryStage.setScene(scene);
	            primaryStage.setWidth(400);
	            primaryStage.setHeight(400);
                    
	            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
	            primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
	            primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
	         }
	    });
	    highscore.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
                /**
                * If button is clicked it will open HighscoreView. 
                * @param e ActionEvent, if the button is clicked it will trigger the code
                */
                public void handle(ActionEvent e) {
                    HighscoreView score = new HighscoreView();
	       	    Scene scene = new Scene(score);
	        	
	            Stage primaryStage = Main.getStage();
	            
	            primaryStage.setScene(scene);
	            
	            primaryStage.setWidth(400);
	            primaryStage.setHeight(400);
	            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
	            primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
	            primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
	         
	         }
	    });
	    exit.setOnAction(new EventHandler<ActionEvent>(){
	        @Override
                /**
                * If button is clicked it will stop code execution and exit.
                * @param e ActionEvent, if the button is clicked it will trigger the code
                */
                public void handle(ActionEvent e) {
	            Stage primaryStage = Main.getStage();
	            primaryStage.close();
	            System.exit(0);
	            
	         }
	    });
	    add(start, 0, 0);
	    add(presentation, 0, 1);
	    add(highscore, 0, 2);
	    add(exit, 0, 3);
	    setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, Insets.EMPTY)));
	}
}
