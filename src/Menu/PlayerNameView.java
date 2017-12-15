package Menu;

import GameLogic.PetrisGame;
import Setup.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * This class creates scene that asks for player's name.
 * @author Jordan, Basia, Stijn, Yvar, Sammuel, Blazej
 */
public class PlayerNameView extends GridPane{
	private String playerName;
	
        /**
         * Constructor creates the window with TextField, to enter name and a button that will start the game.
         */
	public PlayerNameView() {
				
	Label enter = new Label("Enter name:");
        enter.setFont(new Font("Arial", 18));
        TextField name = new TextField();
        name.setPrefColumnCount(10);
        Button submit = new Button("Start game");
        submit.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        
        Label warning = new Label();
        warning.setFont(new Font("Arial", 12));
        warning.setTextFill(Color.RED);
        
        setVgap(10);
        add(enter, 0, 0);
        add(name, 0, 1);
        add(submit, 0, 2);
        
        setAlignment(Pos.CENTER);
        setHalignment(enter, HPos.CENTER);
        setHalignment(submit, HPos.CENTER);
        
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            /**
            * If button is clicked it will check if name is valid, and if yes, it will start the game.
            * @param e ActionEvent, if the button is clicked it will trigger the code
            */
            public void handle(ActionEvent e) {
                playerName = (String) name.getText();
                if(playerName.length() == 0){
                    warning.setText("In order to play you should enter a name!");
                    getChildren().remove(submit);
                    getChildren().remove(warning);
                    add(warning, 0, 2);
                    add(submit, 0, 3);
                }
                else if(playerName.split("\\s+").length != 1){   
                    warning.setText("PLayer names should be one word!");
                    getChildren().remove(submit);
                    getChildren().remove(warning);
                    add(warning, 0, 2);
                    add(submit, 0, 3);
                }
                else{
                    Stage primaryStage = Main.getStage();
                    
                    PetrisGame game = new PetrisGame(playerName);
                    game.spawn();
                    game.runGame();
                    
                    primaryStage.setScene(game.getScene());
                    primaryStage.setWidth(650);
                    primaryStage.setHeight(800);
                    
                    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                    primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                    primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
                }    
          }
        });
        
        setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, Insets.EMPTY)));
	}
}
