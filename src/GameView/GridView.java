package GameView;

import GameLogic.PetrisGame;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GridView extends Pane{
	 private PetrisGame game;
	 public GridView(PetrisGame g){
		 game = g;
		 getChildren().clear();
	    	setMinSize(250, 750);
	        
	        Color[][] grid = game.getGrid();
	        
	        //fill board with rectangles representing each coordinate of gridMatrix from game
	        for(int i = 0; i < grid.length ; i++){
	            for(int x = 0; x < grid[0].length; x++){
	                    Rectangle rec = new Rectangle(50, 50);
	                    //set the color adn other things
	                    rec.setFill(grid[i][x]);
	                    rec.setStroke(Color.BLACK);
	                    rec.setTranslateX(x * 50);
	                    rec.setTranslateY(i * 50);
	                    getChildren().add(rec);
	            }
	        } 
	        //draw the falling block --> exception for the unstarted game
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

