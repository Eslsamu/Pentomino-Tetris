package Dynamics;

import GameLogic.Direction;
import GameLogic.PetrisGame;
import Menu.MenuView;
import Setup.Main;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 Controls class was made to separate handling key events in the game from the rest of the program.
 It simply describes how different keys pressed by the user influence the game.
 */
public class Controlls implements EventHandler<KeyEvent>{
    /**
     Game petris game affected by user actions.
     */
    PetrisGame game;

    /**
     Constructs a controls object which allows to control an arbitrary petris game.
     @param g petris game affected by user actions
     */
    public Controlls(PetrisGame g){
        this.game = g;
    }


    /**
        Determines action according to user's behaviour.
     @param event key pressed by the user
     */

    @Override
    public void handle(KeyEvent event){
        if(event.getCode() == KeyCode.P){
           if(game.getIsRunning()) {
        	game.pause();
           }
           else {
        	game.runGame();
           } 
        }
        else if(event.getCode() == KeyCode.ESCAPE){
            game.setIsRunning(false);
            Main main = new Main();
            Stage primaryStage = main.getStage();
            MenuView menu = new MenuView();
            Scene scene = new Scene(menu);
                
            primaryStage.setTitle("Petris");
            primaryStage.setScene(scene);
            primaryStage.setWidth(400);
            primaryStage.setHeight(400);
             
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
            primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
        }
        /**
         Keys which change the position of the falling block should only be active while the game is running
         */
        else if(game.getIsRunning()){
            if(event.getCode() == KeyCode.RIGHT) {
                game.move(Direction.RIGHT);
            }
            if(event.getCode() == KeyCode.LEFT) {
                game.move(Direction.LEFT);
            }
            if(event.getCode() == KeyCode.UP) {
                game.move(Direction.CLOCKWISE);
            }
            if(event.getCode() == KeyCode.DOWN) {
                game.move(Direction.COUNTERCLOCKWISE);
            }
            if(event.getCode() == KeyCode.SPACE){
                game.move(Direction.DROPDOWN);
            }
            if(event.getCode() == KeyCode.R){
                    game.restart();
            }
        }    
    }
}