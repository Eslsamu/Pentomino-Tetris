package Agent;

public class Pair implements Comparable<Pair> {
    private int index;
    private int value;

    public Pair(int index) {
        this.index = index;
        value = 0;
    }

    @Override
    public int compareTo(Pair other) {
        return -1*Integer.valueOf(this.value).compareTo(other.value);
    }
    
    public void setValue(int v) {
    	value = v;
    }
    
    public int getValue() {
    	return value;
    }
    
    public int getIndex() {
    	return index;
    }
}