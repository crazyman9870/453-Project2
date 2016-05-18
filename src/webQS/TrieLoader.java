package webQS;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TrieLoader {
	
	private Scanner scan;
	
	public TrieLoader() {
		scan = null;
	}
	
	public void loadTrie(Trie trie) {
		File path = new File("logs");
		for(File file : path.listFiles()) {
			try {
				scan = new Scanner(file);
			} catch (FileNotFoundException e) {
				System.out.println("Error thrown when loading the trie");
				e.printStackTrace();
			}
		}
	}

}
