package endversion;

import javafx.animation.Animation;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Controlls implements EventHandler<KeyEvent>{
    PetrisGame game;
    
    public Controlls(PetrisGame game){
        this.game = game;
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
        if(event.getCode() == KeyCode.P){
           if(game.getIsRunning()) {
        	game.pause();
           }
           else{
        	game.runGame();
           }
        }
        if(event.getCode() == KeyCode.R){
            game.restart();
        }
    }
}