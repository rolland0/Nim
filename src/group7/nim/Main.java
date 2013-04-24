package group7.nim;

public class Main {
	public static void main(String[] args) {
		Game game = new Game();
		while(game.isGamesToPlay()) {
			game.setupGame();
			if(game.shouldPlay()) {
				while(game.gameNotOver()) {
					game.update();
				}
				System.out.println(game.getWinner() + " won!\n");
				game.postGame();
			}
			else {
				break;
			}
		}
	}

}
