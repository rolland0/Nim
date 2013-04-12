package group7.nim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Random;


public class Game 
{
	ArrayList<BoardState> states;
	Average[][][] stats;
	ArrayList<BoardState> moves;
	BoardState board = new BoardState(3,5,7);
	Scanner scan = new Scanner(System.in);
	Random rand = new Random();
	int numRows = 3;
	boolean player1Turn = true;
	boolean humanTurn = false;
	boolean pvp = false;
	boolean pvc = false;
	boolean cvc = false;
	int gamesToPlay = 1;
	
	Game()
	{
		states = new ArrayList<BoardState>();
		stats = new Average[4][6][8];
		moves = new ArrayList<BoardState>();
		
		for(int i = 0; i <= BoardState.Row1Max; i++)
		{
			for (int j = 0; j <= BoardState.Row2Max; j++) 
			{
				for(int k = 0; k <= BoardState.Row3Max; k++)
				{
					stats[i][j][k] = new Average();
				}
			}
		}
	}
	
	public void reset()
	{
		board = new BoardState(3,5,7);
	}
	
	public boolean menu()
	{
		boolean validInput = false;
		
		while(!validInput)
		{
			System.out.println("Please enter the number of how you wish to play:" +
					"\n1 - Player Vs. Player" +
					"\n2 - Player Vs. Computer" +
					"\n3 - Computer Vs. Computer" +
					"\n4 - Quit");
			
			int selection = scan.nextInt();
			
			validInput = true;
			if (selection == 1)
			{
				pvp = true;
				pvc = false;
				cvc = false;
			}
			else if (selection == 2)
			{
				pvp = false;
				pvc = true;
				cvc = false;
			}
			else if (selection == 3)
			{
				pvp = false;
				pvc = false;
				cvc = true;
				validInput = false;
				while(!validInput)
				{
					System.out.println("How many games should the computers play?");
					int games = scan.nextInt();
					if(games > 0)
					{
						validInput = true;
						gamesToPlay = games;
					}
				}
			}
			else if (selection == 4)
				return false;
			else
				validInput = false;
		}
			
		if (pvc)
			selectStart();
		
		for(int i = 0; i < gamesToPlay; i++)
		{
			reset();
			while(!update());
			assignValues();
			recordValues();
			//reset();
		}
		
		gamesToPlay = 1;
		
			return true;
	}
	
