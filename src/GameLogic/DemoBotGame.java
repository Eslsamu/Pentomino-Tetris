package GameLogic;

import Agent.Agent;
import Dynamics.BotCycle;
import Dynamics.Controlls;
import Dynamics.GameCycle;
import GameView.MainView;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class DemoBotGame extends PetrisGame{
	
	private static double[] genomes = {1,1,10,1,1};
    private Agent agent;
    
    public DemoBotGame() {
    	super("Roboter");
    	agent = new Agent(genomes);
    	//To better showcase the actions, the initial delay is increased
    	INITIAL_DELAY = 500;  
    	delay = INITIAL_DELAY;
    	System.out.println("Genomes are initialized:");
		System.out.println(genomes[0]);
		System.out.println(genomes[1]);
		System.out.println(genomes[2]);
		System.out.println(genomes[3]);
		System.out.println(genomes[4]);
    }
    
    public static void setDNA(double[] g) {
		genomes = g;
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
