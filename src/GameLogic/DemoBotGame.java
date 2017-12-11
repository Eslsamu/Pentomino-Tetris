package GameLogic;

import Agent.Agent;
import Dynamics.Controlls;
import Dynamics.GameCycle;
import GameView.MainView;
import javafx.scene.paint.Color;

public class DemoBotGame extends PetrisGame{
    private int[] fallOrder = {6, 3, 8, 7, 10, 9, 5, 11, 1, 4, 2, 0};
    private int fallCounter = 0;
    private boolean optimalOrder = false;
    
    private Agent agent;
    
    public DemoBotGame(boolean optimalOrder){
        this.optimalOrder = optimalOrder;
        playerName = "OptimalOrderBot";
        gridMatrix = new Color[HEIGHT][WIDTH];
        PentominoGenerator startGenerator = new PentominoGenerator();
        nextBlock = startGenerator.getTestPentomino(fallOrder[0]);
            
        view = new MainView(this);
        cycle = new GameCycle(this);
        controlls = new Controlls(this);
        isRunning = true;
        agent = new Agent(this);

    }
    public DemoBotGame(double[] genes) {
        super("Roboter");
    	agent = new Agent(this, genes);
    }
    public DemoBotGame() {
    	super("Roboter");
    	agent = new Agent(this);
    }
    
    @Override
    public void spawn() {
        if(!isRunning) {
            return;
        }
        if(!optimalOrder){
            fallingBlock = nextBlock;
            PentominoGenerator startGenerator = new PentominoGenerator();
            nextBlock = startGenerator.getRandomPentomino();   
        }
        else{
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
        //bot makes a move right after a new block is spawned
        agent.makeMove();    
    }
    @Override
    public void restart(){
        if(!optimalOrder){
            super.restart();
        }
        else{
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
}
