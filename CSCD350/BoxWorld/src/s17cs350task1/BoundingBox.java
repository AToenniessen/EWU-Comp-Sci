package s17cs350task1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BoundingBox implements Cloneable {
    private Point3D center;
    private Dimension3D size;

    public BoundingBox(Point3D center, Dimension3D size) {
        if (center == null || size == null)
            throw new TaskException("Parameters passed in cannot be null");
        this.center = center;
        this.size = size;
    }

    public double calculateArea(E_Plane plane) {
        if (plane == null)
            throw new TaskException("Plane cannot be null");
        switch (plane) {
            case XY:
                return ((size.getWidth() * size.getHeight()) * 2);
            case YZ:
                return ((size.getHeight() * size.getDepth()) * 2);
            case XZ:
                return ((size.getWidth() * size.getDepth()) * 2);
        }
        throw new TaskException("Plane provided outside of the XYZ 3 dimensional planes");
    }

    public double calculateVolume() {
        return size.getWidth() * size.getHeight() * size.getDepth();
    }

    @Override
    public BoundingBox clone() throws CloneNotSupportedException {
        BoundingBox clone = (BoundingBox) super.clone();
        clone.center = center.clone();
        clone.size = this.size.clone();
        return clone;
    }

    public BoundingBox extend(BoundingBox boundingBox) {
        if (boundingBox == null)
            throw new TaskException("Can not extend BoundingBox to null");
        List<Point3D> corners1 = this.generateCorners();
        List<Point3D> corners2 = boundingBox.generateCorners();
        double width, height, depth, maxX, minX, maxY, minY, maxZ, minZ;

        minX = Math.min(corners1.get(0).getX(), corners2.get(0).getX());
        maxX = Math.max(corners1.get(6).getX(), corners2.get(6).getX());
        minY = Math.min(corners1.get(0).getY(), corners2.get(0).getY());
        maxY = Math.max(corners1.get(6).getY(), corners2.get(6).getY());
        minZ = Math.min(corners1.get(0).getZ(), corners2.get(0).getZ());
        maxZ = Math.max(corners1.get(6).getZ(), corners2.get(6).getZ());

        width = maxX - minX;
        height = maxY - minY;
        depth = maxZ - minZ;

        return new BoundingBox(new Point3D((maxX - (width / 2)), (maxY - (height / 2)), (maxZ - (depth / 2))), new Dimension3D(width, height, depth));
    }

    public List<Point3D> generateCorners() {
        List<Point3D> corners = new ArrayList<>();
        double width = size.getWidth() / 2, height = size.getHeight() / 2, depth = size.getDepth() / 2,
                x = center.getX(), y = center.getY(), z = center.getZ();
        corners.add(new Point3D((x - width), (y - height), (z - depth)));   //corners[0] min x,y,z
        corners.add(new Point3D((x + width), (y - height), (z - depth)));
        corners.add(new Point3D((x + width), (y - height), (z + depth)));
        corners.add(new Point3D((x - width), (y - height), (z + depth)));
        corners.add(new Point3D((x - width), (y + height), (z - depth)));
        corners.add(new Point3D((x + width), (y + height), (z - depth)));
        corners.add(new Point3D((x + width), (y + height), (z + depth)));   //corners[6] max x,y,z
        corners.add(new Point3D((x - width), (y + height), (z + depth)));
        return corners;
    }

    public Point3D getCenter() {
        return new Point3D(this.center.getX(), this.center.getY(), this.center.getZ());
    }

    public Dimension3D getSize() {
        return new Dimension3D(this.size.getWidth(), this.size.getHeight(), this.size.getDepth());
    }

    public enum E_Plane implements Serializable, Comparable<E_Plane> {
        XY, YZ, XZ
    }
}
