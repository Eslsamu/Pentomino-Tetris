package Agent;


import java.util.ArrayList;

import GameLogic.DemoBotGame;
import GameLogic.Direction;
import GameLogic.PetrisGame;
import javafx.scene.paint.Color;

public class Agent{
	
	protected PetrisGame game;
	
	protected double[] genes;
	
	public Agent(double[] g) {
		genes = g;
	}
	
	public void setGenes(double[] g) {
		genes = g;
	}
	
	public double[] getGenes() {
		return genes;
	}
	
	//comment
	public void makeMove(PetrisGame g) {
		
		game = (DemoBotGame) g;
		
		ArrayList<int[][]> moveList = possibleMoves2(game.getGrid(), game.getFallingBlock().getCoordinates());
		
		if(!game.gameOverCheck() && moveList.size()!=0) {
			//moves the block to the best evaluated grid position considering moving the next block 
			int[][] nextBlock = game.getNextBlock().getCoordinates();
			game.getFallingBlock().setCoordinates(bestMove(game.getGrid(),moveList,true, nextBlock)); 	
		}	
	}
	
	//creates an array of evaluations for each move and returns the move with the highest value
	public int[][] bestMove(Color[][] grid,ArrayList<int[][]> moveList, boolean considerNext, int[][] nextBlock){
				
		double[] evaluations = new double[moveList.size()];
		int best = 0;
		
		
		for(int i = 0; i < evaluations.length; i++) {
			evaluations[i] = (considerNext==true) ? evaluate(grid,moveList.get(i),true,nextBlock) : evaluate(grid,moveList.get(i),false, nextBlock);
		}
		
		for(int j = 0; j < evaluations.length; j++) {
			if(evaluations[j]>evaluations[best]) {
				best = j;	
			}			
		}
		
		return moveList.get(best);
	}
	
	//evaluate a grid position
	public double evaluate(Color[][] grid,int[][] move, boolean considerNext, int[][] nextBlock) {		
		Color[][] gridCopy = new Color[grid.length][grid[0].length];
		double value = 0;
		
		//copies the grid from the game	
		for(int l = 0; l < gridCopy.length;l++) {
			for(int j = 0; j < gridCopy[0].length; j++) {
				gridCopy[l][j] = grid[l][j];
			}
		}
		
		
		//place copy of block into the grid copy
         for(int k = 0; k < move[0].length; k++){
             gridCopy[move[1][k]][move[0][k]] = Color.PURPLE;				            												
         }    
         
         //finds the best move for the next block and places it on the grid copy
         if(considerNext) {
        	 ArrayList<int[][]> nextMoveList = possibleMoves2(gridCopy, nextBlock);    
        	 int[][] nextMove = bestMove(gridCopy,nextMoveList, false, nextBlock); //here it doesn't consider the next block, because we only have the information about one following block
        	 
        	 for(int k = 0; k < move[0].length; k++){
                 gridCopy[nextMove[1][k]][nextMove[0][k]] = Color.PURPLE;				            												
             } 
         }
         
         value -= genes[0]*maxHeight(gridCopy);
         value -= genes[1]*cumulativeHeight(gridCopy);
         value += genes[2]*instantFullRows(gridCopy);
         value -= genes[3]*countHoles(gridCopy);
         value -= genes[4]*bumpiness(gridCopy);
         //TODO       
		return value;
	  }
	  
	
	
	  public int countHoles(Color[][] grid){
	      int holes = 0;
	      for(int col = 0; col < grid[0].length; col++) {
	    	  for(int row = grid.length-1; row >= 0; row--) {//iterates from bottom to top
	    		  if(grid[row][col]==null) { //searches for empty cells
	    			  int holesize = 0;
	    			  for(int i = row; i >= 0; i--) {
	    				  if(i == 0 ) {
	    					  holesize = 0; //detected the distance from the highest piece in the col to the top --> not a hole
	    				  }
	    				  else {
	    					  if(grid[i][col]==null) {
	    						  holesize++;		//measures how many empty cells there are above the detected empty cell
	    					  }
	    					  else {
	    						  holes += holesize;	//adds the holesize to the total number of holes
	    						  row=i;				//jumps to cell to the first cell above that is not empty
	    						  break;				
	    					  }
	    				  }
	    			  }
	    		  }
	    	  }
	       
	        
	      }
	    return holes;    
	  }
	
	public int instantFullRows(Color[][] grid) {	
		int fulls = 0;
		for(int row = 0; row < grid.length; row++) {
			boolean isFull = true;
			for(int col = 0; col < grid[0].length;col++) {
				if(grid[row][col]==null) {
					isFull = false;
				}
			}
			if(isFull) {
				fulls++;
			}
		}
		return fulls;
	}
	
	public double cumulativeHeight(Color[][] grid) {
		double total = 0;
		for(int col = 0; col < grid[0].length; col++) {
			total += grid.length - getPivotRow(col,grid);
		}
		return total/grid[0].length;
	}
	
