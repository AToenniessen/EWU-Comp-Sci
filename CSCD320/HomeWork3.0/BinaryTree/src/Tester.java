public class Tester {
    public static void main(String...args){
        BinaryTree tree = new BinaryTree();
        tree = tree.buildTree(new Object[] {9, 5, 1, 7, 2, 12, 8, 4, 3, 11 }, new Object[] { 9, 1, 2, 12, 7, 5, 3, 11, 4, 8} );
        tree.printInorder();
        tree.printPostOrder();
    }
}
