package TreePackage;

public class Star extends TreeDecorations {
    private final ChristmasTree tree;

    private Star(ChristmasTree tree){
        this.tree = tree;
    }
    public static ChristmasTree getStar(ChristmasTree tree){
        if(!tree.getDescription().contains("TreePackage.Star"))
            return new Star(tree);
        System.out.println("The TreePackage already has a TreePackage.Star!");
        return tree;
    }
    public String getDescription(){
        return tree.getDescription() + ", TreePackage.Star";
    }
    public double cost(){
        return 4.00 + tree.cost();
    }
}
