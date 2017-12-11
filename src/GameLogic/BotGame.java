package GameLogic;

import Agent.Agent;

public class BotGame extends PetrisGame{
	
    private Agent agent;
    
    public BotGame() {
    	super("Roboter");
    	agent = new Agent(this);
    }
    
    @Override
    public void spawn() {   	
    	if(!isRunning) {
    		return;
    	}
    	fallingBlock = nextBlock;
    	PentominoGenerator startGenerator = new PentominoGenerator();
        nextBlock = startGenerator.getRandomPentomino();    
        
        //bot makes a move right after a new block is spawned
        agent.makeMove();
    }
    
}
