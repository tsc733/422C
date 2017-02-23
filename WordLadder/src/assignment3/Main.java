/* WORD LADDER Main.java

 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * Tanner Craig
 * tsc733
 * 16215
 * Aaron Vail
 * 
 * 16215
 * Slip days used: 1
 * Git URL: 
 * Spring 2017
 */


package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	
	// static variables and constants only here.
	private static int quit;
	private static HashMap<String, Node> map;
	private static ArrayList<String> List;
	private static ArrayList<String> dfs;
	private static Set<String> dictionary;
	private static int value;
	private static boolean found;

	public static void main(String[] args) throws Exception {
		
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default from Stdin
			ps = System.out;			// default to Stdout
		}
		initialize();
		
		ArrayList<String> input = parse(kb);
		if(quit == 0){
			List = getWordLadderBFS(input.get(0), input.get(1));
			if(List != null){
				System.out.println("a " + (List.size()-1) + "-rung word ladder exists between " + input.get(0).toLowerCase() + " and " + input.get(1).toLowerCase());
				List.add(input.get(0));
				printLadder(List);
			}
			List = new ArrayList<String>();
			map.clear();
			createMap(dictionary);	// map out words only 1 letter different from selected word
			List = getWordLadderDFS(input.get(0), input.get(1));
			if(List != null){
				System.out.println("a " + (List.size()-1) + "-rung word ladder exists between " + input.get(0).toLowerCase() + " and " + input.get(1).toLowerCase());
				List.add(input.get(0));
				printLadder(List);
			}
		}
//		System.exit(1);
	}
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
		
		quit = 0; // 0 if run program, 1 if end progrm
		value = 0;
		map = new HashMap<String, Node>(); // word by word matches words with 1 letter off
		List = new ArrayList<String>(); // contains wordladder
		dfs = new ArrayList<String>(); // contains wordladder
		found = false;
		dictionary = makeDictionary();	// gives access to our given dictionary
		createMap(dictionary);	// map out words only 1 letter different from selected word
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of Strings containing start word, rungs, and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		String input = keyboard.nextLine();
		String[] input_split = input.split(" ");
		
		if(input.equals("/quit")){
			quit = 1;
			return null;
		}
		
		ArrayList<String> inputs = new ArrayList<String>();
		inputs.add(input_split[0].toUpperCase());
		inputs.add(input_split[1].toUpperCase());
		
		return inputs;
	}
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		Node x = map.get(start);
		x.mark = true;
		for(String y : x.cousins){
			Node m = map.get(y);
			if(m.mark){continue;}
			m.mark = true;
			if(y.equals(end)){
				List.add(y);
				found = true;
				return List;
			}
			getWordLadderDFS(y, end);
			if(found){
				List.add(y);
				return List;
			}
		}
		if(found)System.out.println("no word ladder can be found between " + start.toLowerCase() + " and " + end.toLowerCase() + ".");
		return null;
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		ArrayList<Node> dummy_list = new ArrayList<Node>();
		ArrayList<String> ret = new ArrayList<String>();
		
		Node x = map.get(start);
		dummy_list.add(x);
		x.mark = true;
		
		while(!dummy_list.isEmpty()){
			x = dummy_list.remove(0);
			if(x.word.equals(end)){
				 while(x.parent != null){
					 ret.add(x.word);
					 x = x.parent;
					 value += 1;
				 }
				 return ret;
			}
			
			for(String word : x.cousins){
				Node y = map.get(word);
				if(!y.mark){
					y.mark = true;
					dummy_list.add(y);
					y.parent = x;
				}
			}
		}
		System.out.println("no word ladder can be found between " + start.toLowerCase() + " and " + end.toLowerCase() + ".");
		return null; 
	}
	
	public static void printLadder(ArrayList<String> ladder) {
		 for(int i = ladder.size() - 1; i >= 0; i--){
			 System.out.println(ladder.get(i).toLowerCase());
		 }
	}

	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("C:\\Users\\corbi_000\\Documents\\EE422C\\WordLadder\\src\\assignment3\\five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
	
	private static void createMap(Set<String> dictionary){
		for(String word_one : dictionary){
			Node word = new Node(word_one);
			
			for(String word_two : dictionary){
				if(CheckWord(word_one, word_two) && !word_one.equals(word_two)){
					word.cousins.add(word_two);
				}
			}
			
			map.put(word_one, word);
		}
	}
	
	private static boolean CheckWord(String one, String two){ // checks if word one differs from word two by only 1 letter
		int count = 0;
		for(int i = 0; i < one.length(); i++){
			if(one.charAt(i) != two.charAt(i)){
				count += 1;
			}
			if(count > 1){
				return false;
			}
		}
		
		return true;
	}
	
}
