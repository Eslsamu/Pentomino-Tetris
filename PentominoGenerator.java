
import java.util.ArrayList;


public class PentominoGenerator {
   private ArrayList<int[][]> pentominoList; 
	public PentominoGenerator(){
		pentominoList = new ArrayList<int[][]>();
                int [][] L = {{2,2,2,2,3},{0,1,2,3,3}};
                pentominoList.add(L);
                int[][] I = {{2,2,2,2,2},{0,1,2,3,4}};
                pentominoList.add(I);
                int[][] U = {{2,3,2,2,3},{0,0,1,2,2}};
                pentominoList.add(U);
                int[][] Z = {{1,2,2,2,3},{0,0,1,2,2}};
                pentominoList.add(Z);
                int[][] X = {{2,1,2,1,2},{0,1,1,1,2}};
                pentominoList.add(X);
                int[][] T = {{1,2,3,2,2},{0,0,0,1,2}};
                pentominoList.add(T);
                int[][] V = {{2,2,2,3,4},{0,1,2,2,2}};
                pentominoList.add(V);
                int[][] N = {{1,2,2,3,4},{0,0,1,1,1}};
                pentominoList.add(N);
                int[][] F = {{2,3,1,2,3},{0,0,1,1,2}};
                pentominoList.add(F);
                int[][] W = {{2,2,3,3,4},{0,1,1,2,2}};
                pentominoList.add(W);
                int[][] P = {{2,3,4,3,4},{0,0,0,1,1}};
                pentominoList.add(P);
                int[][] Y = {{2,1,2,2,2},{0,1,1,2,3}};
                pentominoList.add(Y);
	}

	public Pentomino getRandomPentomino(){
		int index = (int) (Math.random()*12);
		Pentomino random = new Pentomino(pentominoList.get(index),index+1); //parameter are a random pick of the coordinate list and a number that represents the index for a color
                return random;
	}

	public Pentomino getTestPentomino(){
		return new Pentomino(pentominoList.get(1), 2);
	}
}
