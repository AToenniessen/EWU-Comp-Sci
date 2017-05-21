import java.util.*;

class Tree {
    private Node root;
    private ArrayList<String> possibleWords = new ArrayList<>();


    Tree() {
        root = new Node(null);
    }

    private final Character[][] numberCharacters = {{}, {}, {'a', 'b', 'c', null}, {'d', 'e', 'f', null},
            {'g', 'h', 'i', null}, {'j', 'k', 'l', null}, {'m', 'n', 'o', null}, {'p', 'q', 'r', 's'},
            {'t', 'u', 'v', null}, {'w', 'x', 'y', 'z'}};

    void insert(Integer[] index) {
        Queue<Node> list = new LinkedList<>();
        list.add(this.root);
        ArrayList<Node> temp = new ArrayList<>();
        Node cur;
        int i;
        int m = 0;
        while (m < index.length) {
            i = index[m];
            m++;
            while(!list.isEmpty()){
                temp.add(list.remove());
            }
            int size = temp.size();
            for(int n = 0; n < size; n++) {
                cur = temp.remove(0);
                cur.insertChild(i);
                list.add(cur.one);
                list.add(cur.two);
                list.add(cur.three);
                if (cur.four != null)
                    list.add(cur.four);
            }

        }
    }
    ArrayList<String> findAllWordsHash(HashSet<String> dict){
        findAllPath(this.root, new ArrayList<>());
        ArrayList<String> words = new ArrayList<>();
        for(String c : possibleWords){
            if(dict.contains(c)){
                words.add(c);
            }
        }
        possibleWords = new ArrayList<>();
        return words;
    }
    ArrayList<String> findAllWordsPreFix(Trie dict){
        findAllPath(this.root, new ArrayList<>());
        ArrayList<String> words = new ArrayList<>();
        for(String s : possibleWords){
            if(dict.find(s))
                words.add(s);
        }
        possibleWords = new ArrayList<>();
        return words;
    }
    private void findAllPath(Node current, ArrayList<Character> path){
        path.add(current.data);
        if(current.one == null){
            possibleWords.add(conversion(path));
        }
        else{
            findAllPath(current.one, new ArrayList<>(path));
            findAllPath(current.two, new ArrayList<>(path));
            findAllPath(current.three, new ArrayList<>(path));
            if(current.four != null)
                findAllPath(current.four, new ArrayList<>(path));
        }
    }
    private String conversion(ArrayList<Character> path){
        String s = "";
        for(Character c : path){
            if(c != null)
                s = s + c;
        }
        return s;
    }

    private class Node {
        Character data;
        Node one;
        Node two;
        Node three;
        Node four;

        Node(Character d) {
            data = d;
            one = null;
            two = null;
            three = null;
            four = null;
        }

        private void insertChild(int i) {
            one = new Node(numberCharacters[i][0]);
            two = new Node(numberCharacters[i][1]);
            three = new Node(numberCharacters[i][2]);
            if (numberCharacters[i][3] != null)
                four = new Node(numberCharacters[i][3]);
        }

        public String toString() {
            return data == null ? "" : data + "";
        }
    }
}