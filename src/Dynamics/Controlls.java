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

public class Controlls implements EventHandler<KeyEvent>{
    PetrisGame game;
    
    public Controlls(PetrisGame g){
        this.game = g;
    }
    
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