package assignment2;


public class Driver {
	
	public static void main (String[] args){
		boolean testing = false;	//check for testing mode
		if(args.length > 0){
			if(args[0].equals('1')){
				testing = true;
			}
		}
		
		System.out.println("Weclome to Mastermind.");	//Greeting
		System.out.println("Do you want to play a new game? (Y/N):");
		
		Game player = new Game(testing);	// my game object
		
		while(Game.yes){		// runs the game engine
			Game.runGame();
		}
	}
	
}
