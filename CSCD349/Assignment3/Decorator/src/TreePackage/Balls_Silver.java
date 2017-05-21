package TreePackage;

public class Balls_Silver extends TreeDecorations{
    private final ChristmasTree tree;
    public Balls_Silver(ChristmasTree tree){
        this.tree = tree;
    }
    public String getDescription(){
        return tree.getDescription() + ", Balls of a Silver color";
    }
    public double cost(){
        return 3.00 + tree.cost();
    }
}
