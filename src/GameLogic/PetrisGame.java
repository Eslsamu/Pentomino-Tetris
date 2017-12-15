package GameLogic;
import java.util.ArrayList;


import Dynamics.Controlls;
import Dynamics.GameCycle;
import GameView.MainView;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;


public class PetrisGame{
    /**
     * A name that player input at the beginning of the game and will be shown in highscore table.
     */
    protected String playerName;
    /**
     * Height of the GUI grid.
     */
    protected final int HEIGHT = 15;
    /**
     * Width of the GUI grid.
     */
    protected final int WIDTH = 5;
    /**
     * Indicates how fast should the block initially fall.
     */
    protected double INITIAL_DELAY = 500;
    /**
     * Level at the beginning of the game.
     */
    protected int level = 1;
    /**
     * Score at the beginning of the game.
     */
    protected int score = 0;
    /**
     * Number of rows that were cleared during current game.
     */
    protected int rowsCleared = 0;
    /**
     * Describes how fast should the block fall at a certain level.
     */
    protected double delay = INITIAL_DELAY;
    /**
     * Rate describing how much the speed of a falling block increase in each following level.
     */
    protected double speedIncrease = 0.8;

    /**
     * States if the game is running.
     */
    protected boolean isRunning = false; //+ get&setters

    /**
     * Whole game view.
     */
    protected MainView view;
    /**
     * Cycle of the game.
     */
    protected GameCycle cycle;
    /**
     * Controls which allow user to interact.
     */
    protected Controlls controlls;


    /**
     *  Grid showed in GUI.
     */
    protected Color[][] gridMatrix;
    /**
     * Pentomino block which is currently falling down the grid.
     */
    protected Pentomino fallingBlock;
    /**
     * Block that will start falling after fallingBlock will stop moving.
     */
    protected Pentomino nextBlock;

    /**
     * Starts the game
     */
    public PetrisGame() {
    	setupGame();
    }

    /**
     * Starts the game after user input his name.
     * @param name name of the user currently playing the game
     */
    public PetrisGame(String name){
    	playerName = name;   	
        setupGame();
    }

    /**
     * Sets up the game.
     */
    public void setupGame() { 
    	  gridMatrix = new Color[HEIGHT][WIDTH];
    	  PentominoGenerator startGenerator = new PentominoGenerator();
        nextBlock = startGenerator.getRandomPentomino();   
        
        view = new MainView(this);
    	  cycle = new GameCycle(this);
        controlls = new Controlls(this);
        
       isRunning = true;  
    }

    /**
     * Checks if the game is over.
     * @return describes if the game is over or not
     */
    public boolean gameOverCheck() {
        int[][] coordinates = nextBlock.getCoordinates();
        for (int i = 0; i < coordinates[0].length; i++){
           if(gridMatrix[coordinates[1][i]][coordinates[0][i]] != null) {
    		System.out.println("GameOver"); 
    		isRunning = false;
            return true;
            }              
    	} 
        return false;
    }

    /**
     * Spawns next block on the grid.
     */
    public void spawn() {
    	if(!isRunning) {
    		return;
    	}
    	fallingBlock = nextBlock;
    	PentominoGenerator startGenerator = new PentominoGenerator();
        nextBlock = startGenerator.getRandomPentomino();         
    }

    /**
     * Updates the view.
     */
    public void updateView() {
    	view.updateMain();
    }

    /**
     * Restarts the game.
     */
    public void restart() {
    	level = 1;
    	score = 0;
    	rowsCleared = 0;
    	delay = INITIAL_DELAY;
    	gridMatrix = new Color[HEIGHT][WIDTH];
    	PentominoGenerator startGenerator = new PentominoGenerator();
    	nextBlock = startGenerator.getRandomPentomino();
        isRunning = true;
        spawn();
    }

    /**
     * Moves the falling block.
     * @param aDirection a direction in which the block is moved
     */
    public void move(Direction aDirection){
        if(aDirection == Direction.DROPDOWN){
            while(!doesCollide(Direction.DOWN)){
                move(Direction.DOWN);
            }
        }
        else{
            if(!doesCollide(aDirection)&&isRunning) {
                int[][] changeCoords = fallingBlock.getCoordinates();
                switch(aDirection) {
                    case DOWN:  {       		
                    for(int i = 0; i < changeCoords[1].length; i++){
                        changeCoords[1][i]++;
                    }
                            break;
                    }	
                    case RIGHT: {
                    for(int i = 0; i < changeCoords[0].length; i++){
                            changeCoords[0][i]++;
                    }
                    break;
                    }        	
                    case LEFT: {
                            for(int i = 0; i < changeCoords[0].length; i++){
                                    changeCoords[0][i]--;
                            }
                            break;
                    }
                    case CLOCKWISE: {
                            changeCoords = rotate(changeCoords, 90,true); //board is swapped around, so 90 instead of 270 
                            break;
                    }   
                    case COUNTERCLOCKWISE: {
                             changeCoords = rotate(changeCoords, 270,true); 
                             break; 
                    }
                }    
            fallingBlock.setCoordinates(changeCoords);
            }
        }
    }

