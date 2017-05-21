package Shape;

public abstract class Shape implements Comparable{
    double height, length;
    private String name;
    Shape(double h, double l, String n){
        this.height = h;
        this.length = l;
        this.name = n;
    }
    abstract double computeArea();
    public void printResults(){
        System.out.printf("Area of the " + this.name + " is %.2f\n", computeArea());
    }
    public int compareTo(Object o){
        if(o == null)
            throw new NullPointerException();
        Shape that = (Shape) o;
        int c = this.name.compareTo(that.name);
        if(c == 0){
            double a = this.computeArea() - that.computeArea();
            if(a < 0) return -1;
            if(a > 0) return 1;
            return 0;
        }
        return c;
    }
}
