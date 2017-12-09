package Agent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import GameLogic.PetrisGame;
import javafx.scene.paint.Color;

public class Agent extends Thread{
	
	private PetrisGame game;
	private int moveCount = 0;
	private double[] genes = {1,0.5,20,0.5};
	
	public Agent(PetrisGame backendGrid) {
		this.game = backendGrid;
	}
	
	public static void main(String[] args) {

	}
	
	//makeMove
	public void makeMove() {
		game.setIsRunning(false);
		moveCount++;
		game.getFallingBlock().setCoordinates(bestMove()); //puts the falling block at the best position --> the GameCycle will then just regularly to move it down and place it.	
		game.setIsRunning(true);
		System.out.println("check2");
	}
	//creates an array of evaluations for each move and returns the move with the highest value
	public int[][] bestMove(){
		
		ArrayList<int[][]> moveList = possibleMoves2();
		//ArrayList<int[][]> moveList2 = possibleMoves2();
		
		moveList.addAll(moveList);
		
		double[] evaluations = new double[moveList.size()];
		int best = 0;
		
		for(int i = 0; i < evaluations.length; i++) {
			evaluations[i] = evaluate(moveList.get(i));
		}
		
		for(int j = 0; j < evaluations.length; j++) {
			if(evaluations[j]>evaluations[best]) {
				best = j;	
			}			
		}
		
		
		return moveList.get(best);
	}
	
	//evaluate a grid position
	public double evaluate(int[][] move) {
		Color[][] gridCopy = new Color[game.getGrid().length][game.getGrid()[0].length];
		double value = 0;
			
		for(int l = 0; l < gridCopy.length;l++) {
			for(int j = 0; j < gridCopy[0].length; j++) {
				gridCopy[l][j] = game.getGrid()[l][j];
			}
		}
		
		
		//place copy of block into the grid copy
         for(int k = 0; k < move[0].length; k++){
             gridCopy[move[1][k]][move[0][k]] = Color.PURPLE;				            												
         }        
         
         value -= genes[0]*maxHeight(gridCopy);
         value -= genes[1]*cumulativeHeight(gridCopy);
         value += genes[2]*instantFullRows(gridCopy);
         value -= genes[3]*countHoles(gridCopy);
         //TODO       
         System.out.println("check1");
		return value;
	}
	
	
	//loops through columns from bottom to top, once we find a null value we check if there is a filled square somewhere
	  //above it in the rest of the column, if so it's the square with null value is a hole
	  public int countHoles(Color[][] grid){
	      int holes = 0;
	      for(int i = 0; i < grid[0].length; i++){
	        for(int j = grid.length-1; j >= 0; j--){
	            if(grid[j][i] == null){
	                for(int k = j; k >= 0; k--){
	                  if(grid[k][i] != null){
	                    holes++;
	                    j=i-1;
	                    break;
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
			int colHeight = grid.length - getPivotRow(col,grid);
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
	
	// returns a list of possible places for the falling block --> We will see what method get's better results
	public ArrayList<int[][]> possibleMoves(){ 
		ArrayList<int[][]> moveList = new ArrayList<int[][]>();
		//copy of the grid to check if a move is possible
		Color[][] grid = new Color[game.getGrid().length][game.getGrid()[0].length];
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length;j++) {
				grid[i][j]=game.getGrid()[i][j];
			}
		}
		//copies the falling block
		int[][] checkCoords = new int[game.getFallingBlock().getCoordinates().length][game.getFallingBlock().getCoordinates()[0].length];
		for(int i = 0; i < checkCoords.length; i++) {
			for(int j = 0; j < checkCoords[0].length;j++) {
				checkCoords[i][j]=game.getFallingBlock().getCoordinates()[i][j];
			}
		}		
		//changes the coordinates of the falling block copy, so that it would lay on top of each column in each rotation
		//first we iterate through each column
		for(int col = 0; col < grid[0].length; col++) {
			//get the pivotRow of that column-->see getPivotRow
			int pivotRow = getPivotRow(col,grid);
			//then we iterate each rotation
			for(int rota = 0; rota < 4; rota++) {				
				//rotate without smallBoardRotation
				checkCoords = game.rotate(checkCoords, rota*90, false);	
				//see getPivotPiece
				int pivotPiece = getPivotPiece(checkCoords);			
				//"place" the coords of the block copy on the pivotCell regarding of its pivotPiece
				int distanceX = col - checkCoords[0][pivotPiece];
				int distanceY = pivotRow - checkCoords[1][pivotPiece];
				for(int i = 0; i < checkCoords[0].length; i++) {
					checkCoords[0][i] += distanceX;
					checkCoords[1][i] += distanceY;
				}				
				//copy checkCoords again before adding (because we want a unique array for each move and not copy the reference)
				if(!moveCollides(checkCoords,grid)) {
					int[][] finalCoords = new int[checkCoords.length][checkCoords[0].length];
					for(int i = 0; i < checkCoords.length; i++) {
						for(int j = 0; j < checkCoords[0].length;j++) {
							finalCoords[i][j]=checkCoords[i][j];
						}
					}
					//if this move is possible then it adds it to the list and resets the changedCoords	
					moveList.add(finalCoords); 			
				}
				
				for(int i = 0; i < checkCoords.length; i++) {
					for(int j = 0; j < checkCoords[0].length;j++) {
						checkCoords[i][j]=game.getFallingBlock().getCoordinates()[i][j];
					}
				}				
			}
		}			
		return moveList;
	}
	
	//secondMethod of listing possible moves
	public ArrayList<int[][]> possibleMoves2(){ // returns a list of possible places for the falling block
		ArrayList<int[][]> moveList = new ArrayList<int[][]>();
		//copy of the grid to check if a move is possible
		Color[][] grid = new Color[game.getGrid().length][game.getGrid()[0].length];
		//TODO
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length;j++) {
				grid[i][j]=game.getGrid()[i][j];
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
						int[][] checkCoords = new int[game.getFallingBlock().getCoordinates().length][game.getFallingBlock().getCoordinates()[0].length];
						for(int i = 0; i < checkCoords.length; i++) {
							for(int j = 0; j < checkCoords[0].length;j++) {
								checkCoords[i][j]=game.getFallingBlock().getCoordinates()[i][j];
							}
						}
						//rotate without smallBoardRotation
						checkCoords = game.rotate(checkCoords, rota*90, false);	
						
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
								break;
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
