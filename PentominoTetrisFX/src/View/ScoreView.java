package View;

import GameLogic.PetrisGame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ScoreView extends GridPane{
	PetrisGame game;
	public ScoreView(PetrisGame g) {
		game = g;
		getChildren().clear();
		setMinSize(125, 375);
		setPadding(new Insets(0, 0, 0, 0));
		setStyle("-fx-border-color: white; -fx-background-color: #9ac6d6; -fx-fill: #DAA520; fx-font-weight: bold; -fx-font-size: 11pt");
		
		Text Highscore = new Text("High score:");
        Text highscore = new Text("To be implemented");
        Text Currentscore = new Text("Current score");
        Text currentscore = new Text(game.getScore() + "");
        Text RowsCleared = new Text("Rows cleared:");
        Text rowscleared = new Text(game.getRowsCleared() + "");
        Text Level = new Text("Level:");
        Text level = new Text(game.getLevel() + "");
        
        setAlignment(Pos.TOP_CENTER);
        add(Highscore, 0, 0);
        add(highscore, 0, 1);
        add(Currentscore, 0, 2);
        add(currentscore, 0, 3);
        add(RowsCleared, 0, 4);
        add(rowscleared, 0, 5);
        add(Level, 0, 6);
        add(level, 0, 7);
	}
}