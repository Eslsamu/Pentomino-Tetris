
public class TimeCycle extends Thread {//maybe we need to work with THREAD & synchronized
	private BackendGrid backendGrid;
	private boolean isRunning;
	private long gravityDelay;
	private double levelUpScale;
	
	public TimeCycle(BackendGrid backendGrid, int initialGravityDelay, double levelUpScale) {
		this.backendGrid = backendGrid;
		this.gravityDelay = initialGravityDelay;
		this.isRunning = true;
		this.levelUpScale = levelUpScale;
	}
	
	@Override
	public void run() {
		while(isRunning) {
			try {
				sleep(gravityDelay);
				backendGrid.move(Direction.DOWN);
				/*int r = (int) (Math.random()*2); //to test random moves in the terminal
				
				switch (r) {
				case 0: backendGrid.move(Direction.CLOCKWISE); break;
				case 1: backendGrid.move(Direction.LEFT); break;
				case 2: backendGrid.move(Direction.RIGHT); break;
				}
				
				backendGrid.testPrint(backendGrid.getFallingBlock()); //TODO: replace*/
			}
			catch (InterruptedException e) {
				
			}
		}
	}
	
	public void stopCycle() {
		isRunning = false;
		interrupt();
	}
	
	public void resetCycle() {//after moving the block
		interrupt();
	}
	
	public void levelUp() {
		gravityDelay = (long) (gravityDelay * levelUpScale);
		interrupt();
	}
}
