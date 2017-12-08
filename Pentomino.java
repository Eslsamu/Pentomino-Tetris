package petris;

import javafx.scene.paint.Color;

public class Pentomino{
	
	private int[][] coords;
	private Color colorIndex;

		public Pentomino(int[][] newCoords, Color newColorIndex){
		coords = newCoords;
		colorIndex = newColorIndex;
	}

	public int[][] getCoordinates(){
            return coords;
	}
        
        public void setCoordinates(int[][] newCoordinates){
            coords = newCoordinates;
        } 
        
	public Color getColorIndex(){
		return colorIndex;
	}
}
