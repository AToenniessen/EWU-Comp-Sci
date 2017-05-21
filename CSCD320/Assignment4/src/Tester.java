
public class Tester {
    public static void main(String...args){
        BinarySearchTree test = new BinarySearchTree();
        int[] ara = {8,3,10,1,6,14,4,7,13};
        for(int i : ara){
            test.insert(i);
        }
        test.breadthFirstTraversal();
    }
}
