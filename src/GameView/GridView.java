package GameView;

import GameLogic.PetrisGame;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class creates the graphical interface of the PetrisGame matrix.
 * @author Jordan, Basia, Stijn, Yvar, Sammuel, Blazej
 */
public class GridView extends Pane{
	 private PetrisGame game;
         /**
          * Constructor receives an instance of PetrisGame - g and assigns it to a
          * private variable game, which will be used in the class. 
          * Because we will call this class multiple times we clear everything that is currently on it
          * and then redraw everything new according to the latest PetrisGame  colour matrix.
          * <p>
          * If we have a falling pentomino and the game isn't over we get it's coordinates and place it 
          * on the board too.
          * @param g an instance of current running PetrisGame
          */
	 public GridView(PetrisGame g){
		 game = g;
		 getChildren().clear();
	    	setMinSize(250, 750);
	        
	        Color[][] grid = game.getGrid();
	        
	        for(int i = 0; i < grid.length ; i++){
	            for(int x = 0; x < grid[0].length; x++){
	                    Rectangle rec = new Rectangle(50, 50);
	                    rec.setFill(grid[i][x]);
	                    rec.setStroke(Color.BLACK);
	                    rec.setTranslateX(x * 50);
	                    rec.setTranslateY(i * 50);
	                    getChildren().add(rec);
	            }
	        } 
            if(!game.gameOverCheck() && game.getFallingBlock() != null){
	            int[][] fallingBlockCoords =  game.getFallingBlock().getCoordinates();
	            Color color = game.getFallingBlock().getColorIndex();
	
	            for(int i = 0; i < fallingBlockCoords[0].length; i++) {
	                    Rectangle blockSquare = new Rectangle(50,50);
	
	                    getChildren().add(blockSquare);
	                    blockSquare.setStroke(Color.BLACK);
	                    blockSquare.setTranslateX(fallingBlockCoords[0][i]*50);
	                    blockSquare.setTranslateY(fallingBlockCoords[1][i]*50);
	                    blockSquare.setFill(color);
	            } 																																	       
	        }	    
	    }
    }

