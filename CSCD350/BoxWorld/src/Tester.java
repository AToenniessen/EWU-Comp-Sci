import javafx.geometry.Point3D;
import s17cs350task1.A_Component;
import s17cs350task1.Connector;
import s17cs350task1.Dimension3D;

public class Tester {
    public static void main(String[] args) {
        A_Component b1 = new A_Component("a", true);
        A_Component b2 = new A_Component("As");
        A_Component b3 = new A_Component("adfadfada");
        A_Component b4 = new A_Component("ab3");
        A_Component b5 = new A_Component("ab2c1");
        A_Component b6 = new A_Component("afsadadsf");
        A_Component b7 = new A_Component("ab1daf");
        A_Component b8 = new A_Component("ab1daf");

        b1.connectChild(new Connector(b2, new Point3D(10,12, 4)));
        b1.connectChild(new Connector(b4, new Point3D(7, -8,9)));
        b3.connectChild(new Connector(b5, new Point3D(5, -8,1)));
        b3.connectChild(new Connector(b6, new Point3D(2, -8,12)));
        b3.connectChild(new Connector(b7, new Point3D(2, -8,4)));
        b1.connectChild(new Connector(b3, new Point3D(-2, -15, 5)));
        //b1.connectChild(new Connector(b8, new Point3D(-2, 15, 5)));
        Dimension3D temp = b5.getSize();
        temp.setDepth(100);
        temp = b5.getSize();




        try {
            b5 = b1.clone();
        }catch(CloneNotSupportedException e){

        }
        System.out.println(b5.toString());
//        System.out.println(b1.getAbsoluteCenterPosition().toString());
//        System.out.println(b2.getAbsoluteCenterPosition().toString());
//        System.out.println(b3.getAbsoluteCenterPosition().toString());
//        System.out.println(b4.getAbsoluteCenterPosition().toString());
//        System.out.println(b1.toString());
    }
}
