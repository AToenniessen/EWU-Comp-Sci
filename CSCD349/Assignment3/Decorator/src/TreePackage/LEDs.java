package TreePackage;

public class LEDs extends TreeDecorations {
    private final ChristmasTree tree;
    public LEDs(ChristmasTree tree){
        this.tree = tree;
    }
    public String getDescription(){
        return tree.getDescription() + ", LED TreePackage.Lights";
    }
    public double cost(){
        return 10.00 + tree.cost();
    }
}
