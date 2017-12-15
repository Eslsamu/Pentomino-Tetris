package OptimalOrder;

import java.util.*;
/**
 * The class will go through all possible combinations of pentominoes and 
 * store the optimal order ones.
 * @author Jordan, Basia, Stijn, Yvar, Sammuel, Blazej
 */
public class FindOptimalOrder{
    private int[] row = {-1, -1, -1, -1, -1, -1};
    private ArrayList<int[][]> boardCollection;
    private ArrayList<int[]> rowCollection;
    
    /**
     * The constructor creates ArrayList's that contain the possible optimal orders and
     * calls method solve that will find all possible optimal orders
     */
    public FindOptimalOrder(){
        boardCollection = new ArrayList<>();
        rowCollection  = new ArrayList<>();
        solve(createBoard(), setup());
    }
    /**
     * The method will go through the board, if it finds impossible constructions it will return false.
     * @param board matrix that represents a possible optimal order
     * @return boolean that declares if it is really an optimal order
     */
    private boolean excludeImpossible(int[][] board){
        for(int z = 0; z < row.length; z+=2){
                 if(row[z] - 1 > -1){
                        for(int n = 0; n < board[0].length; n++){
                                if(board[row[z]][n] == board[row[z]][row[z + 1]] && board[row[z] - 1][n] != board[row[z]][row[z + 1]] && board[row[z] - 1][n] != 0){
                                        int index = board[row[z] - 1][n];
                                        for(int i = row[z]; i < board.length; i++){
                                                for(int x = 0; x < board[0].length; x++){
                                                        if(board[i][x] == index){
                                                                return false;
                                                        }
                                                }
                                        }
                                }	
                        }
                }
        }
        for(int z = 0; z < row.length; z+=2){
                if(row[z] != - 1){
                        int index = board[row[z]][row[z + 1]];
                        for(int i = row[z] + 1; i < board.length; i++){
                                for(int x = 0; x < board[0].length; x++){
                                        if(x != row[z + 1] && board[i][x] == index && board[i - 1][x] != index){
                                                return false;
                                        }
                                }
                        }
                }	
        }
        /* //exclude if u is in column 0 or last column, this is also included in the following for loops
        for(int i = 0; i < board.length; i++){
                        if(board[i][0] == 11 || board[i][board[0].length - 1] == 11){
                                return false;
                }
        }	*/
        //exclude if u is connected to sth vertically
        for(int i = 0; i < board.length; i++){
                for(int x = 0; x < board[0].length; x++){
                        if(board[i][x] != 0){
                                int index = board[i][x];
                                if(i + 1 < board.length && board[i + 1][x] != index && board[i + 1][x] != 0){
                                        if(i + 2 < board.length && board[i + 2][x] == index && board[i + 2][x] != 0){
                                                return false;
                                        }
                                }
                        }	
                }
        }
        return true;
    }
    /**
     * Go through a possible matrix that represents an optimal order and search for multiple cleared lines.
     * In our case, 5 lines, then 4, then 3.
     * @param n searches for 5 - n lines, used for a recursive call
     * @param board matrix representing a possible optimal order
     * @return boolean that declares if it is a possible optimal order
     */
    private boolean searchPossible(int n, int[][] board){
        for(int x = 0; x < board.length; x++){
                for(int i = 0; i < board[0].length; i++){
                        boolean b = true;
                        for(int m = 1; m < 5 - n; m++){
                                if(b && (x + m >= board.length || board[x + m][i] != board[x][i])){
                                        b = false;
                                }
                                for(int p = 0; p < n; p++){
                                        for(int z = 0; z < 5 - p; z++){
                                                if(b && (x + m == row[p*2] + z || x == row[p*2] + z)){
                                                b = false;
                                                }
                                        }
                                }
                        }
                        if(b){
                                row[n*2] = x;
                                row[n*2 + 1] = i;
                                if((n == 2 || searchPossible(n + 1, board)) && excludeImpossible(board)){
                                        return true;
                                }	
                        }
                }	
        }
        return  false;
    }
    /**
     * Calls methods that will check if the board is indeed an optimal order.
     * @param board matrix representing possible optimal order
     * @return boolean that declares if board is an optimal order
     */
    private boolean optimalOrder(int[][] board){
        if(searchPossible(0, board) && excludeImpossible(board)){
            int[] temporaryRow = new int[row.length];
            for(int i = 0; i < row.length; i++){
                temporaryRow[i] = row[i];
            }
            rowCollection.add(temporaryRow);
                return true;
        }	
        else{
                return false;
        }
    }
    /**
     * This method will check if adding a pentominoe is possible
     * @param board matrix representing current board
     * @param yCell top left row coordinate of pentominoe
     * @param xCell top left column coordinate of pentominoe
     * @param pentoSet list of all possible pentominoes
     * @param pentoNum number of pentominoe in the pentominoe list that we want to add
     * @return boolean declaring if the pentominoe can be added
     */
    private boolean doesFit(int[][] board, int yCell, int xCell, int[][] pentoSet, int pentoNum){
        for(int x=1; x <= 4; x++) {
                        if(xCell+pentoSet[pentoNum][2*x+1] < 0 || xCell+pentoSet[pentoNum][2*x+1] >= board[0].length
                        || yCell+pentoSet[pentoNum][2*x] < 0 || yCell+pentoSet[pentoNum][2*x] >= board.length 
                        || board[yCell+pentoSet[pentoNum][2*x]][xCell+pentoSet[pentoNum][2*x+1]]!=0) {			
                                return false;
                        }
        }		
        return true;
    }             

