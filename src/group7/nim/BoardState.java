package group7.nim;

public class BoardState implements Comparable<BoardState> {
	public enum RowMaxes{
		ROW1(3), ROW2(5), ROW3(7), MAX(18);
		private int value;
		
		private RowMaxes(int value){this.value = value;}
		
		public int getValue(){return value;}
	}

	private int row1;
	private int row2;
	private int row3;
	private double val;

	public BoardState(int r1, int r2, int r3) {
		row1 = r1;
		row2 = r2;
		row3 = r3;
	}
	
	public BoardState(int[] rows) {
		setRows(rows);
	}
	
	public int[] getRows() {
		return new int[]{row1, row2, row3};
	}
	
	public void setRows(int rows[]) {
		if(rows.length < 3) {
			row1 = 0;
			row2 = 0;
			row3 = 0;
		}
		else {
			row1 = rows[0];
			row2 = rows[1];
			row3 = rows[2];
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
		System.out.println(row1);
		System.out.println(row2);
		System.out.println(row3);
		System.out.println("-------");
		System.out.println();
	}

	public String toString() {
		return "" + row1 + " , " + row2 + " , " + row3 + " : " + val;
	}

	// returns true if all rows correspond
	public boolean equals(Object obj) {
		// preliminary check
		if (obj == null || obj.getClass() == this.getClass())
			return false;

		BoardState other = (BoardState) obj;

		return (this.row1 == other.row1 && this.row2 == other.row2 && this.row3 == other.row3);
	}

	public int hashCode() {
		int hash = 1;
		hash = hash * 17 + row1;
		hash = hash * 31 + row2;
		hash = hash * 13 + row3;
		return hash;
	}

	// returns true if there are no pieces left
	public boolean isGameOver() {
		return (this.row1 == 0 && this.row2 == 0 && this.row3 == 0);
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
