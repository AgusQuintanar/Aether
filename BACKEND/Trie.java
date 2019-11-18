
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

//Jonathan Chavez A01636160
//Agustin Quintanar A01636142


//Trie data structure that will help us implement the autocomplete feature for the search box
/*
 * Methods:
 * add(String): inserts a string into the trie.
 * search(String): searches for exact ocurrence of a string in the trie.
 * autocomplete(String): returns an array of max 5 autocompleted results from the trie given a prefix string.
 */
public class Trie {
	
	TrieNode root;
	
	Trie(){
		this.root = new TrieNode(null);
	}
	
	public void add(String s) {
		TrieNode current = root;
		
		s = s.toLowerCase();
		
		for(int i=0; i<s.length(); i++) {
			
			if(current.children.containsKey(s.charAt(i))) {
				current = current.children.get(s.charAt(i));
			}
			else {
				current.children.put(s.charAt(i), new TrieNode(s.charAt(i)));
				current = current.children.get(s.charAt(i));
			}
			
			if(i == s.length()-1) {
				current.isEndOfWord = true;
			}
			
		}
		
	}
	
	public boolean search(String s) {
		
		TrieNode current = root;
		
		for(int i=0; i<s.length(); i++) {
			System.out.println(s.charAt(i));
			if(current.children.containsKey(s.charAt(i))) {
				current = current.children.get(s.charAt(i));
			}
			else {
				return false;
			}
		}
		
		if(current.isEndOfWord) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public ArrayList<String> autocomplete(String s, int max){
		s = s.toLowerCase();
		ArrayList<String> results = new ArrayList<String>();

		//A variant of a BFS
		TrieNode currentNode = this.root;
		//Queue with TrieNode and Prefix of current word in queue
		Queue<Pair<TrieNode, String> > queue = new LinkedList< Pair<TrieNode, String> >();
		
		//First reach the last node
		for(int i=0; i<s.length(); i++) {
			if(currentNode.children.containsKey(s.charAt(i))) {
				currentNode = currentNode.children.get(s.charAt(i));
			}
			else {
				return results;
			}
		}
		
		//Now the BFS
		queue.add(new Pair<TrieNode, String>(currentNode, ""));
		
		while(results.size() < max && !queue.isEmpty()) {
			
			Pair<TrieNode, String> currentPair = queue.poll();
			
			if(currentPair.first.isEndOfWord) {
				results.add(currentPair.second);
			}
			
			for(TrieNode node : currentPair.first.children.values()) {
				queue.add(new Pair<TrieNode, String>(node, currentPair.second+node.currentCharacter));
			}
			
		}
		
		return results;
	}


	public static void main(String[] args) {
		Trie trie = new Trie();
		trie.add("perRo");
		trie.add("1,2,4");
		trie.add("permiso");
		trie.add("perros adoptados");
		trie.add("uvas");
		trie.add("pescados");
		System.out.println(trie.autocomplete("p", 5));
		System.out.println(trie.search("perro"));
		System.out.println(trie.autocomplete("peR", 2));
	}

}

class TrieNode{
	public HashMap<Character, TrieNode> children;
	public Character currentCharacter;
	public boolean isEndOfWord;
	
	TrieNode(Character c){
		this.currentCharacter = c;
		this.children = new HashMap<Character, TrieNode>();
		this.isEndOfWord = false;
	}
}
