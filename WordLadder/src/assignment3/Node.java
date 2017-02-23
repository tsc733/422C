package assignment3;

import java.util.ArrayList;

public class Node {
	
	public String word;
	public ArrayList<String> cousins;
	public boolean mark = false;
	public Node parent = null;
	
	public Node(String object){
		word = object;
		cousins = new ArrayList<String>();
	}
}
