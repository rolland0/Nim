package group7.nim;

import java.util.ArrayList;
import java.util.Collections;

public class AI {
	ArrayList<BoardState> states;
	Average[][][] stats;
	ArrayList<BoardState> moves;

	public AI() {
		states = new ArrayList<BoardState>();
		stats = new Average[4][6][8];
		moves = new ArrayList<BoardState>();
		for (int i = 0; i <= BoardState.Row1Max; i++) {
			for (int j = 0; j <= BoardState.Row2Max; j++) {
				for (int k = 0; k <= BoardState.Row3Max; k++) {
					stats[i][j][k] = new Average();
				}
			}
		}
	}

	public void reset() {

	}

	public void learn() {
		assignValues();
		recordValues();
	}

	public int[] update(int[] rows) {
		states.add(0, new BoardState(rows));
		
		if (rows[0] == 0 && rows[1] == 0) {
			if (rows[2] > 1) {
				System.out.println("Row Number: 3\nCount Removed: "
						+ (rows[2] - 1));
				rows[2] = 1;
			} else {
				rows[2] = 0;
				System.out.println("Row Number: 3\nCount Removed: 1");
			}
		} else if (rows[0] == 0 && rows[2] == 0) {
			if (rows[1] > 1) {
				rows[1] = 1;
				System.out.println("Row Number: 2\nCount Removed: "
						+ (rows[2] - 1));
			} else {
				rows[1] = 0;
				System.out.println("Row Number: 2\nCount Removed: 1");
			}
		} else if (rows[1] == 0 && rows[2] == 0) {
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

		for (int i = 1; i < BoardState.Row1Max + 1; i++) {
			if (rows[0] - i >= 0) {
				tmpRows = rows;
				tmpRows[0] -= i;
				moves.add(new BoardState(tmpRows));
			}
		}

		for (int j = 1; j < BoardState.Row2Max + 1; j++) {
			if (rows[1] - j >= 0) {
				tmpRows = rows;
				tmpRows[1] -= j;
				moves.add(new BoardState(tmpRows));
			}
		}

		for (int k = 1; k < BoardState.Row3Max + 1; k++) {
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

	private void assignValues() {
		if (states.get(0).isGameOver()) {
			int numStates = states.size() - 1;
			int numOdds = numStates / 2;
			int numEvens = numOdds + numStates % 2;

			int curEven = 0;
			int curOdd = 0;
			float target;

			for (int i = 0; i < numStates; i++) {
				if (i % 2 == 0) // even
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

	private void recordValues() {
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

}
