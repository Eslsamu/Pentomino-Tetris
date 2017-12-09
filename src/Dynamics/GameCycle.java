package Dynamics;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import GameLogic.Direction;
import GameLogic.PetrisGame;
import Menu.MenuView;
import Setup.ScoreReader;
import GameView.GameOverView;
import GameView.MainView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;


public class GameCycle{
	
    //two timelines, one updates the game every 60ms, the other drops pento down starting from 500ms
    private Timeline update;
    private Timeline gameCycle;
    
    private PetrisGame game;
    private MainView gui;
    
    public GameCycle(PetrisGame g){
        this.game = g;
        //create an instance of GUI
    }
    
    public void updateGUI() throws FileNotFoundException, IOException{
        //this method updates the GUI whenever it is called
        //if gameOverCheck() returns false the two timelines will stop therefore the whole game stops
    	
    	gui = game.getView();
    	if(gui==null) {
    		System.out.println("kek");
    	}
        gui.updateMain();
        if(game.gameOverCheck()){
            //if game is lost stop Timeline and updated board for one last time
            update.stop();
            gameCycle.stop();
            
            gui.showGameOver();      
            
        }
    }
    
    public void pause() {
    		game.setIsRunning(false);
    		update.pause();
    		gameCycle.pause();
    }
    
    public Scene getScene(){
        //this method gets the Scene from the GUI and calls the cycle method which creates and starts the two timelines
        run();//TODO 
        //returns the scene to be used in Main class
        return gui.getScene();
    }
    
    public void run(){
        /* We create two timelines
            1 - updating the GUI
            2 - GameCycle (calls move down)*/
        update = new Timeline(new KeyFrame(
            Duration.millis(60),
            ae -> {
            try {
                updateGUI();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GameCycle.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GameCycle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }));
            update.setCycleCount(Timeline.INDEFINITE);
            update.play();
       
        gameCycle = new Timeline(new KeyFrame(
            Duration.millis(game.getSpeed()),
            ae -> playGame()));
            gameCycle.setCycleCount(Timeline.INDEFINITE);
            gameCycle.play();
    }
    
    public void playGame(){
        //this method is used in the gameCycle timeline, it basically creates a new timeline each time it is called
        //this is needed so that the speed is updated
        //first stop the timeline
        gameCycle.stop();
        //create a new one
        game.move(Direction.DOWN);
        gameCycle = new Timeline(new KeyFrame(
            Duration.millis(game.getSpeed()),
            ae -> playGame()));
        //start it
        gameCycle.play();
    }
    
    public Timeline getUpdate(){
        return update;
    }
    
    public Timeline getGameCycle(){
        return gameCycle;
    }
}