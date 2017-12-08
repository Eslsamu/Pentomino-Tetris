package petris;

import java.util.ArrayList;
import javafx.scene.paint.Color;

public class PentominoGenerator {
   private ArrayList<int[][]> pentominoList; 
	public PentominoGenerator(){
		pentominoList = new ArrayList<int[][]>();
                int [][] L = {{2,2,2,2,3},{0,1,2,3,3}}; //0
                pentominoList.add(L);
                int[][] I = {{2,2,2,2,2},{0,1,2,3,4}}; //1
                pentominoList.add(I);
                int[][] U = {{2,3,2,2,3},{0,0,1,2,2}}; //2
                pentominoList.add(U); 
                int[][] Z = {{1,2,2,2,3},{2,0,1,2,0}}; //3
                pentominoList.add(Z);
                //normal
                //int[][] Z = {{1,2,2,2,3},{0,0,1,2,2}}; //3
                //pentominoList.add(Z);
                int[][] X = {{2,1,2,3,2},{0,1,1,1,2}}; //4
                pentominoList.add(X);
                int[][] T = {{1,2,3,2,2},{0,0,0,1,2}}; //5
                pentominoList.add(T);
                int[][] V = {{2,2,2,3,4},{0,1,2,2,2}}; //6
                pentominoList.add(V);
                int[][] N = {{2,2,3,3,3},{0,1,1,2,3}}; //7
                pentominoList.add(N);
                //normal
                //int[][] N = {{1,2,2,3,4},{0,0,1,1,1}}; //7
                //pentominoList.add(N);
                int[][] F = {{2,3,1,2,2},{0,0,1,1,2}}; //8
                pentominoList.add(F); 
                int[][] W = {{2,2,3,3,4},{0,1,1,2,2}}; //9
                pentominoList.add(W);
                int[][] P = {{2,3,4,3,4},{0,0,0,1,1}}; //10
                pentominoList.add(P);
                int[][] Y = {{2,3,2,2,2},{0,1,1,2,3}}; //11
                pentominoList.add(Y);
                //normal
                //int[][] Y = {{2,1,2,2,2},{0,1,1,2,3}}; //11
                //pentominoList.add(Y);
	}

	public Pentomino getRandomPentomino(){
		int index = (int) (Math.random()*12);
		Color ranColor = ranColor = Color.rgb((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*254)); //254 to leave space for an exception store; 
		while(ranColor.equals(Color.rgb(186, 216, 227))){
                    ranColor = Color.rgb((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*254)); //254 to leave space for an exception store
                }
                Pentomino random = new Pentomino(pentominoList.get(index),ranColor); 
                return random;
	}

	public Pentomino getTestPentomino(){
		return new Pentomino(pentominoList.get(1), Color.BLUE);
	}
        public Pentomino getTestPentomino(int index){
                Color ranColor = ranColor = Color.rgb((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*254)); //254 to leave space for an exception store; 
		while(ranColor.equals(Color.rgb(186, 216, 227))){
                    ranColor = Color.rgb((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*254)); //254 to leave space for an exception store
                }
                return new Pentomino(pentominoList.get(index), ranColor);
        }
}
