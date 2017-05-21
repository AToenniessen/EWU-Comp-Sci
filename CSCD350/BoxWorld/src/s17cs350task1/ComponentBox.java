package s17cs350task1;

import java.util.ArrayList;
import java.util.List;

public class ComponentBox extends A_Component {
    private Dimension3D size;

    public ComponentBox(String id, Dimension3D size) {
        super(id, false);
        if (size == null || (size.getWidth() == 0 || size.getDepth() == 0 || size.getHeight() == 0))
            throw new TaskException("Size cannot be null or equal to 0");
        this.size = size;
    }

    public ComponentBox(String id, Dimension3D size, boolean isRoot) {
        super(id, isRoot);
        if (size == null || (size.getWidth() == 0 || size.getDepth() == 0 || size.getHeight() == 0))
            throw new TaskException("Size cannot be null or equal to 0");
        this.size = size;
    }

    public double calculateAreaAll(BoundingBox.E_Plane p) {
        if (p == null)
            throw new TaskException("Plane Cannot be null");
        double area = this.calculateAreaSelf(p);
        for (A_Component b : this.getDescendants()) {
            area += b.calculateAreaSelf(p);
        }
        return area;
    }

    public double calculateAreaSelf(BoundingBox.E_Plane p) {
        if (p == null)
            throw new TaskException("Plane Cannot be null");
        return this.generateBoundingBoxSelf().calculateArea(p);
    }

    public Point3D calculateCenterOfMassAll() {
        return this.generateBoundingBoxAll().getCenter();
    }

    public Point3D calculateCenterOfMassSelf() {
        return this.getAbsoluteCenterPosition();
    }

    public double calculateVolumeAll() {
        double volume = this.calculateVolumeSelf();
        for (A_Component b : this.getDescendants()) {
            volume += b.calculateVolumeSelf();
        }
        return volume;
    }

    public double calculateVolumeSelf() {
        return generateBoundingBoxSelf().calculateVolume();
    }

    @Override
    public ComponentBox clone() throws CloneNotSupportedException {
        ComponentBox clone = (ComponentBox) super.clone();
        clone.size = size.clone();
        return clone;
    }

    public String export(A_Exporter exporter) {
        ArrayList<A_Component> boxTree = (ArrayList<A_Component>) this.getDescendants();
        boxTree.add(0, this);
        for (List<Point3D> c : this.generateFramesAll()) {
            for (A_Component b : boxTree) {
                if (b.isRoot())
                    exporter.openComponentNode(b.getID());
                else
                    exporter.openComponentNode(b.getID(), b.getConnectorToParent().getComponentParent().getID());
                exporter.addPoint("center", c.get(0));
                exporter.addPoint("left-bottom-far", c.get(1));
                exporter.addPoint("right-bottom-far", c.get(2));
                exporter.addPoint("right-bottom-near", c.get(3));
                exporter.addPoint("left-bottom-near", c.get(4));
                exporter.addPoint("left-top-far", c.get(5));
                exporter.addPoint("right-top-far", c.get(6));
                exporter.addPoint("right-top-near", c.get(7));
                exporter.addPoint("left-top-near", c.get(8));
                exporter.closeComponentNode(b.getID());
            }
        }
        exporter.closeExport();
        return exporter.export();
    }

    public BoundingBox generateBoundingBoxAll() {
        BoundingBox BoundingBox = this.generateBoundingBoxSelf();
        for (A_Component b : this.getDescendants()) {
            BoundingBox = BoundingBox.extend(b.generateBoundingBoxSelf());
        }
        return BoundingBox;
    }

    public BoundingBox generateBoundingBoxSelf() {
        return new BoundingBox(this.getAbsoluteCenterPosition(), this.size);
    }

    public List<List<Point3D>> generateFramesAll() {
        List<List<Point3D>> frame = new ArrayList<>();
        frame.add(this.generateFrameSelf());
        for (A_Component b : this.getDescendants()) {
            frame.add(b.generateFrameSelf());
        }
        return frame;
    }

    public List<Point3D> generateFrameSelf() {
        List<Point3D> frame = new ArrayList<>();
        frame.add(this.getAbsoluteCenterPosition());
        frame.addAll(generateBoundingBoxSelf().generateCorners());
        return frame;
    }

    public Dimension3D getSize() {
        return new Dimension3D(size.getWidth(), size.getHeight(), size.getDepth());
    }

}
