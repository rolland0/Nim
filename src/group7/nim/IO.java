package group7.nim;

import java.util.Scanner;

public class IO {
	Scanner in;
	
	public IO() {
		in = new Scanner(System.in);
	}

	public int promptUserForValidInt(String prompt, int[] validInts) {
		boolean validInput = false;
		int selectedInt = 0;
		do {
			System.out.println(prompt);
			selectedInt = in.nextInt();
			for (int i : validInts) {
				if(i == selectedInt)
					validInput = true;
			}
			if(!validInput)
				System.out.println("Invalid selection. Try again.");
		} while(!validInput);
		return selectedInt;
	}
	
	public int promptUserForValidIntAboveZero(String prompt) {
		boolean validInput = false;
		int selectedInt = 0;
		do {
			System.out.println(prompt);
			selectedInt = in.nextInt();
			if(selectedInt > 0)
					validInput = true;
			
			if(!validInput)
				System.out.println("Invalid selection. Try again.");
		} while(!validInput);
		return selectedInt;
	}
}
