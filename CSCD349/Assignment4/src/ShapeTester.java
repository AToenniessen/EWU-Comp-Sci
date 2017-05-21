import Shape.ShapeFactory;
import Shape.Shape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;

public class ShapeTester {
    public static void main(String[] args){
        try {
            Shape[] temp = {ShapeFactory.createShape("Triangle", 5, 7),
                    ShapeFactory.createShape("Triangle", 3, 2), ShapeFactory.createShape("Rectangle", 5, 7),
                    ShapeFactory.createShape("Rectangle", 3, 2), ShapeFactory.createShape("Square", 5),
                    ShapeFactory.createShape("Square", 3), ShapeFactory.createShape("Circle", 5),
                    ShapeFactory.createShape("Circle", 3)};
            ArrayList<Shape> ara = new ArrayList<>(Arrays.asList(temp));
            System.out.println("Before Sort:");
            print(ara);
            Collections.sort(ara);
            System.out.println("\nAfter Sort:");
            print(ara);
        }catch(NoSuchElementException e){
            System.out.println(e.getMessage());
        }

    }
    private static void print(ArrayList<Shape> a){
        for(Shape o : a){
            o.printResults();
        }
    }
}