    /**
     * When we want to place a pentominoe in a certaion position we use this method
     * @param board matrix representing board
     * @param yCell top left row coordinate of pentominoe
     * @param xCell top left column coordinate of pentominoe
     * @param pentoSet list of all possible pentominoes
     * @param pentoNum number of pentominoe in the pentominoe list that we want to add
     * @return matrix with added pentominoe
     */
    private int[][] addPento(int[][] board,int yCell, int xCell, int[][] pentoSet, int pentoNum){
        board[yCell][xCell] = pentoSet[pentoNum][0];
        for(int x = 1; x <= 4; x++){
                board[yCell+pentoSet[pentoNum][2*x]][xCell+pentoSet[pentoNum][1+2*x]] = pentoSet[pentoNum][0];
        }
        return board;
    }
    /**
     * If a certain pentominoe is no longer needed in a position we remove it with this method.
     * @param board matrix representing board
     * @param yCell top left row coordinate of pentominoe
     * @param xCell top left column coordinate of pentominoe
     * @param pentoSet list of all possible pentominoes
     * @param pentoNum number of pentominoe in the pentominoe list that we want to remove
     * @return matrix with removed pentominoe
     */
    private int[][] removePento(int[][] board,int yCell, int xCell, int[][] pentoSet, int pentoNum){
        board[yCell][xCell] = 0;
        for(int x = 1; x <= 4; x++){
                board[yCell+pentoSet[pentoNum][2*x]][xCell+pentoSet[pentoNum][1+2*x]] = 0;
        }
        return board;
    }
    /**
     * This method will check if a board is filled
     * @param board matrix representing the board that is being filled with pentominoes
     * @return boolean that declares if a board is filled
     */
    private boolean checkIfComplete(int[][] board){
        boolean complete = true;
        for(int i = 0;i<board.length;i++){ 							
                for(int j = 0;j<board[0].length;j++){
                        if(board[i][j]==0){
                                complete = false;
                        }	
                }		
        }
        return complete;
    }
    /**
     * This method finds all possible boards that could be filled with the 12 pentominoes.
     * If it finds one it checks if it is an optimal order, if yes it adds it to the boardCollection.
     * @param board representing the matrix that should be filled with pentominoes
     * @param confirmedPentominoes list of all possible pentominoes
     * @return false always, because we want to find all possible boards, so the method will go until it can't create more boards
     */
    private boolean solve(int[][] board, int[][] confirmedPentominoes){
        if(checkIfComplete(board) && optimalOrder(board)){
            int[][] tempoBoard = new int[board.length][board[0].length];
            for(int y = 0; y < board.length; y++){
                for(int x = 0; x < board[0].length; x++){
                    tempoBoard[y][x] = board[y][x];
                }
            }    
            boardCollection.add(tempoBoard);
            return false;
        }
        for(int i = 0;i<board.length;i++){ 	
                for(int j = 0;j<board[0].length;j++){
                        if(board[i][j]==0){

                                for(int pentoRotation = 0; pentoRotation < confirmedPentominoes.length; pentoRotation++) {	
                                        if(confirmedPentominoes[pentoRotation][1]==1&&doesFit(board,i,j,confirmedPentominoes,pentoRotation)){
                                                board = addPento(board,i,j,confirmedPentominoes,pentoRotation);
                                                for(int k = 0; k < confirmedPentominoes.length;k++) {
                                                        if(confirmedPentominoes[k][0]==confirmedPentominoes[pentoRotation][0]){
                                                                confirmedPentominoes[k][1] = 0;
                                                        }
                                                }
                                                if(solve(board,confirmedPentominoes)){
                                                        return false;
                                                }
                                                else{ 
                                                        board = removePento(board,i,j,confirmedPentominoes,pentoRotation);

                                                        for(int k = 0; k < confirmedPentominoes.length;k++) {
                                                                if(confirmedPentominoes[k][0]==confirmedPentominoes[pentoRotation][0]){
                                                                confirmedPentominoes[k][1] = 1;
                                                                }
                                                        }  
                                                }
                                        }
                                }
                                return false;	
                        }
                }
        }	
        return false;	
    }	
    /**
     * @return matrix 12 by 5 representing a possible optimal order
     */
    private int[][] createBoard(){
        int[][] board = new int[12][5];

        return board;
    }
    /**
     * @return ArrayList containing coordinates of pentominoes that clear multiple lines
     */
    public ArrayList getCoordinates(){
        return rowCollection;
    }
    /**
     * @return ArrayList containing optimal order matrixes
     */
    public ArrayList getOptimalOrder(){
        return boardCollection;
    }
    /**
     * This method stores all possible pentominoe rotations.
     * @return matrix containing all possible pentominoes
     */
    private int[][] setup(){
        int[][] pentominoes = { {2, 1, 0, 1, 0, 2, 0, 3, 0, 4},  // I
                        {2, 1, 1, 0, 2, 0, 3, 0, 4, 0},
                        {11, 1, 1, 0, 2, 0, 1, 1, 2, 1},  // P
                        {11, 1, 1, 0, 2, 0, 1,-1, 2,-1},
                        {11, 1, 0, 1, 1, 0, 1, 1, 2, 0},
                        {11, 1, 0, 1, 1, 0, 1, 1, 2, 1},
                        {11, 1, 1, 0, 0, 1, 1,-1, 1, 1},
                        {11, 1, 1, 0, 0, 1, 1, 1, 1, 2},
                        {11, 1, 0, 1, 1, 0, 1, 1, 0, 2},
                        {11, 1, 0, 1, 0, 2, 1, 2, 1, 1},
                        {1, 1, 1, 0, 1, 1, 1, 2, 1, 3},  // L
                        {1, 1, 0, 1, 0, 2, 0, 3, 1, 0},
                        {1, 1, 0, 1, 0, 2, 0, 3, 1, 3},
                        {1, 1, 1, 0, 1,-1, 1,-2, 1,-3},
                        {1, 1, 1, 0, 2, 0, 3, 0, 3, 1},
                        {1, 1, 1, 0, 2, 0, 3, 0, 3, -1},
                        {1, 1, 0, 1, 1, 1, 2, 1, 3, 1},
                        {1, 1, 0, 1, 1, 0, 2, 0, 3, 0},
                        {7, 1, 1, 0, 2, 0, 2, 1, 2, 2}, // V
                        {7, 1, 0, 1, 0, 2, 1, 0, 2, 0},
                        {7, 1, 0, 1, 0, 2, 1, 2, 2, 2},
                        {7, 1, 1, 0, 2, 0, 2,-1, 2,-2},
                        {10, 1, 1, 0, 1, 1, 2, 1, 2, 2},  // W
                        {10, 1, 1, 0, 1,-1, 2,-1, 2,-2},
                        {10, 1, 0, 1, 1, 1, 1, 2, 2, 2},
                        {10, 1, 0, 1, 1, 0, 1,-1, 2,-1},
                        {6, 1, 0, 1, 1, 1, 2, 1, 0, 2}, //T
                        {6, 1, 2, -1, 1, 0, 2, 0, 2, 1},
                        {6, 1, 1, 0, 2, 0, 1, 1, 1, 2},
                        {6, 1, 1, -2, 1, -1, 1, 0, 2, 0},
                        {4, 1, 0, 1, 1, 1, 2, 1, 2, 2},//Z
                        {4, 1, 2, -1, 1, 0, 2, 0, 0, 1},
                        {4, 1, 1, -2, 2, -2, 1, -1, 1, 0},
                        {4, 1, 1, 0, 1, 1, 1, 2, 2, 2},
                        {12, 1, 1, -1, 1, 0, 2, 0, 3, 0}, //Y
                        {12, 1, 2, -1, 1, 0, 2, 0, 3, 0},
                        {12, 1, 1, 0, 2, 0, 3, 0, 1, 1},
                        {12, 1, 1, 0, 2, 0, 3, 0, 2, 1},
                        {12, 1, 1, -2, 1, -1, 1, 0, 1, 1},
                        {12, 1, 0, 1, 0, 2, 1, 2, 0, 3},
                        {12, 1, 1, -1, 1, 0, 1, 1, 1, 2},
                        {12, 1, 0, 1, 1, 1, 0, 2, 0, 3},
                        {8, 1, 0, 1, 1, 1, 1, 2, 1, 3},//N
                        {8, 1, 1, -1, 1, 0, 0, 1, 0, 2},
                        {8, 1, 1, -1, 2, -1, 3, -1, 1, 0},
                        {8, 1, 1, 0, 2, 0, 2, 1, 3, 1},
                        {8, 1, 1, -2, 1, -1, 1, 0, 0, 1},
                        {8, 1, 0, 1, 0, 2, 1, 2, 1, 3},
                        {8, 1, 1, 0, 1, 1, 2, 1, 3, 1},
                        {8, 1, 2, -1, 3, -1, 1, 0, 2, 0},
                        {9, 1, 1, -2, 1, -1, 2, -1, 1, 0}, //F
                        {9, 1, 1, -1, 1, 0, 1, 1, 2, 1}, 
                        {9, 1, 1, -1, 2, -1, 1, 0, 1, 1},
                        {9, 1, 1, 0, 1, 1, 2, 1, 1, 2}, 
                        {9, 1, 2, -1, 1, 0, 2, 0, 1, 1},
                        {9, 1, 0, 1, 1, 1, 2, 1, 1, 2}, 
                        {9, 1, 1, -1, 1, 0, 2, 0, 2, 1}, 
                        {9, 1, 1, -1, 1, 0, 2, 0, 0, 1}, 
                        {3, 1, 1, 0, 1, 1, 0, 2, 1, 2}, //U
                        {3, 1, 1, 0, 0, 1, 0, 2, 1, 2}, 
                        {3, 1, 1, 0, 2, 0, 0, 1, 2, 1}, 
                        {3, 1, 2, 0, 0, 1, 1, 1, 2, 1}, 
                        {5, 1, 1, -1, 1, 0, 2, 0, 1, 1}, //X
        };
        return pentominoes;
    }	
}