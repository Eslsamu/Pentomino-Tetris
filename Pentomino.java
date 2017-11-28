public class Pentomino{
	
	private int[][] coords;
	private int colorIndex;

		public Pentomino(int[][] newCoords, int newColorIndex){
		coords = newCoords;
		colorIndex = newColorIndex;
	}

	public int[][] getCoordinates(){
            return coords;
	}
        
        public void setCoordinates(int[][] newCoordinates){
            coords = newCoordinates;
        } 
        
	public int getColorIndex(){
		return colorIndex;
	}
}
