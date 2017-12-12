package Agent;

import Dynamics.Controlls;
import Dynamics.GameCycle;
import GameLogic.Direction;
import GameLogic.Pentomino;
import GameLogic.PentominoGenerator;
import GameLogic.PetrisGame;
import GameView.MainView;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

//A simplified version of the tetris game to train a bot
public class TrainEnvironment extends PetrisGame{
	
	private DummyAgent agent;

	public TrainEnvironment() {
		super();
		agent = new DummyAgent(this);
	}
	
	@Override //neither a gamcecycle, view nor controlls are needed
	public void setupGame() {
		gridMatrix = new Color[HEIGHT][WIDTH];
    	PentominoGenerator startGenerator = new PentominoGenerator();
        nextBlock = startGenerator.getRandomPentomino();   
        
	}
	
	@Override //This method is overridden because we do not need the isRunning variable here
	public void spawn() {
		fallingBlock = nextBlock;
    	PentominoGenerator startGenerator = new PentominoGenerator();
        nextBlock = startGenerator.getRandomPentomino();   
	}
	
	@Override //the agent directly places the falling block on the grid. in some cases a moveDown is still needed though
	public void move(Direction aDirection){
        if(!doesCollide(aDirection)) {
            int[][] changeCoords = fallingBlock.getCoordinates();     		
                for(int i = 0; i < changeCoords[1].length; i++){
                    changeCoords[1][i]++;
                }	
            fallingBlock.setCoordinates(changeCoords);
        }
	}
	
	@Override //after the block is placed we directly spawn the next block if there is no game over
	public void placePento(Pentomino aPentomino){
        int[][] whereToPlace = aPentomino.getCoordinates();
        Color colorIndex = aPentomino.getColorIndex();
        for(int i = 0; i < whereToPlace[0].length; i++){
            gridMatrix[whereToPlace[1][i]][whereToPlace[0][i]] = colorIndex;
        }
        if(!gameOverCheck()) {
        	spawn();
        }
	}
	
	@Override 
	 public boolean gameOverCheck() {
        int[][] coordinates = nextBlock.getCoordinates();
        for (int i = 0; i < coordinates[0].length; i++){
           if(gridMatrix[coordinates[1][i]][coordinates[0][i]] != null) {
    		System.out.println("GameOver"); 
            return true;
            }              
    	} 
        return false;
    }
	
}
