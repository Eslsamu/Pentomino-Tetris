package petris;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
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
        backendGrid.spawn();
    }
    
    public void updateGUI(){
        //this method updates the GUI whenever it is called
        //if gameOverCheck() returns false the two timelines will stop therefore the whole game stops
        if(backendGrid.gameOverCheck()){
            update.stop();
            gameCycle.stop();
        }
        else{
            gui.drawNextBlock();
        }
        //even if game has stopped we will still update the game one last time
        gui.drawScore();
        gui.drawGrid();
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
            ae -> updateGUI()));
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
    public Timeline getUpdate(){
        return update;
    }
    public Timeline getGameCycle(){
        return gameCycle;
    }
}
