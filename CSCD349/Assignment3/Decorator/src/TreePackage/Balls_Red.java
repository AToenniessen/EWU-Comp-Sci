package TreePackage;

public class Balls_Red extends TreeDecorations{
    private final ChristmasTree tree;
    public Balls_Red(ChristmasTree tree){
        this.tree = tree;
    }
    public String getDescription(){
        return tree.getDescription() + ", Balls of a red color";
    }
    public double cost(){
        return 1.00 + tree.cost();
    }
}
