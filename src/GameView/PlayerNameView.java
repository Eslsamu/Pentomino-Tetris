package GameView;

import GameLogic.PetrisGame;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PlayerNameView extends GridPane{
	
	PetrisGame game;
        
	public PlayerNameView(PetrisGame g) {
            game = g;

            getChildren().clear();
            
            setStyle("-fx-border-color: white; -fx-border-width: 0 1 1 1; -fx-background-color: #9ac6d6; -fx-fill: #DAA520; fx-font-weight: bold;");
            setAlignment(Pos.TOP_CENTER);
            setVgap(15);
            setMaxSize(175, 375);
            setMinSize(175, 375);
            
            Label playerName = new Label("Current player:");
            playerName.setFont(new Font("Arial", 16));
            add(playerName, 0, 0);
            setHalignment(playerName, HPos.CENTER);
            Label name = new Label(game.getPlayerName());
            name.setFont(new Font("Arial", 16));
            add(name, 0, 1);
            setHalignment(name, HPos.CENTER);
            if(!game.getIsRunning() && !game.gameOverCheck()){
                Label gameIsPaused = new Label("Game is paused!");
                gameIsPaused.setFont(new Font("Arial", 20));
                gameIsPaused.setTextFill(Color.DARKRED);
                setHalignment(gameIsPaused, HPos.CENTER);
                add(gameIsPaused, 0, 2);
            }
    }
} 