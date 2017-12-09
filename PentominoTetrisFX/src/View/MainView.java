package View;
import GameLogic.PetrisGame;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class MainView extends GridPane{

    private GridView gridView;
    
    private PetrisGame game;
    
    private GridPane score;
    
    //pane that displays the next block
    private NextBlockView nextBlock;
    //TODO comment
    public MainView(PetrisGame g){  
    	game = g;
        updateMain();
    	
        setMinSize(600,500);
        setPadding(new Insets(0, 0, 10, 20));
        setVgap(5); 
        setHgap(5);
      //here we add the left rectangle containing scores and rules
        
        getStylesheets().add("petris/stylesheet.css");
    }
    //TODO comment
    public void updateMain() {
    	gridView = new GridView(game);
        nextBlock = new NextBlockView(game);
        score = new GridPane();
        getChildren().clear();
        //TODO comment
    	add(scoresRule(), 0, 0);
        //middle rectangle with gameBoard
        add(gridView, 1, 0);
        //right rectangle where nextPentomino is displayed
        add(nextBlock, 2, 0);
    }

    public Parent scoresRule(){
        //creates the grid for scores and rules
        GridPane scoresRule = new GridPane();
        ScoreView score = new ScoreView(game);
        RulesView rules = new RulesView();
        
        scoresRule.setMinSize(125, 730);
        scoresRule.setPadding(new Insets(0, 0, 0, 0));
        scoresRule.setHgap(5);
        scoresRule.setGridLinesVisible(false);
        //adds scores on the top
        scoresRule.add(score, 0, 0);
        //adds rules on the bottom
        scoresRule.add(rules, 0, 1);
        return scoresRule;
    }

    
}