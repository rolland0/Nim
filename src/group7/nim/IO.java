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
	
	public int promptUserForValidIntUpTo( int highestValidInt) {
		boolean inputIsValid = false;
		int selectedInt = 0;
		do {
			selectedInt = in.nextInt();
			if(selectedInt >= 0 && selectedInt <= highestValidInt) {
				inputIsValid = true;
			}
			else {
				System.out.println("Invalid selection. Try again.");
			}
		} while (!inputIsValid);
		return selectedInt;
	}

	public int promptUserForValidInt(String prompt, int[] validInts) {
		boolean inputIsValid = false;
		int selectedInt = 0;
		do {
			System.out.println(prompt);
			selectedInt = in.nextInt();
			for (int i : validInts) {
				if (i == selectedInt)
					inputIsValid = true;
			}
			if (!inputIsValid)
				System.out.println("Invalid selection. Try again.");
		} while (!inputIsValid);
		return selectedInt;
	}

	public int promptUserForValidIntAboveZero(String prompt) {
		boolean inputIsValid = false;
		int selectedInt = 0;
		do {
			System.out.println(prompt);
			selectedInt = in.nextInt();
			if (selectedInt > 0)
				inputIsValid = true;

			if (!inputIsValid)
				System.out.println("Invalid selection. Try again.");
		} while (!inputIsValid);
		return selectedInt;
	}
}
