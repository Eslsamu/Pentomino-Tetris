package Dynamics;

import GameLogic.Direction;
import GameLogic.PetrisGame;
import javafx.animation.Animation;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Controlls implements EventHandler<KeyEvent>{
    PetrisGame game;
    
    public Controlls(PetrisGame g){
        this.game = g;
    }
    
    @Override
    public void handle(KeyEvent event){
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
            while(!game.doesCollide(Direction.DOWN)){
               game.move(Direction.DOWN);
            }
        }
        //added Pause
        if(event.getCode() == KeyCode.P){
           if(game.getIsRunning()) {
        	game.getCycle().pause();
           }
           else {
        	game.runGame();
           }
        }
    }
}