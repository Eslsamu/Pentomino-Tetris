package GameView;

import GameLogic.PetrisGame;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class NextBlockView extends Pane{
	
	PetrisGame game;
	
	public NextBlockView(PetrisGame g) {
		game = g;
    	
    	getChildren().clear();
        
        setMinSize(200, 300);
        setPadding(new Insets(20, 0, 0, 20));
        
        int[][] nextBlockCoords = game.getNextBlock().getCoordinates();
        
        for(int i = 0; i < nextBlockCoords[0].length; i++) {
        	Rectangle blockSquare = new Rectangle(30,30);

        	blockSquare.setStroke(Color.BLACK);
        	getChildren().add(blockSquare);
        	blockSquare.setTranslateX(nextBlockCoords[0][i]*30);
        	blockSquare.setTranslateY(nextBlockCoords[1][i]*30);
        	blockSquare.setFill(game.getNextBlock().getColorIndex());
        }
	}
} 