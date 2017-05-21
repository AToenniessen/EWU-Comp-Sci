package Shape;

class Circle extends Shape implements Comparable{
    Circle(double d){
        super(d, d, "Circle");
    }
    @Override
    double computeArea(){
        return this.height * this.length * Math.PI;
    }
}
