import java.util.ArrayList;

import javafx.scene.paint.Color;

public class FloodFill {
    public static Color[][] getSubGrid(Color[][] grid, int rowAboveCleared){
        Color[][] subGrid = new Color[rowAboveCleared+1][grid[0].length];
        for(int i=0;i<rowAboveCleared+1;i++){
            for(int j=0;j<grid[0].length;j++){
                subGrid[i][j] = grid[i][j];
            }
        }
        return subGrid;
    }

    public static ArrayList<ArrayList<Integer>> findConnected(int x, int y, ArrayList<ArrayList<Integer>> connectedShapes, int counter, Color[][] subGrid){
        if(x>=subGrid[0].length || x<0 || y>=subGrid.length || y<0){
            return connectedShapes;
        }
        if(subGrid[y][x]==null){
            return  connectedShapes;
        }
        if(subGrid[y][x]==Color.rgb(0,0,255)){
            return connectedShapes;
        }
        subGrid[y][x]= Color.rgb(0,0,255); // exception color that will never be randomly on the board
        connectedShapes.get(counter).add(x);
        connectedShapes.get(counter).add(y);
        findConnected(x-1,y,connectedShapes, counter, subGrid);
        findConnected(x+1,y,connectedShapes,counter, subGrid);
        findConnected(x,y+1,connectedShapes, counter, subGrid);
        findConnected(x,y-1,connectedShapes, counter, subGrid);
        return connectedShapes;
    }

    public static ArrayList<ArrayList<Integer>> getConnectedShapes(Color[][] subGrid){
        ArrayList<ArrayList<Integer>> connectedShapes = new ArrayList<>();
        int counter=0;
        for(int i=0;i<subGrid.length;i++){
            for(int j=0;j<subGrid[0].length;j++){
                if(subGrid[i][j] != Color.rgb(0,0,255) && subGrid[i][j] != null)
                {
                    ArrayList<Integer> list=new ArrayList<>();
                    connectedShapes.add(list);
                    findConnected(j,i, connectedShapes, counter, subGrid);
                    counter++;

                }
            }
        }
        return  connectedShapes;
    }
}
