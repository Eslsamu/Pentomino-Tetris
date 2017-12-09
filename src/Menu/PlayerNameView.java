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

public class PlayerNameView extends GridPane{

	private String playerName;
	
	public PlayerNameView() {
				
		Label enter = new Label("Enter name:");
        enter.setFont(new Font("Arial", 18));
        TextField name = new TextField();
        name.setPrefColumnCount(10);
        Button submit = new Button("Start game");
        submit.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        
        
        setVgap(10);
        add(enter, 0, 0);
        add(name, 0, 1);
        add(submit, 0, 2);
        setAlignment(Pos.CENTER);
        setHalignment(enter, HPos.CENTER);
        setHalignment(submit, HPos.CENTER);
        
        submit.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent e) {
                playerName = (String) name.getText();
                if(playerName.length() != 0){
                    //use the Stage from main class
                    Stage primaryStage = Main.getStage();
                    //create an instance of BackendGrid and use it in GameCycle
                    PetrisGame game = new PetrisGame();
                    game.spawn();
                    game.runGame();
                    //change Scene to scene from GameCycle
                    primaryStage.setScene(game.getScene());
                    primaryStage.setWidth(700);
                    primaryStage.setHeight(800);
                    //this following code places the Window in the centre
                    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                    primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                    primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
                }    
          }
        });
        
        setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, Insets.EMPTY)));
	}
}
