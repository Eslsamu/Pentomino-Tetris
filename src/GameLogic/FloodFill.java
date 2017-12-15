package GameLogic;

import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 An additional class that is involved in clearing a full row. By application of recursive flood fill algorithm, program finds connected
 parts of the grid above and below the cleared row.
 */
public class FloodFill {
    /**
     *Creates additional copy of the grid that is used in later methods.
     *@param grid grid presented in the GUI containing a color for each square.
     *@return return a new subgrid
     */
    public static Color[][] getSubGrid(Color[][] grid){
        Color[][] subGrid = new Color[grid.length][grid[0].length];
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                subGrid[i][j] = grid[i][j];
            }
        }
        return subGrid;
    }

    /**
     * Finds connected shapes (parts) of the grid above and below cleared row.
     * @param x a column of the grid in which the flood fill algorithm is initiated
     * @param y a row of the grid in which the flood fill algorithm is initiated
     * @param connectedShapes a collection of connected shapes (each shape collects squares that belong to it)
     * @param counter an index of each connected shape stored in connectedShapes
     * @param subGrid a subgrid created in getSubGrid() method
     */
    public static void findConnected(int x, int y, ArrayList<ArrayList<Integer>> connectedShapes, int counter, Color[][] subGrid){
        if(x >= subGrid[0].length || x < 0 || y >= subGrid.length || y < 0){
            return;
        }
        /**
         * If square has no color (is not an element of any connected shape), then stop.
         */
        if(subGrid[y][x] == null){
            return;
        }
        if(subGrid[y][x].equals(Color.rgb(0,0,255))){
            return;
        }
        /**
         * If the square is added to a connected shape, to indicate it , his color is changed to an exception color
         * that will never be randomly on the board (blue). That's why we need a subgrid, because we don't want to change colors
         * on the grid seen by the user.
         *
         */

            subGrid[y][x] = Color.rgb(0,0,255);
            connectedShapes.get(counter).add(x);
            connectedShapes.get(counter).add(y);
            findConnected(x-1, y, connectedShapes, counter, subGrid);
            findConnected(x+1, y, connectedShapes, counter, subGrid);
            findConnected(x, y+1, connectedShapes, counter, subGrid);
            findConnected(x, y-1, connectedShapes, counter, subGrid);

    }

    /**
     * Creates a collection of connected shapes (each shape collects squares that belong to it)
     * @param subGrid a subgrid created in getSubGrid() method
     * @return a collection of connected shapes
     */
    public static ArrayList<ArrayList<Integer>> getConnectedShapes(Color[][] subGrid){
        ArrayList<ArrayList<Integer>> connectedShapes = new ArrayList<>();
        int counter = 0;
        for(int i = 0; i < subGrid.length; i++){
            for(int j = 0; j < subGrid[0].length; j++){
                if(subGrid[i][j] != null && !subGrid[i][j].equals(Color.rgb(0,0,255)))
                {
                    ArrayList<Integer> list = new ArrayList<>();
                    connectedShapes.add(list);
                    findConnected(j,i, connectedShapes, counter, subGrid);
                    counter++;

                }
                }
        }
        return  connectedShapes;
    }
}
