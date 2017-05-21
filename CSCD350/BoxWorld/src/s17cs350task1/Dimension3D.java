package s17cs350task1;

public class Dimension3D implements Cloneable {
    private double width, height, depth;
    public Dimension3D(double w, double h, double d){
        if(w < 1 || h < 1 || d < 1)
            throw new TaskException("Cannot have zero or negative dimensions");
        this.width = w;
        this.height = h;
        this.depth = d;
    }

    @Override
    public Dimension3D clone() throws CloneNotSupportedException{
        return (Dimension3D) super.clone();
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getDepth() {
        return depth;
    }
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o instanceof Dimension3D) {
            Dimension3D that = (Dimension3D) o;
            return getWidth() == that.getWidth() && getHeight() == that.getHeight() && getDepth() == that.getDepth();
        }
        else
        return false;
    }
}
