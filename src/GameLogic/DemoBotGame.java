package GameLogic;

import Agent.Agent;
import Dynamics.BotCycle;
import Dynamics.Controlls;
import Dynamics.GameCycle;
import GameView.MainView;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

/**
 * Demonstration framework which allows the bot to play the game after the user selected this option in the menu.
 * Has the option to let the bot place the optimal ordering of the pentominoes
 */

public class DemoBotGame extends PetrisGame{

	private static double[] genes = {1,1,1,1,1};
    private Agent agent;
    
    /**
     * DemoBotGame extends PetrisGame so that it can use its variables
     * int[] fallOrder contains the indexes of the pentominoes in the pentomino arraylist which should
     * be used for the optimal ordering.
     * int fallCounter tracks which pentomino from this list has to fall next.
     * boolean optimalOrder allows us to let the bot place the pentominoes needed for the optimal ordering
     * or just random pentominoes.
     * Then we declare the agent object which is the actual bot.
     */
    public DemoBotGame() {
    	super("Roboter");
    	agent = new Agent(genes);
    	//To better showcase the actions, the initial delay is increased
    	INITIAL_DELAY = 500;  
    	delay = INITIAL_DELAY;
    	System.out.println("Genomes are initialized:");
		System.out.println(genes[0]);
		System.out.println(genes[1]);
		System.out.println(genes[2]);
		System.out.println(genes[3]);
		System.out.println(genes[4]);
    }
    
    public static double[] getDNA() {
    	return genes;
    }
    
    public static void setDNA(double[] g) {
		genes = g;
	}
    
    @Override	//in the bot perfomance demo we will not initialize controlls and have a special game cycle for the bot
    public void setupGame() {
    	gridMatrix = new Color[HEIGHT][WIDTH];
    	PentominoGenerator startGenerator = new PentominoGenerator();
        nextBlock = startGenerator.getRandomPentomino();   
        
        view = new MainView(this);
    	cycle = new BotCycle(this);       
        isRunning = true;  
    }
    
    @Override //Here there are no controlls added to the scene
    public Scene getScene() {
    	Scene scene = new Scene(view);
    	return scene;
    }
    @Override
    public boolean gameOverCheck() {
        int[][] coordinates = nextBlock.getCoordinates();
        for (int i = 0; i < coordinates[0].length; i++){
           if(gridMatrix[coordinates[1][i]][coordinates[0][i]] != null) {
    		System.out.println("GameOver"); 
    		isRunning = false;
            return true;
            }              
    	} 
        return false;
    }
    
	public Agent getAgent() {
		return agent;
	}
}
