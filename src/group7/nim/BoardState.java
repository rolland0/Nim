package group7.nim;

public class BoardState implements Comparable<BoardState> {
	public enum RowMaxes{
		ROW1(3), ROW2(5), ROW3(7), MAX(18);
		private int value;
		
		private RowMaxes(int value){this.value = value;}
		
		public int getValue(){return value;}
	}

	int row1;
	int row2;
	int row3;
	double val;

	public BoardState(int r1, int r2, int r3) {
		row1 = r1;
		row2 = r2;
		row3 = r3;
	}

	public BoardState(BoardState copy) {
		row1 = copy.row1;
		row2 = copy.row2;
		row3 = copy.row3;
	}

	public void print() {
		for (int i = 0; i < row1; i++) {
			System.out.print((i + 1) + " ");
		}
		System.out.println();

		for (int i = 0; i < row2; i++) {
			System.out.print((i + 1) + " ");
		}
		System.out.println();

		for (int i = 0; i < row3; i++) {
			System.out.print((i + 1) + " ");
		}
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
