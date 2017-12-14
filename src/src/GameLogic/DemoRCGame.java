package GameLogic;

import Dynamics.Controlls;
import Dynamics.GameCycle;
import GameView.MainView;
import javafx.scene.paint.Color;


public class DemoRCGame extends PetrisGame{
	
    //comments
    protected int[] fallOrder = {5, 6, 0, 0}; 
    protected int fallCounter = 0;
	
	public DemoRCGame(String name) {
            playerName = name;
            gridMatrix = new Color[HEIGHT][WIDTH];
            PentominoGenerator startGenerator = new PentominoGenerator();
            nextBlock = startGenerator.getTestPentomino(fallOrder[0]);
            
            view = new MainView(this);
            cycle = new GameCycle(this);
            controlls = new Controlls(this);
            isRunning = true;
	}
	
	@Override 
	public void spawn() {
		if(this.fallCounter < this.fallOrder.length - 1){
			fallCounter++;
		}
		else {
			fallCounter = 0;
		}
		fallingBlock = nextBlock;
		PentominoGenerator startGenerator = new PentominoGenerator();
		nextBlock = startGenerator.getTestPentomino(fallOrder[fallCounter]);
	}
       
       @Override
       public void restart(){
            level = 1;
            score = 0;
            rowsCleared = 0;
            delay = 500.0;
            gridMatrix = new Color[HEIGHT][WIDTH];
            PentominoGenerator startGenerator = new PentominoGenerator();
            fallCounter = 0;
            nextBlock  = startGenerator.getTestPentomino(fallOrder[fallCounter]);
            spawn();
       } 
}	
	