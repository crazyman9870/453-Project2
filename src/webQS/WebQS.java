package webQS;

public class WebQS {

	public static void main(String[] args) {
		Trie trie = new Trie();
		TrieLoader loader = new TrieLoader();
		trie = loader.loadTrie(trie);

		System.out.println(trie.toString());
		
		/* Preferences->Installed JREs->Edit->Variables = -Xmx4096m -XX:MaxPermSize=4096m */
		
	}
}
