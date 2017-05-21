package TreePackage;

public class Ruffles extends TreeDecorations {
    private final ChristmasTree tree;
    public Ruffles(ChristmasTree tree){
        this.tree = tree;
    }
    public String getDescription(){
        return tree.getDescription() + ", TreePackage.Ruffles";
    }
    public double cost(){
        return 1.00 + tree.cost();
    }
}
