package TreePackage;

public class Lights extends TreeDecorations{
    private final ChristmasTree tree;
    public Lights(ChristmasTree tree){
        this.tree = tree;
    }
    public String getDescription(){
        return tree.getDescription() + ", TreePackage.Lights";
    }
    public double cost(){
        return 5.00 + tree.cost();
    }
}
