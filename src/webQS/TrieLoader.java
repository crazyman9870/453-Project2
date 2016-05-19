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
	
	public void loadTrie(Trie trie) {
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
					
//					if(query.contains("\'"))
//						System.out.println(query);
					query = formatQuery(query);
//					System.out.println(query);
					
					DateTime time = dateFormatter.parseDateTime(timeStr);
					modified= false;
					if (prevSession.equals(session)) {
						if (prevTime.plusMinutes(10).isBefore(time)) {
//							isMod = checkQuery(queryStr);
						}
					}

//					trie.add(queryStr, isMod);
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
}

//BufferedReader buffReader = new BufferedReader(new FileReader(file));
//StringBuffer strBuff = new StringBuffer(); //Used to build the query that will be stored in the trie
//String nextLine = "";
//while ((nextLine = buffReader.readLine()) != null) {
//	StringTokenizer tokens = new StringTokenizer(nextLine);
////	System.out.println(tokens.countTokens());
//}
//buffReader.close();
