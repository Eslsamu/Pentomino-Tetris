package GameLogic;

import Dynamics.Controlls;
import Dynamics.GameCycle;
import GameView.MainView;
import javafx.scene.paint.Color;

/**
 * Class which showcases row clearing, uses a similar approach as DemoOOGame which showcases
 * the optimal ordering, by selecting the the pentominoes which will be spawned ourselves
 * in order to show the the functionality of the program.
 */

public class DemoRCGame extends PetrisGame{
    protected static final int[] fallOrder = {5, 6, 0, 0}; 
    protected static int fallCounter = 0;
    
    /**
     * int[] fallOrder contains the indexes of the pentominoes in the pentomino arraylist which should
     * be used to show the row clearing mechanic.
     * int fallCounter tracks which pentomino from this list has to fall next.
     */
    
    /**
     * @param name allows the user to set their name
     * Constructor which configures the backend of the game.
     * gridMatrix creates the board with its appropriate height and width.
     * startGenerator is an object of the PentominoGenerator class used to create the 12 pentominoes.
     * As this is the constructor, nextBlock is the first block which we don't create randomly but
     * Which is defined as the first pentomino in the fallOrder array of pentominoes.
     * We also intialize the view, gamecycle and controls and then set isRunning to true.
     */
    public DemoRCGame(String name) {
        super(name);

	}
    @Override
    public void setupGame() {
            gridMatrix = new Color[HEIGHT][WIDTH];
            PentominoGenerator startGenerator = new PentominoGenerator();
        nextBlock = startGenerator.getTestPentomino(fallOrder[fallCounter]);
            
            view = new MainView(this);
            cycle = new GameCycle(this);
            controlls = new Controlls(this);
            isRunning = true;
	}

	@Override
        /**
        * We override the spawn method in order to use the optimal ordering of the pentominoes.
        * The if-block runs until the final pentomino is falling, incrementing the fallCounter so we know which
        * Pentomino from the fallOrder array should be chosen next.
        * We then set the current fallingBlock to what we defined as nextBlock.
        * Then we set the nextBlock using the pentomino in the array which the fallCounter is refering to.
        */
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
       /**
        * Method which restarts the game, resetting all the game variables to their default values,
        * then creating the nextBlock and spawning it at the top of the board.
        */
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
	