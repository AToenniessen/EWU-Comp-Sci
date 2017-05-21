import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by ajt11 on 1/19/2017.
 */
public class BinarySearchTree {
    private Node root;
    public void BinarySearchTree(){
        root = null;
    }
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
    void printAllPath(ArrayList<Comparable> path){
        printAllPath(root, path);
    }
    private void printAllPath(Node current, ArrayList<Comparable> path){
        if(current == null) return;
        path.add(current.data);
        if(current.left == null && current.right == null){
            System.out.println(path);
            return;
        }
        else{
            printAllPath(current.left, new ArrayList<>(path));
            printAllPath(current.right, new ArrayList<>(path));
        }
    }
    public void printAllPathStack(ArrayList<Comparable> path){
        Stack<Comparable> s = new Stack<>();
        Node current = root;
        while(!isLeaf(current)){
            s.add(current.data);
        }
    }
    private boolean isLeaf(Node n){
        return n.left == null && n.right == null;
    }
//use a stack of nodes and stack of strings
//    public static void RoottoPathPrint(BinaryTreeNode root) {  Figure this out for myself
//        Stack<Object> stack = new Stack<Object>();
//        if (root == null)
//            return;
//        stack.push(root.getData() + "");
//        stack.push(root);
//        while (!stack.isEmpty()) {
//            BinaryTreeNode temp = (BinaryTreeNode) stack.pop();
//            String path = (String) stack.pop();
//
//            if (temp.getRight() != null) {
//                stack.push(path + temp.getRight().getData());
//                stack.push(temp.getRight());
//            }
//            if (temp.getLeft() != null) {
//                stack.push(path + temp.getLeft().getData());
//                stack.push(temp.getLeft());
//            }
//            if (temp.getLeft() == null && temp.getRight() == null) {
//                System.out.println(path);
//            }
//        }
//    }

    class Node{
        Comparable data;
        Node left;
        Node right;
        Node(Comparable d){
            data = d;
            left = null;
            right = null;
        }
    }
}
