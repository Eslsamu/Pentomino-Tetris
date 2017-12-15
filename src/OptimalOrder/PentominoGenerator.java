package OptimalOrder;
import javafx.scene.paint.Color;
import GameLogic.Pentomino;

import java.util.ArrayList;

/**
 * Class which provides the information needed to construct the pentominoes, and the methods
 * which use the Pentomino class and this information to create any one of the 12 pentominoes randomly.
 *
 */

public class PentominoGenerator {
   private ArrayList<int[][]> pentominoList;
   
    /**
     * The constructor represents the pentomino coordinates using an arraylist of 2D matrices,
     * each entry in the arraylist being the coordinates of 1 of the 12 pentominoes.
     * The coordinates are represented as 2 by 5 matrices, the first row containing the 5 x coordinates
     * and the second row containing the 5 y coordinates. The coordinates are relative to the square (0,0).
     *
     */
	
	public PentominoGenerator(){
		//X/Y coordinates of the pieces of the 12 pentomino shapes
		pentominoList = new ArrayList<int[][]>();
                int [][] L = {{0,1,2,3,0},{0,0,0,0,1}}; //0
                pentominoList.add(L);
                int[][] I = {{0,1,2,3,4},{0,0,0,0,0}}; //1
                pentominoList.add(I);
                int[][] U = {{2,3,2,2,3},{0,0,1,2,2}}; //2
                pentominoList.add(U); 
                int[][] Z = {{1,2,2,2,3},{2,0,1,2,0}}; //3
                pentominoList.add(Z);
                int[][] X = {{2,1,2,3,2},{0,1,1,1,2}}; //4
                pentominoList.add(X);
                int[][] T = {{1,2,3,2,2},{0,0,0,1,2}}; //5
                pentominoList.add(T);
                int[][] V = {{2,2,2,3,4},{0,1,2,2,2}}; //6
                pentominoList.add(V);
                int[][] N = {{2,2,3,3,3},{0,1,1,2,3}}; //7
                pentominoList.add(N);
                int[][] F = {{2,3,1,2,2},{0,0,1,1,2}}; //8
                pentominoList.add(F); 
                int[][] W = {{2,2,3,3,4},{0,1,1,2,2}}; //9
                pentominoList.add(W);
                int[][] P = {{2,3,4,3,4},{0,0,0,1,1}}; //10
                pentominoList.add(P);
                int[][] Y = {{2,3,2,2,2},{0,1,1,2,3}}; //11
                pentominoList.add(Y);
	}
    /**
     * Method which first creates a random integer between 0 and 11, used to randomly select 1 of the pentominoes from the arraylist.
     * The method then selects a random color by creating random integers between 0 and 255, using red green blue values.
     * We also make sure the color does not happen to be the same as the background color rgb(186,216,227) because then we couldn't see
     * The pentomino on the board.
     * @return returns the Pentomino object which we just constructed
     */
        
	public Pentomino getRandomPentomino(){
            int index = (int) (Math.random()*12);
            //exclude background color
            Color ranColor;
            do{
            ranColor = Color.rgb((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*254)); //254 to leave space for an exception store
            }
            while(ranColor.equals(Color.rgb(186, 216, 227)));
            
            Pentomino random = new Pentomino(pentominoList.get(index),ranColor); 
                
            return random;
	}
    /**
     * Method for testing purposes, allowing us to pick a pentomino as the next falling piece ourselves.
     * @param index representing the pentominoe we want to get
     * @return specific pentominoe accessed with index
     */
	public Pentomino getTestPentomino(int index){
            Color ranColor;
            do{
            ranColor = Color.rgb((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*254)); //254 to leave space for an exception store
            }
            while(ranColor.equals(Color.rgb(186, 216, 227)));
            
            return new Pentomino(pentominoList.get(index), ranColor);
	}
}
