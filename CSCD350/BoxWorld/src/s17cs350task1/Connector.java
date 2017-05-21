package s17cs350task1;

//Alexander Toenniessen

public class Connector implements Cloneable{
    private A_Component childComponent, parentComponent;
    private Point3D offsetFromParentBox;
    public Connector(A_Component childComponent, Point3D offsetFromParentBox){
        if(childComponent == null)
            throw new TaskException("Child box cannot be null when creating a connector");
        if(offsetFromParentBox == null)
            throw new TaskException("Offset from parent cannot be null when creating a connector");
        if(childComponent.isRoot())
            throw new TaskException("Cannot create Connector for root with itself as child box");
        this.childComponent = childComponent;
        this.offsetFromParentBox = offsetFromParentBox;
        this.childComponent.setConnectorToParent(this);
    }
    @Override
    public Connector clone()throws java.lang.CloneNotSupportedException{
        Connector clone = (Connector) super.clone();
        clone.offsetFromParentBox = offsetFromParentBox.clone();
        clone.parentComponent = null;
        clone.childComponent = this.childComponent.clone();
        clone.childComponent.setConnectorToParent(clone);
        return clone;
    }
    public A_Component getComponentChild(){
        return this.childComponent;
    }
    public A_Component getComponentParent(){
        if(hasComponentParent())
            return parentComponent;
        throw new TaskException("No Parent box");
    }
    public Point3D getOffsetFromParent(){
        return this.offsetFromParentBox;
    }
    public boolean hasComponentParent(){
        return this.parentComponent != null;
    }
    public void setComponentParent(A_Component parentComponent){
        if(parentComponent == null)
            throw new TaskException("Parent box to be set cannot be null");
        else if (this.hasComponentParent())
            throw new TaskException("Cannot overwrite a parent box that has already been set");
        this.parentComponent = parentComponent;
    }
}
