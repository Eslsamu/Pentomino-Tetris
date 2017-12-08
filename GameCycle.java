package petris;

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
    
    BackendGrid backendGrid;
    GUI gui;
    
    public GameCycle(BackendGrid backendGrid){
        //use the instance of BackendGrid from Main
        this.backendGrid = backendGrid;
        //create an instance of GUI
        gui = new GUI(backendGrid);
        
        //start the game by spawning a pentomino
        //backendGrid.spawn();
    }
    
    public void updateGUI() throws FileNotFoundException, IOException{
        //this method updates the GUI whenever it is called
        //if gameOverCheck() returns false the two timelines will stop therefore the whole game stops
        gui.drawScore();
        gui.drawGrid();
        if(backendGrid.gameOverCheck()){
            //if game is lost stop Timeline and updated board for one last time
            update.stop();
            gameCycle.stop();
            gui.drawScore();
            gui.drawGrid();
            Score score = new Score();
            score.updateFile(backendGrid.getScore());
            //create an instance of main and draw the exit menu
            Menu menu = new Menu();
            menu.drawExitMenu(backendGrid);
        }
        else{
            gui.drawNextBlock();
        }
    }
    public Scene getScene(){
        //this method gets the Scene from the GUI and calls the cycle method which creates and starts the two timelines
        Cycle();
        //returns the scene to be used in Main class
        return gui.getScene();
    }
    public void Cycle(){
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
            Duration.millis(backendGrid.getSpeed()),
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
        backendGrid.move(Direction.DOWN);
        gameCycle = new Timeline(new KeyFrame(
            Duration.millis(backendGrid.getSpeed()),
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

