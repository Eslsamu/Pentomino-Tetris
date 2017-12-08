package endversion; 

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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
    private GridPane nextBlockPane;
    
    public MainView(PetrisGame game){
        //use the same game from Main from GameCycle
        this.game = game;       
        //create a mainGrid where we add:
    	updateMain();
        
        setMinSize(600,500);
        setPadding(new Insets(0, 0, 10, 20));
        setVgap(5); 
        setHgap(5);
      //here we add the left rectangle containing scores and rules
        
        getStylesheets().add("endversion/stylesheet.css");
    }
    
    public void updateMain() {
    	gridView = new GridView();
        nextBlockPane = new GridPane();
        score = new GridPane();
    	getChildren().clear();
    	add(scoresRule(), 0, 0);
        //middle rectangle with gameBoard
        add(gridView, 1, 0);
        //right rectangle where nextPentomino is displayed
        add(drawNextBlock(), 2, 0);
    }

    public Parent scoresRule(){
        //creates the grid for scores and rules
        GridPane scoresRule = new GridPane();
        scoresRule.setMinSize(125, 730);
        scoresRule.setPadding(new Insets(0, 0, 0, 0));
        scoresRule.setHgap(5);
        scoresRule.setGridLinesVisible(false);
        //adds scores on the top
        scoresRule.add(drawScore(), 0, 0);
        //adds rules on the bottom
        scoresRule.add(rules(), 0, 1);
        return scoresRule;
    }
    
    public Parent drawScore(){
       //added
        score.getChildren().clear();

        score.setMinSize(125, 375);
        score.setPadding(new Insets(5, 5, 5, 5));
        score.setStyle("-fx-border-color: white; -fx-background-color: #9ac6d6; -fx-fill: #DAA520; fx-font-weight: bold; -fx-font-size: 11pt");

        Score scoreReader = new Score();
        int topScore = scoreReader.getScores()[0];
        int currentScore = game.getScore();
        if(topScore < currentScore){
            topScore = currentScore;
        }
        Text Highscore = new Text("Highest score:");
        Text highscore = new Text(topScore + "");
        Text Currentscore = new Text("Current score");
        Text currentscore = new Text(currentScore + "");
        Text RowsCleared = new Text("Rows cleared:");
        Text rowscleared = new Text(game.getRowsCleared() + "");
        Text Level = new Text("Level:");
        Text level = new Text(game.getLevel() + "");

        score.setAlignment(Pos.TOP_CENTER);
        score.add(Highscore, 0, 0);
        score.add(highscore, 0, 1);
        score.add(Currentscore, 0, 2);
        score.add(currentscore, 0, 3);
        score.add(RowsCleared, 0, 4);
        score.add(rowscleared, 0, 5);
        score.add(Level, 0, 6);
        score.add(level, 0, 7);

        return score;
    }
    
    public Parent rules(){
        //gridpane rules
        GridPane rules = new GridPane();
        rules.setMinSize(125, 375);
        rules.setGridLinesVisible(false);
        rules.setAlignment(Pos.TOP_LEFT);
        rules.setStyle("-fx-border-color: white; -fx-border-width: 0 1 1 1; -fx-background-color: #9ac6d6; -fx-fill: #DAA520; fx-font-weight: bold;");
        Text controlsHeader = new Text("Controls");
        controlsHeader.setStyle("-fx-fill: red; -fx-font-size: 12pt");
        Text controls = new Text("Movement: \nleft and right \narrow keys \nRotation: \nup and down \narrow keys \nDrop piece:\nspacebar \n");
        Text rulesHeader = new Text("Rules");
        rulesHeader.setStyle("-fx-fill: red; -fx-font-size: 12pt");
        Text rule = new Text("Earn score by \nclearing lines\nwhen the board \nfills up\nyou lose.\n1 line:  100\n2 lines: 300\n3 lines: 600 \n4 lines: 1000\n5 lines: 1500 ");
        
        rules.add(controlsHeader, 0,1);
        rules.add(controls, 0, 2);
        rules.add(rulesHeader, 0 ,3);
        rules.add(rule, 0, 4);
        
        return rules;
    }
    
    
    public Parent drawNextBlock(){
        nextBlockPane.getChildren().clear();
        nextBlockPane.setAlignment(Pos.TOP_CENTER);

        nextBlockPane.setMinSize(200, 300);
        nextBlockPane.setPadding(new Insets(20, 0, 0, 20));
        Text nextFallingBlock = new Text(450, 250, "Next pentomino:");
        nextBlockPane.setHalignment(nextFallingBlock, HPos.CENTER);
        
        nextBlockPane.getChildren().add(nextFallingBlock);
        int[][] nextBlockCoords = game.getNextBlock().getCoordinates();

        for(int i = 0; i < nextBlockCoords[0].length; i++) {
        	Rectangle blockSquare = new Rectangle(40,40);

        	blockSquare.setStroke(Color.BLACK);
        	nextBlockPane.getChildren().add(blockSquare);
        	blockSquare.setTranslateX(nextBlockCoords[0][i]*40 - 50);
        	blockSquare.setTranslateY(nextBlockCoords[1][i]*40 + 50);
        	blockSquare.setFill(game.getNextBlock().getColorIndex());
        }
        
        return nextBlockPane;
    }
    
    public class GridView extends Pane{
	    public GridView(){
	       
	    	getChildren().clear();
	    	setMinSize(250, 750);
	        
	        Color[][] grid = game.getGrid();
	        
	        //fill board with rectangles representing each coordinate of gridMatrix from game
	        for(int i = 0; i < grid.length ; i++){
	            for(int x = 0; x < grid[0].length; x++){
	                    Rectangle rec = new Rectangle(50, 50);
	                    //set the color adn other things
	                    rec.setFill(grid[i][x]);
	                    rec.setStroke(Color.BLACK);
	                    rec.setTranslateX(x * 50);
	                    rec.setTranslateY(i * 50);
	                    getChildren().add(rec);
	            }
	        }
	        //draw the falling block
	        if(game.getIsRunning()){
	            int[][] fallingBlockCoords =  game.getFallingBlock().getCoordinates();
	            Color color = game.getFallingBlock().getColorIndex();
	
	            for(int i = 0; i < fallingBlockCoords[0].length; i++) {
	                    Rectangle blockSquare = new Rectangle(50,50);
	
	                    getChildren().add(blockSquare);
	                    blockSquare.setStroke(Color.BLACK);
	                    blockSquare.setTranslateX(fallingBlockCoords[0][i]*50);
	                    blockSquare.setTranslateY(fallingBlockCoords[1][i]*50);
	                    blockSquare.setFill(color);
	            } 
	        }	    
	    }
    }
}