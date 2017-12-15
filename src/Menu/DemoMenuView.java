package Menu;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import Agent.TrainEnvironment;
import GameLogic.DemoBotGame;
import GameLogic.DemoRCGame;
import OptimalOrder.OptimalOrder;
import Setup.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
	
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * This class creates a menu for the presentation. With options to play the bot, show the optimal order we found
 * and a sequence of pentominoes to display our clear row.
 * @author Jordan, Basia, Stijn, Yvar, Sammuel, Blazej
 */
public class DemoMenuView extends GridPane{
	
	private Main main;
	
        /**
         * Constructor creates the menu with Buttons: Bot, Optimal order, Clear row, menu.
         */
	public DemoMenuView() {
		
		Button bot = new Button("Bot");
        bot.setMinSize(150, 50);
        bot.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        
        Button training = new Button("Train Bot");
        training.setMinSize(150, 50);
        training.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        
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
        setHalignment(optimalOrdering, HPos.CENTER);
        setHalignment(clearRow, HPos.CENTER);
        setHalignment(startMenu, HPos.CENTER);
        setVgap(10);
        
        bot.setOnAction(new EventHandler<ActionEvent>(){
            /**
             * If button is clicked it will start the bot
             * @param e ActionEvent, if the button is clicked it will trigger the code
             */ 
            @Override
            public void handle(ActionEvent e) {
               Stage primaryStage = main.getStage();
               DemoBotGame game = new DemoBotGame();
                    game.spawn();
               game.runGame();
               primaryStage.setScene(game.getScene());
                    primaryStage.setWidth(650);
               primaryStage.setHeight(800);
               
               Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
               primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
               primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
            }
       });
        
        training.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * Improve bot genes
             * @param e ActionEvent, if the button is clicked it will trigger the code
             */
            @Override 
            public void handle(ActionEvent e) {
                TrainEnvironment te = new TrainEnvironment();              
             }
        });
        
        optimalOrdering.setOnAction(new EventHandler<ActionEvent>(){
            /**
             * If button is clicked it will start optimal ordering
             * @param e ActionEvent, if the button is clicked it will trigger the code
             */
            @Override 
            public void handle(ActionEvent e) {
                Stage primaryStage = main.getStage();
                    OptimalOrder optimalOrder = new OptimalOrder();
                    
                    primaryStage.setScene(new Scene(optimalOrder.OptimalOrderView()));
                    primaryStage.setWidth(650);
                primaryStage.setHeight(800);
                
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
             }});
        
        clearRow.setOnAction(new EventHandler<ActionEvent>(){
            /**
             * If button is clicked it will start the clear row sequence
             * @param e ActionEvent, if the button is clicked it will trigger the code
             */
            @Override 
            public void handle(ActionEvent e) {
                Stage primaryStage = main.getStage();
                DemoRCGame game = new DemoRCGame("RowClearSequence");
                game.spawn();
                game.runGame();
                
                primaryStage.setScene(game.getScene());
                primaryStage.setWidth(650);
                primaryStage.setHeight(800);
                
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
             }
        });
        
        startMenu.setOnAction(new EventHandler<ActionEvent>(){
             /**
             * If button is clicked it will return to main menu
             * @param e ActionEvent, if the button is clicked it will trigger the code
             */
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
        GridPane botButtons = new GridPane();
        botButtons.add(bot, 0, 0);
        botButtons.add(training, 1, 0);
        botButtons.setAlignment(Pos.CENTER);
        botButtons.setHalignment(bot, HPos.CENTER);
        botButtons.setHalignment(training, HPos.CENTER);
        botButtons.setHgap(15);
        
        add(botButtons, 0, 0);
        add(optimalOrdering, 0, 1);
        add(clearRow, 0, 2);
        add(startMenu, 0, 3);
	}
}
