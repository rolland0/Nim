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
	
	public void reset(){
		
	}
	
	public void learn() {
		assignValues();
		recordValues();
	}
	
	public void update(BoardState board) {
		states.add(board);
		if (board.row1 == 0 && board.row2 == 0) {
			if (board.row3 > 1) {
				System.out.println("Row Number: 3\nCount Removed: "
						+ (board.row3 - 1));
				board.row3 = 1;
			} else {
				board.row3 = 0;
				System.out.println("Row Number: 3\nCount Removed: 1");
			}
		} else if (board.row1 == 0 && board.row3 == 0) {
			if (board.row2 > 1) {
				board.row2 = 1;
				System.out.println("Row Number: 2\nCount Removed: "
						+ (board.row3 - 1));
			} else {
				board.row2 = 0;
				System.out.println("Row Number: 2\nCount Removed: 1");
			}
		} else if (board.row2 == 0 && board.row3 == 0) {
			if (board.row1 > 1) {
				board.row1 = 1;
				System.out.println("Row Number: 1\nCount Removed: "
						+ (board.row3 - 1));
			} else {
				board.row1 = 0;
				System.out.println("Row Number: 1\nCount Removed: 1");
			}
		} else {
			randomRowSelect(board);
		}
	}

	private void getMoves() {
		moves.clear();
		BoardState curState = states.get(0);

		for (int i = 1; i < BoardState.Row1Max + 1; i++) {
			if (curState.row1 - i >= 0) {
				BoardState tmpState = new BoardState(curState);
				tmpState.row1 -= i;
				moves.add(tmpState);
			}
		}

		for (int j = 1; j < BoardState.Row2Max + 1; j++) {
			if (curState.row2 - j >= 0) {
				BoardState tmpState = new BoardState(curState);
				tmpState.row2 -= j;
				moves.add(tmpState);
			}
		}

		for (int k = 1; k < BoardState.Row3Max + 1; k++) {
			if (curState.row3 - k >= 0) {
				BoardState tmpState = new BoardState(curState);
				tmpState.row3 -= k;
				moves.add(tmpState);
			}
		}
	}
	
	private void randomRowSelect(BoardState board) {
		getMoves();
		sortMovesByValue();
		BoardState wantedState = moves.get(0);
		BoardState currentState = board;

		if (currentState.row1 != wantedState.row1) {
			System.out.println("Row Number: 1");
			int difference = currentState.row1 - wantedState.row1;
			System.out.println("Count Removed: " + difference);
		}

		if (currentState.row2 != wantedState.row2) {
			System.out.println("Row Number: 2");
			int difference = currentState.row2 - wantedState.row2;
			System.out.println("Count Removed: " + difference);
		}

		if (currentState.row3 != wantedState.row3) {
			System.out.println("Row Number: 3");
			int difference = currentState.row3 - wantedState.row3;
			System.out.println("Count Removed: " + difference);
		}

		board = wantedState;
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
			stats[state.row1][state.row2][state.row3].add(state.val);
		}
	}

	private void sortMovesByValue() {
		for (BoardState move : moves) {
			move.val = stats[move.row1][move.row2][move.row3].getValue();
		}

		Collections.sort(moves);

	}

}
