package Agent;


import java.util.Arrays;

import GameLogic.DemoBotGame;
import GameLogic.Direction;
import GameLogic.Pentomino;
import GameLogic.PentominoGenerator;
import GameLogic.PetrisGame;

import javafx.scene.paint.Color;

/**
 * The Trainenvironment is a subclass of the PetrisGame 
 * designed to run without a timer. Constructing this class 
 * will train an array of genes that function as a parameter 
 * for the agent.
 * 
 * @author Samuel
 *
 */
public class TrainEnvironment extends PetrisGame{
	/**
	 * Genetic operator used to maintain diversity in the 
	 * population. Used in combinition with a random variable.
	 */
	private static final double MUTATION_RATE = 0.5;
	/**
	 * The amount a gene can mutate.
	 */
	private static final double MUTATION_STEP = 0.1;
	/**
	 * The amount of iterations a population gets evolved.
	 */
	private static final double GENERATIONS = 10;
	/**
	 * The amount of games a single agent is playing in 
	 * a generation. Increasing results to the training 
	 * taking more time, but the fitness evaluation becoming
	 * less random.
	 */
	private static final int GAME_ITERATIONS = 25;
	/**
	 * The amount of agents in a population.
	 */
	private static final int POPULATION_SIZE = 50;
	/**
	 * The percentage of fit individuals that get selected inside a population
	 * based on their fitness level and that will recombine.
	 */
	private static final double ELITE_RATIO = 0.3;
	/**
	 * This variable get's assigned to the current agent playing the PetrisGame
	 */
	private DummyAgent agent;
	/**
	 * This variable get's assigned to the genes that are predefined in the DemoBotGame class,
	 * Mainly used to get the length of the gene array.
	 */
	private double[] genes;
	/**
	 * This variable get's assigned to the fittest individual in a population 
	 * in each generation.
	 */
	private DummyAgent fittestAgent;
	/**
	 * The constructor on this point starts the evolution and
	 * sets the static genes variable of the DBG class to the genes
	 * of the fittest individual at the end.
	 */
	public TrainEnvironment() {
		super();
		genes = DemoBotGame.getDNA();
		evolve();
		
		DemoBotGame.setDNA(fittestAgent.getGenes());
	}
	
	
	/**
	 * Essentially generates a population of random agents that will
	 * iteratively selected to recombine to optimize the score that 
	 * is archived in a PetrisGame.
	 */
	public void evolve() {
		//initialize a population of agents
		DummyAgent[] population = new DummyAgent[POPULATION_SIZE];
				
		//assign each individual agent with random genes
		for(int i = 0; i < population.length; i++) {
			double[] ranGenes = new double[genes.length];
			for(int j = 0; j < genes.length; j++) {
				ranGenes[j] = Math.random();
			}			
			population[i] = new DummyAgent(ranGenes);
		}
		
		for(int g = 0; g < GENERATIONS; g++) {						
			//each agent plays x games and their average score is assigned to them
			for(int i = 0; i < population.length; i ++) {
				agent = population[i];			
				for(int j = 0; j < GAME_ITERATIONS; j++) {
					restart();
					population[i].setScore(population[i].getScore() + (score/GAME_ITERATIONS));
				}					
			}
			
			//sorts the population in descending order comparing their score
			Arrays.sort(population);
			
			//prints the out the performance of this generation
			System.out.println("best: "+population[0].getScore());
			System.out.println("worst: "+population[population.length-1].getScore());
			
			double averagePerformance = 0;
			for(int i = 0; i < population.length; i++) {
				averagePerformance += population[i].getScore()/population.length;
			}
			System.out.println("average: "+averagePerformance);
			
			
			//x% of the fittest agents are stored in an array of "elites"
			DummyAgent[] elites = new DummyAgent[(int)(population.length*ELITE_RATIO)];		
			for(int i = 0; i < elites.length; i++) {
				elites[i] = population[i];
			}
			
			//x% of the population get's replaced by new children
			DummyAgent[] children = new DummyAgent[population.length-elites.length];
			
			//the two fittest agents procreate first
			children[0] = procreate(elites[0],elites[1]);
			children[1] = procreate(elites[0],elites[1]);
			
			//now the left elite agents procreate with a random partner 			
			for(int i = 2; i < children.length; i++) {
				int r1 = (int) (Math.random()*elites.length);
				int r2 = (int) (Math.random()*elites.length);
				children[i] = procreate(elites[r1],elites[r2]);
			}
			
			//creates a new population array consisting of the elite agents and their children
			for(int i = 0; i < elites.length; i++) {
				population[i] = elites[i];				
			}	
			for(int i = 0; i < children.length; i++) {
				population[elites.length+i] = children[i];
			}
			
			//resets the score of each agent in the population
			for(int i = 0; i < population.length; i++) {
				population[i].setScore(0);
			}
			
			//the 0th index is the agent with the best performance
			fittestAgent = population[0];
			
			for(int i = 0; i < fittestAgent.getGenes().length;i++) {
				System.out.println("Gene Nr:"+i+" = "+ fittestAgent.getGenes()[i]);
			}
		}		
	}
	
