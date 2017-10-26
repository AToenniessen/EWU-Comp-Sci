import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class LineObject implements Serializable {
    private double x1, y1, x2, y2;
    private double mWidthCur;
    private transient Color mColorCur;
    LineObject(Point2D mS, Point2D mE, double mW, Color mC){
        x1 = mS.getX();
        y1 = mS.getY();
        x2 = mE.getX();
        y2 = mE.getY();
        mWidthCur = mW;
        mColorCur = mC;
    }
    Color getmColorCur(){
        return mColorCur;
    }
    void drawLine(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(mColorCur);
        gc.setLineWidth(mWidthCur);
        gc.strokeLine(x1, y1, x2, y2);
    }
    private void writeObject(ObjectOutputStream out)throws IOException{
        out.defaultWriteObject();
        out.writeDouble(mColorCur.getRed());
        out.writeDouble(mColorCur.getGreen());
        out.writeDouble(mColorCur.getBlue());
    }
    private void readObject(ObjectInputStream in)throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        mColorCur = new Color(in.readDouble(), in.readDouble(), in.readDouble(), 1);
    }

}
