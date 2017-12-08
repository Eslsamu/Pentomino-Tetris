package endversion;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameCycle{
	
    //two timelines, one updates the game every 60ms, the other drops pento down starting from 500ms
    private static Timeline update;
    private static Timeline gameCycle;
    
    PetrisGame game;
    MainView gui;
    
    public GameCycle(PetrisGame game){
        //use the instance of PetrisGame from Main
        this.game = game;
        //create an instance of GUI
        gui = game.getView();
        
        //start the game by spawning a pentomino
        //game.spawn();
    }
    
    public void updateGUI() throws FileNotFoundException, IOException{
        //this method updates the GUI whenever it is called
        //if gameOverCheck() returns false the two timelines will stop therefore the whole game stops
        game.updateView();
        if(game.gameOverCheck()){
            //if game is lost stop Timeline and updated board for one last time
            update.stop();
            gameCycle.stop();
            Menu menu = new Menu();
            menu.drawExitMenu(game);
        }
    }
    
    public void pause() {
    		update.pause();
    		gameCycle.pause();
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
    public static Timeline getUpdate(){
        return update;
    }
    public static Timeline getGameCycle(){
        return gameCycle;
    }
}