	/**
	 * Performs a uniform crossover of two individuals that gives
	 * the fitter gene a higher probability to get selected.
	 * The gene can be altered via the mutation rate. 
	 * 
	 * @param father A randomly or fitness based selected individual of the agent population.
	 * @param mother There is basically no difference in selecting a mother or father agent.
	 * @return A new DummyAgent object with recombined genes.
	 */
	public DummyAgent procreate(DummyAgent father, DummyAgent mother) {
		//new genomes are created
		double[] childGenes = new double[genes.length];
		
		//take the weighted average of each gene 		
		double fatherWeight = father.getScore();
		double motherWeight = mother.getScore();
		double total = fatherWeight + motherWeight;
		
		for(int i = 0; i < childGenes.length; i++) {			
			//give the crossover gene chosen randomly from the parent agents leaning to parent with a higher score			
			childGenes[i] = (Math.random() < fatherWeight/total) ? father.getGenes()[i] : mother.getGenes()[i];
			
			//adds via the mutationrate a mutationstep to each gene
			if(Math.random()<MUTATION_RATE) {
				childGenes[i] += childGenes[i]*MUTATION_STEP;
			}
		}

		DummyAgent child = new DummyAgent(childGenes);		
		return child;
	}
	
	/**
	 * Is called in the super constructor to setup the instance variables for 
	 * the PetrisGame. Here it does not initialize a view, controlls or a GameCycle.
	 */
	@Override 
	public void setupGame() {
		gridMatrix = new Color[HEIGHT][WIDTH];
    	PentominoGenerator startGenerator = new PentominoGenerator();
        nextBlock = startGenerator.getRandomPentomino();   
        System.out.println("setup training");
	}
	
	/**
	 * The only difference to the parent class 
	 * is the absence of the isRunning variable here. 
	 */
	@Override 
	public void spawn() {
		fallingBlock = nextBlock;
    	PentominoGenerator startGenerator = new PentominoGenerator();
        nextBlock = startGenerator.getRandomPentomino();   
        
        agent.makeMove(this);
	}
	
	/**
	 * Again only the absence of the isRunning variable makes the difference.
	 * Also there is no need to give options for other directions than down
	 * as the agent directly places the falling block into the grid.
	 */
	@Override 
	public void move(Direction aDirection){
        if(!doesCollide(aDirection)) {
            int[][] changeCoords = fallingBlock.getCoordinates();     		
                for(int i = 0; i < changeCoords[1].length; i++){
                    changeCoords[1][i]++;
                }	
            fallingBlock.setCoordinates(changeCoords);
        }
	}
	/**
	 * Again only the absence of the isRunning variable makes the difference.
	 * And no GameOver statement is printed.
	 */
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
