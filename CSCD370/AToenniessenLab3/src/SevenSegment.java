import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class SevenSegment extends Region {
    private Canvas mCanvas;
    private int mValue = 0;
    private boolean[] mSegment = new boolean[7];
    private static final double mWidthAspect = 22;
    private static final double mHeightAspect = 36;
    private static final double mAspectRatio = mWidthAspect/mHeightAspect;
    private static final int mMarginX = 1;
    private static final int mMarginY = 1;
    private static final Color mColorOn = Color.RED;
    private static final Color mColorOff = new Color(1, 0, 0, 0.1);
    private static final double[] mXVertex = {2, 3, 17, 18, 17, 3};
    private static final double[] mYVertex = {2, 1, 1, 2, 3, 3};
    private static final boolean[][] mSmap = {
            {true, true, false, true, true, true, true}, {false, true, false, true, false, false, false},
            {true, true, true, false, true, true, false}, {true, true, true, true, true, false, false},
            {false, true, true, true, false, false, true}, {true, false, true, true, true, false, true},
            {true, false, true, true, true, true, true}, {true, true, false, true, false, false, false},
            {true, true, true, true, true, true, true}, {true, true, true, true, true, false, true},
            {false, false, false, false, false, false, false}};

    SevenSegment(){
        init();
    }
    public SevenSegment(int n){
        if(n >= 0 && n < 10){
            mSegment = mSmap[n];
            mValue = n;
            init();
        }
    }
    public int getmValue(){
        return mValue;
    }
    public void setmValue(int n){
        if(n >= 0 && n <= 10){
            mSegment = mSmap[n];
            mValue = n;
            draw();
        }
        else{
            mValue = 0;
            mSegment = mSmap[0];
            draw();
        }
    }
    private void init(){
        mCanvas = new Canvas();
        this.setPrefSize(mWidthAspect * 70, mHeightAspect * 70);
        this.getChildren().add(mCanvas);
        mValue = 10;
        mSegment = new boolean[]{false, false, false, false, false, false, false};
    }
    @Override
    protected void layoutChildren(){
        double curWidth = this.getWidth();
        double curHeight = this.getHeight();
        double width = curHeight * mAspectRatio;
        double height = curWidth / mAspectRatio;
        if(height > curHeight){
            mCanvas.setWidth(width);
            mCanvas.setHeight(curHeight);
        }
        else{
            mCanvas.setWidth(curWidth);
            mCanvas.setHeight(height);
        }
        this.layoutInArea(mCanvas, 0, 0, this.getWidth(), this.getHeight(), this.getBaselineOffset(), HPos.CENTER, VPos.CENTER);
        draw();
    }
    private void drawSegment(GraphicsContext context, double xScale, double yScale, int segPos, double xTranslate, double yTranslate, double rotation){
        context.save();
        context.scale(xScale, yScale);
        context.translate(mMarginX, mMarginY);
        context.translate(xTranslate, yTranslate);
        context.rotate(rotation);
        context.setFill((mSegment[segPos]) ? mColorOn : mColorOff);
        context.fillPolygon(mXVertex, mYVertex, 6);
        context.restore();
    }
    void draw(){
        GraphicsContext context = mCanvas.getGraphicsContext2D();
        context.clearRect(0,0,this.getWidth(), this.getHeight());
        context.save();
        double xScale = mCanvas.getWidth()/mWidthAspect;
        double yScale = mCanvas.getHeight()/mHeightAspect;
        context.scale(xScale, yScale);
        context.translate(mMarginX, mMarginY);
        context.setFill((mSegment[0]) ? mColorOn : mColorOff);      //top segment is 0
        context.fillPolygon(mXVertex, mYVertex, 6);
        context.restore();

        drawSegment(context, xScale, yScale, 1, 20, 0, 90);         //top right segment is 1
        drawSegment(context, xScale, yScale, 2, 0, 16, 0);          //middle segment is 2
        drawSegment(context, xScale, yScale, 3, 20, 16, 90);        //bottom right segment is 3
        drawSegment(context, xScale, yScale, 4, 0, 32, 0);          //bottom segment is 4
        drawSegment(context, xScale, yScale, 5, 4, 16, 90);         //bottom left segment is 5
        drawSegment(context, xScale, yScale, 6, 4, 0, 90);          //top left segment is 6

    }
}
