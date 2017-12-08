package petris;

import java.util.ArrayList;
import javafx.scene.paint.Color;


public class BackendGrid{
    private final int HEIGHT = 15;
    private final int WIDTH = 5;
    
    private int level = 1;
    private int score = 0;
    private int rowsCleared = 0;
    private double delay = 500.0;
    private final double speedIncrease = 0.8;
   
    private Color[][] gridMatrix;
    private Pentomino fallingBlock;
    private Pentomino nextBlock;
    
    //Optimal ordering and Row Clear
    private int[] pentominoes;
    private int pentominoesCounter = 0;
    
    public BackendGrid() {
    	gridMatrix = new Color[HEIGHT][WIDTH];
    	PentominoGenerator startGenerator = new PentominoGenerator();
        nextBlock = startGenerator.getRandomPentomino();   
    }
    public BackendGrid(String testType){
        gridMatrix = new Color[HEIGHT][WIDTH];
        PentominoGenerator startGenerator = new PentominoGenerator();
        if(testType.equals("ClearRow")){
           int[] clearRow = {5, 6, 0, 0};
           pentominoes = clearRow;
        }
        else if(testType.equals("OptimalOrder")){
           int[] optimalOrder = {6, 3, 8, 7, 10, 9, 5, 11, 1, 4, 2, 0};
           pentominoes = optimalOrder;
           
        }
        nextBlock = startGenerator.getTestPentomino(pentominoes[pentominoesCounter]);   
        pentominoesCounter++;
    }
    public BackendGrid(int height, int width){
        gridMatrix = new Color[height][width];
        PentominoGenerator startGenerator = new PentominoGenerator();
        nextBlock = startGenerator.getRandomPentomino();   
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
    public boolean gameOverCheck() {
        int[][] coordinates = fallingBlock.getCoordinates();
        for (int i = 0; i < coordinates[0].length; i++){
           if(gridMatrix[coordinates[1][i]][coordinates[0][i]] != null) {
                return true;
            }              
    	} 
        return false;
    }
    
    public void spawn() {
        if(pentominoes == null){
            fallingBlock = nextBlock;
            PentominoGenerator startGenerator = new PentominoGenerator();
            nextBlock = startGenerator.getRandomPentomino();        
        }
        else{
            fallingBlock = nextBlock;
            PentominoGenerator startGenerator = new PentominoGenerator();
            nextBlock = startGenerator.getTestPentomino(pentominoes[pentominoesCounter]);
            if(pentominoesCounter >= pentominoes.length - 1){
                pentominoesCounter = 0;
            }
            else{
                pentominoesCounter++;
            }
        }
    }
    public void move(Direction aDirection){
        if(!doesCollide(aDirection)) {
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
        		changeCoords = rotate(changeCoords, 90); //board is swapped around, so 90 instead of 270 
                        break;
        	}   
        	case COUNTERCLOCKWISE: {
        		 changeCoords = rotate(changeCoords, 270); 
                         break; 
        	}                    
            }
            fallingBlock.setCoordinates(changeCoords);
        }
    }    
    
    public int[][] rotate(int[][] coords, double degrees){
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
            /* following code until return is something additionally added
                if your pentomino is out of the board after rotating it, it will return it to the board
                the usual games do this and is easier to play, because the board is small and for almost all pentos
                the rotation works if they are in column 2
            */
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
            }
            
            return coords;
    }
    public boolean doesCollide(int x, int y){
        if(y + 1 >= gridMatrix.length || gridMatrix[y + 1][x] != null){
            return true;
        }
        return false;
    }
    
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
                   spawn();
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
            int[][] checkRotationCoords = rotate(checkCoords,90);
            for(int i = 0; i < checkRotationCoords[0].length; i++){
                if(checkRotationCoords[0][i]>=gridMatrix[0].length || checkRotationCoords[1][i]>=gridMatrix.length || checkRotationCoords[0][i]<0 || checkRotationCoords[1][i]<0 || gridMatrix[checkRotationCoords[1][i]][checkRotationCoords[0][i]]!=null){
                    return true;
                }
            }
        }
        else if(movingDirection == Direction.COUNTERCLOCKWISE) {     
            int[][] checkRotationCoords = rotate(checkCoords,270);
            for(int i = 0; i < checkRotationCoords[0].length; i++){
                if(checkRotationCoords[0][i]>=gridMatrix[0].length || checkRotationCoords[1][i]>=gridMatrix.length || checkRotationCoords[0][i]<0 || checkRotationCoords[1][i]<0 || gridMatrix[checkRotationCoords[1][i]][checkRotationCoords[0][i]]!=null){
                    return true;
                }
            }
        }
        return false;
    }
    
    public void placePento(Pentomino aPentomino){
            int[][] whereToPlace = aPentomino.getCoordinates();
            Color colorIndex = aPentomino.getColorIndex();
            for(int i = 0; i < whereToPlace[0].length; i++){
                gridMatrix[whereToPlace[1][i]][whereToPlace[0][i]] = colorIndex;
            }
    }
    
    public void levelUp() {
    	if(score/level >= 1000){
            level++;
            delay = delay*speedIncrease;
        }
    }
    
    public ArrayList<Integer> rowsToClear(){
        ArrayList<Integer> rowsToClear = new ArrayList<>();
        for(int i = 0; i < gridMatrix.length; i++){
            boolean isRowFull = true;
            for(int j=0;j<gridMatrix[0].length;j++){
                if(gridMatrix[i][j] == null){
                    isRowFull = false;
                }
            }
            if(isRowFull){
                rowsToClear.add(i);
            }
        }
        return rowsToClear;
    }
    
    
    public void clearRows(){
        ArrayList<Integer> rowsToClear = rowsToClear();
        //if there are no rows to clear, then nothing happens (because rowToClear.size = 0)
        while(rowsToClear.size() != 0){
            
          rowsCleared = rowsCleared + rowsToClear.size();
          for(int i = 1; i < rowsToClear.size() + 1; i++){
            score = score + 100*i;
          }
          levelUp();
          /* Changes due to some errors during gameplay
            We clear a line and then drop it as much as possible, then if needed clear a line again and so on...
            Removed code that after clearing a line moves everything one down and then searches for shapes
          */
            for(int i = 0; i < rowsToClear.size(); i++){
                for(int x = 0; x < gridMatrix[0].length; x++){
                    gridMatrix[rowsToClear.get(i)][x] = null;
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
            }
            rowsToClear = rowsToClear();
        }
    }
    
    public void testPrint( Pentomino fallingBlock) {
    
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
    public void restart(){
        level = 1;
        score = 0;
        rowsCleared = 0;
        delay = 500.0;
        gridMatrix = new Color[HEIGHT][WIDTH];
    	PentominoGenerator startGenerator = new PentominoGenerator();
        if(pentominoes == null){
            nextBlock = startGenerator.getRandomPentomino();
        }
        else{
            pentominoesCounter = 0;
            nextBlock = startGenerator.getTestPentomino(pentominoes[pentominoesCounter]);
            pentominoesCounter++;
        }
        spawn();
    }
}