	public void selectStart()
	{
		int choice = rand.nextInt(10) + 1;
		
		if(choice % 2 == 0)
		{
			humanTurn = true;
			
			System.out.println("You are Player 1");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		else
		{
			humanTurn = false;
			
			System.out.println("You are Player 2");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean update() 
	{
		states.add(0, new BoardState(board.row1, board.row2, board.row3));
		System.out.println("---------------------------------------------------------------");
		if(player1Turn)
			System.out.println("PLAYER 1\n");
		else
			System.out.println("PLAYER 2\n");
		
		System.out.println("Row 1: " + board.row1);
		System.out.println("Row 2: " + board.row2);
		System.out.println("Row 3: " + board.row3 + "\n");
		
		if (board.isGameOver()) 
		{
			if(player1Turn)
				System.out.println("Player 1 is the winner!");
			else
				System.out.println("Player 2 is the winner!");
			return true;
		} 
		else 
		{
			if(cvc || (pvc && !humanTurn))
			{
				computerUpdate();
				player1Turn = !player1Turn;
				swapPlayers();
			}
			else
			{
				System.out.println("Enter row number: ");
				int rowNum = scan.nextInt();
				System.out.println("Enter amount to take: ");
				int numTook = scan.nextInt();
			
				if(rowNum == 1)
				{
					if (numTook <= board.row1 && numTook > 0)
					{
						board.row1 -= numTook;
						player1Turn = !player1Turn;
						swapPlayers();
					}
					else 
						System.out.println("illegal move, please remake your choice");
				}
				else if (rowNum == 2 && numTook > 0)
				{
					if (numTook <= board.row2)
					{
						board.row2 -= numTook;
						player1Turn = !player1Turn;
						swapPlayers();
					}
					else 
						System.out.println("illegal move, please remake your choice");
				}
				else if (rowNum == 3 && numTook > 0)
				{
					if (numTook <= board.row3)
					{
						board.row3 -= numTook;
						player1Turn = !player1Turn;
						swapPlayers();
					}
					else 
						System.out.println("illegal move, please remake your choice");
				}
				else 
					System.out.println("illegal move, please remake your choice");
			}
		}
		return false;
	}
	
	public void swapPlayers()
	{
		if(pvc)
		{
			humanTurn = !humanTurn;
		}
	}

	public void computerUpdate()
	{
		if (board.row1 == 0 && board.row2 == 0)
		{
			if (board.row3 > 1)
			{
				System.out.println("Row Number: 3\nCount Removed: " + (board.row3 - 1));
				board.row3 = 1;
			}
			else
			{
				board.row3 = 0;
				System.out.println("Row Number: 3\nCount Removed: 1");
			}
		}
		else if (board.row1 == 0 && board.row3 == 0)
		{
			if (board.row2 > 1)
			{
				board.row2 = 1;
				System.out.println("Row Number: 2\nCount Removed: " + (board.row3 - 1));
			}
			else
			{
				board.row2 = 0;
				System.out.println("Row Number: 2\nCount Removed: 1");
			}
		}
		else if(board.row2 == 0 && board.row3 == 0)
		{
			if (board.row1 > 1)
			{
				board.row1 = 1;
				System.out.println("Row Number: 1\nCount Removed: " + (board.row3 - 1));
			}
			else
			{
				board.row1 = 0;
				System.out.println("Row Number: 1\nCount Removed: 1");
			}
		}
		else
		{
			randomRowSelect();
		}
	}
	
	public void randomRowSelect()
	{
		getMoves();
		sortMovesByValue();
		BoardState wantedState = moves.get(0);
		BoardState currentState = board;
		
		if(currentState.row1 != wantedState.row1){
			System.out.println("Row Number: 1");
			int difference = currentState.row1 - wantedState.row1;
			System.out.println("Count Removed: " + difference);
		}
		
		if(currentState.row2 != wantedState.row2){
			System.out.println("Row Number: 2");
			int difference = currentState.row2 - wantedState.row2;
			System.out.println("Count Removed: " + difference);
		}
		
		if(currentState.row3 != wantedState.row3){
			System.out.println("Row Number: 3");
			int difference = currentState.row3 - wantedState.row3;
			System.out.println("Count Removed: " + difference);
		}
		
		board = wantedState;
//		int row = rand.nextInt(3)+1;
//		
//		if (row == 1)
//		{
//			if (board.row1 > 1)
//			{
//				int count = rand.nextInt(board.row1) + 1;
//				board.row1 = board.row1 - count;
//				System.out.println("Row Number: 1\nCount Removed: " + count);
//			}
//			else if (board.row1 == 1)
//			{
//				board.row1 = 0;
//				System.out.println("Row Number: 1\nCount Removed: 1");
//			}
//			else
//				randomRowSelect();
//		}
//		else if (row == 2)
//		{
//			if (board.row2 > 1)
//			{
//				int count = rand.nextInt(board.row2) + 1;
//				board.row2 = board.row2 - count;
//				System.out.println("Row Number: 2\nCount Removed: " + count);
//			}
//			else if (board.row2 == 1)
//			{
//				board.row2 = 0;
//				System.out.println("Row Number: 2\nCount Removed: 1");
//			}
//			else
//				randomRowSelect();
//		}
//		if (row == 3)
//		{
//			if (board.row3 > 1)
//			{
//				int count = rand.nextInt(board.row3) + 1;
//				board.row3 = board.row3 - count;
//				System.out.println("Row Number: 3\nCount Removed: " + count);
//			}
//			else if (board.row3 == 1)
//			{
//				board.row3 = 0;
//				System.out.println("Row Number: 3\nCount Removed: 1");
//			}
//			else
//				randomRowSelect();
//		}
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	private void getMoves()
	{
		moves.clear();
		BoardState curState = states.get(0);
		
		for(int i = 1; i < BoardState.Row1Max+1; i++)
		{
			if(curState.row1 - i >= 0)
			{
				BoardState tmpState = new BoardState(curState);
				tmpState.row1 -= i;
				moves.add(tmpState);
			}
		}
		
		for(int j = 1; j < BoardState.Row2Max+1; j++)
		{
			if(curState.row2 - j >= 0)
			{
				BoardState tmpState = new BoardState(curState);
				tmpState.row2 -= j;
				moves.add(tmpState);
			}
		}
		
		for(int k = 1; k < BoardState.Row3Max+1; k++)
		{
			if(curState.row3 - k >= 0)
			{
				BoardState tmpState = new BoardState(curState);
				tmpState.row3 -= k;
				moves.add(tmpState);
			}
		}
	}
	
	private void sortMovesByValue()
	{
		for(BoardState move : moves)
		{
			move.val = stats[move.row1][move.row2][move.row3].getValue();
		}
		
		Collections.sort(moves);
		
	}
	
	public void Test()
	{
		states.add(0, new BoardState(3, 5, 7));
		states.add(0, new BoardState(1, 5, 7));
		states.add(0, new BoardState(0, 5, 7));
		states.add(0, new BoardState(0, 5, 5));
		states.add(0, new BoardState(0, 3, 5));
		states.add(0, new BoardState(0, 3, 3));
		states.add(0, new BoardState(0, 0, 3));
		states.add(0, new BoardState(0, 0, 0));
		
		assignValues();
		recordValues();
		
		System.out.println("Printing gamestats");
		for(BoardState state : states)
		{
			System.out.println(state);
		}
		states.clear();
		
		states.add(0, new BoardState(3, 5, 7));
		states.add(0, new BoardState(0, 5, 7));
		states.add(0, new BoardState(0, 5, 3));
		states.add(0, new BoardState(0, 3, 3));
		states.add(0, new BoardState(0, 0, 3));
		states.add(0, new BoardState(0, 0, 1));
		states.add(0, new BoardState(0, 0, 0));
		
		assignValues();
		recordValues();
		
		System.out.println("Printing gamestats");
		for(BoardState state : states)
		{
			System.out.println(state);
		}
		states.clear();
		
		for(int i = 0; i <= BoardState.Row1Max; i++)
		{
			for (int j = 0; j <= BoardState.Row2Max; j++) 
			{
				for(int k = 0; k <= BoardState.Row3Max; k++)
				{
					if(stats[i][j][k].getValue() != 0)
					{
						System.out.println(i + " , " + j + " , " + k);
						System.out.println(stats[i][j][k]);
						System.out.println();
					}
				}
			}
		}
	}
	
	public void Test2()
	{
		states.add(0, new BoardState(3, 5, 7));
		states.add(0, new BoardState(1, 5, 7));
		states.add(0, new BoardState(0, 5, 7));
		states.add(0, new BoardState(0, 5, 5));
		
		System.out.println("Current move: ");
		System.out.println(states.get(0));
		
		getMoves();
		
		System.out.println("Possible Moves: ");
		for(BoardState state : moves)
		{
			System.out.println(state);
		}
	}
	
	public void Test3()
	{
		states.add(0, new BoardState(3, 5, 7));
		states.add(0, new BoardState(1, 5, 7));
		states.add(0, new BoardState(0, 5, 7));
		states.add(0, new BoardState(0, 5, 5));
		states.add(0, new BoardState(0, 3, 5));
		states.add(0, new BoardState(0, 3, 3));
		states.add(0, new BoardState(0, 0, 3));
		states.add(0, new BoardState(0, 0, 0));
		
		assignValues();
		recordValues();
		
		states.add(0, new BoardState(3, 5, 7));
		states.add(0, new BoardState(0, 5, 7));
		states.add(0, new BoardState(0, 5, 3));
		states.add(0, new BoardState(0, 3, 3));
		states.add(0, new BoardState(0, 0, 3));
		states.add(0, new BoardState(0, 0, 1));
		states.add(0, new BoardState(0, 0, 0));
		
		assignValues();
		recordValues();
		
		states.add(0, new BoardState(3, 5, 7));
		states.add(0, new BoardState(1, 5, 7));
		states.add(0, new BoardState(0, 5, 7));
		states.add(0, new BoardState(0, 5, 5));
		
		System.out.println("Current move: ");
		System.out.println(states.get(0));
		
		getMoves();
		
		System.out.println("Possible Moves: ");
		for(BoardState state : moves)
		{
			System.out.println(state);
		}
		
		System.out.println("Sorting moves by value:");
		sortMovesByValue();
		
		for(BoardState state : moves)
		{
			System.out.println(state);
		}
	}
	
	public void assignValues()
	{
		if(states.get(0).isGameOver())
		{
			int numStates = states.size() - 1;
			int numOdds = numStates / 2;
			int numEvens = numOdds + numStates % 2;
			
			
			int curEven = 0;
			int curOdd = 0;
			float target;
			
			for(int i = 0; i < numStates; i++)
			{
				if(i%2 == 0) //even
				{
					target = -1 * ((numEvens - curEven) / (float)numEvens);
					curEven++;
				}
				else
				{
					target = (numOdds - curOdd) / (float)numOdds;
					curOdd++;
				}
					
				states.get(i).setValue(target);
			}
			
		}
	}
	
	public void recordValues()
	{
		for(BoardState state : states)
		{
			stats[state.row1][state.row2][state.row3].add(state.val);
		}
	}
}
