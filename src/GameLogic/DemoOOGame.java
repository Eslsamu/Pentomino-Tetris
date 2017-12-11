package GameLogic;

import Dynamics.Controlls;
import Dynamics.GameCycle;
import GameView.MainView;
import javafx.scene.paint.Color;


public class DemoOOGame extends PetrisGame{
    
    protected int[] fallOrder = {6, 3, 8, 7, 10, 9, 5, 11, 1, 4, 2, 0};
    protected Direction[][] directions = {{Direction.LEFT, Direction.LEFT},
                                          {Direction.CLOCKWISE},
                                          {Direction.CLOCKWISE},
                                          {Direction.RIGHT},
                                          {Direction.CLOCKWISE, Direction.LEFT},
                                          {Direction.CLOCKWISE, Direction.LEFT, Direction.LEFT},
                                          {Direction.COUNTERCLOCKWISE, Direction.LEFT, Direction.LEFT},
                                          {Direction.CLOCKWISE, Direction.CLOCKWISE, Direction.RIGHT},
                                          {Direction.CLOCKWISE, Direction.RIGHT, Direction.RIGHT},
                                          {Direction.LEFT},
                                          {Direction.CLOCKWISE, Direction.LEFT},
                                          {Direction.CLOCKWISE, Direction.RIGHT, Direction.RIGHT}};
    protected int fallCounter = 0;
    protected int directionCounter = 0;
	
	
        public DemoOOGame(String name) {
            delay = 100;
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
                    directionCounter = fallCounter - 1;
		}
		else {
			fallCounter = 0;
                        directionCounter = 11;
		}
		fallingBlock = nextBlock;
		PentominoGenerator startGenerator = new PentominoGenerator();
		nextBlock = startGenerator.getTestPentomino(fallOrder[fallCounter]);
                
                for(int i = 0; i < directions[directionCounter].length; i++){
                    move(directions[directionCounter][i]);
                }
	}
       @Override
       public void restart(){
            level = 1;
            score = 0;
            rowsCleared = 0;
            delay = 100.0;
            gridMatrix = new Color[HEIGHT][WIDTH];
            PentominoGenerator startGenerator = new PentominoGenerator();
            fallCounter = 0;
            nextBlock  = startGenerator.getTestPentomino(fallOrder[fallCounter]);
            spawn();
       }
	
}
