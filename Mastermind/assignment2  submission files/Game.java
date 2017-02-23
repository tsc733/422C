package assignment2;

import java.util.*;


public class Game {
	
	public static boolean yes = false;	// value for running game
	public static String solution;
	public static int guesses;
	public static Scanner sc;
	public static boolean notvalid = true;
	public static int bp = 0;
	public static int wp = 0;
	public static int count;
	public static String[] inp;
	public static String[] sol;
	public static String input;
	public static boolean copy;
	public static int number = 0;
	public static StringBuffer[] history = new StringBuffer[GameConfiguration.guessNumber];
	
	Game (boolean t){
		guesses = GameConfiguration.guessNumber;
		
		copy = t;
		
		sc = new Scanner(System.in);	// check for Y or N
		String check = sc.next();
		if(check.equals("Y")){
			yes = true;
		}
		if(check.equals("N")){
			return;
		}
		
		solution = SecretCodeGenerator.getInstance().getNewSecretCode();
		
		if(t == true){
			System.out.println("Secret code: " + solution);
		}
		System.out.println("");
	}
	
	public static void runGame(){
		while(notvalid){	// take input, check validity
			count = 0;
			System.out.println("You have " + guesses + " guess(es) left.");
			System.out.println("Enter guess: ");
			
			input = sc.next(); // convert to search one by one
			inp = input.split("");
			
			if(inp.length == GameConfiguration.pegNumber){
				for(int i = 0; i < inp.length; i++){
					for(int k = 0; k < GameConfiguration.colors.length; k++){
						if(inp[i].equals(GameConfiguration.colors[k])){
							count++;
						}
					}
				}
				if(count == inp.length){
					notvalid = false;
				} else{
					System.out.println("INVALID_GUESS");
					System.out.println("");
				}
			} else{
				if(input.equals("HISTORY")){
					History();
				} else{
					System.out.println("INVALID_GUESS");
					System.out.println("");
				}
			}
		}

		// black peg: right color/position, white peg: right color
		// analysis of input and solution
		sol = solution.split("");	// now both input and solution are arrays
		
		// analysis for bp
		for(int i = 0; i < inp.length; i++){	
			if(inp[i].equals(sol[i])){
				bp += 1;
			}
		}
		int[] index = new int[inp.length - bp]; // holds indexes of non-bp
		bp = 0;
		int value = 0;
		for(int i = 0; i < inp.length; i++){	
			if(inp[i].equals(sol[i])){
				bp += 1;
			} else {
				index[value] = i;
				value += 1;
			}
		}
		
		// if you win
		if(bp == 4){	
			System.out.println("You win!");
			System.out.println("");
			System.out.println("Do you want to play a new game? (Y/N): ");
			input = sc.next();
			if(input.equals("N")){
				yes = false;
				return;
			}
			number = 0;
			history = new StringBuffer[GameConfiguration.guessNumber];
			guesses = GameConfiguration.guessNumber;
			bp = 0;
			wp = 0;
			notvalid = true;
			solution = SecretCodeGenerator.getInstance().getNewSecretCode();
			if(copy){
				System.out.println("Secret code: " + solution);
				System.out.println("");
			}
			return;
		}
		
		// analysis for wp
		for(int i = 0; i < value; i++){
			for(int k = 0; k < value; k++){
				if(inp[index[i]].equals(sol[index[k]])){
					wp += 1;
				}
			}
		}
		
		//print analysis, update guess(es)
		System.out.println(input + " -> " + bp + "b_" + wp + "w");
		
		// update history
		History();
		wp = 0;
		bp = 0;
		
		guesses -= 1;
		if(guesses == 0){	// if you lose
			System.out.println("You lose! The pattern was " + solution);
			System.out.println("");
			System.out.println("Do you want to play a new game? (Y/N): ");
			input = sc.next();
			if(input.equals("N")){
				yes = false;
				return;
			}	
			history = new StringBuffer[GameConfiguration.guessNumber];
			number = 0;
			guesses = GameConfiguration.guessNumber;
			notvalid = true;
			bp = 0;
			wp = 0;
			solution = SecretCodeGenerator.getInstance().getNewSecretCode();
			if(copy){
				System.out.println("Secret code: " + solution);
				System.out.println("");
			}
			return;
		}
		notvalid = true;
	}
	
	public static void History(){
		StringBuffer dummy = new StringBuffer();
		if(input.equals("HISTORY")){
			//print
			for(int i = 0; i < number; i++){
				System.out.println(history[i]);
			}
			System.out.println("");
		} else{
			//update history
			dummy.append(input);
			dummy.append(" -> ");
			dummy.append(bp);
			dummy.append("b_");
			dummy.append(wp);
			dummy.append("w");
			history[number] = dummy;
			number += 1;
		}
		
	}
	
}
