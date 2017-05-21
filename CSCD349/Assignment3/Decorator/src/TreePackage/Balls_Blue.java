package TreePackage;

public class Balls_Blue extends TreeDecorations {
    private final ChristmasTree tree;
    public Balls_Blue(ChristmasTree tree){
        this.tree = tree;
    }
    public String getDescription(){
        return tree.getDescription() + ", Balls of a Blue color";
    }
    @Override
    public double cost(){
        return 2.00 + tree.cost();
    }
}
