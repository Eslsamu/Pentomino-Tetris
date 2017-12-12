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
	
    private Agent agent;
    
    public DemoBotGame() {
    	super("Roboter");
    	agent = new Agent(this);
    	//To better showcase the actions, the initial delay is increased
    	INITIAL_DELAY = 500;  
    	delay = INITIAL_DELAY;
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
    
	public Agent getAgent() {
		return agent;
	}
}
