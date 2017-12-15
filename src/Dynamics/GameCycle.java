package Dynamics;

import GameLogic.Direction;
import GameLogic.PetrisGame;
import GameView.MainView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * This class controls the cycle of the game, moving down and updating the graphical interface
 * at specific time intervals.
 * @author Jordan, Basia, Stijn, Yvar, Sammuel, Blazej
 */
public class GameCycle{
	
    //two timelines, one updates the game every 60ms, the other drops pento down starting from 500ms
    protected Timeline update;
    protected Timeline ticker;
    
    protected PetrisGame game;
    protected MainView gui;
    
    /**
     * Constructor receives an instance of PetrisGame - g and assigns it to a
     * private variable game, which will be used in the class.
     * @param g an instance of current running PetrisGame
     */
    public GameCycle(PetrisGame g){
        this.game = g;
    }
    
    /**
     * Create two time lines:
     * Update calls the method updateGUI every 60 milliseconds.
     * GameCycle calls the method playGame at time intervals it gets from PetrisGame
     */
    public void run(){
        update = new Timeline(new KeyFrame(
            Duration.millis(60),
            ae -> updateGUI()));
            update.setCycleCount(Timeline.INDEFINITE);
            update.play();
       
        ticker = new Timeline(new KeyFrame(
            Duration.millis(game.getSpeed()),
            ae -> playGame()));
            ticker.setCycleCount(Timeline.INDEFINITE);
            ticker.play();
    }
    
    /**
     * Whenever this method is called it gets the current instance of MainView and
     * updates the graphical interface of the game.
     * <p>
     * If gameOverCheck() return true, the game has ended we stop the time cycles.
     * After that we show a game over window.
     */
    public void updateGUI(){
    	gui = game.getView();
        gui.updateMain();
        if(game.gameOverCheck()){
            update.stop();
            ticker.stop();
            gui.showGameOver();
        }
    }
     /**
     * Whenever this method is called it will stop the current gameCycle time line,
     * it will move the pentomino 1 row down and will create a new time line with a new interval, 
     * so that whenever we level up we can increase the speed. In the end we play the time line again.
     */
    public void playGame(){
        //this method is used in the timer timeline, it basically creates a new timeline each time it is called
        //this is needed so that the speed is updated
    	
        //first stop the timeline
        ticker.stop();
        //create a new one
        game.move(Direction.DOWN);       
        ticker = new Timeline(new KeyFrame(
            Duration.millis(game.getSpeed()),
            ae -> playGame()));
        //start it
        ticker.play();
    }
    
    /**
     * Method pause will pause the time lines and will set the isRunning parameter in PetrisGame to false.
     * Just in any case we will update the GUI again, to show the latest PetrisGame and to show a sign that the game is paused.
     */
    public void pause() {
        update.pause();
        ticker.pause();
        game.setIsRunning(false);
        gui.updateMain();        
    }
}