	public int maxHeight(Color[][] grid) { //TODO Bug in here probably		
		int max = 0;
		for(int col = 0; col < grid[0].length; col++) {
			int colHeight = grid.length - getPivotRow(col,grid);
			if(colHeight>max)
				max = grid.length - getPivotRow(col,grid);
		}
		return max;
	}
	
	//returns the level of bumpiness -> difference between two neighboring columnheights
	public int bumpiness(Color[][] grid) {
		int bumpiness = 0;
		for(int col = 0; col < grid[0].length-1; col++) { //-1 because we do not want to count the last col
			int col1 = grid.length - getPivotRow(col,grid);
			int col2 = grid.length - getPivotRow(col+1,grid);
			bumpiness = Math.abs(col1 - col2);
		}
		return bumpiness;
	}
	
	//secondMethod of listing possible moves
	public ArrayList<int[][]> possibleMoves2(Color[][] originalGrid, int[][] fallingBlock){ // returns a list of possible places for the falling block
		ArrayList<int[][]> moveList = new ArrayList<int[][]>();
		//copy of the grid to check if a move is possible
		Color[][] grid = new Color[originalGrid.length][originalGrid[0].length];
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length;j++) {
				grid[i][j]=originalGrid[i][j];
			}
		}
		//changes the coordinates of the falling block copy, so that it would lay on top of each column in each rotation
				//first we iterate through each column
				for(int col = 0; col < grid[0].length; col++) {
					//get the pivotRpow of that column-->see getPivotRow
					int pivotRow = getPivotRow(col,grid);
					//then we iterate each rotation
					for(int rota = 0; rota < 4; rota++) {
						//copies the falling block
						int[][] checkCoords = new int[fallingBlock.length][fallingBlock[0].length];
						for(int i = 0; i < checkCoords.length; i++) {
							for(int j = 0; j < checkCoords[0].length;j++) {
								checkCoords[i][j]=fallingBlock[i][j];
							}
						}
						//rotate without smallBoardRotation
						//checkCoords = game.rotate(checkCoords, rota*90, true); change back
						checkCoords = game.rotate(checkCoords, rota*90, true);	
						
						//see getPivotPiece
						int pivotPiece = getLeftMostPiece(checkCoords);
						
						//shifts the block copy to another x position
						int distanceX = col - checkCoords[0][pivotPiece];
						for(int i = 0; i < checkCoords[0].length; i++) {
							checkCoords[0][i] += distanceX;
						}
						
						
						
						//moves the block down until it "bumps" a surface, then adds it with y-1 to the list
						for(int row = 0; row < grid.length; row++) {
							if(moveCollides(checkCoords,grid)) {
								int[][] finalCoords = new int[checkCoords.length][checkCoords[0].length];
									for(int j = 0; j < checkCoords[0].length;j++) {
										finalCoords[0][j]=checkCoords[0][j];
										finalCoords[1][j]=checkCoords[1][j]-1;
									}
									//checks one more time if the iteration before is possible(probably not necessary, but revealing for bugs)
								if(!moveCollides(finalCoords,grid)) {	
									moveList.add(finalCoords);	
								}
								else{
									/*
									for(int i = 0; i < finalCoords[0].length;i++) {
										System.out.print(finalCoords[0][i]);
										System.out.print(finalCoords[1][i]+" ");
									}
									
									System.out.println("did collide");*/
								}
							}
							else {
								for(int j = 0; j < checkCoords[0].length;j++) {
									checkCoords[1][j]++;
								}
							}	
						}	
					}
				}	
			return moveList;					
		}
		
	

	//get the down-left most piece of a block
	public int getPivotPiece(int[][] testCoords) {
		int pivot = 0;
		for(int i = 0; i < testCoords[0].length;i++) {
			if(testCoords[1][i]>testCoords[1][pivot]) {
				pivot = i;
				}
			else if(testCoords[1][i]==testCoords[1][pivot]){
				if(testCoords[0][i]<=testCoords[0][pivot]) {
					pivot = i;
				}	
			}
			
		}
		return pivot;
	}
	
	//get the left most piece of a block
	public int getLeftMostPiece(int[][] testCoords) {
		int pivot = 0;
		for(int i = 0; i < testCoords[0].length;i++) {				
			if(testCoords[0][i]<=testCoords[0][pivot]) {
					pivot = i;
			}					
		}
		return pivot;
	}
	
	
	//returns the pivot row. the pivot row is the deepest row before hitting a colored cell in a column
	public int getPivotRow(int col, Color[][] grid) {
		for(int row = 0; row < grid.length; row++) {
			if(grid[row][col]!=null) {
				return row - 1; //we want to return the row BEFORE we hit a colored cell
			}
		}
		return grid.length-1;	//if there are no colored cells in a column then we simply return the deepest position
	}
	
	public boolean moveCollides(int[][] move, Color[][] grid) {
		for(int i = 0; i < move[0].length; i++) {
			if(move[0][i] >= grid[0].length || move[1][i] >= grid.length || move[0][i] < 0 || move[1][i] < 0 || grid[move[1][i]][move[0][i]]!=null) {
				return true;
			}
		}
		
		return false;
	}
}
