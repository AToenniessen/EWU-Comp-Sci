import java.util.ArrayList;
import java.util.List;

class BinaryTree {
    private Node root;
    BinaryTree(){
        root = new Node("");
    }

    BinaryTree buildTree(Object[] inOrderSequence, Object[] postOrderSequence) {
        BinaryTree temp = new BinaryTree();
        temp.root.data = postOrderSequence[postOrderSequence.length - 1];
        List<Object> inOrderTemp = toList(inOrderSequence);
        List<Object> postOrderTemp = toList(postOrderSequence);
        temp.root = buildSubTree(inOrderTemp, postOrderTemp, temp.root.data, temp.root);
        return temp;
    }
    private Node buildSubTree(List<Object> subArrayIn, List<Object> subArrayPost, Object rootData, Node cur){
        if(subArrayIn.size()==1)
            return cur;
        if(subArrayIn.size() <= 2){
            if(subArrayIn.indexOf(rootData) == 1) {
                cur.left = new Node(subArrayIn.get(0));
                return cur;
            }
            else {
                cur.right = new Node(subArrayIn.get(1));
                return cur;
            }
        }
        else{
            List<Object> subArrayPostR = subArrayPost.subList((subArrayPost.size()) - (subArrayIn.size()-1), subArrayPost.indexOf(rootData));
            cur.right = buildSubTree( subArrayIn.subList(subArrayIn.indexOf(rootData)+1, subArrayIn.size()), subArrayPostR, subArrayPostR.get(subArrayPostR.size()-1), new Node(subArrayPostR.get(subArrayPostR.size()-1)));
            if(subArrayIn.indexOf(rootData) != 0) {
                List<Object> subArrayPostL = subArrayPost.subList(0, subArrayIn.indexOf(rootData));
                cur.left = buildSubTree(subArrayIn.subList(0, subArrayIn.indexOf(rootData)), subArrayPostL, subArrayPostL.get(subArrayPostL.size() - 1), new Node(subArrayPostL.get(subArrayPostL.size() - 1)));
            }
            return cur;
        }
    }
    void printInorder(){
        printTreeInorder(root);
        System.out.println();
    }
    private void printTreeInorder(Node node){
        if(node == null) return;
        printTreeInorder(node.left);
        System.out.print(node.data + " ");
        printTreeInorder(node.right);
    }
    void printPostOrder(){
        printTreePostOrder(root);
        System.out.println();
    }
    private void printTreePostOrder(Node node){
        if(node == null) return;
        printTreePostOrder(node.left);
        printTreePostOrder(node.right);
        System.out.print(node.data + " ");
    }
    private List<Object> toList(Object[] o){
        List<Object> temp = new ArrayList<>();
        for(Object i : o){
            temp.add(i);
        }
        return temp;
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