package GameLogic;
import javafx.scene.paint.Color;

public class Pentomino{
	
	private int[][] coords;
	private Color color;

		public Pentomino(int[][] newCoords, Color newColor){
		coords = newCoords;
		color = newColor;
	}

	public int[][] getCoordinates(){
            return coords;
	}
        
        public void setCoordinates(int[][] newCoordinates){
            coords = newCoordinates;
        } 
        
	public Color getColorIndex(){
		return color;
	}
}
