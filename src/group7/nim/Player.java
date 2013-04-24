package group7.nim;

public class Player implements IPlayer {
	private String name;
	
	public Player(String name) {
		this.name = name;
	}
	
	@Override
	public int[] takeTurn(int[] rows) {

		boolean moveHasBeenMade = false;
		do {

			int rowNum = IO.getInstance().promptUserForValidInt(
					"Enter row number: ", new int[] { 1, 2, 3 });
			System.out.println();
			int numTook = IO.getInstance()
					.promptUserForValidIntAboveZero("Enter amount to take: ");
			
			
				if(numTook <= rows[rowNum-1]) {
					rows[rowNum-1] -= numTook;
					moveHasBeenMade = true;
				}
				
			if(!moveHasBeenMade)
				System.out.println("Invalid move. Try again.");
		} while (!moveHasBeenMade);
		return rows;
	}
	
	@Override 
	public void processMetaGame() {
		
	}

	@Override
	public String getName() {
		return name;
	}

}
