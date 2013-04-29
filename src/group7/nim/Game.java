package group7.nim;

import java.util.ArrayList;
import java.util.Random;

public class Game {
	enum GameType {
		playerVersusPlayer, 
		playerVersusComputer, 
		computerVersusComputer;
	}
	
	BoardState gameBoard;
	GameType currentGameType;
	int gamesToPlay;
	int playerTurn;
	ArrayList<IPlayer> players;
	boolean shouldPlay = true;

	Game() {
		gameBoard = new BoardState();
		players = new ArrayList<IPlayer>();
		
		playerTurn = 0;
		gamesToPlay = 1;
	}
	
	public boolean isGamesToPlay() {
		return gamesToPlay > 0;
	}
	
	public boolean gameNotOver() {
		return shouldPlay && !gameBoard.isGameOver();
	}
	
	public boolean shouldPlay() {
		return shouldPlay;
	}

	public void setupGame() {
			int selection = IO.getInstance().promptForGameType();
			gameBoard = new BoardState();
			players.clear();
			switch (selection) {
			case 1:
				currentGameType = GameType.playerVersusPlayer;
				players.add(new Player("Player 1"));
				players.add(new Player("Player 2"));
				gamesToPlay = 1;
				break;
				
			case 2:
				currentGameType = GameType.playerVersusComputer;
				players.add(new Player("Player 1"));
				players.add(new AI("Computer 1"));
				shufflePlayers();
				gamesToPlay = 1;
				break;
				
			case 3:
				currentGameType = GameType.computerVersusComputer;
				players.add(new AI("Computer 1"));
				players.add(new AI("Computer 2"));
				gamesToPlay = IO.getInstance()
						.promptUserForValidIntAboveZero("How many games should the computers play?");
				break;
			case 4:
				shouldPlay = false;
				break;
			}
		
	}
	
	public void update() {
		gameBoard.print();
		gameBoard.setRows(players.get(playerTurn).takeTurn(gameBoard.getRows()));
		swapCurrentPlayerTurn();
	}
	
	public String getWinner() {
		return players.get(playerTurn).getName();
	}
	private void shufflePlayers() {
		int randInt = new Random().nextInt(players.size());
		IPlayer shuffledPlayer = players.get(randInt);
		players.remove(randInt);
		players.add(shuffledPlayer);
	}
	
	public void postGame() {
		for(IPlayer player : players) {
			player.processMetaGame();
		}
	}

	private void swapCurrentPlayerTurn() {
		playerTurn = (playerTurn == 0) ? 1 : 0;
	}
}
