package GameView;

import GameLogic.PetrisGame;
import Menu.MenuView;
import Setup.Main;
import Setup.ScoreReader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GameOverView extends GridPane{
	
	private Stage temporaryStage;
	private PetrisGame game;
	
	public GameOverView(PetrisGame g, Stage temp) {
		temporaryStage = temp;
		game = g;
	    //Stage temporaryStage = new Stage();
	    
	    Label gameLost = new Label("Sorry, but you lost!");
	    gameLost.setFont(new Font("Arial", 25));
	    gameLost.setTextFill(Color.DARKRED);
	    gameLost.setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, Insets.EMPTY)));
	    
	    Button start = new Button("Play again");
	    start.setMinSize(150, 50);
	    start.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
	    
	    Button startMenu = new Button("Menu");
	    startMenu.setMinSize(150, 50);
	    startMenu.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
	    
	    Button exit = new Button("Exit");
	    exit.setMinSize(150, 50);
	    exit.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
	
	    setVgap(25);
	    setAlignment(Pos.CENTER);
	    setHalignment(start, HPos.CENTER);
	    setHalignment(startMenu, HPos.CENTER);
	    setHalignment(exit, HPos.CENTER);
	    
	    start.setOnAction(new EventHandler<ActionEvent>(){
	         @Override 
	            public void handle(ActionEvent e) {
	                game.restart();	                
	                temporaryStage.close();
	         }
	    });
	    startMenu.setOnAction(new EventHandler<ActionEvent>(){
	        @Override 
	        public void handle(ActionEvent e) {
	            Stage primaryStage = Main.getStage();
	            MenuView menu = new MenuView();
	            Scene scene = new Scene(menu);
	            primaryStage.setScene(scene);
	            
	            primaryStage.setWidth(400);
	            primaryStage.setHeight(400);
	            
	            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
	            primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
	            primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
	            
	            temporaryStage.close();
	         }
	    });
	    exit.setOnAction(new EventHandler<ActionEvent>(){
	         @Override
	         public void handle(ActionEvent e) {
	            Stage primaryStage = Main.getStage();
	            primaryStage.close();
	            temporaryStage.close();
	            System.exit(0);
	         }
	    });
	    add(gameLost, 0, 0);
	    add(start, 0, 1);
	    add(startMenu, 0, 2);
	    add(exit, 0, 3);
	    
	    setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, Insets.EMPTY)));
	    
	    
	}
}
