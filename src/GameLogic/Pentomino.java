package GameLogic;
import javafx.scene.paint.Color;

/**
 * Class which allows the construction of the pentomino objects, which are the backend representations of
 * the falling pentomino blocks, each object is created with x and y coordinates representing the
 * locations of the 5 blocks and a color. The coordinate and color information is provided by the
 * PentominoGenerator class.
 */


public class Pentomino{
	private int[][] coords;
	private Color color;

        public Pentomino(int[][] newCoords, Color newColor){
            coords = newCoords;
            color = newColor;
	}
        /**
        *
        * @return returns the array of x and y coordinates
        */
        
	public int[][] getCoordinates(){
            return coords;
	}

        /**
         * replaces the existing array of x and y coordinates with a new one
         * @param newCoordinates 2D array containing new coordinates
         */
        public void setCoordinates(int[][] newCoordinates){
            coords = newCoordinates;
        }

   

        /**
         *
         * @return returns the color index
         */
    public Color getColorIndex(){
		return color;
	}

}
