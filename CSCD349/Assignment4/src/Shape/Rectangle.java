package Shape;

class Rectangle extends Shape implements Comparable{
    Rectangle(double h, double l){
        super(h, l, "Rectangle");
    }
    @Override
    double computeArea(){
        return this.height * this.length;
    }
}
