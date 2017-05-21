package Shape;

class Triangle extends Shape implements Comparable{
    Triangle(double h, double l){
        super(h, l, "Triangle");
    }
    @Override
    double computeArea(){
        return this.height * this.length / 2;
    }
}
