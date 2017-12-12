package Agent;

import GameLogic.DemoBotGame;
import GameLogic.Direction;
import GameLogic.PetrisGame;

public class DummyAgent extends Agent{
	
	public DummyAgent() {		

	}
	
	@Override 
	//comment
		public void makeMove(PetrisGame g) {			
			super.game = (TrainEnvironment) g;			
			if(!game.gameOverCheck()) {
				//moves the block to the evaluated position
				game.getFallingBlock().setCoordinates(bestMove()); 	
				//moves the block down which places it on the grid, because it should collide
				game.move(Direction.DOWN);
			}	
		}
}
