package Agent;

import java.util.ArrayList;

import GameLogic.DemoBotGame;
import GameLogic.Direction;
import GameLogic.PetrisGame;

public class DummyAgent extends Agent{
	
	
	public DummyAgent(double[] g) {	
		super(g);
	}
	
	@Override 
	//comment
public void makeMove(PetrisGame g) {
		
		game = (TrainEnvironment) g;
		
		ArrayList<int[][]> moveList = possibleMoves2();
		
		if(!game.gameOverCheck() && moveList.size()!=0) {
			//moves the block to the evaluated position
			game.getFallingBlock().setCoordinates(bestMove(moveList)); 	
			game.move(Direction.DOWN);
		}	
			
	}

	
}
