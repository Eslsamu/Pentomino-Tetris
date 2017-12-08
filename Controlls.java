package petris;

import javafx.animation.Animation;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Controlls{
    BackendGrid backendGrid;
    public Controlls(BackendGrid backendGrid){
        this.backendGrid = backendGrid;
    }
    public EventHandler setControls(){
        GameCycle gameCycle = new GameCycle(backendGrid);
    EventHandler<KeyEvent> eventHandler = (KeyEvent event) -> {
        if(event.getCode() == KeyCode.RIGHT) {
            backendGrid.move(Direction.RIGHT);
        }
        if(event.getCode() == KeyCode.LEFT) {
            backendGrid.move(Direction.LEFT);
        }
        if(event.getCode() == KeyCode.UP) {
            backendGrid.move(Direction.CLOCKWISE);
        }
        if(event.getCode() == KeyCode.DOWN) {
            backendGrid.move(Direction.COUNTERCLOCKWISE);
        }
        if(event.getCode() == KeyCode.SPACE){
            while(!backendGrid.doesCollide(Direction.DOWN)){
                backendGrid.move(Direction.DOWN);
            }
        }
        if(event.getCode() == KeyCode.P){
            if(gameCycle.getGameCycle().getStatus().compareTo(Animation.Status.PAUSED) == 0){
                gameCycle.getGameCycle().play();
                gameCycle.getUpdate().play();
            }
            else{
                gameCycle.getGameCycle().pause();
                gameCycle.getUpdate().pause();
            }
        }
        if(event.getCode() == KeyCode.R){
            backendGrid.restart();
        }
    };
        return eventHandler;
    }
}
