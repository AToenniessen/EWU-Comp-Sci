package Shape;

import java.util.NoSuchElementException;

public class ShapeFactory {
    public static Shape createShape(String s, double h)throws NoSuchElementException{
        switch(s){
            case("Circle") : return new Circle(h);
            case("Square") : return new Square(h);
            default : throw new NoSuchElementException("No such Shape of type: " + s);
        }
    }
    public static Shape createShape(String s, double h, double l)throws NoSuchElementException{
        switch(s){
            case("Rectangle") : return new Rectangle(h,l);
            case("Triangle") : return new Triangle(h, l);
            default : throw new NoSuchElementException("No such Shape of type: " + s);
        }
    }
}
