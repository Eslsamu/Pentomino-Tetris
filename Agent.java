import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javafx.scene.paint.Color;

public class Agent {
	
	private BackendGrid game;
	private int moveCount = 0;
	private double[] genes = {1};
	
	public Agent(BackendGrid backendGrid) {
		this.game = backendGrid;
	}
	
	public static void main(String[] args) {
		BackendGrid game = new BackendGrid(true);
		Agent agent = new Agent(game);
		game.spawn();
		game.testPrint();
		
		ArrayList<int[][]> moveList = agent.possibleMoves();		
	}
	
	//makeMove
	public void makeMove(BackendGrid newGameState) {
		game = newGameState;
		moveCount++;
		game.getFallingBlock().setCoordinates(bestMove()); //puts the falling block at the best position --> the GameCycle will then just regularly to move it down and place it.	
	}
	
	public int[][] bestMove(){
		ArrayList<int[][]> moveList = possibleMoves();
		double[] evaluations = new double[moveList.size()];
		int best = 0;
		
		for(int i = 0; i < evaluations.length; i++) {
			evaluations[i] = evaluate(moveList.get(i));
		}
		
		for(int j = 0; j < evaluations.length; j++) {
			if(evaluations[j]>best) {
				best = j;	
			}			
		}
		
		System.out.println("best:"+evaluations[best]);
		
		return moveList.get(best);
	}
	
	public double evaluate(int[][] i) {
		Color[][] gridCopy = new Color[game.getGrid().length][game.getGrid()[0].length];
		double value = 0;
		
		for(int l = 0; l < gridCopy.length;l++) {
			for(int j = 0; j < gridCopy[0].length; j++) {
				gridCopy[l][j] = game.getGrid()[l][j];
			}
		}
		
         for(int k = 0; k < i[0].length; k++){
             gridCopy[i[1][k]][i[0][k]] = Color.PURPLE;				//we could reserve this color and display all the evaluations in the gui 
             														//to inspect the "thinking" of the agent
         }        
         
         //debug
         System.out.println(moveCount+"------------------------------------------");
         for(int p = 0; p < gridCopy.length;p++) {
     		for (int j = 0; j < gridCopy[0].length; j++) {     			
     			System.out.print(" "+p+" "+j+" "+gridCopy[p][j]+" ");
     		}
     		System.out.println();
     	}
         
         value -= genes[0]*maxHeight(gridCopy);
         //TODO
         
         
		return value;
	}
	
	public int maxHeight(Color[][] grid) { //TODO Bug in here probably
		int max = 0;
		for(int col = 0; col < grid[0].length; col++) {
			int temp = grid[0].length - getPivotCell(col,grid);
			if(temp>max)
				max = grid[0].length-getPivotCell(col,grid);
		}
		return max;
	}
	
	public ArrayList<int[][]> possibleMoves(){ // returns a list of possible places for the falling block
		ArrayList<int[][]> moveList = new ArrayList<int[][]>();
		Color[][] grid = game.getGrid();
		int[][] testCoords = game.getFallingBlock().getCoordinates();
	
		for(int rota = 0; rota < 4; rota++) {	//each rotation
				
			int pivotPiece = getPivotPiece(testCoords);	//most left down piece of the pentomino

			for(int col = 0; col < grid[0].length; col++) { //each column
				int pivotCell = getPivotCell(col, grid);	//this reduces the capability of the bot to find nontrivial moves --> just the top of a column in considered as a possible move
				
				int[][] move = new int[testCoords.length][testCoords[0].length];
				
				for(int i = 0; i < move[0].length; i++) {
					move[0][i] = testCoords[0][i] + (col-testCoords[0][pivotPiece]);
					move[1][i] = testCoords[1][i] + (pivotCell-testCoords[1][pivotPiece]);
				}
				
				if(movePossible(move, grid)) {
					moveList.add(move);
				}				
			}
			testCoords = game.rotate(testCoords, 90);
		}
			
		return moveList;
	}
	
	public int getPivotPiece(int[][] testCoords) {
		int pivot = 0;
		for(int i = 0; i < testCoords[0].length;i++) {
			if(testCoords[0][i]<=testCoords[0][pivot]) {
				if(testCoords[1][i]>=testCoords[1][pivot]) {
					pivot = i;
				}
			}
		}
		return pivot;
	}
	
	public int getPivotCell(int col, Color[][] grid) {
		int pivotCell = 0;
		for(int row = 0; row < grid.length; row ++) {
			if(grid[row][col]!=null) {
				pivotCell = row-1;
				return pivotCell;
			}
		}
		pivotCell = grid.length-1;
		return pivotCell;
	}
	
	public boolean movePossible(int[][] move, Color[][] grid) {
		for(int i = 0; i < move[0].length; i++) {
			if(move[0][i] >= grid[0].length || move[1][i] >= grid.length || move[0][i] < 0 || move[1][i] < 0 || grid[move[1][i]][move[0][i]]!=null) {
				return false;
			}
		}
		return true;
	}
}
