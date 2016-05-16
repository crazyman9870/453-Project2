package webQS;

public class WebQS {

	public static void main(String[] args) {
		Trie trie = new Trie();
		
		trie.add("THIS WORD");
		trie.add("something else");
		trie.add("here we ARE");
		trie.add("here WE Are");
		trie.add("someTHIng else");
		
		System.out.println(trie.toString());
		
	}
	
}
