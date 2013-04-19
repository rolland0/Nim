package group7.nim;

import java.util.Scanner;

public class IO {
	private static IO userIO = new IO();
	private Scanner in;

	private IO() {
		in = new Scanner(System.in);
	}

	public static IO getInstance() {
		return userIO;
	}

	public int promptForGameType() {

		return promptUserForValidInt(
				"Please enter the number of how you wish to play:"
						+ "\n1 - Player Vs. Player"
						+ "\n2 - Player Vs. Computer"
						+ "\n3 - Computer Vs. Computer" + "\n4 - Quit",
				new int[] { 1, 2, 3, 4 });
	}

	public int promptUserForValidInt(String prompt, int[] validInts) {
		boolean validInput = false;
		int selectedInt = 0;
		do {
			System.out.println(prompt);
			selectedInt = in.nextInt();
			for (int i : validInts) {
				if (i == selectedInt)
					validInput = true;
			}
			if (!validInput)
				System.out.println("Invalid selection. Try again.");
		} while (!validInput);
		return selectedInt;
	}

	public int promptUserForValidIntAboveZero(String prompt) {
		boolean validInput = false;
		int selectedInt = 0;
		do {
			System.out.println(prompt);
			selectedInt = in.nextInt();
			if (selectedInt > 0)
				validInput = true;

			if (!validInput)
				System.out.println("Invalid selection. Try again.");
		} while (!validInput);
		return selectedInt;
	}
}
