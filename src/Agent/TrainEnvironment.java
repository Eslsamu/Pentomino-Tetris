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
	
	private static final double MUTATION_RATE = 0.2;
	private static final double MUTATION_STEP = 0.1;
	private static final double GENERATIONS = 10;
	
	private DummyAgent agent;

	public TrainEnvironment() {
		super();
		double[] genomes = evolve();
		System.out.println(genomes[0]);
		System.out.println(genomes[1]);
		System.out.println(genomes[2]);
		System.out.println(genomes[3]);
		
		DemoBotGame.setDNA(genomes);
	}
	
	
	
	public double[] evolve() {
		//initialize a population of 50 agents
		double[][] population = new double[50][4];
		
		//the genomes that had the best score after all iterations
		double[] fittestAgent = new double[4];
				
		//assign each individual agent with random genes
		for(int i = 0; i < population.length; i++) {
			population[i][0] = 0.5;
			population[i][1] = 0.5;
			population[i][2] = 0.5;
			population[i][3] = 0.5;
		}
		
		for(int g = 0; g < GENERATIONS; g++) {
			//this array stores the performance of each agent. using a Pair object is a simple pattern 
			//to sort an array of values without loosing track of its past indexes
			Pair[] fitness = new Pair[population.length];
			for(int i = 0; i < fitness.length; i++) {
				fitness[i] = new Pair(i);
			}
			
			
			//each agent plays 100 games it's perfomance is avaraged and saved in the fitness array
			for(int i = 0; i < fitness.length; i ++) {
				agent = new DummyAgent(population[i]);			
				for(int j = 0; j < 100; j++) {
					restart();
					fitness[i].setValue(fitness[i].getValue() + (score/100));
				}					
			}
			
			//sorts the fitness array in descending order
			Arrays.sort(fitness);
			
			//the 0th index is the agent with the best performance
			fittestAgent = population[fitness[0].getIndex()];
			
			//prints the out the performance of this generation
			System.out.println("best: "+fitness[0].getValue());
			System.out.println("worst: "+fitness[49].getValue());
			
			double averagePerformance = 0;
			for(int i = 0; i < fitness.length; i++) {
				averagePerformance += fitness[i].getValue()/fitness.length;
			}
			System.out.println("average: "+averagePerformance);
			
			
			//50% of the fittest genomes are stored in an array of "elites"
			double[][] elites = new double[population.length/2][4];
			
			for(int i = 0; i < elites.length; i++) {
				elites[i][0] = population[fitness[i].getIndex()][0];
				elites[i][1] = population[fitness[i].getIndex()][1];
				elites[i][2] = population[fitness[i].getIndex()][2];
				elites[i][3] = population[fitness[i].getIndex()][3];
			}
			
			//50% of the population get's replaced by new children
			double[][] children = new double[population.length/2][4];
			
			// the two fittest genomes procreate first
			children[0] = procreate(elites[0],elites[1]);
			children[1] = procreate(elites[0],elites[1]);
			
			//now the left elite genomes procreate with a random partner 			
			for(int i = 2; i < children.length; i++) {
				int r = (int) (Math.random()*elites.length);
				children[i] = procreate(elites[r],elites[r]);
			}
			
			//creates a new population consisting of the elite genomes and their children
			population = new double[50][4]; 
			for(int i = 0; i < population.length/2; i++) {
				population[i] = elites[i];
				population[i+population.length/2] = children[i];
			}	
			
		}
		
		
		
		return fittestAgent;
	}
	
	public double[] procreate(double[] father, double[] mother) {
		//returns child genes
		double[] child = new double[4];
		
		//chooses a gene randomly from the father or the mothers side
		Random ran = new Random();
		child[0] = (ran.nextInt() % 2 == 0) ? father[0] : mother[0];
		child[1] = (ran.nextInt() % 2 == 0) ? father[1] : mother[1];
		child[2] = (ran.nextInt() % 2 == 0) ? father[2] : mother[2];
		child[3] = (ran.nextInt() % 2 == 0) ? father[3] : mother[3];
		
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
		if(Math.random()<MUTATION_RATE) {
			child[3] += child[3]*MUTATION_STEP;
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
