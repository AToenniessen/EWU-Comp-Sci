package Shape;

class Square extends Shape implements Comparable{
    Square(double h){
        super(h, h, "Square");
    }
    @Override
    double computeArea(){
        return this.height * this.length;
    }
}
