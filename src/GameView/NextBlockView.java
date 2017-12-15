package GameView;

import GameLogic.PetrisGame;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *  Creates the view of the pentomino that will be falling next.
 * 
 * @author sam, błażej, jordan, yvar stijn, basia
 */
public class NextBlockView extends GridPane{
	
	PetrisGame game;
	
        /**
         *Constructs the next pentomino that will be falling and sets its position and a label
         * saying next pentomino. Sets style of the background.
         * @param g a PetrisGame is a current game.
         */
	public NextBlockView(PetrisGame g) {
            
            game = g;

            getChildren().clear();
            
            setStyle("-fx-border-color: white; -fx-border-width: 0 1 1 1; -fx-background-color: #9ac6d6; -fx-fill: #DAA520; fx-font-weight: bold;");
            setAlignment(Pos.TOP_CENTER);
            setVgap(15);
            setMaxSize(175, 375);
            setMinSize(175, 375);

            Label nextBlock = new Label("Next pentomino:");
            setHalignment(nextBlock, HPos.CENTER);
            add(nextBlock, 0, 0);


            int[][] nextBlockCoords = game.getNextBlock().getCoordinates();

            for(int i = 0; i < nextBlockCoords[0].length; i++) {
                    Rectangle blockSquare = new Rectangle(30,30);

                    blockSquare.setStroke(Color.BLACK);
                    add(blockSquare, 0, 1);
                    setHalignment(blockSquare, HPos.CENTER);
                    blockSquare.setTranslateX(nextBlockCoords[0][i]*30 - 60);
                    blockSquare.setTranslateY(nextBlockCoords[1][i]*30);
                    blockSquare.setFill(game.getNextBlock().getColorIndex());
            }
    }
} 