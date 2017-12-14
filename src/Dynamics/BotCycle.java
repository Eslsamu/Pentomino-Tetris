package Dynamics;

import Agent.Agent;
import GameLogic.DemoBotGame;
import GameLogic.Direction;
import GameLogic.PetrisGame;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class BotCycle extends GameCycle{
	
	private DemoBotGame bgame;
	private Agent agent;
	
	public BotCycle(PetrisGame g) {
		super(g);
		bgame = (DemoBotGame) game;
	}
	
	@Override 
	public void playGame(){
		agent = bgame.getAgent();
		
		agent.makeMove(bgame);
		
        ticker.stop();
        
        bgame.move(Direction.DOWN);
        
        ticker = new Timeline(new KeyFrame(
            Duration.millis(game.getSpeed()),
            ae -> playGame()));
        
        ticker.play();
    }
}
