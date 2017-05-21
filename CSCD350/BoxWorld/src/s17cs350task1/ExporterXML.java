package s17cs350task1;

public class ExporterXML extends A_Exporter{

    public ExporterXML(){
        append("<components>\n");
    }
    @Override
    public void addPoint(String id, Point3D point){
        append("\t\t<point id=" + id + " x=" + point.getX() + " y=" + point.getY() + " z=" + point.getZ() + "/>\n");
    }
    public void closeComponentNode(String id){
        if(isComponentClosed())
            throw new TaskException("Component Node not opened, cannot close");
        if(checkTag(id)) {
            append("\t</component>\n");
            setComponentClosed(true);
        }
        else
            throw new TaskException("Cannot close node");
    }
    @Override
    public void closeExport(){
        if(isClosed())
            throw new TaskException("Export is already closed");

        append("</components>\n");
        super.closeExport();
    }
    public void openComponentNode(String id){
        if(!isComponentClosed())
            throw new TaskException("Cannot open new Component while one is still open");
        setTag(id);
        setComponentClosed(false);
        append("\t<component id=\"" + id + "\" isRoot = true>\n");

    }
    public void openComponentNode(String id, String idParent){
        if(!isComponentClosed())
            throw new TaskException("Cannot open new Component while one is still open");
        setTag(id);
        setComponentClosed(false);
        append("\t<component id=\"" + id + "\" isRoot = \"false\"" + " parent-id=\"" + idParent + "\"\n");
    }
}
