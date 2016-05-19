package webQS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Scanner;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TrieLoader {

	StopWords stopWords;
	
	public TrieLoader() {
		stopWords = new StopWords();
	}
	
	public Trie loadTrie(Trie trie) {
		File path = new File("resources\\logs");
		DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

		for(File file : path.listFiles()) {
			try {
				String line;
				Scanner scan = new Scanner(file);
				String prevSession = "";
				String prevQuery = "";
				DateTime prevTime = new DateTime();
				boolean modified = false;
				scan.nextLine();
				while(scan.hasNextLine()) {
					line = scan.nextLine();
					String[] lineSections = line.split("\t");

					String session = lineSections[0];
					String query = lineSections[1];
					String timeStr = lineSections[2];
					
					query = formatQuery(query);
					if(query.equals(""))
						continue;
					
					DateTime time = dateFormatter.parseDateTime(timeStr);
					modified = false;
					if (prevSession.equals(session)) {
						if (prevTime.plusMinutes(10).isBefore(time)) {
							modified = modifiedQuery(query, prevQuery);
						}
					}

					//TODO Implement later
					trie.add(query, modified);
					prevSession = session;
					prevQuery = query;
					prevTime = time;
				}
				scan.close();
				
			} catch (IOException e) {
				System.out.println("Error thrown when loading the trie");
				e.printStackTrace();
			}
//			System.out.println("DONE WITH FILE");
		}
		return trie;
	}
	
	private String formatQuery(String query) {
		StringTokenizer tokens = new StringTokenizer(query);
		StringBuilder sb = new StringBuilder();
		
		if(!tokens.hasMoreTokens()) //empty check
			return "";
		
		String word = tokens.nextToken();
		word = removeApostrophe(word);
		
		if(!stopWords.contains(word))
			sb.append(word);
		
		while(tokens.hasMoreTokens()) {
			word = tokens.nextToken();
			word = removeApostrophe(word);
			
			if(sb.length() != 0)
				sb.append(" ");
			
			sb.append(word);
		}
		return sb.toString();
	}
	
	private String removeApostrophe(String word) {
		if(word.contains("\'"))
			return word.substring(0, word.length()-2);
		return word;
	}
	
	private boolean modifiedQuery(String query, String prevQuery) {
		
		//If the queries are the same length or query is shorter return false
		if(query.length() == prevQuery.length() || query.length() < prevQuery.length())
			return false;
		
		StringTokenizer queryTokens = new StringTokenizer(query);
		StringTokenizer prevTokens = new StringTokenizer(prevQuery);
		String word1 = "";
		String word2 = "";
		
		//Run through previous query to make sure all the words are the same
		while(prevTokens.hasMoreTokens()) {
			word1 = queryTokens.nextToken();
			word2 = prevTokens.nextToken();
			if(!word1.equals(word2)) {
				return false;
			}
		}
		
		//If all the words are the same and the new query still has more tokens it's modified otherwise false
		if(queryTokens.hasMoreTokens()) {
			return true;
		}
		
		return false;
	}
}

//BufferedReader buffReader = new BufferedReader(new FileReader(file));
//StringBuffer strBuff = new StringBuffer(); //Used to build the query that will be stored in the trie
//String nextLine = "";
//while ((nextLine = buffReader.readLine()) != null) {
//	StringTokenizer tokens = new StringTokenizer(nextLine);
////	System.out.println(tokens.countTokens());
//}
//buffReader.close();
