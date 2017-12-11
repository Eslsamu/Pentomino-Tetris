package GameLogic;

import Agent.Agent;

public class DemoBotGame extends PetrisGame{
	
    private Agent agent;
    
    public DemoBotGame() {
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
