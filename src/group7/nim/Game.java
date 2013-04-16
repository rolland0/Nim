package group7.nim;

import java.util.Random;

public class Game {
	enum GameType {
		playerVersusPlayer, playerVersusComputer, computerVersusComputer;
	}

	AI compAI;
	BoardState board;
	Random rand;
	IO userIO;
	int numRows = 3;
	boolean player1Turn = true;
	boolean humanTurn = false;
	GameType gameType;
	int gamesToPlay = 1;

	Game() {
		compAI = new AI();
		board = new BoardState(3, 5, 7);
		userIO = new IO();
		rand = new Random();
	}

	public void reset() {
		board = new BoardState(3, 5, 7);
	}

	public boolean menu() {
			int selection = userIO.promptUserForValidInt(
					"Please enter the number of how you wish to play:"
							+ "\n1 - Player Vs. Player"
							+ "\n2 - Player Vs. Computer"
							+ "\n3 - Computer Vs. Computer" + "\n4 - Quit",
					new int[] { 1, 2, 3, 4 });

			switch (selection) {
			case 1:
				gameType = GameType.playerVersusPlayer;
				break;
			case 2:
				gameType = GameType.playerVersusComputer;
				break;
			case 3:
				gameType = GameType.computerVersusComputer;
				gamesToPlay = userIO
						.promptUserForValidIntAboveZero("How many games should the computers play?");
				selectStart();
				break;
			case 4:
				return false;
			}

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
		board.print();
		if (player1Turn)
			System.out.println("PLAYER 1\n");
		else
			System.out.println("PLAYER 2\n");

		if (board.isGameOver()) {
			if (player1Turn)
				System.out.println("Player 1 is the winner!");
			else
				System.out.println("Player 2 is the winner!");
			return true;
		} else {
			if (gameType == GameType.computerVersusComputer
					|| (gameType == GameType.playerVersusComputer && !humanTurn)) {
				board = compAI.update(board);
				player1Turn = !player1Turn;
				swapPlayers();
			} else {
				boolean moveMade = false;
				do {

					int rowNum = userIO.promptUserForValidInt(
							"Enter row number: ", new int[] { 1, 2, 3 });
					System.out.println();
					int numTook = userIO
							.promptUserForValidIntAboveZero("Enter amount to take: ");

					switch (rowNum) {
					case 1:
						if (numTook <= board.row1) {
							board.row1 -= numTook;
							moveMade = true;
						}
						break;

					case 2:
						if (numTook <= board.row2) {
							board.row2 -= numTook;
							moveMade = true;
						}
						break;

					case 3:
						if (numTook <= board.row3) {
							board.row3 -= numTook;
							moveMade = true;
						}
						break;						
					}
					if(!moveMade)
						System.out.println("Invalid move. Try again.");
					else {
						player1Turn = !player1Turn;
						swapPlayers();
					}
				} while (!moveMade);
			}
		}
		return false;
	}

	public void swapPlayers() {
		if (gameType == GameType.playerVersusComputer) {
			humanTurn = !humanTurn;
		}
	}

	/*
	 * public void Test() { states.add(0, new BoardState(3, 5, 7));
	 * states.add(0, new BoardState(1, 5, 7)); states.add(0, new BoardState(0,
	 * 5, 7)); states.add(0, new BoardState(0, 5, 5)); states.add(0, new
	 * BoardState(0, 3, 5)); states.add(0, new BoardState(0, 3, 3));
	 * states.add(0, new BoardState(0, 0, 3)); states.add(0, new BoardState(0,
	 * 0, 0));
	 * 
	 * assignValues(); recordValues();
	 * 
	 * System.out.println("Printing gamestats"); for (BoardState state : states)
	 * { System.out.println(state); } states.clear();
	 * 
	 * states.add(0, new BoardState(3, 5, 7)); states.add(0, new BoardState(0,
	 * 5, 7)); states.add(0, new BoardState(0, 5, 3)); states.add(0, new
	 * BoardState(0, 3, 3)); states.add(0, new BoardState(0, 0, 3));
	 * states.add(0, new BoardState(0, 0, 1)); states.add(0, new BoardState(0,
	 * 0, 0));
	 * 
	 * assignValues(); recordValues();
	 * 
	 * System.out.println("Printing gamestats"); for (BoardState state : states)
	 * { System.out.println(state); } states.clear();
	 * 
	 * for (int i = 0; i <= BoardState.Row1Max; i++) { for (int j = 0; j <=
	 * BoardState.Row2Max; j++) { for (int k = 0; k <= BoardState.Row3Max; k++)
	 * { if (stats[i][j][k].getValue() != 0) { System.out.println(i + " , " + j
	 * + " , " + k); System.out.println(stats[i][j][k]); System.out.println(); }
	 * } } } }
	 * 
	 * public void Test2() { states.add(0, new BoardState(3, 5, 7));
	 * states.add(0, new BoardState(1, 5, 7)); states.add(0, new BoardState(0,
	 * 5, 7)); states.add(0, new BoardState(0, 5, 5));
	 * 
	 * System.out.println("Current move: "); System.out.println(states.get(0));
	 * 
	 * getMoves();
	 * 
	 * System.out.println("Possible Moves: "); for (BoardState state : moves) {
	 * System.out.println(state); } }
	 * 
	 * public void Test3() { states.add(0, new BoardState(3, 5, 7));
	 * states.add(0, new BoardState(1, 5, 7)); states.add(0, new BoardState(0,
	 * 5, 7)); states.add(0, new BoardState(0, 5, 5)); states.add(0, new
	 * BoardState(0, 3, 5)); states.add(0, new BoardState(0, 3, 3));
	 * states.add(0, new BoardState(0, 0, 3)); states.add(0, new BoardState(0,
	 * 0, 0));
	 * 
	 * assignValues(); recordValues();
	 * 
	 * states.add(0, new BoardState(3, 5, 7)); states.add(0, new BoardState(0,
	 * 5, 7)); states.add(0, new BoardState(0, 5, 3)); states.add(0, new
	 * BoardState(0, 3, 3)); states.add(0, new BoardState(0, 0, 3));
	 * states.add(0, new BoardState(0, 0, 1)); states.add(0, new BoardState(0,
	 * 0, 0));
	 * 
	 * assignValues(); recordValues();
	 * 
	 * states.add(0, new BoardState(3, 5, 7)); states.add(0, new BoardState(1,
	 * 5, 7)); states.add(0, new BoardState(0, 5, 7)); states.add(0, new
	 * BoardState(0, 5, 5));
	 * 
	 * System.out.println("Current move: "); System.out.println(states.get(0));
	 * 
	 * getMoves();
	 * 
	 * System.out.println("Possible Moves: "); for (BoardState state : moves) {
	 * System.out.println(state); }
	 * 
	 * System.out.println("Sorting moves by value:"); sortMovesByValue();
	 * 
	 * for (BoardState state : moves) { System.out.println(state); } }
	 */
}
