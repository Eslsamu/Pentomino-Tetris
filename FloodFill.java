import java.util.ArrayList;

public class FloodFill {
    public static int[][] getSubGrid(int[][] grid, int rowAboveCleared){
        int[][] subGrid = new int[rowAboveCleared+1][grid[0].length];
        for(int i=0;i<rowAboveCleared+1;i++){
            for(int j=0;j<grid[0].length;j++){
                subGrid[i][j] = grid[i][j];
            }
        }
        return subGrid;
    }

    public static ArrayList<ArrayList<Integer>> findConnected(int x, int y, ArrayList<ArrayList<Integer>> connectedShapes, int counter, int[][]subGrid){
        if(x>=subGrid[0].length || x<0 || y>=subGrid.length || y<0){
            return connectedShapes;
        }
        if(subGrid[y][x]==0){
            return  connectedShapes;
        }
        if(subGrid[y][x]==-1){
            return connectedShapes;
        }
        subGrid[y][x]=-1;
        connectedShapes.get(counter).add(x);
        connectedShapes.get(counter).add(y);
        findConnected(x-1,y,connectedShapes, counter, subGrid);
        findConnected(x+1,y,connectedShapes,counter, subGrid);
        findConnected(x,y+1,connectedShapes, counter, subGrid);
        findConnected(x,y-1,connectedShapes, counter, subGrid);
        return connectedShapes;
    }

    public static ArrayList<ArrayList<Integer>> getConnectedShapes(int[][] subGrid){
        ArrayList<ArrayList<Integer>> connectedShapes = new ArrayList<>();
        int counter=0;
        for(int i=0;i<subGrid.length;i++){
            for(int j=0;j<subGrid[0].length;j++){
                if(subGrid[i][j] != -1 && subGrid[i][j] != 0)
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
