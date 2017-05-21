package s17cs350task1;

public class Point3D {
    private double x, y, z;
    public Point3D(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3D add(Point3D point){
        return new Point3D((this.getX() + point.getX()),(this.getY() + point.getY()), (this.getZ() + point.getZ()));
    }

    @Override
    public Point3D clone() throws CloneNotSupportedException{
        return (Point3D) super.clone();
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
    public Point3D subtract(Point3D point){
        return new Point3D((this.getX() - point.getX()),(this.getY() - point.getY()), (this.getZ() - point.getZ()));
    }
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o instanceof Point3D) {
            Point3D that = (Point3D) o;
            return getX() == that.getX() && getY() == that.getY() && getZ() == that.getZ();
        }
        else
            return false;
    }

}
