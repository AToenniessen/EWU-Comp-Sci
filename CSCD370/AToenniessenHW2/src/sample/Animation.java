package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

//Could not get any scaling to work properly and dont know why

public class Animation extends Region {

    private Canvas mCanvas;
    private DoubleProperty yPos;
    private DoubleProperty minYPos;
    private DoubleProperty maxYPos;
    private final double mWidth = 22;
    private final double mHeight = 36;
    private final double mAspRatio = mWidth/mHeight;
    private Double mYPos = 0.0;



    Animation() {

        mCanvas = new Canvas();
        getChildren().add(mCanvas);
        setPrefSize(mWidth*100, mHeight*100);
        yPos = new SimpleDoubleProperty(0);
        minYPos = new SimpleDoubleProperty(0);
        maxYPos = new SimpleDoubleProperty(0);


    }
    DoubleProperty getyPos(){
        return yPos;
    }
    DoubleProperty getMinYPos(){
        return minYPos;
    }
    DoubleProperty getMaxYPos(){
        return maxYPos;
    }



    private void drawLinks(double y){
        GraphicsContext gc = mCanvas.getGraphicsContext2D();;
        double radii = y/11;
        double y2 = 0.0;
        for(int i = 0; i < 10; i++) {
            gc.strokeOval(mCanvas.getWidth()/2, y2, 25, radii * 2);
            y2+=radii;
        }
    }

    void draw(double y) {
        mYPos = y;
        double temp = y/17.5;
        GraphicsContext gc = mCanvas.getGraphicsContext2D();
        double newYPos = gc.getCanvas().getHeight()/3 + y* (mCanvas.getHeight() / (mHeight + 9));
        yPos.set(temp);
        if(temp > maxYPos.get()) {
            maxYPos.set(temp);
        }
        else if(temp < minYPos.get()){
            minYPos.set(temp);
        }
        gc.clearRect(0, 0, mCanvas.getWidth(), mCanvas.getHeight());
//        gc.save();
//        gc.scale(mCanvas.getWidth()/mWidth, mCanvas.getHeight()/mHeight);
        //gc.translate(1, 1);
        //gc.translate(1, 20);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(gc.getCanvas().getWidth()/2.65, newYPos, 150, 100);
        drawLinks(newYPos);

//        gc.restore();
    }

    @Override
    protected void layoutChildren() {

        double accWidth = this.getWidth();
        double accHeight = this.getHeight();
        double width = accHeight * mAspRatio;
        double height = accWidth / mAspRatio;
        if (height > accHeight) {
            mCanvas.setHeight(accHeight);
            mCanvas.setWidth(width);
        } else {
            mCanvas.setHeight(height);
            mCanvas.setWidth(accWidth);
        }

        this.layoutInArea(mCanvas, 0, 0, accWidth, accHeight, this.getBaselineOffset(), HPos.CENTER, VPos.CENTER);
        draw(mYPos);
    }

}
