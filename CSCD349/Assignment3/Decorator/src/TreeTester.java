import TreePackage.*;

class TreeTester {
    public static void main(String[] args) {
        ChristmasTree mytree = new Colorado_Blue_Spruce();
        mytree = TreePackage.Star.getStar(mytree);
        mytree = new TreePackage.Ruffles(mytree);
        mytree = TreePackage.Star.getStar(mytree); //this is problematic and should not be permitted
        mytree = new TreePackage.Ruffles(mytree);
        printtree(mytree);
        mytree = new Balsam_Fir();
        mytree = TreePackage.Star.getStar(mytree);
        mytree = new TreePackage.Balls_Blue(mytree);
        printtree(mytree);
        mytree = TreePackage.Star.getStar(mytree);
        mytree = new TreePackage.Balls_Red(mytree);
        printtree(mytree);
        mytree = new TreePackage.Balls_Silver(mytree);
        mytree = new TreePackage.LEDs(mytree);
        mytree = new TreePackage.Ribbons(mytree);
        printtree(mytree);
        mytree = new TreePackage.Lights(mytree);
        mytree = TreePackage.Star.getStar(mytree);
        printtree(mytree);
    }
    private static void printtree(ChristmasTree tree){
        System.out.printf(tree.getDescription() + "\nTotal Cost: $%.2f\n\n", tree.cost());
    }
}
