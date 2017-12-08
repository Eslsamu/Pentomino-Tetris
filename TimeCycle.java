import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.util.Duration;


public class TimeCycle extends Thread {
	private PetrisGame game;
	private boolean isRunning;
	private long gravityDelay;
	private double levelUpScale;
	
	public TimeCycle(PetrisGame g) {
		game = g;
		isRunning = game.getIsRunning();
		gravityDelay = (long) game.getSpeed();
		System.out.println(gravityDelay);
	}
	
	@Override
	public void run() {
		while(isRunning) {
			try {
				System.out.println("runs");
				sleep(gravityDelay);
				game.move(Direction.DOWN);		
			}
			catch (InterruptedException e) {
				System.out.println("not running");
			}
		}
		System.out.println("not running");
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

