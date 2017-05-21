import java.util.LinkedList;
import java.util.Queue;

class BinarySearchTree {
    private Node root;
    void insert(Comparable data){
        root = insert(data,root);
    }
    private Node insert(Comparable data, Node current) {
        if(current == null){
            current = new Node(data);
        }
        else{
            if(data.compareTo(current.data) < 0)
                current.left = insert(data, current.left);
            else
                current.right = insert(data, current.right);
        }
        return current;
    }
    void breadthFirstTraversal(){
        Node cur;
        Queue<Object> list = new LinkedList<>();
        list.add(root);
        while(!list.isEmpty()){
            cur = (Node) list.remove();
            System.out.print("|" + cur.data + "|");
            if(cur.left != null)
                list.add(cur.left);
            if(cur.right != null)
                list.add(cur.right);
        }
    }
    private class Node{
        Object data;
        Node left;
        Node right;
        Node(Object d){
            data = d;
            left = null;
            right = null;
        }
    }
}
