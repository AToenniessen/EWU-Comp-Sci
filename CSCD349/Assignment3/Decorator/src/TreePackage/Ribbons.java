package TreePackage;

public class Ribbons extends TreeDecorations{
    private final ChristmasTree tree;
    public Ribbons(ChristmasTree tree){
        this.tree = tree;
    }
    public String getDescription(){
        return tree.getDescription() + ", TreePackage.Ribbons";
    }
    public double cost(){
        return 2.00 + tree.cost();
    }
}
