package webQS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TrieLoader {
		
	public TrieLoader() {
	}
	
	public void loadTrie(Trie trie) {
		File path = new File("logs");
		for(File file : path.listFiles()) {
			try {
				BufferedReader buffReader = new BufferedReader(new FileReader(file));
				StringBuffer strBuff = new StringBuffer(); //Used to build the query that will be stored in the trie
				String nextLine = "";
				while ((nextLine = buffReader.readLine()) != null) {
					StringTokenizer tokens = new StringTokenizer(nextLine);
					System.out.println(tokens.countTokens());
				}
				buffReader.close();
				//save it to a bin tree.
				
				
			} catch (IOException e) {
				System.out.println("Error thrown when loading the trie");
				e.printStackTrace();
			}
		}
	}

}
