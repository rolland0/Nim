package group7.nim;

public class BoardState implements Comparable<BoardState> {
	public enum RowMaxes{
		ROW1(3), ROW2(5), ROW3(7), MAX(18);
		private int value;
		
		private RowMaxes(int value){this.value = value;}
		
		public int getValue(){return value;}
	}
	
	private final int NumRows = 3;

	private int topRowValue;
	private int middleRowValue;
	private int bottomRowValue;
	private double val;
	
	public BoardState() {
		topRowValue = RowMaxes.ROW1.getValue();
		middleRowValue = RowMaxes.ROW2.getValue();
		bottomRowValue = RowMaxes.ROW3.getValue();
	}

	public BoardState(int r1, int r2, int r3) {
		topRowValue = r1;
		middleRowValue = r2;
		bottomRowValue = r3;
	}
	
	public BoardState(int[] rows) {
		setRows(rows);
	}
	
	public int[] getRows() {
		return new int[]{topRowValue, middleRowValue, bottomRowValue};
	}
	
	public void setRows(int rows[]) {
		if(rows.length < NumRows) {
			topRowValue = 0;
			middleRowValue = 0;
			bottomRowValue = 0;
		}
		else {
			topRowValue = rows[0];
			middleRowValue = rows[1];
			bottomRowValue = rows[2];
		}
	}
	
	public double getVal() {
		return val;
	}
	
	public void setVal(double value) {
		val = value;
	}

	public void print() {
		
		System.out.println("-------");
		System.out.println(topRowValue);
		System.out.println(middleRowValue);
		System.out.println(bottomRowValue);
		System.out.println("-------");
		System.out.println();
	}

	public String toString() {
		return "" + topRowValue + " , " + middleRowValue + " , " + bottomRowValue + " : " + val;
	}

	// returns true if all rows correspond
	public boolean equals(Object obj) {
		// preliminary check
		if (obj == null || obj.getClass() != this.getClass())
			return false;

		BoardState other = (BoardState) obj;

		return (this.topRowValue == other.topRowValue && this.middleRowValue == other.middleRowValue && this.bottomRowValue == other.bottomRowValue);
	}

	public int hashCode() {
		return bottomRowValue + middleRowValue*10 + topRowValue*100;	 
	}

	// returns true if there are no pieces left
	public boolean isGameOver() {
		return (this.topRowValue == 0 && this.middleRowValue == 0 && this.bottomRowValue == 0);
	}

	public void setValue(float value) {
		val = value;
	}

	@Override
	public int compareTo(BoardState other) {
		if (val == other.val)
			return 0;
		else if (val > other.val)
			return -1;
		else
			return 1;
	}

}