    /**
     * Rotates the block clockwise or counter-clockwise.
     * @param coords Coordinates of the pentomino
     * @param degrees Degrees determine whether the block is rotated clockwise (90 degrees) or counter-clockwise (270 degrees)
     * @param smallBoardRotation If a block is out of the board after rotating it, it will be returned to the board.
    Usually games do it and it makes the game easier to play, because the board is small.
     * @return a rotated block
     */
    public int[][] rotate(int[][] coords, double degrees, boolean smallBoardRotation){//smallBoardRotation is a feature to rotate the block even if it would hit a wall in that position
            int rotationPoint = coords[0].length / 2;
            int reducedAmountX = coords[0][rotationPoint];
            int reducedAmountY = coords[1][rotationPoint];
                    
            for(int i = 0; i < coords[0].length; i++){
                coords[0][i] -= reducedAmountX;
                coords[1][i] -= reducedAmountY;
            } 
        
            int cos = (int) Math.cos(Math.toRadians(degrees));
            int sin = (int) Math.sin(Math.toRadians(degrees));
            int[][] transformationMatrix = {{cos,-sin},{sin,cos}};
        
            coords = multiplyMatrix(transformationMatrix, coords);
        
            for(int i = 0; i < coords[0].length; i++){
              coords[0][i] += reducedAmountX;
              coords[1][i] += reducedAmountY;
            }

            if(smallBoardRotation) {
            	for(int i = 0; i < coords[0].length; i++){
                	while(coords[0][i] >= gridMatrix[0].length){
                    	for(int x = 0; x < coords[0].length; x++){
                        	coords[0][x] = coords[0][x] - 1;
                    	}
                	}
                	while(coords[0][i] < 0){
                    	for(int x = 0; x < coords[0].length; x++){
                        	coords[0][x] = coords[0][x] + 1;
                    	}
                	}    
                	while(coords[1][i] >= gridMatrix.length){
                    	for(int x = 0; x < coords[0].length; x++){
                        	coords[1][x] = coords[1][x] - 1;
                    	}
                	}
                	while(coords[1][i] < 0){
                    	for(int x = 0; x < coords[0].length; x++){
                        	coords[1][x] = coords[1][x] + 1;
                    	}
                	}  
            	}
            }
            return coords;
    }

    /**
     * Checks if a square would collide if moved in a certain direction. Used for flood fill algorithm.
     * @param x x-coordinate of the square
     * @param y y-coordinate of the square
     * @return shows if the square collides or not
     */
    public boolean doesCollide(int x, int y){
        if(y + 1 >= gridMatrix.length || gridMatrix[y + 1][x] != null){
            return true;
        }
        return false;
    }

