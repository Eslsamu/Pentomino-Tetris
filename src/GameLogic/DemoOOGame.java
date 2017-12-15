package GameLogic;

import Dynamics.Controlls;
import Dynamics.GameCycle;
import GameView.MainView;
import javafx.scene.paint.Color;
import OptimalOrder.PentominoGenerator;

/**
 * Class which showcases the optimal ordering, placing the pentominoes appropriately on the board.
 */

public class DemoOOGame extends PetrisGame{
    private static int[] fallOrder = new int[12];
    private static Direction[][] directions = {{Direction.LEFT, Direction.LEFT},
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
    private static int fallCounter = 0;
    private static int directionCounter = 0;

    /**
     * int[] fallOrder contains the indexes of the pentominoes in the pentomino arraylist which should
     * be used for the optimal ordering.
     * Direction[][] directions' rows refer to each of the 12 pentominoes, while its columns refer to the
     * moves which are required for that piece to be placed in the appropriate location.
     * int fallCounter tracks which pentomino from this list has to fall next.
     * int directionCounter tracks tracks which directions from the array has to be used next.
     */
    
    /**
     * Constructor which configures the backend of the game.
     * delay sets the delay for a pentomino to fall 1 square down in ms.
     * gridMatrix creates the board with its height and width.
     * startGenerator is an object of the PentominoGenerator class used to create the 12 pentominoes.
     * As this is the constructor, nextBlock is the first block which we don't create randomly but
     * Which is defined as the first pentomino in the array of optimal ordering pentominoes.
     * We also intialize the view, gamecycle and controls and then set isRunning to true.
     * @param sequence gets the falling sequence from FindOptimalOrder
     */
        public DemoOOGame(int[] sequence) {
            for(int i = sequence.length - 1, x = 0; i > -1; i--, x++){
                fallOrder[x] = sequence[i] - 1;
	}
            setupGame();
	}
	@Override
        public void setupGame() {
            delay = 100;
            gridMatrix = new Color[HEIGHT][WIDTH];
            PentominoGenerator startGenerator = new PentominoGenerator();
            nextBlock = startGenerator.getTestPentomino(fallOrder[0]);
            
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
        * We then set the current fallingBlock to what we defined as nextBlock
        * Then we set the nextBlock using the pentomino in the array which the fallCounter is refering to.
        * Then we loop through the directions for each pentomino, and move it in the specified directions
        * just after it's spawned.
        */
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
       /**
        * Method which restarts the game, resetting all the game variables to their default values,
        * then creating the nextBlock and spawning it at the top of the board.
        */
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
