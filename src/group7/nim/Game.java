package group7.nim;

import java.util.ArrayList;
import java.util.Random;

public class Game {
	enum GameType {
		playerVersusPlayer, 
		playerVersusComputer, 
		computerVersusComputer;
	}
	
	BoardState board;
	Random rand;
	GameType gameType;
	int gamesToPlay;
	int playerTurn;
	ArrayList<IPlayer> players;

	Game() {
		board = new BoardState(3, 5, 7);
		players = new ArrayList<IPlayer>();
		rand = new Random();
		playerTurn = 0;
		gamesToPlay = 1;
	}

	public void reset() {
		GameBoard = new BoardState(3, 5, 7);
	}
	
	public boolean isGamesToPlay() {
		return gamesToPlay > 0;
	}
	
	public boolean gameNotOver() {
		return !board.isGameOver();
	}

	public void setupGame() {
			int selection = IO.getInstance().promptForGameType();
			board = new BoardState(3, 5, 7);
			players.clear();
			switch (userSelection) {
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
			}
		
	}
	
	public void update() {
		board.print();
		board.setRows(players.get(playerTurn).takeTurn(board.getRows()));
		swapTurn();
	}
	
	public String getWinner() {
		return players.get(playerTurn).getName();
	}
	private void shufflePlayers() {
		int randInt = rand.nextInt(players.size());
		IPlayer shuffledPlayer = players.get(randInt);
		players.remove(randInt);
		players.add(shuffledPlayer);
	}
	

	
	public void postGame() {
		for(IPlayer player : players) {
			player.postGame();
		}
	}

	private void swapTurn() {
		playerTurn = (playerTurn == 0) ? 1 : 0;
	}
}
