package group7.nim;

import java.util.ArrayList;
import java.util.Collections;

public class AI implements IPlayer {
	private ArrayList<BoardState> states;
	private Average[][][] stats;
	private ArrayList<BoardState> moves;
	private String name;

	public AI(String name) {
		this.name = name;
		states = new ArrayList<BoardState>();
		stats = new Average[4][6][8];
		moves = new ArrayList<BoardState>();
		for (int i = 0; i <= BoardState.RowMaxes.ROW1.getValue(); i++) {
			for (int j = 0; j <= BoardState.RowMaxes.ROW2.getValue(); j++) {
				for (int k = 0; k <= BoardState.RowMaxes.ROW3.getValue(); k++) {
					stats[i][j][k] = new Average();
				}
			}
		}
	}
	
	private boolean rowsAreEmpty(int[] rows, int row1, int row2) {
		return rows[row1] == 0 && rows[row2] == 0;
	}

	@Override
	public int[] takeTurn(int[] rows) {
		states.add(0, new BoardState(rows));
		
		if (rowsAreEmpty(rows, 0, 1)) {
			if (rows[2] > 1) {
				System.out.println("Row Number: 3\nCount Removed: "
						+ (rows[2] - 1));
				rows[2] = 1;
			} else {
				rows[2] = 0;
				System.out.println("Row Number: 3\nCount Removed: 1");
			}
		} else if (rowsAreEmpty(rows, 0, 2)) {
			if (rows[1] > 1) {
				rows[1] = 1;
				System.out.println("Row Number: 2\nCount Removed: "
						+ (rows[2] - 1));
			} else {
				rows[1] = 0;
				System.out.println("Row Number: 2\nCount Removed: 1");
			}
		} else if (rowsAreEmpty(rows, 1, 2)) {
			if (rows[0] > 1) {
				rows[0] = 1;
				System.out.println("Row Number: 1\nCount Removed: "
						+ (rows[2] - 1));
			} else {
				rows[0] = 0;
				System.out.println("Row Number: 1\nCount Removed: 1");
			}
		} else {
			rows = randomRowSelect(rows);
		}
		return rows;
	}

	private void getMoves() {
		moves.clear();
		int[] rows = states.get(0).getRows();
		int[] tmpRows;

		for (int i = 1; i < BoardState.RowMaxes.ROW1.getValue() + 1; i++) {
			if (rows[0] - i >= 0) {
				tmpRows = rows;
				tmpRows[0] -= i;
				moves.add(new BoardState(tmpRows));
			}
		}

		for (int j = 1; j < BoardState.RowMaxes.ROW2.getValue() + 1; j++) {
			if (rows[1] - j >= 0) {
				tmpRows = rows;
				tmpRows[1] -= j;
				moves.add(new BoardState(tmpRows));
			}
		}

		for (int k = 1; k < BoardState.RowMaxes.ROW3.getValue() + 1; k++) {
			if (rows[2] - k >= 0) {
				tmpRows = rows;
				tmpRows[2] -= k;
				moves.add(new BoardState(tmpRows));
			}
		}
	}

	private int[] randomRowSelect(int[] rows) {
		getMoves();
		sortMovesByValue();
		int[] idealRows = moves.get(0).getRows();
		
		for(int i = 0; i < rows.length; i++) {
			if (rows[i] != idealRows[i]) {
				System.out.println("Row Number: " + i);
				int difference = rows[i] - idealRows[i];
				System.out.println("Count Removed: " + difference);
				break;
			}
		}
		return idealRows;
	}
	
	private Boolean isEven(int numToCheck){
		return numToCheck%2==0;
	}

	private void assignValues() {
		if (states.get(0).isGameOver()) {
			int numStates = states.size() - 1;
			int numOdds = numStates / 2;
			int numEvens = numOdds + numStates % 2;

			int curEven = 0;
			int curOdd = 0;
			float target;

			for (int i = 0; i < numStates; i++) {
				if (isEven(i)) // even
				{
					target = -1 * ((numEvens - curEven) / (float) numEvens);
					curEven++;
				} else {
					target = (numOdds - curOdd) / (float) numOdds;
					curOdd++;
				}

				states.get(i).setValue(target);
			}

		}
	}

	private void recordBoardStateValues() {
		for (BoardState state : states) {
			int[] rows = state.getRows();
			stats[rows[0]][rows[1]][rows[2]].add(state.getVal());
		}
	}

	private void sortMovesByValue() {
		for (BoardState move : moves) {
			int[] rows = move.getRows();
			move.setVal(stats[rows[0]][rows[1]][rows[2]].getValue());
		}

		Collections.sort(moves);

	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void processMetaGame() {
		assignValues();
		recordBoardStateValues();
	}

}
