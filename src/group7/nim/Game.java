package group7.nim;

import java.util.ArrayList;
import java.util.Random;

public class Game {
	enum GameType {
		playerVersusPlayer, 
		playerVersusComputer, 
		computerVersusComputer;
	}
	
	private BoardState gameBoard;
	private GameType currentGameType;
	private Menu menu;
	private int gamesToPlay;
	private int playerTurn;
	private ArrayList<IPlayer> players;
	private boolean shouldPlay = true;

	Game() {
		gameBoard = new BoardState();
		players = new ArrayList<IPlayer>();
		constructMenu();
		
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
	
	private void constructMenu() {
		menu = new Menu("How would you like to play?");
		
		menu.addItem(new MenuItem("Player Vs. Player") {
			@Override
			public void run() {
				currentGameType = GameType.playerVersusPlayer;
				players.add(new Player("Player 1"));
				players.add(new Player("Player 2"));
				gamesToPlay = 1;
			}
		});
		
		menu.addItem(new MenuItem("Player Vs. Computer") {
			@Override
			public void run() {
				currentGameType = GameType.playerVersusComputer;
				players.add(new Player("Player 1"));
				players.add(new AI("Computer 1"));
				shufflePlayers();
				gamesToPlay = 1;
			}
		});
		
		menu.addItem(new MenuItem("Computer Vs. Computer") {
			@Override
			public void run() {
				currentGameType = GameType.computerVersusComputer;
				players.add(new AI("Computer 1"));
				players.add(new AI("Computer 2"));
				gamesToPlay = IO.getInstance()
						.promptUserForValidIntAboveZero("How many games should the computers play?");
			}
		});
		
		menu.addItem(new MenuItem("Quit") {
			@Override
			public void run() {
				shouldPlay = false;
			}
		});
	}

	public void setupGame() {
			gameBoard = new BoardState();
			players.clear();
			
			System.out.print(menu.getOptions());
			int selection = IO.getInstance().promptUserForValidIntUpTo(menu.highestOption());
			if(menu.isValid(selection)) 
				menu.execute(selection);
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
