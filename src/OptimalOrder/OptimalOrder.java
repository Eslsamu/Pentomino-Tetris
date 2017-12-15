package OptimalOrder;

import GameLogic.DemoOOGame;
import Setup.Main;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
/**
 * This class creates a Pane that will display a graphical representation of possible optimal orders 
* @author Jordan, Basia, Stijn, Yvar, Sammuel, Blazej
 */
public class OptimalOrder {
    private ArrayList<int[][]> boardCollection;
    private ArrayList<int[]> rowCollection;
    private ArrayList<int[]> sequence = new ArrayList();
    
    /**
     * Constructor makes an instance of FindOptimalOrder class and gets the boardCollection and rowCollection from it.
     */
    public OptimalOrder(){
        FindOptimalOrder find = new FindOptimalOrder();
        boardCollection = find.getOptimalOrder();
        rowCollection = find.getCoordinates();
    }
    /**
     * After getting the possible optimal orders, this method will create a Pane that contains all possible optimal orders the algorithm has found.
     * @return Pane, visual representation of optimal orders
     */
    public Pane getBoard(){
        Pane coloredBoard = new Pane();
        coloredBoard.setMinSize(600, 600);
        int size = boardCollection.size();
        if(size == 0){
            Label noData = new Label("No data");
            coloredBoard.getChildren().add(noData);
        }
        for(int n = 0; n < size; n++){
            int[] pentominoSequence = new int[12];
            int[][] board = boardCollection.get(n);
            int[] row = rowCollection.get(n);
            for(int s = 0; s < row.length; s+=2){
                if(row[s] - 1 >= 0){
                    pentominoSequence[row[s] - 1] = board[row[s]][row[s + 1]];
                }
                else{
                    pentominoSequence[row[s]] = board[row[s]][row[s + 1]];
                }
            }
            for(int i = 1; i < 13; i++){
                Color ranColor;
                do{
                ranColor = Color.rgb((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*254)); //254 to leave space for an exception store
                }
                while(ranColor.equals(Color.rgb(186, 216, 227)));
                for(int y = board.length - 1; y > - 1 ; y--){
                    for(int x = 0; x < board[0].length; x++){
                        if(board[y][x] == i){
                            Rectangle rec = new Rectangle(50, 50);
                            rec.setFill(ranColor);
                            rec.setStroke(Color.BLACK);
                            rec.setTranslateX((x * 50) + 275*n + 40);
                            rec.setTranslateY(y * 50);
                            coloredBoard.getChildren().add(rec);
                        }
                    }
                }
            }
            for(int y = board.length - 1; y > - 1 ; y--){
                for(int x = 0; x < board[0].length; x++){
                    int[] coordinates = topCoordinate(y, x, board);
                    if(checkPosition(coordinates[0], coordinates[1], board) && pentominoSequence[y] == 0){
                        if(checkIfAdded(pentominoSequence, board[y][x]) && checkBelow(board[y][x], board, pentominoSequence)){
                            pentominoSequence[y] = board[y][x];
                        }
                    }
                    if(x == 4 && pentominoSequence[y] == 0){
                        for(int p = 0; p < board[0].length; p++){
                            if(checkIfAdded(pentominoSequence, board[y][p]) && checkBelow(board[y][x], board, pentominoSequence)){
                                pentominoSequence[y] = board[y][p];
                            }
                        }
                    }        
                }
            }
            int[] temporarySequence = new int[pentominoSequence.length];
            for(int o = 0; o < pentominoSequence.length; o++){
                temporarySequence[o] = pentominoSequence[o];
            }
            sequence.add(temporarySequence);
        }
        return coloredBoard;
    }
    /**
     * This method is used to find the correct sequence of pentominoes falling.
     * @param y row of pentominoe coordinate we want to check for
     * @param z column of pentominoe coordinate we want to check for
     * @param board matrix representing board
     * @return boolean declaring if the pentominoe can be added
     */
    private boolean checkPosition(int y, int z, int[][] board){
        if(y - 1 < 0 || board[y - 1][z] == board[y][z]){
            return false;
        }
        int index = board[y - 1][z];
        for(int i = y; i <  board.length; i++){
            for(int x = 0; x <  board[0].length; x++){
                if(board[i][x] == index){
                    return true;
                }
            }
        }
        if(z - 1 >= 0){
            int index1 = board[y][z - 1];
            for(int i = y; i <  board.length; i++){
                for(int x = 0; x <  board[0].length; x++){
                    if(board[i][x] == index1){
                        return true;
                    }
                }    
            }
        }
        if(z + 1 >= board[0].length){
            int index1 = board[y][z + 1];
            for(int i = y; i <  board.length; i++){
                for(int x = 0; x <  board[0].length; x++){
                    if(board[i][x] == index1){
                        return true;
                    }
                }    
            }
        }
        return false;
    }
    /**
     * This method is used to find the correct falling sequence of pentominoes to form optimal order.
     * It finds the top coordinate of a pentominoe.
     * @param y representing row of pentominoe
     * @param x representing column of pentominoe
     * @param board matrix in which we will search for top coordinate of pentominoe
     * @return array containing top coordinates of pentominoe
     */
    private int[] topCoordinate(int y, int x, int[][] board){
        int coordinates[] = new int[2];
        for(int i = board.length -1; i > - 1; i--){
            for(int n = 0; n < board[0].length; n++){
                if(board[i][n] == board[y][x]){
                    coordinates[0] = i;
                    coordinates[1] = n;
                }
            }
        }
        return coordinates;
    }
    /**
     * This method is again used to find the correct falling sequence of pentominoes.
     * It goes through a list of sequence and checks if a pentominoe is already there
     * @param pentominoSequence array containing sequence of pentominoes
     * @param index of pentominoe we will search for in a list.
     * @return boolean declaring if the pentominoe is in the list
     */
    private boolean checkIfAdded(int[] pentominoSequence, int index){
        for(int p = 0; p < pentominoSequence.length; p++){
            if(index == pentominoSequence[p]){
               return false;    
            } 
        }
        return true;
    }
    /**
     * This method finds if there is  a pentominoe below a certain pentominoe that is not added in the sequence.
     * @param index of pentominoe we want to check under
     * @param board matrix representing board we are checking through
     * @param pentominoSequence list representing sequence of falling of pentominoes
     * @return boolean declaring if there is a pentominoe that is under the pentominoe we are checking for that is not added in the list
     */
    private boolean checkBelow(int index, int[][] board, int[] pentominoSequence){
        for(int y = board.length - 1; y > - 1 ; y--){
            for(int x = 0; x < board[0].length; x++){
                if(board[y][x] == index){
                    if(y + 1 < board.length && board[y+1][x] != index && checkIfAdded(pentominoSequence, board[y + 1][x])){
                       return false;
                    }
                }
            }
        }    
        return true;
    }
    /**
     * This method will create a visual representation of the pane with an option to see optimal order as an animation.
     * @return GridPane
     */
    public Parent OptimalOrderView(){
        GridPane view = new GridPane();
        view.setVgap(15);
        view.setAlignment(Pos.CENTER);
        Label pento = new Label("Optimal order:");
        pento.setFont(new Font("Arial", 25));
        pento.setTextFill(Color.DARKRED);
        view.add(pento, 0, 0);
        view.setHalignment(pento, HPos.CENTER);
        Pane boards = getBoard();
        view.add(boards, 0, 1);
        Button button = new Button("Optimal order animation");
        button.setMinSize(150, 50);
        button.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        view.add(button, 0, 2);
        view.setHalignment(boards, HPos.CENTER);
        view.setHalignment(button, HPos.CENTER);
        button.setOnAction(new EventHandler<ActionEvent>(){
            /**
             * If button is clicked it will start the bot
             * @param e ActionEvent, if the button is clicked it will trigger the code
             */ 
            @Override
            public void handle(ActionEvent e) {
                Main main = new Main();
                Stage primaryStage = main.getStage();
                DemoOOGame game = new DemoOOGame(sequence.get(sequence.size() -1));
                game.spawn();
                game.runGame();
                
                primaryStage.setScene(game.getScene());
                primaryStage.setWidth(650);
                primaryStage.setHeight(800);

                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
            }
        });
        view.setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, Insets.EMPTY)));
        return view;        
    }
}