    /**
     * Checks if the falling block would collide if moved in a certain direction.
     * @param movingDirection direction in which the falling block will move if it doesn't collide
     * @return tells if falling block will collide or not
     */
    public boolean doesCollide(Direction movingDirection){
        int[][] checkCoords = new int[fallingBlock.getCoordinates().length][fallingBlock.getCoordinates()[0].length];
        
        for(int i = 0; i < checkCoords.length; i++){
            checkCoords[i] = fallingBlock.getCoordinates()[i].clone();
        }  
        
        if(movingDirection == Direction.DOWN){
            for(int i = 0; i < checkCoords[1].length; i++){                     	
                if(checkCoords[1][i] + 1 >= gridMatrix.length || gridMatrix[ checkCoords[1][i] + 1 ][ checkCoords[0][i] ]!=null){
                   //here a new block gets created as it fell down to the ground
                   placePento(fallingBlock);
                   clearRows();
                   return true;
                }
            }   
        }
        else if(movingDirection == Direction.LEFT) {
        	for(int i = 0; i < checkCoords[0].length;i++) {
        		if(checkCoords[0][i] - 1 < 0 || gridMatrix[ checkCoords[1][i] ][checkCoords[0][i] - 1 ]!=null) {
        			return true;
        		}
        	}
        }
        else if(movingDirection == Direction.RIGHT) {
        	for(int i = 0; i < checkCoords[0].length;i++) {
        		if(checkCoords[0][i] + 1 >= gridMatrix[0].length || gridMatrix[ checkCoords[1][i] ][checkCoords[0][i] + 1 ]!=null) {

        			return true;
        		}
        	}
        }
        else if(movingDirection == Direction.CLOCKWISE) {     
            int[][] checkRotationCoords = rotate(checkCoords,90,true);
            for(int i = 0; i < checkRotationCoords[0].length; i++){
                if(checkRotationCoords[0][i]>=gridMatrix[0].length || checkRotationCoords[1][i]>=gridMatrix.length || checkRotationCoords[0][i]<0 || checkRotationCoords[1][i]<0 || gridMatrix[checkRotationCoords[1][i]][checkRotationCoords[0][i]]!=null){
                    return true;
                }
            }
        }
        else if(movingDirection == Direction.COUNTERCLOCKWISE) {     
            int[][] checkRotationCoords = rotate(checkCoords,270,true);
            for(int i = 0; i < checkRotationCoords[0].length; i++){
                if(checkRotationCoords[0][i]>=gridMatrix[0].length || checkRotationCoords[1][i]>=gridMatrix.length || checkRotationCoords[0][i]<0 || checkRotationCoords[1][i]<0 || gridMatrix[checkRotationCoords[1][i]][checkRotationCoords[0][i]]!=null){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Places the falling block on the grid
     * @param aPentomino type of pentomino to be placed
     */
    public void placePento(Pentomino aPentomino){
            int[][] whereToPlace = aPentomino.getCoordinates();
            Color colorIndex = aPentomino.getColorIndex();
            for(int i = 0; i < whereToPlace[0].length; i++){
                gridMatrix[whereToPlace[1][i]][whereToPlace[0][i]] = colorIndex;
            }
            clearRows();          
            if(!gameOverCheck()) {
            	spawn();
            }
    }

    /**
     * Increases the level.
     */
    public void levelUp() {
    	if(score/level >= 1000){
            level++;
            delay = delay*speedIncrease;
        }
    }

    /**
     * Finds full rows which has to be cleared.
     * @return a collection of rows which should be cleared
     */
    public ArrayList<Integer> rowsToClear(){
        ArrayList<Integer> rowsToClear = new ArrayList<>();
        for(int i = 0; i < gridMatrix.length; i++){
            boolean isRowFull = true;
            for(int j=0;j<gridMatrix[0].length;j++){
                if(gridMatrix[i][j]==null){
                    isRowFull = false;
                }
            }
            if(isRowFull){
                rowsToClear.add(i);
            }
        }
        return rowsToClear;
    }

    /**
     * Clears full rows found by rowsToClear() method.
     */
    public void clearRows(){
        ArrayList<Integer> rowsToClear = rowsToClear();
        //if there are no rows to clear, then nothing happens (because rowToClear.size = 0)
        while(rowsToClear.size() != 0){
          rowsCleared = rowsCleared + rowsToClear.size();
          for(int i = 1; i < rowsToClear.size() + 1; i++){
              score += 100*i;
          }
          levelUp();
          /* This part clears a row that rowsToClear returns and then will move everything above it one row down
            until there are no more rows in rowsToClear
          */
            for(int i = 0; i < rowsToClear.size(); i++){
                //move everything one down
                for(int x = rowsToClear.get(i) - 1; x > -1; x--){
                            for(int t = 0; t < gridMatrix[0].length; t++){
                                gridMatrix[x + 1][t] = gridMatrix[x][t];
                                if(x == 0){
                                    gridMatrix[x + 1][t] = gridMatrix[x][t];
                                    gridMatrix[x][t] = null;
                                }
                            }
                        }
            }
            //use the floodFill algorithm and create a subGrid above the line we last cleared, which is used to find coordinates of connectedShapes
            Color[][] subGrid = FloodFill.getSubGrid(gridMatrix);
            //we get the coordinates from getConnectedShapes
            ArrayList<ArrayList<Integer>> connectedShapes = FloodFill.getConnectedShapes(subGrid);
            /* 
            The while loop will iterate as long as any shape has moved
            */
            boolean hasMoved = true;
            while(hasMoved == true){
                hasMoved = false;
                //go through each shape and move it down as much as it can
                for(int c = 0; c < connectedShapes.size(); c++){
                        //initialize starting column to check
                        int x = 0;        
                        //initialize starting row to check
                        int y = 0;
                        int moveDown = 16;
                        //make a moveDown variable for the moves this shapes needs to do
                        //while loop that gets through all possible columns and finds minimum moves for each of them
                        while(x < 5){
                            for(int j = 0; j < connectedShapes.get(c).size(); j+=2){
                                if(connectedShapes.get(c).get(j) == x){
                                    //find the biggest Y,(lowest row) in this column to check for moves
                                    for(int p = 0; p < connectedShapes.get(c).size(); p+=2){
                                        if(connectedShapes.get(c).get(p) == x)
                                            y = Math.max(y, connectedShapes.get(c).get(p + 1));
                                    }
                                    //move Y to find until when this column could be moved
                                    int lastPossibleY = y;
                                    while(!doesCollide(x, lastPossibleY)){
                                        lastPossibleY++;
                                    }
                                    int move = lastPossibleY - y;
                                    //store the minimum moves of all possible columns of this shape
                                    moveDown = Math.min(moveDown, move);
                                    //check for next column
                                }
                            }    
                            x++;
                            y = 0;
                        }
                        //a temporal grid is used to move the shape
                        Color[][] tempGrid = new Color[HEIGHT][WIDTH];
                        for(int j = 0; j < connectedShapes.get(c).size(); j+=2){
                            int yCoordinate = connectedShapes.get(c).get(j + 1);
                            int xCoordinate = connectedShapes.get(c).get(j);
                                tempGrid[yCoordinate + moveDown][xCoordinate] = gridMatrix[yCoordinate][xCoordinate];
                                gridMatrix[yCoordinate][xCoordinate] = null;
                        }
                        for(int j = 0; j < connectedShapes.get(c).size(); j+=2){
                            int yCoordinate = connectedShapes.get(c).get(j + 1);
                            int xCoordinate = connectedShapes.get(c).get(j);
                            gridMatrix[yCoordinate + moveDown][xCoordinate] = tempGrid[yCoordinate + moveDown][xCoordinate];
                            connectedShapes.get(c).set(j + 1, connectedShapes.get(c).get(j + 1) + moveDown);
                        }
                        //if moveDown of any shape is not equal to 0 then the shape is has been moved, so we need to check if it is floating
                        if(moveDown != 0){
                            hasMoved = true;
                        }
                    }
                }
            rowsToClear = rowsToClear();
        }
    }

    /**
     * A method used for testing.
     */
    public void testPrint() {
    	int[][] testPentominoCoords = fallingBlock.getCoordinates();
    	System.out.println("The coordinates of the falling Pentomino:");
    	for(int i = 0; i < testPentominoCoords.length; i++) {
    		for(int j = 0; j < testPentominoCoords[i].length;j++) {
    			System.out.print(testPentominoCoords[i][j]);
    		}
    		System.out.println();
    	}
    	
    	for(int i = 0; i < gridMatrix.length;i++) {
    		for (int j = 0; j < gridMatrix[0].length; j++) {
    			System.out.print(" "+gridMatrix[i][j]+" ");
    		}
    		System.out.println();
    	}
    }

    /**
     * Performs matrix multiplication.
     * @param matrix1 first matrix
     * @param matrix2 second matrix
     * @return result of matrix multiplication
     */
    public int[][] multiplyMatrix(int[][] matrix1, int[][] matrix2){
		int[][] matrix = new int[matrix1.length][matrix2[0].length];
		if (matrix1[0].length != matrix2.length){
			System.out.println("The sum is illegal - widths or lengths of matrices don't match!");
		}
		
		for(int matrixRow = 0;matrixRow<matrix1.length;matrixRow++){
			for(int matrixCell1 =0;matrixCell1<matrix1[0].length;matrixCell1++){
				for(int matrixColumn = 0;matrixColumn<matrix2[0].length;matrixColumn++){
					int partialMultiplication = 0;
					for(int matrixCell2 = 0; matrixCell2<matrix2.length;matrixCell2++){
						partialMultiplication += matrix1[matrixRow][matrixCell2]*matrix2[matrixCell2][matrixColumn];					
					}
					matrix[matrixRow][matrixColumn] = partialMultiplication;
				}
			} 
		}
		return matrix;
	}

    public String getPlayerName() {
		return playerName;
	}
    
    public Controlls getControlls() {
    	return controlls;
    }
    
    public MainView getView() {
    	return view;
    }
    
    public Scene getScene() {
    	Scene scene = new Scene(view);
    	scene.addEventFilter(KeyEvent.KEY_PRESSED, controlls);
    	return scene;
    }
    
    public GameCycle getCycle() {
    	return cycle;
    }
    
    public boolean getIsRunning() {
    	return isRunning;
    }
    
    public void setIsRunning(boolean r) {
    	isRunning = r;
    }
    
    public Pentomino getFallingBlock() {
    	return fallingBlock;
    }
    
    public Pentomino getNextBlock() {
    	return nextBlock;
    }
    
    public Color[][] getGrid(){
    	return gridMatrix;
    }
    
    public int getLevel() {
    	return level;
    }
    
    public int getScore() {
    	return score;
    }
    
    public int getRowsCleared() {
    	return rowsCleared;
    }
    public double getSpeed(){
        return delay;
    }

    /**
     * Pauses the game.
     */
    public void pause() {
    	cycle.pause();
        isRunning = false;
    }

    /**
     * Runs the game.
     */
    public void runGame() {
    	isRunning = true;
    	cycle.run();
    }
}
