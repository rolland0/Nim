package group7.nim;

public class Player implements IPlayer {
	private String name;
	
	public Player(String name) {
		this.name = name;
	}
	
	@Override
	public int[] takeTurn(int[] rows) {

		boolean moveMade = false;
		do {

			int rowNum = IO.getInstance().promptUserForValidInt(
					"Enter row number: ", new int[] { 1, 2, 3 });
			System.out.println();
			int numTook = IO.getInstance()
					.promptUserForValidIntAboveZero("Enter amount to take: ");
			
			
				if(numTook <= rows[rowNum-1]) {
					rows[rowNum-1] -= numTook;
					moveMade = true;
				}
				
			if(!moveMade)
				System.out.println("Invalid move. Try again.");
		} while (!moveMade);
		return rows;
	}
	
	@Override 
	public void postGame() {
		
	}

	@Override
	public String getName() {
		return name;
	}

}
