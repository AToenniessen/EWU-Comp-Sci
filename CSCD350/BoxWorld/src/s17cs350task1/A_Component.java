package s17cs350task1;

import java.util.*;

//Alexander Toenniessen

public abstract class A_Component implements Cloneable {
    private String id;
    private boolean Root;
    private Connector parent;
    private ArrayList<Connector> children;

    public A_Component(String id, Boolean isRoot) {
        if (id == null || id.equals("")) throw new TaskException("ID cannot be null or empty");
        this.id = id;
        this.children = new ArrayList<>();
        this.Root = isRoot;
    }

    public abstract double calculateAreaAll(BoundingBox.E_Plane p);

    public abstract double calculateAreaSelf(BoundingBox.E_Plane p);

    public abstract Point3D calculateCenterOfMassAll();

    public abstract Point3D calculateCenterOfMassSelf();

    public abstract double calculateVolumeAll();

    public abstract double calculateVolumeSelf();

    @Override
    public A_Component clone() throws java.lang.CloneNotSupportedException {
        A_Component clone = (A_Component) super.clone();
        clone.parent = null;
        clone.id = new String(this.getID());
        clone.children = new ArrayList<>();
        for (Connector c : this.children) {
            clone.connectChild(c.clone());
        }
        return clone;
    }

    public void connectChild(Connector connector) {
        if (connector == null) throw new TaskException("Connector must be valid");
        if (this.validIDs(connector)) {
            connector.setComponentParent(this);
            children.add(connector);
        } else
            throw new TaskException("Duplicate IDs present in structure");
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o instanceof A_Component) {
            A_Component that = (A_Component) o;
            return getID().equals(that.getID()) && isRoot() == that.isRoot()
                    && getChildren().equals(that.getChildren());
        } else return false;
    }

    public abstract String export(A_Exporter exporter);

    public abstract BoundingBox generateBoundingBoxAll();

    public abstract BoundingBox generateBoundingBoxSelf();

    public abstract List<List<Point3D>> generateFramesAll();

    public abstract List<Point3D> generateFrameSelf();

    public Point3D getAbsoluteCenterPosition() {
        if (!this.hasConnectorToParent() && !this.isRoot())
            throw new TaskException("Absolute center position only available for structures with a root box at position (0,0,0)");
        return this.isRoot() ? new Point3D(0, 0, 0) : this.parent.getOffsetFromParent().add(this.parent.getComponentParent().getAbsoluteCenterPosition());
    }

    public int getChildCount() {
        return getChildren().size();
    }

    public List<A_Component> getChildren() {
        List<A_Component> l = new ArrayList<>();
        for (Connector c : children)
            l.add(c.getComponentChild());
        l.sort(new BoxComparator());
        return l;
    }

    public List<Connector> getConnectorsToChildren() {
        List<Connector> temp = new ArrayList<>();
        temp.addAll(children);
        return temp;
    }

    public Connector getConnectorToParent() {
        if (!this.hasConnectorToParent())
            throw new TaskException("No connector to parent available");
        return this.parent;
    }

    public int getDescendantCount() {
        return getDescendants().size();
    }

    public List<A_Component> getDescendants() {
        List<A_Component> l = new ArrayList<>();
        l.addAll(this.getChildren());
        for (A_Component c : this.getChildren()) {
            List<A_Component> temp = c.getDescendants();
            l.addAll(temp);
        }
        l.sort(new BoxComparator());
        return l;
    }

    public String getID() {
        return this.id;
    }

    public boolean hasConnectorToParent() {
        return this.parent != null;
    }

    public boolean isRoot() {
        return this.Root;
    }

    public void setConnectorToParent(Connector connector) {
        if (connector == null)
            throw new TaskException("Connector cannot be null");
        if (this.hasConnectorToParent())
            throw new TaskException("A A_Component can only have one parent");
        this.parent = connector;
    }

    private boolean validIDs(Connector connector) {
        A_Component b = this;
        A_Component d = connector.getComponentChild();
        ArrayList<String> IDs = new ArrayList<>();
        ArrayList<String> newIDs = new ArrayList<>();
        List<A_Component> dTemp = d.getDescendants();
        for (A_Component n : dTemp) {
            newIDs.add(n.getID().toLowerCase());
        }
        while (b.hasConnectorToParent())
            b = b.parent.getComponentParent();
        List<A_Component> bTemp = b.getDescendants();
        for (A_Component n : bTemp) {
            IDs.add(n.getID().toLowerCase());
        }
        newIDs.add(connector.getComponentChild().getID().toLowerCase());
        IDs.add(b.getID().toLowerCase());

        return Collections.disjoint(IDs, newIDs);
    }

    class BoxComparator implements Comparator<A_Component> {
        public int compare(A_Component b1, A_Component b2) {
            return b1.getID().compareTo(b2.getID());
        }
    }
}