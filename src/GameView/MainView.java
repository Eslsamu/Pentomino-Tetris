package GameView;
import GameLogic.PetrisGame;
import Setup.ScoreReader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainView extends GridPane{
    private PetrisGame game;
    
    //TODO comment
    public MainView(PetrisGame g){  
    	game = g;
        updateMain();
    	setGridLinesVisible(true);
        setMaxSize(600, 750);
        setMinSize(600, 750);
      //here we add the left rectangle containing scores and rules
        setHgap(10);
        
        getStylesheets().add("GameView/stylesheet.css");
    }
    //TODO comment
    public void updateMain() {
    	GridView gridView = new GridView(game);
        setAlignment(Pos.CENTER);
        getChildren().clear();
        //TODO comment
    	add(scoresAndRules(), 0, 0);
        //middle rectangle with gameBoard
        add(gridView, 1, 0);
        //right rectangle where nextPentomino is displayed
        add(nextPentoAndPlayerName(), 2, 0);
    }
    
    public Parent nextPentoAndPlayerName(){
        GridPane nextPentoAndPlayerName = new GridPane();
        NextBlockView nextBlock = new NextBlockView(game);
        PlayerNameView playerName = new PlayerNameView(game);
        nextPentoAndPlayerName.setAlignment(Pos.TOP_CENTER);
        nextPentoAndPlayerName.setMaxSize(175, 750);
        nextPentoAndPlayerName.setMinSize(175, 750);
        //adds scores on the top
        nextPentoAndPlayerName.add(nextBlock, 0, 0);
        //adds rules on the bottom
        nextPentoAndPlayerName.add(playerName, 0, 1);
        return nextPentoAndPlayerName;
    }
    public Parent scoresAndRules(){
        //creates the grid for scores and rules
        GridPane scoresRule = new GridPane();
        ScoreView score = new ScoreView(game);
        RulesView rules = new RulesView();
        scoresRule.setAlignment(Pos.TOP_CENTER);
        scoresRule.setMaxSize(175, 750);
        scoresRule.setMinSize(175, 750);
        scoresRule.setGridLinesVisible(false);
        //adds scores on the top
        scoresRule.add(score, 0, 0);
        //adds rules on the bottom
        scoresRule.add(rules, 0, 1);
        return scoresRule;
    }
    
    public void showGameOver() {
    	ScoreReader sr = new ScoreReader();
    	sr.updateFile(game.getScore(), game.getPlayerName());
    	
    	Stage temporaryStage = new Stage();
    	GameOverView gov = new GameOverView(game, temporaryStage);
    	  	
    	temporaryStage.setScene(new Scene(gov));
	    temporaryStage.setTitle("End game");
	        
	    temporaryStage.setWidth(400);
	    temporaryStage.setHeight(400);
	    temporaryStage.setResizable(false);
	        
	    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
	    temporaryStage.setX((primScreenBounds.getWidth() - temporaryStage.getWidth()) / 2);
	    temporaryStage.setY((primScreenBounds.getHeight() - temporaryStage.getHeight()) / 2);
	      
	    temporaryStage.show();
    }
    
}