package petris;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Menu {
    private Main main;
    
    public Menu(){
        main = new Main();
    }
    public Scene drawStartMenu() {  
        Button start = new Button("Play");
        start.setMinSize(150, 50);
        start.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        Button presentation = new Button("Presentation menu");
        presentation.setMinSize(150, 50);
        presentation.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        Button highscore = new Button("Highscore");
        highscore.setMinSize(150, 50);
        highscore.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        Button exit = new Button("Exit");
        exit.setMinSize(150, 50);
        exit.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        GridPane menu = new GridPane();
        
        menu.setAlignment(Pos.CENTER);
        menu.setHalignment(start, HPos.CENTER);
        menu.setHalignment(highscore, HPos.CENTER);
        menu.setHalignment(exit, HPos.CENTER);
        menu.setVgap(10);
       
        start.setOnAction(new EventHandler<ActionEvent>(){
             @Override public void handle(ActionEvent e) {
                //use the Stage from main class
                Stage primaryStage = main.getStage();
                //create an instance of BackendGrid and use it in GameCycle
                BackendGrid backendGrid = new BackendGrid();
                backendGrid.spawn();
                GameCycle gameCycle = new GameCycle(backendGrid);
                //change Scene to scene from GameCycle
                primaryStage.setScene(gameCycle.getScene());
                primaryStage.setWidth(700);
                primaryStage.setHeight(800);
                //this following code places the Window in the centre
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
             }
        });
        presentation.setOnAction(new EventHandler<ActionEvent>(){
             @Override public void handle(ActionEvent e) {
                //use the Stage from main class
                Stage primaryStage = main.getStage();
                primaryStage.setScene(drawPresentationMenu());
                primaryStage.setWidth(400);
                primaryStage.setHeight(400);
                //this following code places the Window in the centre
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
             }
        });
        highscore.setOnAction(new EventHandler<ActionEvent>() {
             @Override public void handle(ActionEvent e) {
                Stage primaryStage = main.getStage();
                
                primaryStage.setScene(drawHighscoreList());
                
                primaryStage.setWidth(400);
                primaryStage.setHeight(400);
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
             
             }
        });
        exit.setOnAction(new EventHandler<ActionEvent>(){
             @Override public void handle(ActionEvent e) {
                Stage primaryStage = main.getStage();
                primaryStage.close();
                System.exit(0);
                
             }
        });
        menu.add(start, 0, 0);
        menu.add(presentation, 0, 1);
        menu.add(highscore, 0, 2);
        menu.add(exit, 0, 3);
        menu.setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, Insets.EMPTY)));
        
        //create the Scene
        Scene startMenu = new Scene(menu);
        
        return startMenu;
    }
    
    
    public Scene drawHighscoreList() {
        Score score = new Score();
        GridPane highscoreList = new GridPane();
        String[] names = score.getNames();
        int[] scores = score.getScores();
        
        if(names[0] == null){
            System.out.println("Check");
            Label scoreLabel = new Label("No data available!");
            scoreLabel.setFont(new Font("Arial", 25));
            scoreLabel.setTextFill(Color.DARKRED);
            highscoreList.add(scoreLabel, 0, 0);
            highscoreList.setHalignment(scoreLabel, HPos.CENTER);
            highscoreList.setVgap(20);
        }
        else{
            for(int i = 0; i < names.length; i++){
                if(!names[i].equals("null")){
                    Label scoreLabel = new Label((i + 1) + ": " + names[i] + " has score: " + scores[i]);
                    scoreLabel.setFont(new Font("Arial", 16));
                    highscoreList.add(scoreLabel, 0, i);
                    highscoreList.setHalignment(scoreLabel, HPos.CENTER);
                    highscoreList.setVgap(10);
                }
            }   
        }
        
        Button back = new Button("Menu");
        back.setMinSize(150, 50);
        back.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        highscoreList.add(back, 0, 10);
        
        highscoreList.setAlignment(Pos.CENTER);
        highscoreList.setHalignment(back, HPos.CENTER);
        
        back.setOnAction(new EventHandler<ActionEvent>(){
         @Override 
            public void handle(ActionEvent e) {
                Stage primaryStage = main.getStage();

                primaryStage.setScene(drawStartMenu());
                primaryStage.setWidth(400);
                primaryStage.setHeight(400);

                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
         }
        });
        
        highscoreList.setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, Insets.EMPTY)));
        
        Scene list = new Scene(highscoreList);
    
        return list;
    }
    
    public void drawExitMenu(BackendGrid backendGrid) {
        Stage temporaryStage = new Stage();
        
        Label gameLost = new Label("Sorry, but you lost!");
        gameLost.setFont(new Font("Arial", 25));
        gameLost.setTextFill(Color.DARKRED);
        gameLost.setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, Insets.EMPTY)));
        
        Button start = new Button("Play again");
        start.setMinSize(150, 50);
        start.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        Button startMenu = new Button("Menu");
        startMenu.setMinSize(150, 50);
        startMenu.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        Button exit = new Button("Exit");
        exit.setMinSize(150, 50);
        exit.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        GridPane menu = new GridPane();
    
        menu.setVgap(25);
        menu.setAlignment(Pos.CENTER);
        menu.setHalignment(start, HPos.CENTER);
        menu.setHalignment(startMenu, HPos.CENTER);
        menu.setHalignment(exit, HPos.CENTER);
        
        start.setOnAction(new EventHandler<ActionEvent>(){
             @Override 
                public void handle(ActionEvent e) {
                    backendGrid.restart();
                    GameCycle gameCycle = new GameCycle(backendGrid);
                    
                    Stage primaryStage = main.getStage();
                    
                    primaryStage.setScene(gameCycle.getScene());
                    primaryStage.setWidth(700);
                    primaryStage.setHeight(800);
                    
                    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                    primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                    primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
                    
                    temporaryStage.close();
             }
        });
        startMenu.setOnAction(new EventHandler<ActionEvent>(){
            @Override 
            public void handle(ActionEvent e) {
                Stage primaryStage = main.getStage();
                
                primaryStage.setScene(drawStartMenu());
                
                primaryStage.setWidth(400);
                primaryStage.setHeight(400);
                
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
                
                temporaryStage.close();
             }
        });
        exit.setOnAction(new EventHandler<ActionEvent>(){
             @Override
             public void handle(ActionEvent e) {
                Stage primaryStage = main.getStage();
                primaryStage.close();
                temporaryStage.close();
                System.exit(0);
             }
        });
        menu.add(gameLost, 0, 0);
        menu.add(start, 0, 1);
        menu.add(startMenu, 0, 2);
        menu.add(exit, 0, 3);
        
        menu.setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, Insets.EMPTY)));
        
        temporaryStage.setScene(new Scene(menu));
        temporaryStage.setTitle("End game");
            
        temporaryStage.setWidth(400);
        temporaryStage.setHeight(400);
        temporaryStage.setResizable(false);
            
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        temporaryStage.setX((primScreenBounds.getWidth() - temporaryStage.getWidth()) / 2);
        temporaryStage.setY((primScreenBounds.getHeight() - temporaryStage.getHeight()) / 2);
          
        temporaryStage.show();
    }
    public Scene drawPresentationMenu(){
        Button bot = new Button("Bot");
        bot.setMinSize(150, 50);
        bot.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        Button optimalOrdering = new Button("Optimal ordering");
        optimalOrdering.setMinSize(150, 50);
        optimalOrdering.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        Button clearRow = new Button("Clearing row");
        clearRow.setMinSize(150, 50);
        clearRow.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        Button startMenu = new Button("Menu");
        startMenu.setMinSize(150, 50);
        startMenu.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        GridPane menu = new GridPane();
        
        menu.setAlignment(Pos.CENTER);
        menu.setHalignment(bot, HPos.CENTER);
        menu.setHalignment(optimalOrdering, HPos.CENTER);
        menu.setHalignment(clearRow, HPos.CENTER);
        menu.setHalignment(startMenu, HPos.CENTER);
        menu.setVgap(10);
        
        optimalOrdering.setOnAction(new EventHandler<ActionEvent>(){
             @Override public void handle(ActionEvent e) {
                //use the Stage from main class
                Stage primaryStage = main.getStage();
                //create an instance of BackendGrid and use it in GameCycle
                BackendGrid backendGrid = new BackendGrid("OptimalOrder");
                backendGrid.spawn();
                GameCycle gameCycle = new GameCycle(backendGrid);
                //change Scene to scene from GameCycle
                primaryStage.setScene(gameCycle.getScene());
                primaryStage.setWidth(700);
                primaryStage.setHeight(800);
                //this following code places the Window in the centre
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
             }
        });
        
        clearRow.setOnAction(new EventHandler<ActionEvent>(){
             @Override public void handle(ActionEvent e) {
                //use the Stage from main class
                Stage primaryStage = main.getStage();
                //create an instance of BackendGrid and use it in GameCycle
                BackendGrid backendGrid = new BackendGrid("ClearRow");
                backendGrid.spawn();
                GameCycle gameCycle = new GameCycle(backendGrid);
                //change Scene to scene from GameCycle
                primaryStage.setScene(gameCycle.getScene());
                primaryStage.setWidth(700);
                primaryStage.setHeight(800);
                //this following code places the Window in the centre
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
             }
        });
        
        startMenu.setOnAction(new EventHandler<ActionEvent>(){
            @Override 
            public void handle(ActionEvent e) {
                Stage primaryStage = main.getStage();
                
                primaryStage.setScene(drawStartMenu());
                
                primaryStage.setWidth(400);
                primaryStage.setHeight(400);
                
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
             }
        });
        
        menu.setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, Insets.EMPTY)));
        
        menu.add(bot, 0, 0);
        menu.add(optimalOrdering, 0, 1);
        menu.add(clearRow, 0, 2);
        menu.add(startMenu, 0, 3);
        
        
        //create the Scene
        Scene presentationMenu = new Scene(menu);
        
        return presentationMenu;
    }
}


