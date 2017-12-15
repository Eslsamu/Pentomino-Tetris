package GameView;
import GameLogic.PetrisGame;
import Setup.ScoreReader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * We call the classes that create graphical interface during the game, including scores, rules, next pentomino and player name.
 * @author Jordan, Basia, Stijn, Yvar, Sammuel, Blazej
 */
public class MainView extends GridPane{
    private PetrisGame game;
    
    /**
     * Constructor receives an instance of PetrisGame - g and assigns it to a
     * private variable game, which will be used in the class. We set size and gaps, we also add style sheets.
     * When the constructor is called we call the updateMain method.
     * @param g an instance of current running PetrisGame
     */
    public MainView(PetrisGame g){  
    	game = g;
        updateMain();
        setMaxSize(600, 750);
        setMinSize(600, 750);
        setHgap(10);
        getStylesheets().add("GameView/stylesheet.css");
    }
    	
    /**
     * This method creates an instance of GridView. We set alignment to the main GridPane
     * remove if something is in it. Then we add scores, rules, the game graphical interface, next pentomino
     * and player name to the GridPane.
     */
    public void updateMain() {
    	GridView gridView = new GridView(game);
        setAlignment(Pos.CENTER);
        getChildren().clear();
    	add(scoresAndRules(), 0, 0);
        add(gridView, 1, 0);
        add(nextPentoAndPlayerName(), 2, 0);
    }

    /**
     * Create a new GridPane that will combine the NextBlockView GridPane and 
     * the PlayerNameView and position them the way we want to.
     * @return nextPentoAndPlayerName a Parent that contains nextBlock and playerName
     */
    public Parent nextPentoAndPlayerName(){
        GridPane nextPentoAndPlayerName = new GridPane();
        NextBlockView nextBlock = new NextBlockView(game);
        PlayerNameView playerName = new PlayerNameView(game);
        nextPentoAndPlayerName.setAlignment(Pos.TOP_CENTER);
        nextPentoAndPlayerName.setMaxSize(175, 750);
        nextPentoAndPlayerName.setMinSize(175, 750);
        nextPentoAndPlayerName.add(nextBlock, 0, 0);
        nextPentoAndPlayerName.add(playerName, 0, 1);
        
        return nextPentoAndPlayerName;
    }
    /**
     * Does similar things to the nextPentoAndPlayerName. Create a new GridPane #
     * that will combine the ScoreView GridPane and 
     * the RulesView and position them the way we want to.
     * @return scoresRule a Parent that contains score and rules
     */
    public Parent scoresAndRules(){
        GridPane scoresRule = new GridPane();
        ScoreView score = new ScoreView(game);
        RulesView rules = new RulesView();
        scoresRule.setAlignment(Pos.TOP_CENTER);
        scoresRule.setMaxSize(175, 750);
        scoresRule.setMinSize(175, 750);
        scoresRule.setGridLinesVisible(false);
        scoresRule.add(score, 0, 0);
        scoresRule.add(rules, 0, 1);
        
        return scoresRule;
    }
    
    /**
     * If this method is called it will open a temporary stage displaying a game over menu.
     */
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