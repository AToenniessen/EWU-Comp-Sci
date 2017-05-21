package s17cs350task1;

import javafx.concurrent.Task;

public abstract class A_Exporter {

    private String export = "";
    private boolean exportClosed = false;
    private boolean componentClosed = true;
    private String tagID;

    public A_Exporter(){}
    public abstract void addPoint(String id, Point3D point);
    void append(String data){
        export = export + data;
    }
    public abstract void closeComponentNode(String id);
    public void closeExport(){
        exportClosed = true;
    }
    public String export(){
        if(isClosed())
            return export;
        else
            throw new TaskException("XML representation is not closed");

    }
    public boolean isClosed(){
        return exportClosed;
    }
    public abstract void openComponentNode(String id);
    public abstract void openComponentNode(String id, String idParent);
    void validateOpen(){
        if(isClosed())
            throw new TaskException("Export stream is closed");
    }
    //personal methods
    void setComponentClosed(boolean b){
        componentClosed = b;
    }
    boolean isComponentClosed(){
        return componentClosed;
    }
    void setTag(String s){
        tagID = s;
    }
    boolean checkTag(String other){
        return tagID.equals(other);
    }
}
