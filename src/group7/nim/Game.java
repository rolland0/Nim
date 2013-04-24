package group7.nim;

import java.util.Random;

public class Game {
	enum GameType {
		playerVersusPlayer, playerVersusComputer, computerVersusComputer;
	}

	AI compAI;
	BoardState GameBoard;
	boolean player1Turn = true;
	boolean humanTurn = false;
	GameType currentGameType;

	Game() {
		compAI = new AI();
		GameBoard = new BoardState(3, 5, 7);
	}

	public void reset() {
		GameBoard = new BoardState(3, 5, 7);
	}

	public void menu() {
		final int userWantsToQuit = 4;
		int gamesToPlay = 1;
		int userSelection;
		do{
			IO userIO = new IO();
			userSelection = userIO.promptUserForValidInt(
					"Please enter the number of how you wish to play:"
							+ "\n1 - Player Vs. Player"
							+ "\n2 - Player Vs. Computer"
							+ "\n3 - Computer Vs. Computer" + "\n4 - Quit",
							new int[] { 1, 2, 3, 4 });

			switch (userSelection) {
			case 1:
				currentGameType = GameType.playerVersusPlayer;
				break;
			case 2:
				currentGameType = GameType.playerVersusComputer;
				break;
			case 3:
				currentGameType = GameType.computerVersusComputer;
				gamesToPlay = userIO
						.promptUserForValidIntAboveZero("How many games should the computers play?");
				selectStart();
				break;
			}

			for (int i = 0; i < gamesToPlay; i++) {
				reset();
				update();
				compAI.learn();
			}

			gamesToPlay = 1;
		}
		while(userSelection != userWantsToQuit);
	}

	public void selectStart() {
		if(!isComputerVsComputer()){
			humanTurn=(isPlayer1Turn())? true:false;
			int currentUsersPlayerNumber=(humanTurn)? 1:2;
			System.out.println("You are Player " + currentUsersPlayerNumber);
		}

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private Boolean isComputerVsComputer(){
		return currentGameType == GameType.computerVersusComputer;
	}

	private Boolean isPlayer1Turn(){
		Random rand = new Random();
		int choice = rand.nextInt(10) + 1;
		return choice%2==0;
	}

	private int determineWhichPlayer(){
		return (player1Turn)? 1:2;
	}

	private boolean isComputersTurn(){
		return currentGameType == GameType.computerVersusComputer
				|| (currentGameType == GameType.playerVersusComputer && !humanTurn);
	}
	
	public void update() {
		GameBoard.print();
		int declareWhoseTurn=determineWhichPlayer();
		System.out.println("PLAYER " + declareWhoseTurn + "\n");
		do{
			if (isComputersTurn()) {
				GameBoard = compAI.update(GameBoard);
				player1Turn = !player1Turn;
				humanTurn=true;
				swapPlayers();
			} else {
				boolean moveMade = false;
				do {
					IO userIO = new IO();
					int rowNum = userIO.promptUserForValidInt(
							"Enter row number: ", new int[] { 1, 2, 3 });
					System.out.println();
					int numTook = userIO
							.promptUserForValidIntAboveZero("Enter amount to take: ");

					switch (rowNum) {
					case 1:
						if (numTook <= GameBoard.row1) {
							GameBoard.row1 -= numTook;
							moveMade = true;
						}
						break;

					case 2:
						if (numTook <= GameBoard.row2) {
							GameBoard.row2 -= numTook;
							moveMade = true;
						}
						break;

					case 3:
						if (numTook <= GameBoard.row3) {
							GameBoard.row3 -= numTook;
							moveMade = true;
						}
						break;						
					}
					if(moveMade){
						player1Turn = !player1Turn;
						swapPlayers();
						humanTurn=false;
					}
					else {
						System.out.println("Invalid move. Try again.");
					}
				} while (!moveMade);
			}
		}while(!GameBoard.isGameOver());
		int declareWhoWins=determineWhichPlayer();
		System.out.println("PLAYER " + declareWhoWins + " is the winner!\n");
	}

	public void swapPlayers() {
		if (currentGameType == GameType.playerVersusComputer) {
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
