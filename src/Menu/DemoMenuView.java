package Menu;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import GameLogic.DemoBotGame;
import GameLogic.DemoOOGame;
import GameLogic.DemoRCGame;
import GameLogic.GameMode;
import GameLogic.PetrisGame;
import Setup.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
	
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
 import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class DemoMenuView extends GridPane{
	
	private Main main;
	
	public DemoMenuView() {
		
		Button bot = new Button("Bot");
        bot.setMinSize(150, 50);
        bot.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        
        Button optimalOrdering = new Button("Optimal ordering");
        optimalOrdering.setMinSize(150, 50);
        optimalOrdering.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        
        Button clearRow = new Button("Clearing row");
        clearRow.setMinSize(150, 50);
        clearRow.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        
        Button startMenu = new Button("Menu");
        startMenu.setMinSize(150, 50);
        startMenu.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        
        setAlignment(Pos.CENTER);
        setHalignment(bot, HPos.CENTER);
        setHalignment(optimalOrdering, HPos.CENTER);
        setHalignment(clearRow, HPos.CENTER);
        setHalignment(startMenu, HPos.CENTER);
        setVgap(10);
        
        bot.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
               //playerName = "OptimalOrderingTest";
               Stage primaryStage = main.getStage();
               //create an instance of BackendGrid and use it in GameCycle
               DemoBotGame game = new DemoBotGame();
               game.spawn();//TODO shouldnt be like this 
               game.runGame();
               //change Scene to scene from GameCycle
               primaryStage.setScene(game.getScene());
               primaryStage.setWidth(700);
               primaryStage.setHeight(800);
               
               //this following code places the Window in the centr
               Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
               primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
               primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
            }
       });
        
        optimalOrdering.setOnAction(new EventHandler<ActionEvent>(){
             @Override public void handle(ActionEvent e) {
                //playerName = "OptimalOrderingTest";
                Stage primaryStage = main.getStage();
                //create an instance of BackendGrid and use it in GameCycle
                DemoOOGame game = new DemoOOGame();
                game.spawn();//TODO shouldnt be like this 
                game.runGame();
                //change Scene to scene from GameCycle
                primaryStage.setScene(game.getScene());
                primaryStage.setWidth(700);
                primaryStage.setHeight(800);
                
                //this following code places the Window in the centr
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
             }
        });
        
        clearRow.setOnAction(new EventHandler<ActionEvent>(){
             @Override public void handle(ActionEvent e) {
                Stage primaryStage = main.getStage();
                //create an instance of BackendGrid and use it in GameCycle
                DemoRCGame game = new DemoRCGame();
                game.spawn();//TODO shouldnt be like this 
                game.runGame();
                //change Scene to scene from GameCycle
                primaryStage.setScene(game.getScene());
                primaryStage.setWidth(700);
                primaryStage.setHeight(800);
                //this following code places the Window in the centr
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
             }
        });
        
        startMenu.setOnAction(new EventHandler<ActionEvent>(){
            @Override 
            public void handle(ActionEvent e) {          	
                Stage primaryStage = main.getStage();
                MenuView menu = new MenuView();
                Scene scene = new Scene(menu);
                
                primaryStage.setScene(scene);
                
                primaryStage.setWidth(400);
                primaryStage.setHeight(400);
                
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
                
             }
        });
        
        setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, Insets.EMPTY)));
        
        add(bot, 0, 0);
        add(optimalOrdering, 0, 1);
        add(clearRow, 0, 2);
        add(startMenu, 0, 3);
        
	}
}
