package Agent;

import java.util.ArrayList;


import GameLogic.Direction;
import GameLogic.PetrisGame;

/**
 * A subclass of the Agent class, which implements the Comparable interface so that an array
 * of DummyAgents can be sorted by it's achieved score.
 * @author Samuel
 *
 */
public class DummyAgent extends Agent implements Comparable<DummyAgent>{
	
	public int score;
	
	public DummyAgent(double[] g) {	
		super(g);
	}
	
	/**
	 * Works the same way as the makeMove method in Agent, but also moves the falling block down after placing it.
	 * This causes it to collide, which spawns a new falling block. 
	 */
	@Override 
	public void makeMove(PetrisGame g) {
		
		game = (TrainEnvironment) g;
		
		ArrayList<int[][]> moveList = possibleMoves2(game.getGrid(), game.getFallingBlock().getCoordinates());
		
		if(!game.gameOverCheck() && moveList.size()!=0) {
			//moves the block to the evaluated position
			game.getFallingBlock().setCoordinates(bestMove(game.getGrid(),moveList,false,game.getNextBlock().getCoordinates())); 	
			game.move(Direction.DOWN);
		}	
			
	}

	@Override
	public int compareTo(DummyAgent other) {
		return -1*Integer.valueOf(this.score).compareTo(other.score);
	}
	
	public void setScore(int s) {
	    	score = s;
	}
	
	public int getScore() {
		return score;
	}
}
