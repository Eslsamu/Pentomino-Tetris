package GameLogic;


public class DemoOOGame extends PetrisGame{
	
	//comments
    protected int[] fallOrder = {6, 3, 8, 7, 10, 9, 5, 11, 1, 4, 2, 0}; 
    protected int fallCounter = 0;
	
	public DemoOOGame() {
		super();
	}
	
	@Override 
	public void spawn() {
		if(this.fallCounter < this.fallOrder.length){
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
