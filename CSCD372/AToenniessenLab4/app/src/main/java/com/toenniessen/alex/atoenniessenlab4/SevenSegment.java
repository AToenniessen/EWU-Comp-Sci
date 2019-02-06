package com.toenniessen.alex.atoenniessenlab4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


public class SevenSegment extends View {
    private int mCurrentDisplay = 10;
    private final boolean[][] mSegmentState = {{true, true, true, true, true, true, false}, {false, true, true, false, false, false, false},
            {true, true, false, true, true, false, true}, {true, true, true, true, false, false, true},
            {false, true, true, false, false, true, true}, {true, false, true, true, false, true, true},
            {true, false, true, true, true, true, true}, {true, true, true, false, false, false, false},
            {true, true, true, true, true, true, true}, {true, true, true, false, false, true, true},
            {false, false, false, false, false, false, false}};
    private static final float[] mXYVerticies = {5, 5, 7, 3, 18, 3, 20, 5, 18, 7, 7, 7};
    private Path mPath = new Path();
    private Canvas mCanvas;
    private final int mOn = Color.argb(255, 255, 0, 0);
    private final int mOff = Color.argb(50, 255, 0, 0);

    public SevenSegment(Context context) {
        super(context);
    }

    public SevenSegment(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SevenSegment(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    public void setmCurrentDisplay(int n) {
        if (n < 0)
            mCurrentDisplay = 10;
        else if (n > 10)
            mCurrentDisplay = 0;
        else
            mCurrentDisplay = n;
    }

    public int getmCurrentDisplay() {
        return mCurrentDisplay;
    }
    private void updateCanvas(Canvas canvas){
        //segment one
        drawSegment(canvas, 0, 0, 0, mSegmentState[mCurrentDisplay][0]);

        //segment two
        drawSegment(canvas, 90, 15, 0, mSegmentState[mCurrentDisplay][1]);

        //segment three
        drawSegment(canvas, 90, 15, 15, mSegmentState[mCurrentDisplay][2]);

        //segment four
        drawSegment(canvas, 0, 0, 30, mSegmentState[mCurrentDisplay][3]);

        //segment five
        drawSegment(canvas, 90, 0, 15, mSegmentState[mCurrentDisplay][4]);

        //segment six
        drawSegment(canvas, 90, 0, 0, mSegmentState[mCurrentDisplay][5]);

        //segment seven
        drawSegment(canvas, 0, 0, 15, mSegmentState[mCurrentDisplay][6]);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRGB(0, 0, 0);

        mPath.moveTo(mXYVerticies[0], mXYVerticies[1]);
        mPath.lineTo(mXYVerticies[2], mXYVerticies[3]);
        mPath.lineTo(mXYVerticies[4], mXYVerticies[5]);
        mPath.lineTo(mXYVerticies[6], mXYVerticies[7]);
        mPath.lineTo(mXYVerticies[8], mXYVerticies[9]);
        mPath.lineTo(mXYVerticies[10], mXYVerticies[11]);
        mPath.close();

        int w = getWidth();
        int h = getHeight();
        canvas.scale((float) w / 25, (float) h / 40);

        updateCanvas(canvas);
        invalidate();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle saveState = (Bundle) state;

        mCurrentDisplay = (int) saveState.getSerializable("CurrentDisplay");

        state = saveState.getParcelable("instanceState");
        super.onRestoreInstanceState(state);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle saveState = new Bundle();
        saveState.putParcelable("instanceState", super.onSaveInstanceState());
        saveState.putSerializable("CurrentDisplay", mCurrentDisplay);
        return saveState;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int originalwidth = View.MeasureSpec.getSize(widthMeasureSpec);
        int originalHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        int calculatedHeight = originalwidth * 2;
        if(calculatedHeight > originalHeight){
            setMeasuredDimension(originalHeight / 2, originalHeight);
        }
        else
            setMeasuredDimension(originalwidth, calculatedHeight);
    }

    private void drawSegment(Canvas canvas, int rotation, float xTranslate, float yTranslate, boolean isOn) {
        canvas.save();
        canvas.translate(xTranslate, yTranslate);
        canvas.rotate(rotation, mXYVerticies[0], mXYVerticies[1]);
        canvas.clipPath(mPath);
        canvas.drawColor(isOn ? mOn : mOff);
        canvas.restore();
    }
}
