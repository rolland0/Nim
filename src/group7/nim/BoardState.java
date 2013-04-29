package group7.nim;

public class BoardState implements Comparable<BoardState> {
	public enum RowMaxes {
		ROW1(3), ROW2(5), ROW3(7);
		private int value;

		private RowMaxes(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	private final int NumRows = 3;

	private int[] rows;
	private double val;

	public BoardState() {
		rows = new int[NumRows];
		RowMaxes[] maxValues = RowMaxes.values();
		if (rows.length == maxValues.length) {
			for (int i = 0; i < rows.length; i++) {
				rows[i] = maxValues[i].getValue();
			}
		}
	}

	public BoardState(int[] newRows) {
		rows = new int[NumRows];
		setRows(newRows);
	}

	public int[] getRows() {
		return rows.clone();
	}

	public void setRows(int newRows[]) {
		if (newRows.length == NumRows) {
			for (int i = 0; i < rows.length; i++) {
				rows[i] = newRows[i];
			}
		} else {
			for (int i = 0; i < rows.length; i++) {
				rows[i] = 0;
			}
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
		for (int i = 0; i < rows.length; i++) {
			System.out.println((i + 1) + "| " + rows[i]);
		}
		System.out.println("-------");
		System.out.println();
	}

	public String toString() {
		String str = "";
		for (int i = 0; i < NumRows; i++) {
			str += (i < NumRows - 1) ? rows[i] + " , " : rows[i];
		}
		str += " : " + val;
		return str;
	}

	// returns true if all rows correspond
	public boolean equals(Object obj) {
		// preliminary check
		if (obj == null || obj.getClass() != this.getClass())
			return false;

		BoardState other = (BoardState) obj;

		boolean areEqual = true;
		for (int i = 0; i < rows.length && areEqual; i++) {
			areEqual = rows[i] == other.rows[i];
		}

		return areEqual;
	}

	public int hashCode() {
		int code = 0;
		for (int i = 0; i < rows.length; i++) {
			code += rows[i] * (int) Math.pow(10, i);
		}
		return code;
	}

	// returns true if there are no pieces left
	public boolean isGameOver() {
		boolean areZero = true;
		for (int i = 0; i < rows.length && areZero; i++) {
			areZero = rows[i] == 0;
		}
		return areZero;
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
