package group7.nim;

import java.util.Scanner;
import java.util.Random;

public class Game {
	AI compAI;
	BoardState board = new BoardState(3, 5, 7);
	Scanner scan = new Scanner(System.in);
	Random rand = new Random();
	int numRows = 3;
	boolean player1Turn = true;
	boolean humanTurn = false;
	boolean pvp = false;
	boolean pvc = false;
	boolean cvc = false;
	int gamesToPlay = 1;

	Game() {
		compAI = new AI();
	}

	public void reset() {
		board = new BoardState(3, 5, 7);
	}

	public boolean menu() {
		boolean validInput = false;

		while (!validInput) {
			System.out
					.println("Please enter the number of how you wish to play:"
							+ "\n1 - Player Vs. Player"
							+ "\n2 - Player Vs. Computer"
							+ "\n3 - Computer Vs. Computer" + "\n4 - Quit");

			int selection = scan.nextInt();

			validInput = true;
			if (selection == 1) {
				pvp = true;
				pvc = false;
				cvc = false;
			} else if (selection == 2) {
				pvp = false;
				pvc = true;
				cvc = false;
			} else if (selection == 3) {
				pvp = false;
				pvc = false;
				cvc = true;
				validInput = false;
				while (!validInput) {
					System.out
							.println("How many games should the computers play?");
					int games = scan.nextInt();
					if (games > 0) {
						validInput = true;
						gamesToPlay = games;
					}
				}
			} else if (selection == 4)
				return false;
			else
				validInput = false;
		}

		if (pvc)
			selectStart();

		for (int i = 0; i < gamesToPlay; i++) {
			reset();
			while (!update())
				;
			compAI.learn();
			// reset();
		}

		gamesToPlay = 1;

		return true;
	}

	public void selectStart() {
		int choice = rand.nextInt(10) + 1;

		if (choice % 2 == 0) {
			humanTurn = true;

			System.out.println("You are Player 1");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			humanTurn = false;

			System.out.println("You are Player 2");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean update() {
		System.out
				.println("---------------------------------------------------------------");
		if (player1Turn)
			System.out.println("PLAYER 1\n");
		else
			System.out.println("PLAYER 2\n");

		System.out.println("Row 1: " + board.row1);
		System.out.println("Row 2: " + board.row2);
		System.out.println("Row 3: " + board.row3 + "\n");

		if (board.isGameOver()) {
			if (player1Turn)
				System.out.println("Player 1 is the winner!");
			else
				System.out.println("Player 2 is the winner!");
			return true;
		} else {
			if (cvc || (pvc && !humanTurn)) {
				compAI.update(board);
				player1Turn = !player1Turn;
				swapPlayers();
			} else {
				System.out.println("Enter row number: ");
				int rowNum = scan.nextInt();
				System.out.println("Enter amount to take: ");
				int numTook = scan.nextInt();

				if (rowNum == 1) {
					if (numTook <= board.row1 && numTook > 0) {
						board.row1 -= numTook;
						player1Turn = !player1Turn;
						swapPlayers();
					} else
						System.out
								.println("illegal move, please remake your choice");
				} else if (rowNum == 2 && numTook > 0) {
					if (numTook <= board.row2) {
						board.row2 -= numTook;
						player1Turn = !player1Turn;
						swapPlayers();
					} else
						System.out
								.println("illegal move, please remake your choice");
				} else if (rowNum == 3 && numTook > 0) {
					if (numTook <= board.row3) {
						board.row3 -= numTook;
						player1Turn = !player1Turn;
						swapPlayers();
					} else
						System.out
								.println("illegal move, please remake your choice");
				} else
					System.out
							.println("illegal move, please remake your choice");
			}
		}
		return false;
	}

	public void swapPlayers() {
		if (pvc) {
			humanTurn = !humanTurn;
		}
	}

	
	

	
//	public void Test() {
//		states.add(0, new BoardState(3, 5, 7));
//		states.add(0, new BoardState(1, 5, 7));
//		states.add(0, new BoardState(0, 5, 7));
//		states.add(0, new BoardState(0, 5, 5));
//		states.add(0, new BoardState(0, 3, 5));
//		states.add(0, new BoardState(0, 3, 3));
//		states.add(0, new BoardState(0, 0, 3));
//		states.add(0, new BoardState(0, 0, 0));
//
//		assignValues();
//		recordValues();
//
//		System.out.println("Printing gamestats");
//		for (BoardState state : states) {
//			System.out.println(state);
//		}
//		states.clear();
//
//		states.add(0, new BoardState(3, 5, 7));
//		states.add(0, new BoardState(0, 5, 7));
//		states.add(0, new BoardState(0, 5, 3));
//		states.add(0, new BoardState(0, 3, 3));
//		states.add(0, new BoardState(0, 0, 3));
//		states.add(0, new BoardState(0, 0, 1));
//		states.add(0, new BoardState(0, 0, 0));
//
//		assignValues();
//		recordValues();
//
//		System.out.println("Printing gamestats");
//		for (BoardState state : states) {
//			System.out.println(state);
//		}
//		states.clear();
//
//		for (int i = 0; i <= BoardState.Row1Max; i++) {
//			for (int j = 0; j <= BoardState.Row2Max; j++) {
//				for (int k = 0; k <= BoardState.Row3Max; k++) {
//					if (stats[i][j][k].getValue() != 0) {
//						System.out.println(i + " , " + j + " , " + k);
//						System.out.println(stats[i][j][k]);
//						System.out.println();
//					}
//				}
//			}
//		}
//	}
//
//	public void Test2() {
//		states.add(0, new BoardState(3, 5, 7));
//		states.add(0, new BoardState(1, 5, 7));
//		states.add(0, new BoardState(0, 5, 7));
//		states.add(0, new BoardState(0, 5, 5));
//
//		System.out.println("Current move: ");
//		System.out.println(states.get(0));
//
//		getMoves();
//
//		System.out.println("Possible Moves: ");
//		for (BoardState state : moves) {
//			System.out.println(state);
//		}
//	}
//
//	public void Test3() {
//		states.add(0, new BoardState(3, 5, 7));
//		states.add(0, new BoardState(1, 5, 7));
//		states.add(0, new BoardState(0, 5, 7));
//		states.add(0, new BoardState(0, 5, 5));
//		states.add(0, new BoardState(0, 3, 5));
//		states.add(0, new BoardState(0, 3, 3));
//		states.add(0, new BoardState(0, 0, 3));
//		states.add(0, new BoardState(0, 0, 0));
//
//		assignValues();
//		recordValues();
//
//		states.add(0, new BoardState(3, 5, 7));
//		states.add(0, new BoardState(0, 5, 7));
//		states.add(0, new BoardState(0, 5, 3));
//		states.add(0, new BoardState(0, 3, 3));
//		states.add(0, new BoardState(0, 0, 3));
//		states.add(0, new BoardState(0, 0, 1));
//		states.add(0, new BoardState(0, 0, 0));
//
//		assignValues();
//		recordValues();
//
//		states.add(0, new BoardState(3, 5, 7));
//		states.add(0, new BoardState(1, 5, 7));
//		states.add(0, new BoardState(0, 5, 7));
//		states.add(0, new BoardState(0, 5, 5));
//
//		System.out.println("Current move: ");
//		System.out.println(states.get(0));
//
//		getMoves();
//
//		System.out.println("Possible Moves: ");
//		for (BoardState state : moves) {
//			System.out.println(state);
//		}
//
//		System.out.println("Sorting moves by value:");
//		sortMovesByValue();
//
//		for (BoardState state : moves) {
//			System.out.println(state);
//		}
//	}
}
