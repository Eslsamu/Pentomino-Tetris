package Menu;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import Agent.TrainEnvironment;
import GameLogic.DemoBotGame;
import GameLogic.DemoOOGame;
import GameLogic.DemoRCGame;
<<<<<<< HEAD
import GameLogic.PetrisGame;
=======
>>>>>>> 3b96da34955ac363863d5cf895f79ea7e6cd26ad
import Setup.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
	
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class DemoMenuView extends GridPane{
	
	private Main main;
        private Stage temporaryStage;
	
	public DemoMenuView() {
	temporaryStage = new Stage();
            
        Button bot = new Button("Bot");
        bot.setMinSize(150, 50);
        bot.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        

        Button training = new Button("Training");
        bot.setMinSize(150, 50);
        bot.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");

        ToggleGroup group = new ToggleGroup();
        
        RadioButton first = new RadioButton("Initial genes");
        first.setToggleGroup(group);
        first.setSelected(true);
 
        RadioButton perfect = new RadioButton("Improved genes");
        perfect.setToggleGroup(group);
        
        RadioButton custom = new RadioButton("Custom genes");
        custom.setToggleGroup(group);
        
        Button optimalOrdering = new Button("Optimal ordering");
        optimalOrdering.setMinSize(150, 50);
        optimalOrdering.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        CheckBox withBot = new CheckBox("with bot");
        
        Button clearRow = new Button("Clearing row");
        clearRow.setMinSize(150, 50);
        clearRow.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        
        Button startMenu = new Button("Menu");
        startMenu.setMinSize(150, 50);
        startMenu.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        
        setAlignment(Pos.CENTER);
        setHalignment(bot, HPos.CENTER);
        setHalignment(training, HPos.CENTER);
        setHalignment(optimalOrdering, HPos.CENTER);
        setHalignment(withBot, HPos.CENTER);
        setHalignment(clearRow, HPos.CENTER);
        setHalignment(startMenu, HPos.CENTER);
        setVgap(10);
        
        
        bot.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
               if(first.isSelected()){
                   Stage primaryStage = main.getStage();
                    //create an instance of BackendGrid and use it in GameCycle
                    DemoBotGame game = new DemoBotGame();
                    game.spawn();//TODO shouldnt be like this 
                    game.runGame();
                    //change Scene to scene from GameCycle
                    primaryStage.setScene(game.getScene());
                    primaryStage.setWidth(650);
                    primaryStage.setHeight(800);

                    //this following code places the Window in the centr
                    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                    primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                    primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
               }
               else if(perfect.isSelected()){
                   Stage primaryStage = main.getStage();
                    //create an instance of BackendGrid and use it in GameCycle
                    DemoBotGame game = new DemoBotGame();
                    game.spawn();//TODO shouldnt be like this 
                    game.runGame();
                    //change Scene to scene from GameCycle
                    primaryStage.setScene(game.getScene());
                    primaryStage.setWidth(650);
                    primaryStage.setHeight(800);

                    //this following code places the Window in the centr
                    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                    primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                    primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
               }
               else if(custom.isSelected()){
                   temporaryStage.setScene(setGenes());
                   temporaryStage.setWidth(300);
                   temporaryStage.setHeight(200);
                   Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                   temporaryStage.setX((primScreenBounds.getWidth() - temporaryStage.getWidth()) / 2);
                   temporaryStage.setY((primScreenBounds.getHeight() - temporaryStage.getHeight()) / 2);
                   temporaryStage.show();
               }
            }
       });
        
        training.setOnAction(new EventHandler<ActionEvent>() {
        	@Override public void handle(ActionEvent e) {
                TrainEnvironment te = new TrainEnvironment();              
             }
        });
        
        
        optimalOrdering.setOnAction(new EventHandler<ActionEvent>(){
             @Override public void handle(ActionEvent e) {
                Stage primaryStage = main.getStage();
                if(withBot.isSelected()){
                    //create an instance of BackendGrid and use it in GameCycle
                    DemoBotGame game = new DemoBotGame(true);
                    game.spawn();//TODO shouldnt be like this 
                    game.runGame();
                    //change Scene to scene from GameCycle
                    primaryStage.setScene(game.getScene());   
                }
                else{
                    //create an instance of BackendGrid and use it in GameCycle
                    DemoOOGame game = new DemoOOGame("OptimalOrder");
                    game.spawn();//TODO shouldnt be like this 
                    game.runGame();
                    //change Scene to scene from GameCycle
                    primaryStage.setScene(game.getScene());
                }
                primaryStage.setWidth(650);
                primaryStage.setHeight(800);
                
                //this following code places the Window in the centr
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
             }
        });
        
        clearRow.setOnAction(new EventHandler<ActionEvent>(){
             @Override public void handle(ActionEvent e) {
                Stage primaryStage = main.getStage();
                //create an instance of BackendGrid and use it in GameCycle
                DemoRCGame game = new DemoRCGame("RowClear");
                game.spawn();//TODO shouldnt be like this 
                game.runGame();
                //change Scene to scene from GameCycle
                primaryStage.setScene(game.getScene());
                primaryStage.setWidth(650);
                primaryStage.setHeight(800);
                //this following code places the Window in the centr
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
             }
        });
        
        startMenu.setOnAction(new EventHandler<ActionEvent>(){
            @Override 
            public void handle(ActionEvent e) {          	
                Stage primaryStage = main.getStage();
                MenuView menu = new MenuView();
                Scene scene = new Scene(menu);
                
                primaryStage.setScene(scene);
                
                primaryStage.setWidth(400);
                primaryStage.setHeight(400);
                
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
                
             }
        });
        
        setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, Insets.EMPTY)));
        GridPane radioButtons = new GridPane();
        radioButtons.setAlignment(Pos.CENTER);
        radioButtons.setHgap(10);
        radioButtons.add(first, 0, 0);
        radioButtons.add(perfect, 1, 0);
        radioButtons.add(custom, 2, 0);
    
        add(bot, 0, 0);
        add(training, 1,0);
        add(radioButtons, 0, 1);
        add(optimalOrdering, 0, 2);
        add(withBot, 0, 3);
        add(clearRow, 0, 4);
        add(startMenu, 0, 5);

	}
        public Scene setGenes(){
            GridPane setGenes = new GridPane();
            setGenes.setVgap(10);
            Label set = new Label("Enter 4 genes:");
            TextField enter = new TextField();
            set.setFont(new Font("Arial", 18));
            Label warning = new Label("Genes must be doubles and separated with space!");
            warning.setFont(new Font("Arial", 11));
            warning.setTextFill(Color.DARKRED);
            enter.setPrefColumnCount(8);
            Button submit = new Button("Start bot");
            submit.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
            setGenes.add(set, 0, 0);
            setGenes.add(enter, 0, 1);
            setGenes.add(submit, 0, 2);
            setGenes.setAlignment(Pos.CENTER);
            setGenes.setHalignment(set, HPos.CENTER);
            setGenes.setHalignment(enter, HPos.CENTER);
            setGenes.setHalignment(submit, HPos.CENTER);
            setGenes.setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, Insets.EMPTY)));
            
            submit.setOnAction(new EventHandler<ActionEvent>() {
             @Override
                public void handle(ActionEvent e) {
                    String genesRaw = enter.getText();
                    String[] genesSplit = genesRaw.split("\\s+");
                    double[] genes = new double[genesSplit.length];
                    for(int i = 0; i < genes.length; i++){
                        try{
                            genes[i] = Double.parseDouble(genesSplit[i]);
                        }
                        catch(Exception ex){
                            setGenes.getChildren().remove(submit);
                            setGenes.getChildren().remove(warning);
                            setGenes.add(warning, 0, 2);
                            setGenes.add(submit, 0, 3);
                        }
                    }
                    if(genes.length != 4){
                        setGenes.getChildren().remove(submit);
                        setGenes.getChildren().remove(warning);
                        setGenes.add(warning, 0, 2);
                        setGenes.add(submit, 0, 3);
                    }
                    else{
                        temporaryStage.close();
                        Stage primaryStage = main.getStage();
                        //create an instance of BackendGrid and use it in GameCycle
                        DemoBotGame game = new DemoBotGame(genes);
                        game.spawn();//TODO shouldnt be like this 
                        game.runGame();
                        //change Scene to scene from GameCycle
                        primaryStage.setScene(game.getScene());
                        primaryStage.setWidth(650);
                        primaryStage.setHeight(800);

                        //this following code places the Window in the centr
                        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
                    }
                }    
            });
            
            return new Scene(setGenes);
        }
}
