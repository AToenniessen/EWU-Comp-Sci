import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

class Trie {
    private TrieNode root;
    Trie(){
        this.root = new TrieNode();
    }
    void createPreFix(HashSet d){
        for(Object s : d){
            insert((String) s);
        }
    }
    boolean find(String s){
        TrieNode cur = root;
        for(char c : s.toCharArray()){
            TrieNode next = cur.children.get(c);
            if(next == null){
                return false;
            }
            cur = next;
        }
        return cur.word;
    }
    private void insert(String s){
        TrieNode cur = root;
        for(char c : s.toCharArray()){
            TrieNode next = cur.children.get(c);
            if(next == null){
                cur.children.put(c, next = new TrieNode());
            }
            cur = next;
        }
        cur.word = true;
    }
    private class TrieNode {
        Map<Character, TrieNode> children = new TreeMap<>();
        boolean word = false;
    }
}