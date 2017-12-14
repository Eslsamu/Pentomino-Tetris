package GameView;

import GameLogic.PetrisGame;
import Setup.ScoreReader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ScoreView extends GridPane{
	PetrisGame game;
	public ScoreView(PetrisGame g) {
            game = g;
            getChildren().clear();
            setMinSize(175, 375);
            setStyle("-fx-border-color: white; -fx-background-color: #9ac6d6; -fx-fill: #DAA520; fx-font-weight: bold; -fx-font-size: 11pt");

            ScoreReader sr = new ScoreReader();

            int topScore = sr.getScores()[0];
            int currentScore = game.getScore();
            if(topScore < currentScore){
                                 topScore = currentScore;
            }

            Text Highscore = new Text("High score:");
            Text highscore = new Text(topScore + "");
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
            setHalignment(Highscore, HPos.CENTER);
            setHalignment(highscore, HPos.CENTER);
            setHalignment(Currentscore, HPos.CENTER);
            setHalignment(currentscore, HPos.CENTER);
            setHalignment(RowsCleared, HPos.CENTER);
            setHalignment(rowscleared, HPos.CENTER);
            setHalignment(Level, HPos.CENTER);
            setHalignment(level, HPos.CENTER);
	}
}