package Agent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import Dynamics.Controlls;
import Dynamics.GameCycle;
import GameLogic.DemoBotGame;
import GameLogic.Direction;
import GameLogic.Pentomino;
import GameLogic.PentominoGenerator;
import GameLogic.PetrisGame;
import GameView.MainView;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

//A simplified version of the tetris game to train a bot
public class TrainEnvironment extends PetrisGame{
	
	private static final double MUTATION_RATE = 0.1;
	private static final double MUTATION_STEP = 0.05;
	private static final double GENERATIONS = 10;
	
	private DummyAgent agent;

	public TrainEnvironment() {
		super();
		double[] genomes = evolve();
		System.out.println(genomes[0]);
		System.out.println(genomes[1]);
		System.out.println(genomes[2]);
		
		DemoBotGame.setDNA(genomes);
	}
	
	public double[] evolve() {
		//initialize a population of 50 agents
		double[][] population = new double[50][3];
		
		//assign each individual agent with random genes
		for(int i = 0; i < population.length; i++) {
			population[i][0] = Math.random();
			population[i][1] = Math.random();
			population[i][2] = Math.random();
		}
		
		for(int g = 0; g < GENERATIONS; g++) {
			//this array stores the performance of each agent. using a Pair object is a simple pattern 
			//to sort an array of values without loosing track of its past indexes
			Pair[] fitness = new Pair[50];
			for(int i = 0; i < fitness.length; i++) {
				fitness[i] = new Pair(i);
			}
			
			
			//each agent plays 10 games
			for(int i = 0; i < 50; i ++) {
				agent = new DummyAgent(population[i]);			
				for(int j = 0; j < 10; j++) {
					restart();
					fitness[i].setValue(fitness[i].getValue() + score);
				}					
			}
			
			//sorts the fitness array in descending order
			Arrays.sort(fitness);
			
			//the 50 fittest genes are stored in an array of "elites"
			double[][] elites = new double[25][3];
			
			for(int i = 0; i < elites.length; i++) {
				elites[i][0] = population[fitness[i].getIndex()][0];
				elites[i][1] = population[fitness[i].getIndex()][1];
				elites[i][2] = population[fitness[i].getIndex()][2];
			}
			
			//now these elite genomes procreate with a random partner 
			double[][] children = new double[25][3];
			for(int i = 0; i < children.length; i++) {
				int r = (int) (Math.random()*25);
				children[i] = procreate(elites[r],elites[r]);
			}
			
			//creates a new population consisting of the elite genomes and their children
			population = new double[50][3]; 
			System.arraycopy(elites, 0, population, 0, elites.length);
			System.arraycopy(children, 0, population, 0, children.length);	
		}
		
		//the 0th index is the elite gene with the best performance
		double[] fittestGenes = population[0];
		
		return fittestGenes;
	}
	
	public double[] procreate(double[] father, double[] mother) {
		//returns child genes
		double[] child = new double[3];
		
		//chooses a gene randomly from the father and the mothers side
		Random ran = new Random();
		child[0] = (ran.nextInt() % 2 == 0) ? father[0] : mother[0];
		child[1] = (ran.nextInt() % 2 == 0) ? father[1] : mother[1];
		child[2] = (ran.nextInt() % 2 == 0) ? father[2] : mother[2];
		
		//mutates each gene via a mutationstep		
		if(Math.random()<MUTATION_RATE) {
			child[0] += child[0]*MUTATION_STEP;
		}
		if(Math.random()<MUTATION_RATE) {
			child[1] += child[1]*MUTATION_STEP;
		}
		if(Math.random()<MUTATION_RATE) {
			child[2] += child[2]*MUTATION_STEP;
		}
		
		return child;
	}
	
	@Override //neither a gamcecycle, view nor controlls are needed
	public void setupGame() {
		gridMatrix = new Color[HEIGHT][WIDTH];
    	PentominoGenerator startGenerator = new PentominoGenerator();
        nextBlock = startGenerator.getRandomPentomino();   
        System.out.println("setup training");
	}
	
	@Override //This method is overridden because we do not need the isRunning variable here
	public void spawn() {
		fallingBlock = nextBlock;
    	PentominoGenerator startGenerator = new PentominoGenerator();
        nextBlock = startGenerator.getRandomPentomino();   
        
        agent.makeMove(this);
	}
	
	@Override //the agent directly places the falling block on the grid. in some cases a moveDown is still needed though
	public void move(Direction aDirection){
        if(!doesCollide(aDirection)) {
            int[][] changeCoords = fallingBlock.getCoordinates();     		
                for(int i = 0; i < changeCoords[1].length; i++){
                    changeCoords[1][i]++;
                }	
            fallingBlock.setCoordinates(changeCoords);
        }
	}
	
	@Override //after the block is placed we directly spawn the next block if there is no game over
	public void placePento(Pentomino aPentomino){
        int[][] whereToPlace = aPentomino.getCoordinates();
        Color colorIndex = aPentomino.getColorIndex();
        for(int i = 0; i < whereToPlace[0].length; i++){
            gridMatrix[whereToPlace[1][i]][whereToPlace[0][i]] = colorIndex;
        }
        clearRows();
        if(!gameOverCheck()) {
        	spawn();
        }
	}
	
	
	@Override 
	 public boolean gameOverCheck() {
        int[][] coordinates = nextBlock.getCoordinates();
        for (int i = 0; i < coordinates[0].length; i++){
           if(gridMatrix[coordinates[1][i]][coordinates[0][i]] != null) {       	
            return true;
            }              
    	} 
        return false;
    }
	
}
