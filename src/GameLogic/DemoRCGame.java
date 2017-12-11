package GameLogic;


public class DemoRCGame extends PetrisGame{
	
	//comments
    protected int[] fallOrder = {5, 6, 0, 0}; 
    protected int fallCounter = 0;
	
	public DemoRCGame() {
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
	