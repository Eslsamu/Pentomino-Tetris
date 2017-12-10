package GameLogic;

public class DemoOOGame extends PetrisGame{
	
	//comments
    private int[] fallOrder; //TODO needs to be defined 
    private int fallCounter;
	
	public DemoOOGame() {
		super();
		fallOrder = new int[]{6, 3, 8, 7, 10, 9, 5, 11, 1, 4, 2, 0};
		fallCounter = 0;
	}
	
	@Override 
	public void spawn() {
		if(fallCounter < fallOrder.length){
			fallCounter++;
		}
		else {
			fallCounter = 0;
		}
		fallingBlock = nextBlock;
		PentominoGenerator startGenerator = new PentominoGenerator();
		nextBlock = startGenerator.getTestPentomino(fallOrder[fallCounter]);
	}
	
}
